package com.knowwhere.classroom.classes.controllers;

import com.knowwhere.classroom.classes.models.Classes;
import com.knowwhere.classroom.classes.services.ClassesService;
import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@Api(value = "ClassesControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClassesController {

    @Autowired
    private ClassesService classesService;

    /*
    TO BE REVIEWED
    IS THIS A VULNERABILITY IN THE SYSTEM
     */
    @GetMapping("{id}/")
    @ApiOperation("Gets class with specified id")
    @ApiResponses(value = {@ApiResponse(code=200, message="OK", response=Classes.class)})
    public ResponseEntity<?> getClassesDetails(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.classesService.findById(id));
    }

    @GetMapping("{id}/students/")
    @ApiOperation("returns a list of students(BaseUser) given the class id")
    @ApiResponses(value = {@ApiResponse(code=200, message="OK", response=BaseUser.class, responseContainer = "List")})
    public ResponseEntity<?> getClassStudents(@PathVariable("id") Long classId, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.getClassStudents(classId,Utils.getBaseUserFromRequest(request)));
    }

    @GetMapping("{id}/teachers/")
    @ApiOperation("returns a list of teachers(BaseUser) givent the class id")
    @ApiResponses(value = {@ApiResponse(code=200, message="OK", response=BaseUser.class, responseContainer = "List")})
    public ResponseEntity<?> getClassTeachers(@PathVariable("id") Long classId, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.getClassTeacher(classId,Utils.getBaseUserFromRequest(request)));
    }

    /*
    TO BE REVIEWED
    IS THIS A VULNERABILITY IN THE SYSTEM
     */
    @ApiOperation(value = "this fetches classroom information by classroom-code")
    @GetMapping("code/{classroom-code/")
    @ApiResponses(value = {@ApiResponse(code=200, message="OK", response=Classes.class)})
    public ResponseEntity<?> getClassesDetailsWithCode(@PathVariable("classroom-code") String code){
        return ResponseEntity.ok().body(this.classesService.findByCode(code));
    }

    @PostMapping
    @ApiOperation("This lets organizations to create classes.")
    @ApiResponses(value = {@ApiResponse(code=200, message="OK", response=Classes.class), @ApiResponse(code=401, message = "UNAUTHORIZED")})
    public ResponseEntity<?> createClass(@RequestBody Classes classes, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.createClass(classes, Utils.getBaseUserFromRequest(request)));
    }

    @PutMapping("{id}/")
    @ApiResponses(value = {@ApiResponse(code=200, message="OK", response=Classes.class), @ApiResponse(code=401, message = "UNAUTHORIZED")})
    public ResponseEntity<?> updateClass(@PathVariable("id") Long classId,@RequestBody Classes classes, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.updateClass(classId, classes, Utils.getBaseUserFromRequest(request)));
    }

    @PutMapping("add/teacher/{class-id}/{teacher-id/")
    @ApiOperation("this lets an organization to remove a teacher from a class.")
    @ApiResponses(value = {@ApiResponse(code=200, message="OK", response=Classes.class), @ApiResponse(code=401, message = "UNAUTHORIZED")})
    public ResponseEntity<?> addTeacherToClass(@PathVariable("class-id") Long classId, @PathVariable("teacher-id") Long teacherId, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.addTeacherToClass(classId, Utils.getBaseUserFromRequest(request), teacherId));
    }

    @DeleteMapping("remove/teacher/{class-id}/{teacher-id}/")
    @ApiOperation("this lets an organization to remove a teacher from a class.")
    @ApiResponses(value = {@ApiResponse(code=200, message="OK", response=Classes.class), @ApiResponse(code=401, message = "UNAUTHORIZED")})
    public ResponseEntity<?> removeTeacherFromClass(@PathVariable("class-id") Long classId, @PathVariable("teacher-id") Long teacherId, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.removeTeacherFromClass(classId, Utils.getBaseUserFromRequest(request), teacherId));

    }

    @PutMapping("add/student/{class-id}/{student-id}/")
    @ApiOperation("this lets an organization user add students to a class.")
    @ApiResponses(value = {@ApiResponse(code=200, message="OK", response=Classes.class), @ApiResponse(code=401, message = "UNAUTHORIZED")})
    public ResponseEntity<?> addStudentToClass(@PathVariable("class-id") Long classId, @PathVariable("student-id") Long studentId, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.organizationAddStudentToClass(classId, Utils.getBaseUserFromRequest(request), studentId));
    }

    @PutMapping("subscribe/{code}/")
    @ApiOperation("this lets students to subscribe to a class.")
    @ApiResponses(value = {@ApiResponse(code=200, message="OK", response=Classes.class), @ApiResponse(code=401, message = "UNAUTHORIZED")})
    public ResponseEntity<?> subscribeToClass(@PathVariable("code") String classRoomCode, HttpServletRequest request){

        return ResponseEntity.ok().body(this.classesService.subscribeToClass(classRoomCode, Utils.getBaseUserFromRequest(request)));
    }




    @DeleteMapping("remove/student/{class-id}/{student-id}/")
    @ApiOperation("this lets an organization user to remove students from a class.")
    @ApiResponses(value = {@ApiResponse(code=200, message="OK", response=Classes.class), @ApiResponse(code=401, message = "UNAUTHORIZED")})
    public ResponseEntity<?> removeStudentFromClass(@PathVariable("class-id") Long classId, @PathVariable("student-id") Long studentId, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.organizationRemoveStudentFromClass(classId, Utils.getBaseUserFromRequest(request), studentId));
    }

    @DeleteMapping("unsubscribe/{id}/")
    @ApiOperation("this lets students to unsubscribe from a class.")
    @ApiResponses(value = {@ApiResponse(code=200, message="OK", response=Classes.class), @ApiResponse(code=401, message = "UNAUTHORIZED")})
    public ResponseEntity<?> unsubscibeFromClass(@PathVariable("id") Long classId, HttpServletRequest request){
        return ResponseEntity.ok().body(this.classesService.unsubscribeFromClass(classId, Utils.getBaseUserFromRequest(request)));
    }





















}
