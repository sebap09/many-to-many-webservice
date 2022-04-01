package com.task.springboot.Services;

import com.task.springboot.Entities.Student;
import com.task.springboot.Entities.Teacher;
import com.task.springboot.Entities.PagingAndSortingProperties.TeacherPage;
import com.task.springboot.Exeptions.PersonNotFoundException;
import com.task.springboot.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherService {


    private final TeacherRepository teacherRepository;
    private final StudentService studentService;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, StudentService studentService) {
        this.teacherRepository = teacherRepository;
        this.studentService=studentService;
    }



    public Page<Teacher> getAllTeachersWithPagingAndSorting(TeacherPage teacherPage){
        return teacherRepository.findAll(teacherPage.getPageRequestOf());
    }

    public List<Teacher> getTeacherByFirstNameAndOrLastName(String firstName, String lastName){
        return teacherRepository.findByFirstNameAndOrLastName(firstName,lastName);
    }

    public Teacher getTeacher(Long teacherId){
        return teacherRepository.findById(teacherId).orElseThrow(()->
                new PersonNotFoundException(HttpStatus.NOT_FOUND,"Could not find person with specified id."));
    }

    public Teacher addNewTeacher(Teacher teacher) {
       return teacherRepository.save(teacher);
    }

    public Teacher deleteTeacher(Long teacherId) {
        Teacher teacher=getTeacher(teacherId);
        teacherRepository.delete(teacher);
        return teacher;
    }

    @Transactional
    public Teacher editTeacher(Long teacherId, Teacher editedTeacher) {
        Teacher teacher=getTeacher(teacherId);
        teacher.setFirstName(editedTeacher.getFirstName());
        teacher.setLastName(editedTeacher.getLastName());
        teacher.setAge(editedTeacher.getAge());
        teacher.setEmail(editedTeacher.getEmail());
        teacher.setSubject(editedTeacher.getSubject());

        return teacher;
    }


                                            /*ManyToManyRelationship*/


    public List<Teacher> getAllTeachersByStudentId(Long studentId){
        return teacherRepository.findAllTeachersByStudentId(studentId);
    }

    @Transactional
    public Teacher haveAStudent(Long teacherId, Long studentId){
        Teacher teacher=getTeacher(teacherId);
        Student student=studentService.getStudent(studentId);
        teacher.haveAStudent(student);
        student.haveATeacher(teacher);
        return teacher;
    }

    @Transactional
    public Teacher removeAStudent(Long teacherId, Long studentId){
        Teacher teacher=getTeacher(teacherId);
        Student student=studentService.getStudent(studentId);
        teacher.removeAStudent(student);
        student.removeATeacher(teacher);
        return teacher;
    }
}
