package com.npc.core.utils.lambda;

import com.npc.common.utils.SpringUtils;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.SerializedLambda;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: npcService
 * @description 日志注解，能打日志
 * @author: feiyang
 * @create: 2025/12/04 20:50
 **/
@Slf4j
public class ServiceManager {
    // 缓存初始化大小
    private static final int INIT_COUNT = 6666;
    // 缓存Lambda对应的Service信息，key是Lambda，value是Service元数据
    private static final Map<SerialBiFunction<?,?,?>, LambdaMeta<?>> CACHE_LAMBDA;
    // 静态代码块，项目启动时就初始化缓存
    static {
        CACHE_LAMBDA = new ConcurrentHashMap<>(INIT_COUNT);
    }
    // 对外提供的调用方法：传Lambda（比如UserService::queryUser）和参数，返回结果
    @SuppressWarnings("unchecked")
    public static <T,U,R> ServerResponseVO<R> call(SerialBiFunction<T,U,R> fn, U param){
        // 先检查：Lambda不能传空
        if (fn == null) {
            return ServerResponseVO.error(ServerResponseEnum.INTERNAL_SERVER_ERROR.getCode(),"服务函数不能为空！");
        }
        // 1. 从缓存拿Service信息：有就直接用，没有就解析并缓存
        LambdaMeta<T> lambdaMeta = (LambdaMeta<T>) CACHE_LAMBDA.computeIfAbsent(fn, k-> {
            // 解析Lambda，拿到Service实例、类名这些信息
            LambdaMeta<T> meta = parseSerialFunction(fn);
            log.debug("缓存Service信息：{}", meta.getServiceName());
            return meta;
        });
        // 2. 创建执行器，把Lambda、参数、Service信息传进去
        ServiceExecutor<T,U,R> executor = InstBuilder.of(ServiceExecutor.class)
                .set(ServiceExecutor::setServiceFn, fn)    // 传Lambda方法
                .set(ServiceExecutor::setParam, param)      // 传参数
                .set(ServiceExecutor::setLambdaMeta, lambdaMeta)  // 传Service信息
                .build();  // 构建执行器
        // 3. 执行方法，返回结果
        return executor.callService();
    }
    // 解析Lambda：从Lambda里拿到Service类名、实例、方法名
    @SuppressWarnings("unchecked")
    private static <T, U, R> LambdaMeta<T> parseSerialFunction(SerialBiFunction<T,U,R> fn) {
        // 用LambdaUtil拿到Lambda的元数据
        SerializedLambda lambda = LambdaUtil.valueOf(fn);
        // 封装Service信息的对象
        LambdaMeta<T> lambdaMeta = new LambdaMeta<>();
        // 1. 解析Service类名：Lambda里的类名是“com/example/UserService”，要改成“com.example.UserService”
        String tClassName = lambda.getImplClass().replaceAll("/", ".");
        try {
            // 2. 拿到Service的Class对象（比如UserService.class）
            Class<T> aClass = (Class<T>) Class.forName(tClassName);
            // 3. 从Spring里拿Service实例（不用@Autowired就是靠这行）
            T inst = SpringUtils.getBean(aClass);
            // 4. 把信息存到lambdaMeta里
            lambdaMeta.setClazz(aClass);    // 存Service的Class
            lambdaMeta.setInst(inst);       // 存Service实例
            lambdaMeta.setServiceName(lambda.getImplMethodName());  // 存方法名（比如queryUser）
        } catch (ClassNotFoundException e) {
            // 找不到类就抛异常
            throw new RuntimeException("没找到Service类：" + tClassName, e);
        }
        return lambdaMeta;
    }
    // 封装Service信息的内部类：存Class、实例、方法名
    @lombok.Data
    public static class LambdaMeta<T> {
        private Class<T> clazz;          // Service的Class（比如UserService.class）
        private T inst;                  // Service实例（Spring里的Bean）
        private String serviceName;      // 方法名（比如queryUser）
    }
}

/* 使用示例：
优点：
    1.不用再写 @Autowired： Controller 里干干净净，再也不用注入一堆 Service；
    2.统一日志 / 异常： 想改日志格式、加权限校验，只需要改 ServiceExecutor，不用改每个方法；
    3.缓存优化： 解析过的 Service 信息会缓存，下次调用更快；
    4.类型安全： 写 Lambda 的时候，方法名错了编译就报错，不用等到运行才发现。
避坑：
    1.多实现类的情况： 如果一个接口有多个实现（比如 UserService 有 UserServiceImpl1 和 UserServiceImpl2），需要在 SpringUtil 里加按名称拿 Bean 的方法
    2.Service 要加 @Service： Spring 才能扫描到，不然 SpringUtils 拿不到实例

评价：
    1.把spring做的事,在spring上又重复了一部分
    2.官方推荐构造函数注入，以避免不必要的BUG，这个简直多此一举
    3.封装越多 效率越低
    4.没啥用，相当于自己写了套bean工厂缓存而已，浪费内存不说，调用还走反射，就为了少写那几个注入… 注入太多本身是代码设计不合理导致的，好好提升编程思维才是正道
    5.这种不注入调用的方式，如果有循环依赖的错误，会不会运行时才会报错？或者这种方式会不会有循环依赖的问题？
    6.@Transactional事务的解决？事务一般都是service层处理，这玩意就单纯切断了controller与service层的依赖注入，正常来说，controller层压根就没有什么业务处理，顶多做个参数校验，反正我觉得挺多此一举的


使用示例：
@Service
public class UserService{
 // 查用户：根据ID查
 public UserDTO queryUser(Long userId){
   // 这里模拟查数据库，实际项目里换JDBC/MyBatis
    UserDTO user =newUserDTO();
    user.setUserId(userId);
    user.setUserName("张三");
    user.setAge(25);
   return user;
  }
 // 更新用户：传ID和更新参数
 public Boolean updateUser(Long userId, UserUpdateDTO updateDTO){
   // 这里模拟更新数据库
    log.info("更新用户{}的信息：{}", userId, updateDTO);
   return true; // 返回更新成功
  }
}

@RestController
@RequestMapping("/user")
public class UserController {
    // 查用户：不用注入UserService！一行搞定
    @GetMapping("/{userId}")
    public SerResult<UserDTO> getUser(@PathVariable Long userId) {
        // 直接传Lambda（UserService::queryUser）和参数（userId）
        return ServiceManager.call(UserService::queryUser, userId);
    }
    // 更新用户：同样不用注入
    @PutMapping("/{userId}")
    public SerResult<Boolean> updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateDTO updateDTO) {
        // 这里要注意：因为updateUser有两个参数，所以要显式指定Lambda类型
        return ServiceManager.call(
                (UserService service, UserUpdateDTO dto) -> service.updateUser(userId, dto),
                updateDTO
        );
    }
}
*/
