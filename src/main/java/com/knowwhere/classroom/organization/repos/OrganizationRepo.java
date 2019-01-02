package com.knowwhere.classroom.organization.repos;

import com.knowwhere.classroom.organization.models.Organization;
import com.knowwhere.classroom.user.models.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepo extends JpaRepository<Organization, Long> {
    Organization findByEmailOrPhone(String email, String phone);
    Optional<Organization> findByBaseUser(BaseUser baseUser);

}
