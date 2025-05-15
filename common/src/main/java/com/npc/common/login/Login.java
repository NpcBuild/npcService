package com.npc.common.login;

import cn.hutool.core.img.ImgUtil;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.maxmind.geoip2.model.CityResponse;
import com.npc.client.Netty.service.PushMsgService;
import com.npc.common.login.service.LoginService;
import com.npc.common.modular.user.entity.User;
import com.npc.common.modular.user.model.parame.UserParame;
import com.npc.common.modular.user.model.result.UserResult;
import com.npc.common.modular.user.service.UserService;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import com.npc.core.alarm.MessageTypes;
import com.npc.core.alarm.aop.Alarm;
import com.npc.core.jwt.JwtFilter;
import com.npc.core.jwt.JwtTokenUtil;
import com.npc.core.jwt.UserToken;
import com.npc.core.jwt.UserTokenInfo;
import com.npc.core.utils.QRCodeUtils;
import com.npc.core.utils.StringUtils;
import com.npc.core.utils.ip.IpUtils;
import com.npc.exception.YFLoginTimeoutException;
import com.npc.kafka.producer.KafkaLoginMailProducer;
import com.npc.redis.aop.login.anno.RedisLimit;
import com.npc.redis.utils.RedisPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author wow
 * @createTime 2022/9/20 23:08
 * @descripttion 用户登录
 */
@Slf4j
@CrossOrigin
@RestController
public class Login {
    @Value("${server.port}")
    private int port ;
    @Resource
    private UserService userService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private KafkaLoginMailProducer kafkaLoginMailProducer;
    @Autowired
    PushMsgService pushMsgService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Alarm(title = "登录业务告警", messageType = MessageTypes.TEXT, templateId = "errorTemp")
    @RedisLimit(identifier = "account", watch = 30, lock = 600, times = 10)
    @PostMapping("/login")
    public ServerResponseVO login(@RequestBody User user, HttpServletRequest request) {
        Subject userSubject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getUserPwd());
        try {
            // 登录验证
            userSubject.login(token);
            // 获取用户IP地址
            String userIpAddress = IpUtils.getIpAddr(request);
            Boolean flag = loginService.recordIpToRedis(userIpAddress, user.getAccount());
            System.out.println("存储结果：" + flag);

//            KafkaMessage kafkaMessage = new KafkaMessage(0,"login","登录成功");
//            kafkaLoginMailProducer.sendMsgSync(kafkaMessage);
            // 获取当前用户的 Subject
            Subject currentUser = SecurityUtils.getSubject();
            UserResult userInfo = new UserResult();
            if (currentUser.isAuthenticated()) {
                userInfo = (UserResult)currentUser.getPrincipal();
            } else {
                userInfo.setId(1);
            }
            UserTokenInfo userTokenInfo = new UserTokenInfo();
            userTokenInfo.setUserId(Long.valueOf(userInfo.getId()));
            userTokenInfo.setUserName(user.getAccount());
//            userTokenInfo.setRealName(user.getAccount());
            // 生成Token
            UserToken userToken = jwtTokenUtil.createToekns(userTokenInfo);
            return ServerResponseVO.success("登录成功", userToken);
        }catch (UnknownAccountException e) {
            return ServerResponseVO.error(ServerResponseEnum.ACCOUNT_NOT_EXIST);
        }catch (DisabledAccountException e) {
            return ServerResponseVO.error(ServerResponseEnum.ACCOUNT_IS_DISABLED);
        }catch (IncorrectCredentialsException e) {
            throw e;
//            return ServerResponseVO.error(ServerResponseEnum.INCORRECT_CREDENTIALS);
        }catch (Throwable e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.ERROR);
        }
    }

    @PostMapping("/regist")
    public ServerResponseVO registe(@RequestBody UserParame user){
        UserResult existUser = userService.findByAccount(user.getAccount());
        if (existUser != null){
            return ServerResponseVO.error(ServerResponseEnum.DUPLICATE_ACCOUNT);
        } else {
            userService.regist(user);
        }
        return ServerResponseVO.success("注册成功");
    }

    /**
     * 判断token是否有效
     * @param token 令牌
     * @param response 响应
     * @return 判断结果
     */
    @PostMapping("/isValidToken/{token}")
    public ServerResponseVO isValidToken(@PathVariable("token") String token, HttpServletResponse response) {
        if (jwtTokenUtil.isTokenExpired(token)) {
            return ServerResponseVO.error(ServerResponseEnum.TOKEN_INVALID);
        }
        if (jwtTokenUtil.checkBlacklist(token)) {
            return ServerResponseVO.error(ServerResponseEnum.TOKEN_INVALID);
        }
        return ServerResponseVO.success(true);
    }

    /**
     * 刷新令牌
     * 黑名单的清理： 你需要定期清理黑名单中过期的令牌，以防止黑名单无限增长，导致内存或存储问题。你可以使用一个定时任务来清理过期的令牌。
     * 并发问题： 如果你的应用程序是多线程的，确保在多个线程中对黑名单进行访问时没有竞争条件，否则可能会导致问题。
     * 令牌的存储和检索： 确保你有一种有效的方式来存储和检索令牌，以便在需要时能够快速查找并验证令牌是否在黑名单中。
     * 对刷新令牌的安全处理： 刷新令牌通常比访问令牌更长时间有效，因此需要更加谨慎地处理。确保刷新令牌只能在安全的环境中使用，并且在刷新过程中生成新的访问令牌。
     *
     * @param refreshToken
     * @return
     */
    @PostMapping("/refreshToken/{refreshToken}")
    public ServerResponseVO refreshToken(@PathVariable("refreshToken") String refreshToken, HttpServletResponse response) {
        try {

        // 判断token是否超时
        if (jwtTokenUtil.isTokenExpired(refreshToken)) {
            return ServerResponseVO.error(ServerResponseEnum.TOKEN_INVALID);
        }

            // 刷新令牌 放入黑名单
        jwtTokenUtil.addBlacklist(refreshToken, jwtTokenUtil.getExpirationDate(refreshToken));
//        // 访问令牌 放入黑名单
//        String odlAccessToken = jwtTokenUtil.getAccessTokenByRefresh(refreshToken);
//        if (!StringUtils.isEmpty(odlAccessToken)) {
//            jwtTokenUtil.addBlacklist(odlAccessToken, jwtTokenUtil.getExpirationDate(odlAccessToken));
//        }

            // 生成新的 访问令牌 和 刷新令牌
            UserTokenInfo userInfoToken = jwtTokenUtil.getUserInfoToken(refreshToken);
            // 生成Token
            UserToken userToken = jwtTokenUtil.createToekns(userInfoToken);
            return ServerResponseVO.success(userToken);
        } catch (YFLoginTimeoutException e) {
            return ServerResponseVO.error(ServerResponseEnum.TOKEN_INVALID);
        }
    }


    /**
     * 登出
     *
     * @return
     */
    @PostMapping("/logOut/{token}")
    public ServerResponseVO logOut(@PathVariable("token") String token) {
        // 放入黑名单
        jwtTokenUtil.addBlacklist(token, jwtTokenUtil.getExpirationDate(token));
        return ServerResponseVO.success();
    }

    /**
     * 注销
     *
     * @return
     */
    @PostMapping("/logOff/{token}")
    public ServerResponseVO logOff(@PathVariable("token") String token) {
        // 修改用户状态
        //TODO 涉及到业务，这里不在阐述

        // 放入黑名单
        jwtTokenUtil.addBlacklist(token, jwtTokenUtil.getExpirationDate(token));

        return ServerResponseVO.success();
    }

    /**
     * 生成登录字节流
     * @return
     * @throws IOException
     */
    @GetMapping("/qrCode")
    public ServerResponseVO qrCode(String userId) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String ip = IpUtils.getHostIp();
        System.out.println("ip地址："+ip);

        Map<String,Object> data = new HashMap<>();
        String pref = "http://" + ip + ":" + port + "/";
        data.put("redirect",pref + "");
        data.put("subscribe",pref + "subscribe?uuid=" + uuid);

//        String qrUrl = pref + "scan?uuid=" +uuid;
        String qrUrl = pref + "loginBind?uuid=" +uuid + "&userId=" + userId;
        System.out.println(qrUrl);

        Map<String ,String> param = new HashMap<>();
        param.put("qrUrl",qrUrl);
        //获取一个二维码图片
        BitMatrix bitMatrix = QRCodeUtils.createCode(param);

        //字节数组输出流
        ByteArrayOutputStream imageOut = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "jpg", imageOut);

        Map res = new HashMap();
        res.put("uuid",uuid);
        res.put("img",imageOut.toByteArray());
        return ServerResponseVO.success(res);
    }

    /**
     * 生成图片（能传递参数uuid给PC端）
     * @param request
     * @param response
     */
    @GetMapping("/loginQrCode")
    public void qrCode(HttpServletRequest request,HttpServletResponse response){
        String uuid = UUID.randomUUID().toString();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        try {
            response.setHeader("uuid", uuid);
            Map<String, String> content = new HashMap<>();
            String ip = IpUtils.getHostIp();
            System.out.println("ip地址："+ip);
            String pref = "http://" + ip + ":" + port + "/";
            String qrUrl = pref + "scan?uuid=" +uuid;
            System.out.println(qrUrl);
            content.put("qrUrl",qrUrl);
            final BufferedImage image = QRCodeUtils.createImage(content,"");
            ImgUtil.write(image,"jpg",response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/loginBind")
    public ServerResponseVO bindUserIdAndToken(String uuid,String userId, HttpServletRequest request) {
        User user = userService.getById(userId);
        ServerResponseVO vo = login(user,request);
        if (null != user) {
            pushMsgService.pushMsgToOne(uuid, vo);
        }
        return vo;
    }
}
