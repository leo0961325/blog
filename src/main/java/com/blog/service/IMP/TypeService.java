package com.blog.service.IMP;


import com.blog.exception.NotFoundException;
import com.blog.model.Type;
import com.blog.repository.ITypeRepository;
import com.blog.service.ITypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TypeService implements ITypeService {

    @Autowired
    ITypeRepository iTypeRepository;

    /**
     * @Transactional 抛出异常之后，事务会自动回滚，数据不会插入到数据库
     *
     * @param type
     * @return
     */


    @Transactional
    @Override
    public Type saveType(Type type) {


        return iTypeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return iTypeRepository.getOne(id);
    }



    @Transactional
    @Override
    public Type getTypeByname(String name) {
        return iTypeRepository.findByName(name);
    }



    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return iTypeRepository.findAll(pageable);
    }




    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Optional<Type> getTypeById = Optional.of(iTypeRepository.getOne(id));
        if (!getTypeById.isPresent()){
            throw new NotFoundException("Id is not exist");

        }
        //Optional -> Type
        Type typeTarget = getTypeById.get();
        //source,target
        BeanUtils.copyProperties(type,typeTarget);

        return iTypeRepository.save(typeTarget);
    }



    @Transactional
    @Override
    public void deleteType(Long id) {

        Optional<Type> getTypeById = Optional.of(iTypeRepository.getOne(id));

        Type type = getTypeById.get();

        iTypeRepository.delete(type);
    }
}
