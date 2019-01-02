package com.knowwhere.classroom.post.models;

import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.utils.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * This entity holds a set of filePaths that belong to a submission.
 */
@Entity
public class SubmissionFilePaths extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Submission submission;

    private String fileName;


    public SubmissionFilePaths(){

    }

    public SubmissionFilePaths(Submission submission, String fileName){
        this.submission = submission;
        this.fileName = fileName;
    }





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
