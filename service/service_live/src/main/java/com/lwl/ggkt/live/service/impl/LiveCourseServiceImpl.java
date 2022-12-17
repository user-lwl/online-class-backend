package com.lwl.ggkt.live.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.client.course.CourseFeignClient;
import com.lwl.ggkt.client.user.UserInfoFeignClient;
import com.lwl.ggkt.exception.GgktException;
import com.lwl.ggkt.live.mapper.LiveCourseMapper;
import com.lwl.ggkt.live.mtcloud.CommonResult;
import com.lwl.ggkt.live.mtcloud.MTCloud;
import com.lwl.ggkt.live.service.*;
import com.lwl.ggkt.model.live.*;
import com.lwl.ggkt.model.user.UserInfo;
import com.lwl.ggkt.model.vod.Teacher;
import com.lwl.ggkt.utils.DateUtil;
import com.lwl.ggkt.vo.live.LiveCourseConfigVo;
import com.lwl.ggkt.vo.live.LiveCourseFormVo;
import com.lwl.ggkt.vo.live.LiveCourseGoodsView;
import com.lwl.ggkt.vo.live.LiveCourseVo;
import lombok.SneakyThrows;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
* @author user-lwl
* @description 针对表【live_course(直播课程表)】的数据库操作Service实现
* @createDate 2022-12-17 10:31:22
*/
@Service
public class LiveCourseServiceImpl extends ServiceImpl<LiveCourseMapper, LiveCourse>
    implements LiveCourseService {
    @Resource
    private CourseFeignClient courseFeignClient;

    @Resource
    private LiveCourseMapper liveCourseMapper;

    @Resource
    private MTCloud mtCloudClient;

    @Resource
    private LiveCourseDescriptionService liveCourseDescriptionService;

    @Resource
    private LiveCourseAccountService liveCourseAccountService;

    @Resource
    private LiveCourseConfigService liveCourseConfigService;

    @Resource
    private LiveCourseGoodsService liveCourseGoodsService;

    @Resource
    private UserInfoFeignClient userInfoFeignClient;

    public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    public static final String YMD = "yyyy年MM月dd HH:mm";

    /**
     * 直播课程分页查询
     * @param pageParam pageParam
     * @return 分页列表
     */
    @Override
    public IPage<LiveCourse> selectPage(Page<LiveCourse> pageParam) {
        IPage<LiveCourse> page = liveCourseMapper.selectPage(pageParam, null);
        List<LiveCourse> liveCourseList = page.getRecords();
        for(LiveCourse liveCourse : liveCourseList) {
            Teacher teacher = courseFeignClient.getTeacherLive(liveCourse.getTeacherId());
            liveCourse.getParam().put("teacherName", teacher.getName());
            liveCourse.getParam().put("teacherLevel", teacher.getLevel());
        }
        return page;
    }

    /**
     * 添加直播课程
     * @param liveCourseFormVo liveCourseFormVo
     */
    @Override
    public void saveLive(LiveCourseFormVo liveCourseFormVo) {
        LiveCourse liveCourse = new LiveCourse();
        BeanUtils.copyProperties(liveCourseFormVo, liveCourse);
        // 获取讲师信息
        Teacher teacher = courseFeignClient.getTeacherLive(liveCourseFormVo.getTeacherId());
        HashMap<Object, Object> options = new HashMap<>();
        options.put("scenes", 2);//直播类型。1: 教育直播，2: 生活直播。默认 1，说明：根据平台开通的直播类型填写
        options.put("password", liveCourseFormVo.getPassword());
        String res = null;
        try {
            res = mtCloudClient.courseAdd(
                    liveCourse.getCourseName(),
                    teacher.getId().toString(),
                    new DateTime(liveCourse.getStartTime()).toString(YMD_HMS),
                    new DateTime(liveCourse.getEndTime()).toString(YMD_HMS),
                    teacher.getName(),
                    teacher.getIntro(),
                    options
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("return:: "+res);
        CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
        if (commonResult != null) {
            if(Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
                JSONObject object = commonResult.getData();
                liveCourse.setCourseId(object.getLong("course_id"));
                baseMapper.insert(liveCourse);

                //保存课程详情信息
                LiveCourseDescription liveCourseDescription = new LiveCourseDescription();
                liveCourseDescription.setDescription(liveCourseFormVo.getDescription());
                liveCourseDescription.setLiveCourseId(liveCourse.getId());
                liveCourseDescriptionService.save(liveCourseDescription);

                //保存课程账号信息
                LiveCourseAccount liveCourseAccount = new LiveCourseAccount();
                liveCourseAccount.setLiveCourseId(liveCourse.getId());
                liveCourseAccount.setZhuboAccount(object.getString("bid"));
                liveCourseAccount.setZhuboPassword(liveCourseFormVo.getPassword());
                liveCourseAccount.setAdminKey(object.getString("admin_key"));
                liveCourseAccount.setUserKey(object.getString("user_key"));
                liveCourseAccount.setZhuboKey(object.getString("zhubo_key"));
                liveCourseAccountService.save(liveCourseAccount);
            } else {
                String getMsg = commonResult.getmsg();
                throw new GgktException(20001,getMsg);
            }
        }
    }

    /**
     * 删除直播课程
     * @param id id
     */
    @Override
    public void removeLive(Long id) {
        //根据id查询直播课程信息
        LiveCourse liveCourse = liveCourseMapper.selectById(id);
        if(liveCourse != null) {
            //获取直播courseId
            Long courseId = liveCourse.getCourseId();
            try {
                //调用方法删除平台直播课程
                mtCloudClient.courseDelete(courseId.toString());
                //删除表数据
                baseMapper.deleteById(id);
            } catch (Exception e) {
                e.printStackTrace();
                throw new GgktException(20001,"删除直播课程失败");
            }
        }
    }

    /**
     * 查询直播课程详细信息(包括描述信息)
     * @param id id
     * @return 直播课程详细信息
     */
    @Override
    public LiveCourseFormVo getLiveCourseFormVo(Long id) {
        LiveCourse liveCourse = liveCourseMapper.selectById(id);
        LiveCourseDescription liveCourseDescription = liveCourseDescriptionService.getLiveCourseById(id);
        LiveCourseFormVo liveCourseFormVo = new LiveCourseFormVo();
        BeanUtils.copyProperties(liveCourse, liveCourseFormVo);
        liveCourseFormVo.setDescription(liveCourseDescription.getDescription());
        return liveCourseFormVo;
    }

    /**
     * 修改直播课程信息
     * @param liveCourseFormVo liveCourseFormVo
     */
    @Override
    public void updateLiveById(LiveCourseFormVo liveCourseFormVo) {
        //根据id获取直播课程基本信息
        LiveCourse liveCourse = liveCourseMapper.selectById(liveCourseFormVo.getId());
        BeanUtils.copyProperties(liveCourseFormVo,liveCourse);
        //讲师
        Teacher teacher =
                courseFeignClient.getTeacherLive(liveCourseFormVo.getTeacherId());

//              *   course_id    课程ID
//              *   account      发起直播课程的主播账号
//              *   course_name  课程名称
//              *   start_time   课程开始时间,格式:2015-01-01 12:00:00
//              *   end_time     课程结束时间,格式:2015-01-01 13:00:00
//              *   nickname 	 主播的昵称
//              *   accountIntro 主播的简介
//              *   options 	 可选参数
        HashMap<Object, Object> options = new HashMap<>();
        try {
            String res = mtCloudClient.courseUpdate(liveCourse.getCourseId().toString(),
                    teacher.getId().toString(),
                    liveCourse.getCourseName(),
                    new DateTime(liveCourse.getStartTime()).toString(YMD_HMS),
                    new DateTime(liveCourse.getEndTime()).toString(YMD_HMS),
                    teacher.getName(),
                    teacher.getIntro(),
                    options
            );
            //返回结果转换，判断是否成功
            CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if(Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
                JSONObject object = commonResult.getData();
                //更新直播课程基本信息
                liveCourse.setCourseId(object.getLong("course_id"));
                baseMapper.updateById(liveCourse);
                //直播课程描述信息更新
                LiveCourseDescription liveCourseDescription =
                        liveCourseDescriptionService.getLiveCourseById(liveCourse.getId());
                liveCourseDescription.setDescription(liveCourseFormVo.getDescription());
                liveCourseDescriptionService.updateById(liveCourseDescription);
            } else {
                throw new GgktException(20001,"修改直播课程失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看配置信息
     * @param id id
     * @return 配置信息
     */
    @Override
    public LiveCourseConfigVo getCourseConfig(Long id) {
        LiveCourseConfigVo liveCourseConfigVo = new LiveCourseConfigVo();
        LiveCourseConfig liveCourseConfig = liveCourseConfigService.getCourseConfigCourseId(id);
        if(null != liveCourseConfig) {
            List<LiveCourseGoods> liveCourseGoodsList = liveCourseGoodsService.getGoodsListCourseId(id);
            BeanUtils.copyProperties(liveCourseConfig, liveCourseConfigVo);
            liveCourseConfigVo.setLiveCourseGoodsList(liveCourseGoodsList);
        }
        return liveCourseConfigVo;
    }

    /**
     * 修改直播配置
     * @param liveCourseConfigVo liveCourseConfigVo
     */
    @Override
    public void updateConfig(LiveCourseConfigVo liveCourseConfigVo) {
        LiveCourseConfig liveCourseConfigUpt = new LiveCourseConfig();
        BeanUtils.copyProperties(liveCourseConfigVo, liveCourseConfigUpt);
        if(null == liveCourseConfigVo.getId()) {
            liveCourseConfigService.save(liveCourseConfigUpt);
        } else {
            liveCourseConfigService.updateById(liveCourseConfigUpt);
        }
        LambdaQueryWrapper<LiveCourseGoods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LiveCourseGoods::getLiveCourseId, liveCourseConfigVo.getLiveCourseId());
        liveCourseGoodsService.remove(queryWrapper);
        if(!CollectionUtils.isEmpty(liveCourseConfigVo.getLiveCourseGoodsList())) {
            liveCourseGoodsService.saveBatch(liveCourseConfigVo.getLiveCourseGoodsList());
        }
        this.updateLifeConfig(liveCourseConfigVo);
    }

    /**
     * 获取最近的直播课程
     * @return 最近的直播课程
     */
    @Override
    public List<LiveCourseVo> findLatelyList() {
        List<LiveCourseVo> liveCourseVoList = liveCourseMapper.findLatelyList();
        for(LiveCourseVo liveCourseVo : liveCourseVoList) {
            liveCourseVo.setStartTimeString(new DateTime(liveCourseVo.getStartTime()).toString(YMD));
            liveCourseVo.setEndTimeString(new DateTime(liveCourseVo.getEndTime()).toString("HH:mm"));

            Long teacherId = liveCourseVo.getTeacherId();
            Teacher teacher = courseFeignClient.getTeacherLive(teacherId);
            liveCourseVo.setTeacher(teacher);

            liveCourseVo.setLiveStatus(this.getLiveStatus(liveCourseVo));
        }
        return liveCourseVoList;
    }

    /**
     * 获取用户access_token
     * @param id id
     * @param userId userId
     * @return JSONObject
     */
    @Override
    public JSONObject getAccessToken(Long id, Long userId) {
        LiveCourse liveCourse = this.getById(id);
        UserInfo userInfo = userInfoFeignClient.getById(userId);
        HashMap<Object,Object> options = new HashMap<>();
        String res = null;
        try {
            res = mtCloudClient.courseAccess(
                    liveCourse.getCourseId().toString(),
                    userId.toString(),
                    userInfo.getNickName(),
                    MTCloud.ROLE_USER,
                    3600,
                    options
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
        if (commonResult != null) {
            if(Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
                JSONObject object = commonResult.getData();
                System.out.println("access::"+object.getString("access_token"));
                return object;
            } else {
                throw new GgktException(20001,"获取失败");
            }
        }
        return null;
    }

    /**
     * 根据ID查询课程
     * @param courseId 课程id
     * @return 课程信息
     */
    @Override
    public Map<String, Object> getInfoById(Long courseId) {
        LiveCourse liveCourse = this.getById(courseId);
        liveCourse.getParam().put("startTimeString", new DateTime(liveCourse.getStartTime()).toString(YMD));
        liveCourse.getParam().put("endTimeString", new DateTime(liveCourse.getEndTime()).toString(YMD));
        Teacher teacher = courseFeignClient.getTeacherLive(liveCourse.getTeacherId());
        LiveCourseDescription liveCourseDescription = liveCourseDescriptionService.getLiveCourseById(courseId);

        Map<String, Object> map = new HashMap<>();
        map.put("liveCourse", liveCourse);
        map.put("liveStatus", this.getLiveStatus(liveCourse));
        map.put("teacher", teacher);
        if(null != liveCourseDescription) {
            map.put("description", liveCourseDescription.getDescription());
        } else {
            map.put("description", "");
        }
        return map;
    }

    /**
     * 直播状态 0：未开始 1：直播中 2：直播结束
     * @param liveCourse liveCourse
     * @return 直播状态
     */
    private int getLiveStatus(LiveCourse liveCourse) {
        // 直播状态 0：未开始 1：直播中 2：直播结束
        int liveStatus;
        Date curTime = new Date();
        if(DateUtil.dateCompare(curTime, liveCourse.getStartTime())) {
            liveStatus = 0;
        } else if(DateUtil.dateCompare(curTime, liveCourse.getEndTime())) {
            liveStatus = 1;
        } else {
            liveStatus = 2;
        }
        return liveStatus;
    }

    /**
     * 上传直播配置
     * @param liveCourseConfigVo liveCourseConfigVo
     */
    @SneakyThrows
    private void updateLifeConfig(LiveCourseConfigVo liveCourseConfigVo) {
        LiveCourse liveCourse = liveCourseMapper.selectById(liveCourseConfigVo.getLiveCourseId());

        //参数设置
        HashMap<Object, Object> options = new HashMap<>();
        //界面模式
        options.put("pageViewMode", liveCourseConfigVo.getPageViewMode());
        //观看人数开关
        JSONObject number = new JSONObject();
        number.put("enable", liveCourseConfigVo.getNumberEnable());
        options.put("number", number.toJSONString());
        //观看人数开关
        JSONObject store = new JSONObject();
        store.put("enable", liveCourseConfigVo.getStoreEnable());
        store.put("type", liveCourseConfigVo.getStoreType());
        options.put("store", store.toJSONString());
        //商城列表
        List<LiveCourseGoods> liveCourseGoodsList = liveCourseConfigVo.getLiveCourseGoodsList();
        if (!CollectionUtils.isEmpty(liveCourseGoodsList)) {
            List<LiveCourseGoodsView> liveCourseGoodsViewList = new ArrayList<>();
            for (LiveCourseGoods liveCourseGoods : liveCourseGoodsList) {
                LiveCourseGoodsView liveCourseGoodsView = new LiveCourseGoodsView();
                BeanUtils.copyProperties(liveCourseGoods, liveCourseGoodsView);
                liveCourseGoodsViewList.add(liveCourseGoodsView);
            }
            JSONObject goodsListEdit = new JSONObject();
            goodsListEdit.put("status", "0");
            options.put("goodsListEdit ", goodsListEdit.toJSONString());
            options.put("goodsList", JSON.toJSONString(liveCourseGoodsViewList));
        }

        String res = mtCloudClient.courseUpdateLifeConfig(liveCourse.getCourseId().toString(), options);

        CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
        if (Integer.parseInt(commonResult.getCode()) != MTCloud.CODE_SUCCESS) {
            throw new GgktException(20001, "修改配置信息失败");
        }
    }
}




