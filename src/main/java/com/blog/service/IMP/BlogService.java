package com.blog.service.IMP;


import com.blog.model.Blog;
import com.blog.model.Type;
import com.blog.repository.IBlogRepository;
import com.blog.service.IBlogService;
import com.blog.vo.BlogQuery;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService implements IBlogService {


    @Autowired
    IBlogRepository iBlogRepository;


    @Override
    @Transactional
    public Blog getBlog(Long id) {
        return iBlogRepository.getOne(id);
    }



    @Override
    @Transactional
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        //findAll 選 Specification 那一個
        return iBlogRepository.findAll(new Specification<Blog>() {
            //root 是查詢對象 , criteriaQuery 查詢容器 , criteriaBuilder條件表達式
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                //找Title
                //如果title不為空值
                if (!"".equals(blog.getTitle()) && blog.getTitle() !=null){
                    //取名為SQL為title的 (屬性名字,屬性值)
                    predicates.add(cb.like(root.get("title") ,"%"+blog.getTitle()+"%"));
                }
                //找分類
                if (blog.getTypeId() != null){
                    //拿到Type類型 type表裡面的ID
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                //是否推薦
                if(blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend") , blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));

                return null;
            }
        } , pageable);

    }

    @Override
    @Transactional
    public Blog saveBlog(Blog blog) {


        return iBlogRepository.save(blog);
    }

    @Override
    @Transactional
    public Blog updateBlog(Long id, Blog blog) throws NotFoundException {

        Optional<Blog> findBlogById = iBlogRepository.findById(id);

        if(!findBlogById.isPresent()){
            throw new NotFoundException("This Blog is not exist");
        }

        Blog targetBlog = findBlogById.get();

        //org.springframework.beans.BeanUtils;  將blog賦值到targetBlog
        BeanUtils.copyProperties(blog,targetBlog);

        return iBlogRepository.save(targetBlog);
    }

    @Override
    @Transactional
    public void deleteBlog(Long id) throws NotFoundException {

        Optional<Blog> findBlogById = iBlogRepository.findById(id);

        if(!findBlogById.isPresent()){
            throw new NotFoundException("This Blog is not exist");
        }

        Blog blog = findBlogById.get();


        iBlogRepository.delete(blog);
    }
}
