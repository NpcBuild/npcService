package com.npc.common.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.file.entity.FileSlice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-05-05
 */
@Mapper
public interface FileSliceMapper extends BaseMapper<FileSlice> {
    
	/**
     * 通过  的某一列, 查询在该列所有包含Ids 的数据
     * @param column 列名
     * @param ids id 集合
     * @return
     */
    List<FileSlice> getFileSliceListByColList(@Param("column") String column, @Param("ids") List<Integer> ids);
    int selectChunkCount(FileSlice fileSlice);
    int selectUploadedChunks(@Param("md5") String md5);
    List<FileSlice> selectChunksByMd5(@Param("md5") String md5);

}
