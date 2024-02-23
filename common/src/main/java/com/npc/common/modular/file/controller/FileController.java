//package com.npc.common.modular.file.controller;
//import com.npc.core.ServerResponseVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//import com.npc.common.modular.file.service.IFileService;
//import com.npc.common.modular.file.entity.File;
//
///**
// * <p>
// * 缓存文件 前端控制器
// * </p>
// *
// * @author yangfei
// * @since 2023-08-05
// */
//@RestController
//@RequestMapping("/file")
//public class FileController {
//
//    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
//
//    @Autowired
//    public IFileService fileService;
//
//
//    /**
//     * 保存、修改 【区分id即可】
//     * @param file 传递的实体
//     * @return ServerResponseVO转换结果
//     */
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public ServerResponseVO<?> save(@RequestBody @Validated File file) {
//        File obj = fileService.save(file);
//        return ServerResponseVO.success(obj);
//    }
//
//
//    /**
//     * 通过Id 删除对象
//     * @param id 要删除的实体
//     * @return ServerResponseVO转换结果
//     */
//    @GetMapping("deleteFileById")
//    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {
//        File file =fileService.deleteFileById(id);
//        return ServerResponseVO.success(file);
//    }
//
//    /**
//     * 批量删除 缓存文件
//     * @param ids
//     * @return
//     */
//    @GetMapping("batchDeleteFileByIdList")
//    @Transactional(rollbackFor = Exception.class)
//    public ServerResponseVO<?> batchDeleteFileByIdList(@RequestParam("ids") Integer[] ids) {
//        fileService.batchDeleteFileByIdList(ids);
//        return ServerResponseVO.success("批量删除成功");
//    }
//
//
//    /**
//     * 通过Id 获取对象
//     * @param id
//     * @return ServerResponseVO转换结果
//     */
//    @RequestMapping(value = "/getFileById", method = RequestMethod.GET)
//    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
//        File file =fileService.selectById(id);
//        return ServerResponseVO.success(file);
//    }
//
//
////    /**
////     * 分页查询数据：
////     * @param fileDto 查询对象
////     * @return PageList 分页对象
////     */
////    @RequestMapping(value = "/getFileList", method = RequestMethod.GET)
////    public ServerResponseVO<?> getFileList(@Validated FileDto fileDto) {
////        Page<File> page = fileService.getFileList(fileDto);
////        return new ServerResponseVO<>(page.getTotal(), page.getRecords());
////    }
//}
