package com.knowwhere.classroom.classes.repos;

import com.knowwhere.classroom.classes.models.Classes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassesRepo extends JpaRepository<Classes, Long> {
    Optional<Classes> findByClassroomCode(String classroomCode);

}
