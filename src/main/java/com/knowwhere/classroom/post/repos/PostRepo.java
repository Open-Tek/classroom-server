package com.knowwhere.classroom.post.repos;

import com.knowwhere.classroom.classes.models.Classes;
import com.knowwhere.classroom.post.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Iterator;

public interface PostRepo extends JpaRepository<Post, Long> {
    Iterator<Post> getByClassesOrderByCreatedDateDesc(Classes classes);

}
