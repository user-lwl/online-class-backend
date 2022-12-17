package com.lwl.ggkt.vod.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.exception.GgktException;
import com.lwl.ggkt.model.vod.Subject;
import com.lwl.ggkt.vo.vod.SubjectEeVo;
import com.lwl.ggkt.vod.listener.SubjectListener;
import com.lwl.ggkt.vod.mapper.SubjectMapper;
import com.lwl.ggkt.vod.service.SubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
* @author user-lwl
* @description 针对表【subject(课程科目)】的数据库操作Service实现
* @createDate 2022-12-02 12:45:50
*/
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject>
    implements SubjectService {
    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private SubjectListener subjectListener;

    /**
     * 课程分类列表
     * @param id 课程id
     * @return 课程分类列表
     */
    @Override
    public List<Subject> selectSubjectList(Long id) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        List<Subject> subjectList = subjectMapper.selectList(queryWrapper);
        for (Subject subject : subjectList) {
            Long subjectId = subject.getId();
            boolean isChild = this.isChildren(subjectId);
            subject.setHasChildren(isChild);
        }
        return subjectList;
    }

    /**
     * 课程分类导出
     * @param response response
     */
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easy excel没有关系
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            List<Subject> dictList = subjectMapper.selectList(null);
            List<SubjectEeVo> dictVoList = new ArrayList<>(dictList.size());
            for (Subject dict : dictList) {
                SubjectEeVo dictVo = new SubjectEeVo();
                BeanUtils.copyProperties(dict, dictVo);
                dictVoList.add(dictVo);
            }
            EasyExcelFactory.write(response.getOutputStream(), SubjectEeVo.class)
                    .sheet("课程分类").doWrite(dictVoList);
        } catch (Exception e) {
            throw new GgktException(20001, "导出失败");
        }
    }

    /**
     * 课程分类导入
     * @param file 文件
     */
    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcelFactory.read(file.getInputStream(),
                    SubjectEeVo.class,
                    subjectListener).sheet().doRead();
        } catch (IOException e) {
            throw new GgktException(20001, "导入失败");
        }
    }

    /**
     * 判断是否右下一层数据
     * @param subjectId id
     * @return 是否有数据
     */
    private boolean isChildren(Long subjectId) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", subjectId);
        Integer count = subjectMapper.selectCount(queryWrapper);
        return count > 0;
    }
}




