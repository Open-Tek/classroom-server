package com.knowwhere.classroom.classes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.knowwhere.classroom.organization.models.Organization;
import com.knowwhere.classroom.post.models.Post;
import com.knowwhere.classroom.utils.BaseEntity;
import com.knowwhere.classroom.utils.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;


@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Classes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String title;

    @JsonIgnore
    @NaturalId
    @Column(nullable = false, unique = true)
    private String classroomCode;

    private String description;


    /**
     * FOR FUTURE USE
     */
    private int state;

    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "student",
            orphanRemoval = true
    )
    private Set<UserClassesSubscription> students;

    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "classes",
            orphanRemoval = true
    )
    private Set<OrgTeacherClasses> teachers;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Organization organization;

    @JsonIgnore
    @OneToMany(
            mappedBy = "classes",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<Post> posts;



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

    public Set<UserClassesSubscription> getStudents() {
        return students;
    }

    public void setStudents(Set<UserClassesSubscription> students) {
        this.students = students;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getClassroomCode() {
        return classroomCode;
    }

    public void setClassroomCode(String classroomCode) {
        this.classroomCode = this.classroomCode == null?StringUtils.toB64(this.id+""): this.classroomCode;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<OrgTeacherClasses> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<OrgTeacherClasses> teachers) {
        this.teachers = teachers;
    }
}
