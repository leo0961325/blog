package com.blog.service.IMP;


import com.blog.model.Comment;
import com.blog.repository.ICommentRepository;
import com.blog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentService implements ICommentService {


    @Autowired
    ICommentRepository iCommentRepository;



    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {

        Sort sort = Sort.by(Sort.Direction.DESC , "createTime");

        return iCommentRepository.findByBlogId(blogId,sort);
    }



    @Transactional
    @Override
    public Comment saveComment(Comment comment) {

        Long parentCommentId = comment.getParentComment().getId();

        //如果是回覆留言的話,/Blog parentComment = -1是初始值
        if (parentCommentId != -1 ){
            comment.setParentComment(iCommentRepository.getOne(parentCommentId));
        }
        else {
            //不是回覆留言
            comment.setParentComment(null);

        }
        comment.setCreateTime(new Date());

        return iCommentRepository.save(comment);
    }
}
