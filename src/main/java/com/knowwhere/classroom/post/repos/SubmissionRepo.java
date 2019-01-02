package com.knowwhere.classroom.post.repos;

import com.knowwhere.classroom.post.models.Post;
import com.knowwhere.classroom.post.models.Submission;
import com.knowwhere.classroom.user.models.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Iterator;

public interface SubmissionRepo extends JpaRepository<Submission, Long> {
    Iterator<Submission> getAllByParentAssignmentOrderByIdDesc(Post parentAssignment);
    Submission findByParentAssignmentAndSubOwner(Post assignment, BaseUser owner);
}
