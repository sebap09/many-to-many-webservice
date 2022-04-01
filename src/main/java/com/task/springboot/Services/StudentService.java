package com.task.springboot.Services;

import com.task.springboot.Entities.PagingAndSortingProperties.StudentPage;
import com.task.springboot.Entities.Student;
import com.task.springboot.Entities.Teacher;
import com.task.springboot.Exeptions.PersonNotFoundException;
import com.task.springboot.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StudentService {


    private final StudentRepository studentRepository;
    private final TeacherService teacherService;

    @Autowired
    public StudentService(StudentRepository studentRepository,@Lazy TeacherService teacherService) {
        this.studentRepository = studentRepository;
        this.teacherService = teacherService;
    }


    public Page<Student> getAllStudentsWithPagingAndSorting(StudentPage studentPage){
        return studentRepository.findAll(studentPage.getPageRequestOf());
    }

    public List<Student> getStudentByFirstNameAndOrLastName(String firstName, String lastName){
        return studentRepository.findByFirstNameAndOrLastName(firstName,lastName);
    }

    public Student getStudent(Long studentId){
        return studentRepository.findById(studentId).orElseThrow(()->
            new PersonNotFoundException(HttpStatus.NOT_FOUND,"Could not find person with specified id."));
    }

    public Student addNewStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student deleteStudent(Long studentId) {
        Student student=getStudent(studentId);
        studentRepository.delete(student);
        return student;
    }

    @Transactional
    public Student editStudent(Long studentId, Student editedStudent) {
        Student student=getStudent(studentId);
        student.setFirstName(editedStudent.getFirstName());
        student.setLastName(editedStudent.getLastName());
        student.setAge(editedStudent.getAge());
        student.setEmail(editedStudent.getEmail());
        student.setFieldOfStudy(editedStudent.getFieldOfStudy());

        return student;
    }


                                        /*ManyToManyRelationship*/


    public List<Student> getAllStudentsByTeacherId(Long teacherId){
        return studentRepository.findAllStudentsByTeacherId(teacherId);
    }

    @Transactional
    public Student haveATeacher(Long studentId, Long teacherId){
        Student student=getStudent(studentId);
        Teacher teacher=teacherService.getTeacher(teacherId);
        student.haveATeacher(teacher);
        teacher.haveAStudent(student);
        return student;
    }

    @Transactional
    public Student removeATeacher(Long studentId, Long teacherId){
        Student student=getStudent(studentId);
        Teacher teacher=teacherService.getTeacher(teacherId);
        student.removeATeacher(teacher);
        teacher.removeAStudent(student);
        return student;
    }



}
