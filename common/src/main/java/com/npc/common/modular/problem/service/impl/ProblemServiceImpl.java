package com.npc.common.modular.problem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.modular.plan.dto.PlanDto;
import com.npc.common.modular.plan.service.IPlanService;
import com.npc.common.modular.plan.vo.PlanTreeVo;
import com.npc.common.modular.problem.dto.ProblemDto;
import com.npc.common.modular.problem.entity.Problem;
import com.npc.common.modular.problem.mapper.ProblemMapper;
import com.npc.common.modular.problem.service.IProblemService;
import com.npc.common.modular.problem.vo.ProblemVO;
import com.npc.common.modular.tags.entity.Tags;
import com.npc.common.modular.tags.service.ITagsService;
import com.npc.common.monitor.server.ServerService;
import com.npc.core.utils.PicUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.npc.common.modular.problem.controller.ProblemController.LIUNX_DIR;
import static com.npc.common.modular.problem.controller.ProblemController.WIN_DIR;

/**
 * <p>
 * 问题及解决方案 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-21
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements IProblemService {

    @Autowired
    private ITagsService tagsService;
    @Autowired
    private IPlanService planService;

    private static final Logger logger = LoggerFactory.getLogger(ProblemServiceImpl.class);

    private static Map<Integer, String> TAG_MAP = new HashMap<>();
    private static Map<Integer, Tags> TAG_OBJ = new HashMap<>();
    private static List<String> PROBLEM_IMG_ID = new ArrayList<>();

    @PostConstruct
    public void loadTagsFromDB() {
        List<Tags> list = tagsService.list();
        TAG_MAP = list.stream().collect(Collectors.toMap(Tags::getId, Tags::getName));
        TAG_OBJ = list.stream().collect(Collectors.toMap(Tags::getId, tag -> tag));
        PROBLEM_IMG_ID = getProblemIdsWithImages(ServerService.IS_LINUX ? LIUNX_DIR : WIN_DIR);
        log.error("加载图片列表成功：" + PROBLEM_IMG_ID.toString());
    }

    public List<String> getProblemIdsWithImages(String fileDir) {
        try {
            log.error("获取图片的地址: " + fileDir);

            // 添加文件系统信息日志
            File baseDir = new File(fileDir);
            log.error("基础目录绝对路径: " + baseDir.getAbsolutePath());
            log.error("基础目录是否存在: " + baseDir.exists());
            log.error("是否为目录: " + baseDir.isDirectory());
            log.error("当前用户: " + System.getProperty("user.name"));
            log.error("用户主目录: " + System.getProperty("user.home"));

            // 检查父目录
            File parentDir = baseDir.getParentFile();
            if (parentDir != null) {
                log.error("父目录路径: " + parentDir.getAbsolutePath());
                log.error("父目录是否存在: " + parentDir.exists());
                log.error("父目录是否可读: " + parentDir.canRead());
                log.error("父目录是否可写: " + parentDir.canWrite());
            }

            // 检查目录权限
            log.error("基础目录是否可读: " + baseDir.canRead());
            log.error("基础目录是否可写: " + baseDir.canWrite());

            if (!baseDir.exists() || !baseDir.isDirectory()) {
                log.error("目录不存在或不是目录");
                return new ArrayList<>();
            }

            List<String> problemIds = new ArrayList<>();
            File[] problemDirs = baseDir.listFiles(File::isDirectory);

            log.error("子目录数量: " + (problemDirs != null ? problemDirs.length : 0));

            if (problemDirs != null) {
                for (File dir : problemDirs) {
                    log.warn("检查目录是否存在：" + dir.getName());
                    log.warn("子目录绝对路径: " + dir.getAbsolutePath());
                    log.warn("子目录是否可读: " + dir.canRead());
                    log.warn("子目录下文件数: " + (dir.listFiles() != null ? dir.listFiles().length : 0));
                    problemIds.add(dir.getName());
                }
            }

            return problemIds;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取有图片的问题列表异常：" + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**获取列表分页*/
    @Override
    public IPage<Problem> selectListByPage(ProblemVO problemVO) {
        // 创建分页对象
        Page<Problem> page = new Page<>(problemVO.getPageNum(), problemVO.getPageSize());

        IPage<Problem> corpusPage = this.baseMapper.getList(problemVO, page);
        return corpusPage;
    }

    @Override
    public boolean updateSolutionById(Problem problem) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id",problem.getId());
        boolean update = this.update(problem, updateWrapper);
        return update;
    }

    @Override
    public List<Problem> search(ProblemDto problem) {
        List<Problem> problemList = this.baseMapper.search(problem);
        return problemList;
    }

    @Override
    public ProblemVO translate(Problem record, List<PlanTreeVo> planTree) {
        ProblemVO vo = new ProblemVO();
        BeanUtil.copyProperties(record, vo);
        if (!ObjectUtils.isEmpty(record.getTags())) {
            String[] split = record.getTags().split(",");
            List<String> tagNameList = new ArrayList<>();
            List<Tags> tagObjList = new ArrayList<>();
            for (String tag : split) {
                tagNameList.add(TAG_MAP.get(Integer.valueOf(tag)));
                tagObjList.add(TAG_OBJ.get(Integer.valueOf(tag)));
            }
            vo.setTagName(String.join(",", tagNameList));
            vo.setTagList(tagObjList);
        } else {
            vo.setTagName("");
            vo.setTagList(new ArrayList<>());
        }
        vo.setHaveImg(PROBLEM_IMG_ID.contains(record.getId().toString()));
        if (ObjectUtils.isEmpty(planTree)) {
            PlanDto planDto = new PlanDto();
            planDto.setId(0);
            planDto.setLevel(5);
            planTree = planService.getTree(planDto);
        }
        vo.setPlanText(buildPlanPath(planTree, record.getPlanId()));
        return vo;
    }

    @Override
    public List<ProblemVO> translate(List<Problem> records) {
        List<ProblemVO> res = new ArrayList<>();
        PlanDto planDto = new PlanDto();
        planDto.setId(0);
        planDto.setLevel(5);
        List<PlanTreeVo> planTree = planService.getTree(planDto);
        for (Problem record : records) {
            res.add(translate(record, planTree));
        }
        return res;
    }

    // 新增辅助方法
    private String buildPlanPath(List<PlanTreeVo> planTree, Integer targetPlanId) {
        if (ObjectUtils.isEmpty(planTree) || targetPlanId == null) {
            return "";
        }

        // 查找目标节点并构建路径
        List<String> path = findPath(planTree, targetPlanId, new ArrayList<>());
        if (!path.isEmpty()) {
            return String.join("->", path);
        }
        return "";
    }

    private List<String> findPath(List<PlanTreeVo> nodes, Integer targetId, List<String> currentPath) {
        if (ObjectUtils.isEmpty(nodes) || targetId == null) {
            return Collections.emptyList();
        }

        for (PlanTreeVo node : nodes) {
            List<String> newPath = new ArrayList<>(currentPath);
            newPath.add(node.getContent());

            if (targetId.equals(node.getId())) {
                return newPath;
            }

            List<String> result = findPath(node.getChildren(), targetId, newPath);
            if (!result.isEmpty()) {
                return result;
            }
        }

        return Collections.emptyList();
    }
}
