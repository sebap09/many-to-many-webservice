package com.task.springboot.Repositories;

import com.task.springboot.Entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long>, JpaSpecificationExecutor<Teacher> {

    @Query(value = "SELECT * FROM teacher t WHERE (:firstName IS NULL OR t.first_name = :firstName)"
            +" AND (:lastName IS NULL OR t.last_name = :lastName)",nativeQuery = true)
    List<Teacher> findByFirstNameAndOrLastName(String firstName,String lastName);

    @Query(value = "SELECT * FROM teacher t1 INNER JOIN student_teacher_relationship t2 ON t1.teacher_id=t2.teacher_id WHERE t2.student_id=:studentId",nativeQuery = true)
    List<Teacher> findAllTeachersByStudentId(Long studentId);




}
