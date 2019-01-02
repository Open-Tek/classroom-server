package com.knowwhere.classroom.classes.models;

import com.knowwhere.classroom.post.models.Post;
import com.knowwhere.classroom.utils.BaseEntity;
import com.knowwhere.classroom.organization.models.UserOrganizationTeachers;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * This entity maps the m2m OrgTeacherClasses to Classes.
 * This means that a particular teacher is teaching this class and has access the all the functionality that this class offers.
 * Having this relation allows us to have multiple teachers assigned to 1 class.
 */

@Entity
public class OrgTeacherClasses extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserOrganizationTeachers teacher;

    @ManyToOne
    private Classes classes;

    //posts of this particular teacher
    @OneToMany(
            mappedBy = "orgTeacherClasses",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Post> teacherPosts;

    public OrgTeacherClasses(){

    }

    public OrgTeacherClasses(UserOrganizationTeachers teacher, Classes classes) {
        this.teacher = teacher;
        this.classes = classes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserOrganizationTeachers getTeacher() {
        return teacher;
    }

    public void setTeacher(UserOrganizationTeachers teacher) {
        this.teacher = teacher;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Set<Post> getTeacherPosts() {
        return teacherPosts;
    }

    public void setTeacherPosts(Set<Post> teacherPosts) {
        this.teacherPosts = teacherPosts;
    }


    @Override
    public boolean equals(Object otherObject){
        if ( ! (otherObject instanceof OrgTeacherClasses))
            return false;
        OrgTeacherClasses orgTeacherClasses = (OrgTeacherClasses) otherObject;
        return this.teacher.getId().equals(orgTeacherClasses.getId()) && this.classes.getId().equals(orgTeacherClasses.getClasses().getId());
    }

    @Override
    public int hashCode(){
        return this.teacher.getId().intValue();
    }

}
