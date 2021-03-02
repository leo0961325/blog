package com.blog.service.IMP;


import com.blog.exception.NotFoundException;
import com.blog.model.Type;
import com.blog.repository.ITypeRepository;
import com.blog.service.ITypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        //網路上有pageable的json格式 https://www.wimdeblauwe.com/blog/2018/2018-06-10-pageimpl-json-serialization-with-spring-boot-2/

        return iTypeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return iTypeRepository.findAll();
    }



    @Override
    public List<Type> listTypeTop(Integer size) {

        //https://blog.csdn.net/weixin_44216706/article/details/106480251
        //查看源码，原来是Sort的构造器私有了private；所以不能通过new Sort()的方式来创建Sort对象
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");

        //https://stackoverflow.com/questions/44848653/pagerequest-constructors-have-been-deprecated
        Pageable pageable = PageRequest.of(0,size,sort);
        return iTypeRepository.findTop(pageable);
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
