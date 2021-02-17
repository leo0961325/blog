package com.blog.repository;

import com.blog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ITagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);
}
