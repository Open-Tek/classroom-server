package com.knowwhere.classroom.post.models;

import com.knowwhere.classroom.classes.models.Classes;
import com.knowwhere.classroom.filepath.models.PostFilePath;
import com.knowwhere.classroom.post.comment.models.Comment;
import com.knowwhere.classroom.utils.BaseEntity;
import com.knowwhere.classroom.classes.models.OrgTeacherClasses;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private Boolean isAssignment;

    private Date dueDate;

    @OneToMany(
        orphanRemoval = true,
        mappedBy = "parentPost",
        cascade = CascadeType.ALL
    )
    private Set<Comment> comments;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<PostFilePath> postFilePaths;



    @ManyToOne(fetch = FetchType.EAGER)
    private Classes classes;

    @ManyToOne
    private OrgTeacherClasses orgTeacherClasses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAssignment() {
        return isAssignment;
    }

    public void setAssignment(Boolean assignment) {
        isAssignment = assignment;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public OrgTeacherClasses getOrgTeacherClasses() {
        return orgTeacherClasses;
    }

    public void setOrgTeacherClasses(OrgTeacherClasses orgTeacherClasses) {
        this.orgTeacherClasses = orgTeacherClasses;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Set<PostFilePath> getPostFilePaths() {
        return postFilePaths;
    }

    public void setPostFilePaths(Set<PostFilePath> postFilePaths) {
        this.postFilePaths = postFilePaths;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
