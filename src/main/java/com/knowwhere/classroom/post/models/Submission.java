package com.knowwhere.classroom.post.models;

import com.knowwhere.classroom.filepath.models.SubmissionFilePaths;
import com.knowwhere.classroom.post.models.Post;
import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.utils.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * This entity maps a BaseUser and a Post thats an assignment.
 * It also also holds a set of SubmissionFilePaths.
 *
 */
@Entity
public class Submission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private BaseUser subOwner;

    @ManyToOne
    private Post parentAssignment;

    @OneToMany(
            mappedBy = "submission",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<SubmissionFilePaths> submissionFilePaths;


    public Submission(){

    }

    public Submission(BaseUser subOwner, Post parentAssignment) {
        this.subOwner = subOwner;
        this.parentAssignment = parentAssignment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BaseUser getSubOwner() {
        return subOwner;
    }

    public void setSubOwner(BaseUser subOwner) {
        this.subOwner = subOwner;
    }

    public Post getParentAssignment() {
        return parentAssignment;
    }

    public void setParentAssignment(Post parentAssignment) {
        this.parentAssignment = parentAssignment;
    }

    public Set<SubmissionFilePaths> getSubmissionFilePaths() {
        return submissionFilePaths;
    }

    public void setSubmissionFilePaths(Set<SubmissionFilePaths> submissionFilePaths) {
        this.submissionFilePaths = submissionFilePaths;
    }
}
