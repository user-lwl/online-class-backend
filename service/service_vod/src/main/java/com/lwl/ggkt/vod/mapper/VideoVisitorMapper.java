package com.lwl.ggkt.vod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwl.ggkt.model.vod.VideoVisitor;
import com.lwl.ggkt.vo.vod.VideoVisitorCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author user-lwl
* @description 针对表【video_visitor(视频来访者记录表)】的数据库操作Mapper
* @createDate 2022-12-02 13:37:30
*/
public interface VideoVisitorMapper extends BaseMapper<VideoVisitor> {

    /**
     * 课程统计接口
     * @param courseId 课程id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 统计结果
     */
    List<VideoVisitorCountVo> findCount(@Param("courseId") Long courseId,
                                        @Param("startDate") String startDate,
                                        @Param("endDate") String endDate);
}




