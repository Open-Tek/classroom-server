package com.knowwhere.classroom.classes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.knowwhere.classroom.classes.models.Classes;
import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.utils.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * This maps a BaseUser to a Classes entity.
 * If a mapping for a BaseUser exists in this Entity, it means that the BaseUser is a student of this particular class.
 */

@Entity
public class UserClassesSubscription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Classes classes;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private BaseUser student;


    public UserClassesSubscription(){

    }

    public UserClassesSubscription(Classes classes, BaseUser student) {
        this.classes = classes;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public BaseUser getStudent() {
        return student;
    }

    public void setStudent(BaseUser student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object obj) {
        if ( !(obj instanceof UserClassesSubscription) )
            return false;
        UserClassesSubscription classesSubscription = (UserClassesSubscription) obj;
        return this.student.getId().equals(classesSubscription.getStudent().getId()) && this.classes.getId().equals(classesSubscription.getClasses().getId());
    }

    @Override
    public int hashCode(){
        return this.classes.getId().intValue();
    }
}
