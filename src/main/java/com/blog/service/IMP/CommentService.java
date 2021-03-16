package com.blog.service.IMP;


import com.blog.model.Comment;
import com.blog.repository.ICommentRepository;
import com.blog.service.ICommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService implements ICommentService {


    @Autowired
    ICommentRepository iCommentRepository;



    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {

        Sort sort = Sort.by(Sort.Direction.DESC , "createTime");

        //findByBlogIdAndParentCommentNot JPA提供方法，只找父級為空(即為頂級節點)
        List<Comment> comments = iCommentRepository.findByBlogIdAndParentCommentNull(blogId,sort);

        return eachComment(comments);
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


    /**
     * 循環每個顶级的評論節點
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合併評論的各層子代到第一級子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根節點，blog不為空集合對象
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循環迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改頂級節點的reply集合为跌代處理後的集合
            comment.setReplyComments(tempReplys);
            //清除臨時存放區
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();


    /**
     * 替歸迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//頂級節點添加到臨時存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
