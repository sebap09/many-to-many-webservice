package com.task.springboot.Repositories;

import com.task.springboot.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long>, JpaSpecificationExecutor<Student> {

    @Query(value = "SELECT * FROM student s WHERE (:firstName IS NULL OR s.first_name = :firstName)"
            +" AND (:lastName IS NULL OR s.last_name = :lastName)",nativeQuery = true)
    List<Student> findByFirstNameAndOrLastName(String firstName,String lastName);

    @Query(value = "SELECT * FROM student s1 INNER JOIN student_teacher_relationship t2 ON s1.student_id=t2.student_id WHERE t2.teacher_id=:teacherId",nativeQuery = true)
    List<Student> findAllStudentsByTeacherId(Long teacherId);
}
