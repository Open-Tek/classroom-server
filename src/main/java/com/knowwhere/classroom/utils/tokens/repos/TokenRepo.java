package com.knowwhere.classroom.utils.tokens.repos;

import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.utils.tokens.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long> {

    Token findByApiTokenAndApiTokenExpiryBefore(String apiToken, Date currentDateTime);
    Token findByRefreshTokenAndRefreshTokenExpiryBefore(String refreshToken, Date currentDateTime);

    Token findByBaseUser(BaseUser baseUser);

}
