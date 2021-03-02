package com.blog.repository;

import com.blog.model.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITypeRepository extends JpaRepository<Type , Long> {

        Type findByName(String name);

        //自定義為了獲取Type最多的使用
        @Query("SELECT t from Type t ")
        List<Type> findTop(Pageable pageable);

}
