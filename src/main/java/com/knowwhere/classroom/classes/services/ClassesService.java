package com.knowwhere.classroom.classes.services;

import com.knowwhere.classroom.classes.models.Classes;
import com.knowwhere.classroom.classes.models.OrgTeacherClasses;
import com.knowwhere.classroom.classes.models.UserClassesSubscription;
import com.knowwhere.classroom.classes.repos.ClassesRepo;
import com.knowwhere.classroom.organization.models.Organization;
import com.knowwhere.classroom.organization.models.UserOrganizationTeachers;
import com.knowwhere.classroom.organization.services.OrganizationService;
import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.user.services.BaseUserService;
import com.knowwhere.classroom.utils.NoSuchResourceException;
import com.knowwhere.classroom.utils.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class ClassesService {

    @Autowired
    private ClassesRepo classesRepo;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private BaseUserService baseUserService;

    /**
     * TODO- REVIEW THIS METHODS ACCESSIBILITY by checking BaseUser against organization id provided. BaseUser since API-KEYS are assigned to BaseUsers and they are bound to an organization.
     * This method returns a Classes instance given the id
     * @param id- -> The id of the instance
     * @return Classes
     */
    public Classes findById(Long id){
        return this.classesRepo.findById(id).orElseThrow(() -> new NoSuchResourceException("Classes", "id", id.toString()));
    }


    /**
     * TODO- REVIEW THIS METHODS ACCESSIBILITY by checking BaseUser against organization id provided. BaseUser since API-KEYS are assigned to BaseUsers and they are bound to an organization.
     * This method returns a Classes instance given the classRoomCode
     * @param code- -> The classRoomCode of the instance
     * @return Classes
     */
    public Classes findByCode(String code){
        return this.classesRepo.findByClassroomCode(code).orElseThrow(() -> new NoSuchResourceException("Classes", "code", code));
    }


    /**
     * This method persists a Classes instance.
     * TODO check BaseUser against organization id provided. BaseUser since API-KEYS are assigned to BaseUsers and they are bound to an organization.
     * @param classToBeCreated- -> The class that must be persisted.
     * @param orgUser- -> The orgUser that tried to invoke this method.
     * @return Classes
     */
    public Classes createClass(Classes classToBeCreated, BaseUser orgUser){
        if ( classToBeCreated == null )
            return null;
        Organization organization = this.organizationService.getOrganizationByBaseUser(orgUser);

        classToBeCreated.setClassroomCode("");
        classToBeCreated.setOrganization(organization);
        return this.classesRepo.save(classToBeCreated);
    }

    /**
     * This method updates the title, description present in the instance.
     * @param classId- -> The id of the class to be updated.
     * @param classToBeUpdated- -> An updated instance.
     * @param orgUser- -> The orgUser that tried to invoke this method.
     * @return Classes
     */
    public Classes updateClass(Long classId, Classes classToBeUpdated, BaseUser orgUser){
        if ( classToBeUpdated == null )
            return null;
        Organization organization = classToBeUpdated.getOrganization();
        BaseUser classOrgUser = organization.getBaseUser();
        Classes oldClass = this.findById(classId);
        //checking for teacher/ orgUser
        if ( (this.organizationService.getUserOrganizationTeacherId(organization, classOrgUser))>=1 || classOrgUser.getId().equals(orgUser.getId()) ) {



            oldClass.setState(classToBeUpdated.getState());
            oldClass.setTitle(classToBeUpdated.getTitle());
            oldClass.setDescription(classToBeUpdated.getDescription());

            return this.classesRepo.save(oldClass);
        }
        throw new UnauthorizedAccessException("Classes");


    }

    /**
     * This method adds a teacher to class
     * @param classId- -> The id of the classes instance
     * @param orgUser- -> The user bound to an organization
     * @param teacherId- -> The teacher bound to the organization mentioned above
     * @return Classes
     */
    public Classes addTeacherToClass(Long classId, BaseUser orgUser, Long teacherId){
        return this.addOrRemoveTeacherFromClass(classId, orgUser, teacherId, true);
    }

    /**
     * This method removes a teacher from a class
     * @param classId- -> The id of the classes instance
     * @param orgUser- -> The user bound to an organization
     * @param teacherId- -> The teacher bound to the organization mentioned above
     * @return Classes
     */
    public Classes removeTeacherFromClass(Long classId, BaseUser orgUser, Long teacherId){
        return this.addOrRemoveTeacherFromClass(classId, orgUser, teacherId, false);
    }


    /**
     * This method removes a student from a class
     * @param classId- -> The id of the classes instance
     * @param orgUser- -> The user bound to an organization
     * @param studentId- -> The id of the student who must be kicked from the class.
     * @return Classes
     */
    public Classes organizationRemoveStudentFromClass(Long classId, BaseUser orgUser, Long studentId){
        return this.addOrRemoveStudentFromClass(classId, orgUser, studentId, true);
    }

    /**
     * This method add a student to a class
     * @param classId- -> The id of the classes instance
     * @param orgUser- -> The user bound to an organization
     * @param studentId- -> The id of the student who must be kicked from the class.
     * @return Classes
     */
    public Classes organizationAddStudentToClass(Long classId, BaseUser orgUser, Long studentId){
        return this.addOrRemoveStudentFromClass(classId, orgUser, studentId, false);
    }

    /**
     * This method adds a BaseUser to a class
     * @param classRoomToken- -> The token of the classroom.
     * @param orgStudent- -> The user who wishes to join this class.
     * @return Classes
     */
    public Classes subscribeToClass(String classRoomToken, BaseUser orgStudent){
        Classes classes = this.findByCode(classRoomToken);
        UserClassesSubscription userClassesSubscription = new UserClassesSubscription(classes, orgStudent);
        classes.getStudents().add(userClassesSubscription);
        return this.classesRepo.save(classes);
    }

    /**
     * This methods unsubscribe's the BaseUser from the class
     * @param id- -> The id of the classroom.
     * @param orgStudent- -> The user who wishes to join this class.
     * @return Classes
     */
    public Classes unsubscribeFromClass(Long id, BaseUser orgStudent){
        Classes classes = this.findById(id);
        UserClassesSubscription userClassesSubscription = new UserClassesSubscription(classes, orgStudent);
        classes.getStudents().remove(userClassesSubscription);
        return this.classesRepo.save(classes);
    }

    /**
     * This method returns a list of students present in the requested class.
     * @param classId- -> The id of the class.
     * @param baseUser- -> The user who made the request.
     * @return LinkedList<BaseUser>
     */
    public LinkedList<BaseUser> getClassStudents(Long classId, BaseUser baseUser){
        //todo check if baseUser is student, is org user or is teacher
        Classes classes = this.findById(classId);
        LinkedList<BaseUser> list = new LinkedList<>();
        for(UserClassesSubscription c: classes.getStudents())
            list.add(c.getStudent());
        return list;

    }

    /**
     * This method returns a list of teachers present in the requested class.
     * @param classId- -> The id of the class.
     * @param baseUser- -> The user who made the request.
     * @return LinkedList<BaseUser>
     */
    public LinkedList<BaseUser> getClassTeacher(Long classId, BaseUser baseUser){
        //todo check if baseUser is student, is org user or is teacher
        Classes classes = this.findById(classId);
        LinkedList<BaseUser> list = new LinkedList<>();
        for(OrgTeacherClasses orgTeacherClasses: classes.getTeachers())
            list.add(orgTeacherClasses.getTeacher().getTeacher());
        return list;

    }




    private Classes addOrRemoveStudentFromClass(Long classId, BaseUser orgUser, Long studentId, boolean add){
        Classes classes = this.findById(classId);
        Organization organization = this.organizationService.getOrganizationByBaseUser(orgUser);

        if( !organization.getId().equals(classes.getOrganization().getId()))//todo add check for teacher
            throw new UnauthorizedAccessException("Classes");

        BaseUser student = this.baseUserService.getBaseUserById(studentId);

        UserClassesSubscription userClassesSubscription = new UserClassesSubscription(classes, student);
        if ( add )
            classes.getStudents().add(userClassesSubscription);
        else
            classes.getStudents().remove(userClassesSubscription);
        return this.classesRepo.save(classes);
    }




    private Classes addOrRemoveTeacherFromClass(Long classId, BaseUser orgUser, Long teacherId, boolean add){
        Organization organization = this.organizationService.getOrganizationByBaseUser(orgUser);
        Classes classes = this.findById(classId);

        //checking if class belongs to this organization
        if( !organization.getId().equals(classes.getOrganization().getId()))
            throw new UnauthorizedAccessException("Classes");

        BaseUser teacher = this.baseUserService.getBaseUserById(teacherId);

        // checking if the teacher is part of this org

        Long probableTeacherId = this.organizationService.getUserOrganizationTeacherId(organization, teacher);
        if ( probableTeacherId >= 1 ){
            //valid

            UserOrganizationTeachers userOrganizationTeachers = new UserOrganizationTeachers(organization, teacher);
            userOrganizationTeachers.setId(probableTeacherId);

            OrgTeacherClasses orgTeacherClasses = new OrgTeacherClasses(userOrganizationTeachers, classes);

            if ( add )
                classes.getTeachers().add(orgTeacherClasses);
            else
                classes.getTeachers().remove(orgTeacherClasses);

            return this.classesRepo.save(classes);

        }
        throw new UnauthorizedAccessException("Classes");

    }









}
