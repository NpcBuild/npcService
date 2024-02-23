package com.npc.common.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.file.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-04-08
 */
@Mapper
public interface VideoMapper extends BaseMapper<Video> {
    
	/**
     * 通过  的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<Video> getVideoListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);

}
