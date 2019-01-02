package com.knowwhere.classroom.organization.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.knowwhere.classroom.classes.models.Classes;
import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.utils.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Entity
public class Organization extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, length = 10, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String name;

    private String address;


    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "organization",
            orphanRemoval = true
    )
    private Set<Classes> organizationClasses;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private BaseUser baseUser;


    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "organization",
            orphanRemoval = true
    )
    private Set<UserOrganizationTeachers> teachers;




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


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Classes> getOrganizationClasses() {
        return organizationClasses;
    }

    public void setOrganizationClasses(Set<Classes> organizationClasses) {
        this.organizationClasses = organizationClasses;
    }

    public BaseUser getBaseUser() {
        return baseUser;
    }

    public void setBaseUser(BaseUser baseUser) {
        this.baseUser = baseUser;
    }

    public Set<UserOrganizationTeachers> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<UserOrganizationTeachers> teachers) {
        this.teachers = teachers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
