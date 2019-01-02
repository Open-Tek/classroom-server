package com.knowwhere.classroom.organization.controllers;

import com.knowwhere.classroom.organization.models.Organization;
import com.knowwhere.classroom.organization.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/organization/")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("{id}/")
    public ResponseEntity<?> getOrganizationInfo(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.organizationService.getOrganizationById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> createOrganization(@RequestBody Organization organizationToBeCreated){
        return ResponseEntity.ok().body(this.organizationService.createOrganization(organizationToBeCreated));
    }







}
