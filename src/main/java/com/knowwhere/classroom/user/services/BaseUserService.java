package com.knowwhere.classroom.user.services;

import com.knowwhere.classroom.classes.models.Classes;
import com.knowwhere.classroom.classes.models.UserClassesSubscription;
import com.knowwhere.classroom.organization.models.Organization;
import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.user.repos.BaseUserRepo;
import com.knowwhere.classroom.utils.NoSuchResourceException;
import com.knowwhere.classroom.utils.ResourceAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class BaseUserService {
    @Autowired
    private BaseUserRepo baseUserRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * This method persists and returns a BaseUser instance. If the passed in params already define a user a RuntimeException(ResourceAlreadyExistsException) is thrown
     * @param baseUser- -> The BaseUser to be persisted
     * @return BaseUser
     */
    public BaseUser createBaseUser(BaseUser baseUser) {
        BaseUser emailUser = this.baseUserRepo.findByEmailOrPhone(baseUser.getEmail(), baseUser.getPhone());
        if( emailUser != null )
            throw new ResourceAlreadyExistsException("BaseUser", "email/phone", baseUser.getEmail()+"/"+baseUser.getPhone());

        return this.baseUserRepo.save(baseUser);
    }

    /**
     * A helper method that creates a user for an organization
     * @param orgEmail- -> The email of the organization
     * @param orgPhone- -> The phone number of the organization
     * @return BaseUser
     */
    public BaseUser createOrganizationUser(String orgEmail, String orgPhone) {
        BaseUser adminUser = new BaseUser(orgEmail, orgPhone, orgPhone);
        return this.createBaseUser(adminUser);
    }

    /**
     * Returns a BaseUser instance with the provided id
     * @param id- -> The id to hit the database with
     * @return BaseUser
     */
    public BaseUser getBaseUserById(Long id){
        return this.baseUserRepo.findById(id).orElseThrow(() -> new NoSuchResourceException("BaseUser", "id", id.toString()));
    }

    /**
     * This method returns a BaseUser given the correct email and password combo
     * @param email- -> The email of the user
     * @param password- -> The password of the user
     * @return BaseUser
     */
    public BaseUser getBaseUserByEmailAndPassword(String email, String password){
        return this.baseUserRepo.findByEmailAndPassword(email, password).orElseThrow(()-> new RuntimeException("Wrong email password combo"));
    }
    /**
     * This method returns a BaseUser given the correct phone and password combo
     * @param phone- -> The phone of the user
     * @param password- -> The password of the user
     * @return BaseUser
     */
    public BaseUser getBaseUserByPhoneAndPassword(String phone, String password){
        return this.baseUserRepo.findByPhoneAndPassword(phone, password).orElseThrow(()-> new RuntimeException("Wrong phone password combo"));
    }

    public LinkedList<Classes> getLearningClasses(BaseUser baseUser){
        LinkedList <Classes>list = new LinkedList<Classes>();
        for (UserClassesSubscription subs: baseUser.getLearningClasses()) {
            list.add(subs.getClasses());
        }
        return list;
    }

    /**
     * Returns the classes that this teacher teaches
     * @param baseUser- ->
     * @return
     */
    public LinkedList<Classes> getTeachingClasses(BaseUser baseUser){
        String query = "select Classes.id, Classes.title, Classes.description from Classes, OrgTeacherClasses\n" +
                "where Classes.id = OrgTeacherClasses.classes_id AND OrgTeacherClasses.teacher_id = ?;";
        List<Map<String, Object>> lm = this.jdbcTemplate.queryForList(query, baseUser.getId());
        LinkedList<Classes> classes = new LinkedList<>();

        for(Map<String, Object> row: lm){
            Classes cl = new Classes();
            cl.setTitle(row.get("title").toString());
            cl.setId(Long.parseLong(row.get("id").toString()));
            cl.setDescription(row.get("description").toString());

            classes.add(cl);
        }
        return classes;


    }




}
