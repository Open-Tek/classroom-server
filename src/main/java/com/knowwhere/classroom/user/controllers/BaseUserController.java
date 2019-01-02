package com.knowwhere.classroom.user.controllers;

import com.knowwhere.classroom.classes.models.Classes;
import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.user.services.BaseUserService;
import com.knowwhere.classroom.utils.Utils;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

@RestController
@RequestMapping("api/v1/user/")
public class BaseUserController {

    @Autowired
    private BaseUserService baseUserService;



    @GetMapping("/learning-classes/")
    @ApiResponse(responseContainer = "List", code = 200, message = "OK",response = Classes.class)
    public ResponseEntity<?> getUserLearningClasses(HttpServletRequest request){
        return ResponseEntity.ok().body(this.baseUserService.getLearningClasses(Utils.getBaseUserFromRequest(request)));
    }

    @GetMapping("/teaching-classes/")
    @ApiResponses({@ApiResponse(code=200, message = "OK", responseContainer = "List", response = Classes.class)})
    public ResponseEntity<?> getTeachingClasses(HttpServletRequest request){
        return ResponseEntity.ok().body(this.baseUserService.getTeachingClasses(Utils.getBaseUserFromRequest(request)));
    }

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody BaseUser baseUser){
        return ResponseEntity.ok().body(this.baseUserService.createBaseUser(baseUser));
    }



}
