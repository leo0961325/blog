package com.blog.service.IMP;


import com.blog.exception.NotFoundException;
import com.blog.model.Tag;
import com.blog.repository.ITagRepository;
import com.blog.service.ITagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TagService implements ITagService {

    @Autowired
    private ITagRepository itagRepository;

    /**
     * @param tag
     * @return
     * @Transactional 抛出异常之后，事务会自动回滚，数据不会插入到数据库
     */

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return itagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return itagRepository.getOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return itagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return itagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {

        //https://blog.csdn.net/weixin_44216706/article/details/106480251
        //查看源码，原来是Sort的构造器私有了private；所以不能通过new Sort()的方式来创建Sort对象
        Sort sort = Sort.by(Sort.Direction.DESC  , "blogs.size");

        //https://stackoverflow.com/questions/44848653/pagerequest-constructors-have-been-deprecated
        Pageable pageable = PageRequest.of(0,size,sort);

        return itagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return itagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        //選用iterator的那一個JPA
        return itagRepository.findAllById(convertToList(ids));
    }



    //轉換成List
    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for (int i=0 ; i < idarray.length ; i++){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }


    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Optional<Tag> getTagById = Optional.of(itagRepository.getOne(id));
        if (!getTagById.isPresent()) {
            throw new NotFoundException("Id is not exist");

        }
        //Optional -> Tag
        Tag tagTarget = getTagById.get();
        //source,target
        BeanUtils.copyProperties(tag, tagTarget);

        return itagRepository.save(tagTarget);
    }


    @Transactional
    @Override
    public void deleteTag(Long id) {

        Optional<Tag> getTagById = Optional.of(itagRepository.getOne(id));

        Tag tag = getTagById.get();

        itagRepository.delete(tag);
    }


}
