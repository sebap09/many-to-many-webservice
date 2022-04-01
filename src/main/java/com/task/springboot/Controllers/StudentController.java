package com.task.springboot.Controllers;

import com.task.springboot.Entities.PagingAndSortingProperties.StudentPage;
import com.task.springboot.Entities.Student;
import com.task.springboot.Entities.Teacher;
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
@RequestMapping("/api/students/")
public class StudentController {

    private final StudentService studentService;
    private final TeacherService teacherService;

    @Autowired
    public StudentController(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    // GET http://localhost:8080/api/students/sorted
    @GetMapping(value = "sorted")
    public ResponseEntity<Page<Student>> getAllStudentsWithPagingAndSorting(StudentPage studentPage){
        return new ResponseEntity<>(studentService.getAllStudentsWithPagingAndSorting(studentPage),HttpStatus.OK);
    }

    // GET http://localhost:8080/api/students/
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudentsOrWithFilteringBy(String firstName,String lastName){
        return new ResponseEntity<>(studentService.getStudentByFirstNameAndOrLastName(firstName,lastName),HttpStatus.OK);
    }

    // GET http://localhost:8080/api/students/{studentId}
    @GetMapping(value = "{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable final Long studentId){
        Student student= studentService.getStudent(studentId);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

    // POST http://localhost:8080/api/students/
    @PostMapping
    public ResponseEntity<Student> registerNewStudent(@Valid @RequestBody final Student newStudent) throws ServerException {
        Student student= studentService.addNewStudent(newStudent);
        if(student==null){
            throw new ServerException("Null Student");
        }
        else
            return new ResponseEntity<>(student, HttpStatus.OK);
    }

    // DELETE http://localhost:8080/api/students/{studentId}
    @DeleteMapping(value = "{studentId}")
    public ResponseEntity<Student> deleteStudent(@PathVariable final Long studentId){
        Student student= studentService.deleteStudent(studentId);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

    // PUT http://localhost:8080/api/students/{studentId}
    @PutMapping(value = "{studentId}")
    public ResponseEntity<Student> editStudent(@PathVariable final Long studentId,
                                                 @Valid @RequestBody final Student editedStudent){
        Student student= studentService.editStudent(studentId, editedStudent);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }


                                            /*ManyToManyRelationship*/

    // GET http://localhost:8080/api/students/{studentId}/teachers
    @GetMapping(value = "{studentId}/teachers")
    public ResponseEntity<List<Teacher>> getAllTeachersByStudentId(@PathVariable Long studentId){
        return new ResponseEntity<>(teacherService.getAllTeachersByStudentId(studentId),HttpStatus.OK);
    }

    // POST http://localhost:8080/api/students/{studentId}/teachers/{teacherId}/add
    @PostMapping(value = "{studentId}/teachers/{teacherId}/add")
    public ResponseEntity<Student> haveATeacher(@PathVariable final Long studentId,
                                                @PathVariable final Long teacherId){
            Student student=studentService.haveATeacher(studentId,teacherId);
            return new ResponseEntity<>(student,HttpStatus.OK);
    }

    // DELETE http://localhost:8080/api/students/{studentId}/teachers/{teacherId}/remove
    @DeleteMapping(value = "{studentId}/teachers/{teacherId}/remove")
    public ResponseEntity<Student> removeATeacher(@PathVariable final Long studentId,
                                                @PathVariable final Long teacherId){
        Student student=studentService.removeATeacher(studentId,teacherId);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

}
