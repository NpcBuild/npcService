package com.npc.common.modular.problem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.net.HttpHeaders;
import com.npc.common.modular.problem.dto.ProblemDto;
import com.npc.common.modular.problem.dto.UploadDto;
import com.npc.common.modular.problem.entity.Problem;
import com.npc.common.modular.problem.service.IProblemService;
import com.npc.common.modular.problem.vo.ProblemVO;
import com.npc.common.modular.tags.mapper.TagsMapper;
import com.npc.common.monitor.server.ServerService;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import com.npc.core.constant.Constants;
import com.npc.core.encrypt.base64.ImageUtil;
import com.npc.core.utils.FileUtils;
import com.npc.core.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * <p>
 * 问题及解决方案 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2023-12-21
 */
@RestController
@RequestMapping("/problem")
public class ProblemController {
    
    private static final Logger logger = LoggerFactory.getLogger(ProblemController.class);

    private static final String WIN_DIR = "C:\\Users\\NPC\\Pictures\\problem\\";
    private static final String LIUNX_DIR = "/home/npc/Desktop/problem/";

    @Autowired
    public IProblemService problemService;
    @Resource
    private TagsMapper tagsMapper;


    /**
     * 保存、修改 【区分id即可】
     * @param problem 传递的实体
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated Problem problem) {
        try {
            boolean obj = problemService.saveOrUpdate(problem);
            return ServerResponseVO.success(problem);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.SAVE_FAILED);
        }
    }


    /**
     * 更新内容
     * @param problem 传递的实体
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ServerResponseVO<?> update(@RequestBody @Validated Problem problem) {
        try {
            boolean obj = problemService.updateSolutionById(problem);
            return ServerResponseVO.success(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.UPDATE_FAILED);
        }
    }


    /**
     * 上传图片
     * @param files 传递的实体
     * @return ResponseDataModel转换结果
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ServerResponseVO<?> upload(UploadDto uploadDto) {
        try {
            List<MultipartFile> files = uploadDto.getPic();
            if (CollectionUtils.isEmpty(files) && ObjectUtils.isEmpty(uploadDto.getFile1())) {
                return ServerResponseVO.error(ServerResponseEnum.SAVE_FAILED);
            }
            String fileDir = ServerService.IS_LINUX?LIUNX_DIR:WIN_DIR;
            String dir = fileDir + uploadDto.getId();
            File dirFile = new File(dir);
            if (dirFile != null && !dirFile.exists()) {
                boolean created = dirFile.mkdirs(); // 创建父目录
                if (!created) {
                    throw new IOException("无法创建目录：" + dirFile.getAbsolutePath());
                }
            }
            File parentDir = dirFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean created = parentDir.mkdirs(); // 创建父目录
                if (!created) {
                    throw new IOException("无法创建目录：" + parentDir.getAbsolutePath());
                }
            }
            if (ObjectUtils.isEmpty(files)) {
                files = new ArrayList<>();
                files.add(uploadDto.getFile1());
                files.add(uploadDto.getFile2());
                files.add(uploadDto.getFile3());
                files.add(uploadDto.getFile4());
                files.add(uploadDto.getFile5());
                files.add(uploadDto.getFile6());
                files.add(uploadDto.getFile7());
                files.add(uploadDto.getFile8());
                files.add(uploadDto.getFile9());
            }
            int noNullCount = 0;
            for (MultipartFile file : files) {
                if (!ObjectUtils.isEmpty(file)) {
                    noNullCount++;
                }
            }
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                if (ObjectUtils.isEmpty(file)) {
                    continue;
                }
                String suffix = FileUtils.getFileSuffix(file);
                String fileName = StringUtils.isEmpty(uploadDto.getFileName()) ? "" : uploadDto.getFileName();
                String imgFileName = StringUtils.isEmpty(fileName) ? file.getOriginalFilename() : (fileName + "(" + (i+1) +")" +  "." + suffix);
                if (noNullCount == 1) {
                    imgFileName = StringUtils.isEmpty(fileName) ? file.getOriginalFilename() : (fileName +  "." + suffix);
                }
                Path filePath = Paths.get(dir + (ServerService.IS_LINUX? Constants.LIUNX_SP:Constants.WIN_SP), imgFileName);
                // 保存文件到服务器
                Files.copy(file.getInputStream(), filePath);
            }
            return ServerResponseVO.success("成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error:" + e.getMessage());
            return ServerResponseVO.error(ServerResponseEnum.SAVE_FAILED);
        }
    }

    /**
     * 获取图片列表
     * @param id 问题ID
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/picList", method = RequestMethod.GET)
    public ServerResponseVO<?> picList(@RequestParam("id") String id) {
        try {
            String fileDir = ServerService.IS_LINUX?LIUNX_DIR:WIN_DIR;
            String dir = fileDir + id;
            File all = new File(dir);
            if (!all.exists()) {
                return ServerResponseVO.success(new ArrayList<>());
            }
            List<String> allPic = new ArrayList<>();
            // 递归遍历所有文件和文件夹
            getAllImageFiles(all, allPic);
            return ServerResponseVO.success(allPic);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("异常：" + e.getMessage());
            return ServerResponseVO.error(ServerResponseEnum.DATA_EXCEPTION);
        }
    }

    /**
     * 递归获取文件夹中的所有图片文件
     * @param directory 文件夹
     * @param imageFiles 图片文件列表
     */
    private void getAllImageFiles(File directory, List<String> imageFiles) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归处理子文件夹
                    getAllImageFiles(file, imageFiles);
                } else {
                    // 处理文件，检查是否为图片文件
                    String fileName = file.getName().toLowerCase();
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                            fileName.endsWith(".png") || fileName.endsWith(".gif") ||
                            fileName.endsWith(".bmp")) {
                        imageFiles.add(ImageUtil.convertImageToBase64Str(file.getAbsolutePath()));
                    }
                }
            }
        }
    }

    /**
     * 获取图片列表
     * @param id 问题ID
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/picListName", method = RequestMethod.GET)
    public ServerResponseVO<?> picListName(@RequestParam("id") String id) {
        try {
            String fileDir = ServerService.IS_LINUX?LIUNX_DIR:WIN_DIR;
            String dir = fileDir + id;
            File all = new File(dir);
            if (!all.exists()) {
                return ServerResponseVO.success(null);
            }
            String files = "";
            for (File file : all.listFiles()) {
                files += file.getName() + "=" + file.getName() + "&";
            }
            return ServerResponseVO.success(files.length()>0?files.substring(0,files.length()-1):files);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("异常：" + e.getMessage());
            return ServerResponseVO.error(ServerResponseEnum.DATA_EXCEPTION);
        }
    }

    @GetMapping("/download/{id}/{fileName:.+}")
    public ResponseEntity<?> downloadPic(@PathVariable String id, @PathVariable String fileName, HttpServletResponse response) throws IOException {
        String filePath = (ServerService.IS_LINUX?LIUNX_DIR:WIN_DIR) + id + (ServerService.IS_LINUX? Constants.LIUNX_SP:Constants.WIN_SP) + fileName;
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found " + fileName);
        }
        String contentType = null;
        try {
            contentType = Files.probeContentType(path);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileName + "\"").body(new UrlResource(path.toUri()));
    }


    /**
     * 通过Id 删除对象
     * @param id 要删除的实体
     * @return ResponseDataModel转换结果
     */
    @GetMapping("deleteProblemById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            boolean problem =problemService.removeById(id);
            return ServerResponseVO.success(problem);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 问题及解决方案
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteProblemByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteProblemByIdList(@RequestParam("ids") Integer[] ids) {

        problemService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ResponseDataModel转换结果
     */
    @RequestMapping(value = "/getProblemById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Problem problem =problemService.getById(id);
        ProblemVO vo = new ProblemVO();
        try {
            BeanUtils.copyProperties(vo, problem);
        } catch (Exception e) {
            return ServerResponseVO.success(problem);
        }
        if (!ObjectUtils.isEmpty(problem.getTags())) {
            List tagNameList = new ArrayList<>();
            for (String tagId : problem.getTags().split(",")) {
                tagNameList.add(tagsMapper.getTagName(Integer.valueOf(tagId)));
            }
            vo.setTagName(StringUtils.join(tagNameList, ","));
        }
        return ServerResponseVO.success(vo);
    }

    /**
     * 搜索问题（普通搜索、ES搜索）
     * @param problem
     * @return
     */
    @GetMapping("/search")
    public ServerResponseVO<?> search(ProblemDto problem) {
        List<Problem> problems = problemService.search(problem);
        List<ProblemVO> translate = problemService.translate(problems);
        return ServerResponseVO.success(translate);
    }


    /**
     * 分页查询数据：
     * @param problemVO 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getProblemList", method = RequestMethod.GET)
    public ServerResponseVO<?> getProblemList(@Validated ProblemVO problemVO) {
        IPage<Problem> page = problemService.selectListByPage(problemVO);
        List<Problem> records = page.getRecords();
        IPage<ProblemVO> res = new Page<>();
        res.setCurrent(page.getCurrent());
        res.setPages(page.getPages());
        res.setRecords(problemService.translate(records));
        res.setSize(page.getSize());
        res.setTotal(page.getTotal());
        return ServerResponseVO.success(res);
    }
}
