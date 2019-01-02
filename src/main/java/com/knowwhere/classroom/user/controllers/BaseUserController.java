package com.knowwhere.classroom.user.controllers;

import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.user.services.BaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user/")
public class BaseUserController {

    @Autowired
    private BaseUserService baseUserService;

    @GetMapping("{id}/")
    public ResponseEntity<?> getUserInfoById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.baseUserService.getBaseUserById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody BaseUser baseUser){
        return ResponseEntity.ok().body(this.baseUserService.createBaseUser(baseUser));
    }



}
