package com.task.springboot.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

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

    @NotNull(message = "fieldOfStudy: field cannot be null")
    private String fieldOfStudy;

    @JsonIgnore
    @ManyToMany(mappedBy = "hisStudents",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Teacher> hisTeachers = new HashSet<>();

    public void haveATeacher(Teacher teacher){
        hisTeachers.add(teacher);
    }

    public void removeATeacher(Teacher teacher){
        hisTeachers.remove(teacher);
    }

}
