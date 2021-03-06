package com.knowwhere.classroom.user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.knowwhere.classroom.utils.BaseEntity;
import com.knowwhere.classroom.utils.StringUtils;
import com.knowwhere.classroom.classes.models.UserClassesSubscription;
import com.knowwhere.classroom.organization.models.UserOrganizationTeachers;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class BaseUser extends BaseEntity {
    public final static String PASSWORD_SALT = "afe23481f92ea1";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(unique = true, length = 10, nullable = false)
    private String phone;

    @JsonIgnore
    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            mappedBy = "classes",
            fetch = FetchType.EAGER
    )
    private Set<UserClassesSubscription> learningClasses;

    @JsonIgnore
    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            mappedBy = "teacher",
            fetch = FetchType.EAGER
    )
    private Set<UserOrganizationTeachers> teachingOrganizations;

    public BaseUser(){

    }

    public BaseUser(String email, String plainTextPassword, String phone) {
        this.email = email;

        this.setPassword(plainTextPassword);
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = StringUtils.getSHA256(password+PASSWORD_SALT);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<UserClassesSubscription> getLearningClasses() {
        return learningClasses;
    }

    public void setLearningClasses(Set<UserClassesSubscription> learningClasses) {
        this.learningClasses = learningClasses;
    }

    public Set<UserOrganizationTeachers> getTeachingOrganizations() {
        return teachingOrganizations;
    }

    public void setTeachingOrganizations(Set<UserOrganizationTeachers> teachingOrganizations) {
        this.teachingOrganizations = teachingOrganizations;
    }
}
