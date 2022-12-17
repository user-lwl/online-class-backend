package com.lwl.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.model.vod.Comment;
import com.lwl.ggkt.vod.mapper.CommentMapper;
import com.lwl.ggkt.vod.service.CommentService;
import org.springframework.stereotype.Service;

/**
* @author user-lwl
* @description 针对表【comment(评论)】的数据库操作Service实现
* @createDate 2022-12-02 13:37:30
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {

}




