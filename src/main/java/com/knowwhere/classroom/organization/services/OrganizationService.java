package com.knowwhere.classroom.organization.services;

import com.knowwhere.classroom.organization.models.Organization;
import com.knowwhere.classroom.organization.models.UserOrganizationTeachers;
import com.knowwhere.classroom.organization.repos.OrganizationRepo;
import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.user.repos.BaseUserRepo;
import com.knowwhere.classroom.user.services.BaseUserService;
import com.knowwhere.classroom.utils.NoSuchResourceException;
import com.knowwhere.classroom.utils.ResourceAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

@Service
public class OrganizationService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrganizationRepo organizationRepo;

    @Autowired
    private BaseUserService baseUserService;

    /**
     * This method persists an Organization and a BaseUser provided the email and phone are unique.
     * @param organization- -> The organization instance to be persisted
     * @return
     */
    public Organization createOrganization(Organization organization){
        if (organization == null)
            return null;
        Organization emailOrg = this.organizationRepo.findByEmailOrPhone(organization.getEmail(), organization.getPhone());

        if( emailOrg!= null) //org exists
            throw new ResourceAlreadyExistsException("Organization", "email/phone", organization.getEmail()+"/"+organization.getPhone());


        organization.setBaseUser(this.baseUserService.createOrganizationUser(organization.getEmail(), organization.getPhone()));
        return this.organizationRepo.save(organization);

    }

    /**
     * TODO- ADD TOKEN-AUTHORIZATION to this method
     * Returns an Organization instance with the provided id
     * @param id- -> The id to hit the database with
     * @return
     */
    public Organization getOrganizationById(Long id){
        return this.organizationRepo.findById(id).orElseThrow(() -> new NoSuchResourceException("Organization", "id", id.toString()));
    }

    /**
     * This method returns an organization given BaseUser
     * @param baseUser- -> The BaseUser bound to an Organization
     * @return Organization
     */
    public Organization getOrganizationByBaseUser(BaseUser baseUser){
        return this.organizationRepo.findByBaseUser(baseUser).orElseThrow(() -> new NoSuchResourceException("Organization", "BaseUser", baseUser.toString()));
    }


    /**
     * TODO- ADD THIS TO ORGADMINSERVICE class
     * This method adds a BaseUser to an Organization
     * @param orgUser- -> The BaseUser bound to an Organization
     * @param teacherId- -> The id of the teacher
     * @return Organization
     */
    public Organization addTeacherToOrganization(BaseUser orgUser, Long teacherId){
        return this.addOrRemoveTeacherFromOrganization(orgUser, teacherId, true);
    }

    /**
     * TODO - ADD THIS TO ORGADMINSERVICE CLASS
     * This methdo removes a teacher from an Organization.
     * @param orgUser- -> The BaseUser bound to an Organization
     * @param teacherId- -> The id of the teacher
     * @return Organization
     */
    public Organization removeTeacherFromOrganization(BaseUser orgUser, Long teacherId){
        return this.addOrRemoveTeacherFromOrganization(orgUser, teacherId, false);
    }


    /**
     * TODO - ADD THIS TO ORGADMINSERVICE class
     * This methods add/removes a teacher from BaseUser's Organization
     * @param orgUser- -> The BaseUser bound to an Organization
     * @param teacherId- -> The id of the teacher
     * @param add- -> true- add teacher, false remove teacher
     * @return Organization
     */
    private Organization addOrRemoveTeacherFromOrganization(BaseUser orgUser, Long teacherId, boolean add) {
        Organization organization = this.getOrganizationByBaseUser(orgUser);
        BaseUser teacher = this.baseUserService.getBaseUserById(teacherId);

        UserOrganizationTeachers userOrganizationTeachers = new UserOrganizationTeachers();
        userOrganizationTeachers.setOrganization(organization);
        userOrganizationTeachers.setTeacher(teacher);
        if ( add )
            organization.getTeachers().add(userOrganizationTeachers);
        else
            organization.getTeachers().remove(userOrganizationTeachers);

        return this.organizationRepo.save(organization);
    }

    /**
     * TODO - ADD THIS TO ORGADMINSERVICE class
     * This returns a list of teachers for this organization
     * @param orgUser- -> The user bound to this organization.
     * @return LinkedList<BaseUser>
     */
    public LinkedList<BaseUser> getOrganizationTeachers(BaseUser orgUser){
        Set<UserOrganizationTeachers> userOrganizationTeachers = this.getOrganizationByBaseUser(orgUser).getTeachers();
        LinkedList<BaseUser> baseUsers = new LinkedList<>();
        for (UserOrganizationTeachers organizationTeachers: userOrganizationTeachers)
            baseUsers.add(organizationTeachers.getTeacher());
        return baseUsers;
    }


    /**
     * This method returns the id of the row that matches the provided Organization and BaseUser.
     * Returns -1 if the entry does'nt exist or any value greater than 0 if it does.
     * @param organization- -> The organization to check for
     * @param baseUser- -> The baseUser who is to supposed to be a teacher
     * @return Long(id of the row)
     */
    public Long getUserOrganizationTeacherId(Organization organization, BaseUser baseUser){
        String query = "select id from UserOrganizationTeachers where organization_id = %s and teacher_id = %s";
        return jdbcTemplate.query(String.format(query, organization.getId().toString(), baseUser.getId().toString()), new ResultSetExtractor<Long>() {
            @Override
            public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                return resultSet.next()? resultSet.getLong("id"): -1;
            }
        });

    }







}
