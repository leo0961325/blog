package com.blog.service.IMP;


import com.blog.exception.NotFoundException;
import com.blog.model.Tag;
import com.blog.repository.ITagRepository;
import com.blog.service.ITagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by limi on 2017/10/16.
 */
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


    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Optional<Tag> getTypeById = Optional.of(itagRepository.getOne(id));
        if (!getTypeById.isPresent()) {
            throw new NotFoundException("Id is not exist");

        }
        //Optional -> Type
        Tag tagTarget = getTypeById.get();
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
