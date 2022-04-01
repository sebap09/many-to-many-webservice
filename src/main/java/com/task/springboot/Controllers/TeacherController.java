package com.task.springboot.Controllers;

import com.task.springboot.Entities.Student;
import com.task.springboot.Entities.Teacher;
import com.task.springboot.Entities.PagingAndSortingProperties.TeacherPage;
import com.task.springboot.Services.StudentService;
import com.task.springboot.Services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.rmi.ServerException;
import java.util.List;

@RestController
@RequestMapping("/api/teachers/")
public class TeacherController {

    private final TeacherService teacherService;
    private final StudentService studentService;

    @Autowired
    public TeacherController(TeacherService teacherService, StudentService studentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
    }


    // GET http://localhost:8080/api/teachers/sorted
    @GetMapping(value = "sorted")
    public ResponseEntity<Page<Teacher>> getAllTeachersWithPagingAndSorting(TeacherPage teacherPage){
        return new ResponseEntity<>(teacherService.getAllTeachersWithPagingAndSorting(teacherPage),HttpStatus.OK);
    }

    // GET http://localhost:8080/api/teachers/
    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachersOrWithFilteringBy(String firstName,String lastName){
        return new ResponseEntity<>(teacherService.getTeacherByFirstNameAndOrLastName(firstName,lastName),HttpStatus.OK);
    }

    // GET http://localhost:8080/api/teachers/{teacherId}
    @GetMapping(value = "{teacherId}")
    public ResponseEntity<Teacher> getTeacher(@PathVariable final Long teacherId){
        Teacher teacher=teacherService.getTeacher(teacherId);
            return new ResponseEntity<>(teacher,HttpStatus.OK);
    }

    // POST http://localhost:8080/api/teachers/
    @PostMapping
    public ResponseEntity<Teacher> registerNewTeacher(@Valid @RequestBody final Teacher newTeacher) throws ServerException {
        Teacher teacher=teacherService.addNewTeacher(newTeacher);
                return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    // DELETE http://localhost:8080/api/teachers/{teacherId}
    @DeleteMapping(value = "{teacherId}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable final Long teacherId){
        Teacher teacher=teacherService.deleteTeacher(teacherId);
            return new ResponseEntity<>(teacher,HttpStatus.OK);
    }

    // PUT http://localhost:8080/api/teachers/{teacherId}
    @PutMapping(value = "{teacherId}")
    public ResponseEntity<Teacher> editTeacher(@PathVariable final Long teacherId,
                                               @Valid @RequestBody final Teacher editedTeacher){
        Teacher teacher=teacherService.editTeacher(teacherId, editedTeacher);
        return new ResponseEntity<>(teacher,HttpStatus.OK);
    }



                                        /*ManyToManyRelationship*/

    // GET http://localhost:8080/api/teachers/{teacherId}/students
    @GetMapping(value = "{teacherId}/students")
    public ResponseEntity<List<Student>> getAllStudentsByTeacherId(@PathVariable Long teacherId){
        return new ResponseEntity<>(studentService.getAllStudentsByTeacherId(teacherId),HttpStatus.OK);
    }


    // POST http://localhost:8080/api/teachers/{teacherId}/students/{studentId}/add
    @PostMapping(value = "{teacherId}/students/{studentId}/add")
    public ResponseEntity<Teacher> haveAStudent(@PathVariable final Long teacherId,
                                                @PathVariable final Long studentId){
        Teacher teacher=teacherService.haveAStudent(teacherId,studentId);
        return new ResponseEntity<>(teacher,HttpStatus.OK);
    }

    // DELETE http://localhost:8080/api/teachers/{teacherId}/students/{studentId}/remove
    @DeleteMapping(value = "{teacherId}/students/{studentId}/remove")
    public ResponseEntity<Teacher> removeAStudent(@PathVariable final Long teacherId,
                                                  @PathVariable final Long studentId){
        Teacher teacher=teacherService.removeAStudent(teacherId,studentId);
        return new ResponseEntity<>(teacher,HttpStatus.OK);
    }

}
