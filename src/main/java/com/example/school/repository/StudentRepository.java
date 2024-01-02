package com.example.school.repository;

import java.util.*;
import com.example.school.model.*;

public interface StudentRepository {
    ArrayList<Student> getAllStudents();

    Student addStudent(Student student);

    int addStudents(ArrayList<Student> students);

    Student getStudentById(int studentId);

    Student updateStudent(int studentId, Student student);

    void deleteStudent(int studentId);
}
