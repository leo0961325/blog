package com.blog.repository;

import com.blog.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITypeRepository extends JpaRepository<Type , Long> {

        Type findByName(String name);

}
