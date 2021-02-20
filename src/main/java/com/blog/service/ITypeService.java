package com.blog.service;

import com.blog.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITypeService {


        Type saveType(Type type);

        Type getType(Long id);

        Type getTypeByname(String name);

        Page<Type> listType(Pageable pageable);

        Type updateType(Long id , Type type);

        void deleteType(Long id);

        List<Type> listType();

}
