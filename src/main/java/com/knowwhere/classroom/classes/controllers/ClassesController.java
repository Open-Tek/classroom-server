package com.knowwhere.classroom.classes.controllers;

import com.knowwhere.classroom.classes.models.Classes;
import com.knowwhere.classroom.classes.services.ClassesService;
import com.knowwhere.classroom.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

@RestController
@RequestMapping("api/v1/classes/")
public class ClassesController {

    @Autowired
    private ClassesService classesService;

    /*
    TO BE REVIEWED
    IS THIS A VULNERABILITY IN THE SYSTEM
     */
    @GetMapping("{id}/")
    public ResponseEntity<?> getClassesDetails(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.classesService.findById(id));
    }

    @GetMapping("{id}/students/")
    public ResponseEntity<?> getClassStudents(@PathVariable("id") Long classId, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.getClassStudents(classId,Utils.getBaseUserFromRequest(request)));
    }

    @GetMapping("{id}/teachers/")
    public ResponseEntity<?> getClassTeachers(@PathVariable("id") Long classId, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.getClassTeacher(classId,Utils.getBaseUserFromRequest(request)));
    }

    /*
    TO BE REVIEWED
    IS THIS A VULNERABILITY IN THE SYSTEM
     */
    @GetMapping("code/{classroom-code/")
    public ResponseEntity<?> getClassesDetailsWithCode(@PathVariable("classroom-code") String code){
        return ResponseEntity.ok().body(this.classesService.findByCode(code));
    }

    @PostMapping
    public ResponseEntity<?> createClass(@RequestBody Classes classes, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.createClass(classes, Utils.getBaseUserFromRequest(request)));
    }

    @PutMapping("{id}/")
    public ResponseEntity<?> updateClass(@PathVariable("id") Long classId,@RequestBody Classes classes, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.updateClass(classId, classes, Utils.getBaseUserFromRequest(request)));
    }

    @PutMapping("add/teacher/{class-id}/{teacher-id/")
    public ResponseEntity<?> addTeacherToClass(@PathVariable("class-id") Long classId, @PathVariable("teacher-id") Long teacherId, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.addTeacherToClass(classId, Utils.getBaseUserFromRequest(request), teacherId));
    }

    @DeleteMapping("remove/teacher/{class-id}/{teacher-id}/")
    public ResponseEntity<?> removeTeacherFromClass(@PathVariable("class-id") Long classId, @PathVariable("teacher-id") Long teacherId, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.removeTeacherFromClass(classId, Utils.getBaseUserFromRequest(request), teacherId));

    }

    @PutMapping("add/student/{class-id}/{student-id}/")
    public ResponseEntity<?> addStudentToClass(@PathVariable("class-id") Long classId, @PathVariable("student-id") Long studentId, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.organizationAddStudentToClass(classId, Utils.getBaseUserFromRequest(request), studentId));
    }

    @PutMapping("subscribe/{code}/")
    public ResponseEntity<?> subscribeToClass(@PathVariable("code") String classRoomCode, HttpServletRequest request){

        return ResponseEntity.ok().body(this.classesService.subscribeToClass(classRoomCode, Utils.getBaseUserFromRequest(request)));
    }




    @DeleteMapping("remove/student/{class-id}/{student-id}/")
    public ResponseEntity<?> removeStudentFromClass(@PathVariable("class-id") Long classId, @PathVariable("student-id") Long studentId, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.organizationRemoveStudentFromClass(classId, Utils.getBaseUserFromRequest(request), studentId));
    }

    @DeleteMapping("unsubscribe/{id}/")
    public ResponseEntity<?> unsubscibeFromClass(@PathVariable("id") Long classId, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.unsubscribeFromClass(classId, Utils.getBaseUserFromRequest(request)));
    }





















}
