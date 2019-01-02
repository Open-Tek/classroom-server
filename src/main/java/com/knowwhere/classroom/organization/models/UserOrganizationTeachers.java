package com.knowwhere.classroom.organization.models;

import com.knowwhere.classroom.classes.models.OrgTeacherClasses;
import com.knowwhere.classroom.organization.models.Organization;
import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.utils.BaseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * This entity maps BaseUser to Organization.
 * An entry in this entity means that the particular user is a Teacher for that organization.
 */
@Entity
public class UserOrganizationTeachers extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Organization organization;

    @ManyToOne
    private BaseUser teacher;

    @OneToMany(
            mappedBy = "teacher",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<OrgTeacherClasses> classesTaught;

    public UserOrganizationTeachers(){

    }

    public UserOrganizationTeachers(Organization organization, BaseUser teacher) {
        this.organization = organization;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public BaseUser getTeacher() {
        return teacher;
    }

    public void setTeacher(BaseUser teacher) {
        this.teacher = teacher;
    }

    public Set<OrgTeacherClasses> getClassesTaught() {
        return classesTaught;
    }

    public void setClassesTaught(Set<OrgTeacherClasses> classesTaught) {
        this.classesTaught = classesTaught;
    }


    @Override
    public boolean equals(Object otherObject){
        if (otherObject == null || ! (otherObject instanceof UserOrganizationTeachers) )
            return false;
        UserOrganizationTeachers otherObjects = (UserOrganizationTeachers) otherObject;
        return this.getOrganization().getId().equals(otherObjects.getId()) && this.getTeacher().getId().equals(otherObjects.getTeacher().getId());
    }


    @Override
    public int hashCode(){
        return this.organization.getId().intValue();
    }
}
