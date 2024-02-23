package com.npc.common.modular.file.mapper;

import com.npc.common.modular.file.entity.Files;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 * 缓存文件 Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-08-05
 */
@Mapper
public interface FileMapper extends BaseMapper<Files> {
    
	/**
     * 通过 缓存文件 的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Files> getFileListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
