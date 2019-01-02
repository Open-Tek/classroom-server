package com.knowwhere.classroom.post.repos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFilePathRepo extends JpaRepository<com.knowwhere.classroom.filepath.models.PostFilePath, Long> {
}
