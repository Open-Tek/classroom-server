package com.knowwhere.classroom.utils;

import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.utils.tokens.interceptor.TokenInterceptorHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * This class contains a set of utility methods.
 */
public class Utils {
    /**
     * This method returns a BaseUser from a HttpServletRequest
     * @param httpServletRequest- -> The request from which you want to extract the user from.
     * @return BaseUser
     */
    public static BaseUser getBaseUserFromRequest(HttpServletRequest httpServletRequest){
        return (BaseUser) httpServletRequest.getSession().getAttribute(TokenInterceptorHandler.BASE_USER_KEY);
    }

}
