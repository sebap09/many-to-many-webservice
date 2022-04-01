package com.task.springboot.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Teacher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherId;

    @NotEmpty(message = "firstName: field cannot be empty")
    @Size(min = 2, message = "firstName: should have at least 2 characters")
    private String firstName;

    @NotNull(message = "lastName: field cannot be null")
    private String lastName;

    @NotNull(message = "age: field cannot be null")
    @Min(value = 18, message = "age: positive number, min 18 is required")
    private Integer age;

    @NotEmpty(message = "email: field cannot be empty")
    @Email(message = "email: should be properly formatted email address")
    private String email;

    @NotNull(message = "subject: field cannot be null")
    private String subject;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "StudentTeacherRelationship",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> hisStudents = new HashSet<>();

    public void haveAStudent(Student student){
        hisStudents.add(student);
    }
    public void removeAStudent(Student student){
        hisStudents.remove(student);
    }
}
