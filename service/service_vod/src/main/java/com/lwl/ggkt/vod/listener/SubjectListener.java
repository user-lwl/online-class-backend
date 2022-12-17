package com.lwl.ggkt.vod.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lwl.ggkt.model.vod.Subject;
import com.lwl.ggkt.vo.vod.SubjectEeVo;
import com.lwl.ggkt.vod.mapper.SubjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author user-lwl
 * @createDate 2022/12/2 13:20
 */
@Component
public class SubjectListener extends AnalysisEventListener<SubjectEeVo> {
    @Resource
    private SubjectMapper subjectMapper;
    /**
     * 一行一行读取，从第二行开始
     * @param subjectEeVo subjectEeVo
     * @param analysisContext analysisContext
     */
    @Override
    public void invoke(SubjectEeVo subjectEeVo, AnalysisContext analysisContext) {
        Subject subject = new Subject();
        // SubjectEeVo --> Subject
        BeanUtils.copyProperties(subjectEeVo, subject);
        subjectMapper.insert(subject);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
