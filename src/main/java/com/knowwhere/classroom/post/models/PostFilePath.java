package com.knowwhere.classroom.filepath.models;

import com.knowwhere.classroom.post.models.Post;
import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.utils.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * This class stores the path of the file and some basic info.
 * The url to access this file must be so- /api/vxx/class-id/post-id/user-id/file-name/
 */
@Entity
public class PostFilePath extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    private String fileName;

    public PostFilePath(){

    }

    public PostFilePath(Post post, String fileName) {
        this.post = post;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
