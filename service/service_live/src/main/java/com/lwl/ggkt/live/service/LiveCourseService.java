package com.lwl.ggkt.live.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lwl.ggkt.model.live.LiveCourse;
import com.lwl.ggkt.vo.live.LiveCourseConfigVo;
import com.lwl.ggkt.vo.live.LiveCourseFormVo;
import com.lwl.ggkt.vo.live.LiveCourseVo;

import java.util.List;
import java.util.Map;

/**
* @author user-lwl
* @description 针对表【live_course(直播课程表)】的数据库操作Service
* @createDate 2022-12-17 10:31:22
*/
public interface LiveCourseService extends IService<LiveCourse> {

    /**
     * 直播课程分页查询
     * @param pageParam pageParam
     * @return 分页列表
     */
    IPage<LiveCourse> selectPage(Page<LiveCourse> pageParam);

    /**
     * 添加直播课程
     * @param liveCourseFormVo liveCourseFormVo
     */
    void saveLive(LiveCourseFormVo liveCourseFormVo);

    /**
     * 删除直播课程
     * @param id id
     */
    void removeLive(Long id);

    /**
     * 查询直播课程详细信息(包括描述信息)
     * @param id id
     * @return 直播课程详细信息
     */
    LiveCourseFormVo getLiveCourseFormVo(Long id);

    /**
     * 修改直播课程信息
     * @param liveCourseFormVo liveCourseFormVo
     */
    void updateLiveById(LiveCourseFormVo liveCourseFormVo);

    /**
     * 查看配置信息
     * @param id id
     * @return 配置信息
     */
    LiveCourseConfigVo getCourseConfig(Long id);

    /**
     * 修改直播配置
     * @param liveCourseConfigVo liveCourseConfigVo
     */
    void updateConfig(LiveCourseConfigVo liveCourseConfigVo);

    /**
     * 获取最近的直播课程
     * @return 最近的直播课程
     */
    List<LiveCourseVo> findLatelyList();

    /**
     * 获取用户access_token
     * @param id id
     * @param userId userId
     * @return JSONObject
     */
    JSONObject getAccessToken(Long id, Long userId);

    /**
     * 根据ID查询课程
     * @param courseId 课程id
     * @return 课程信息
     */
    Map<String, Object> getInfoById(Long courseId);
}
