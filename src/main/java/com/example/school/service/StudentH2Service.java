package com.example.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.school.model.Student;
import com.example.school.repository.StudentRepository;
import com.example.school.model.StudentRowMapper;

import java.util.*;
import java.util.List;

@Service
public class StudentH2Service implements StudentRepository {
    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Student> getAllStudents() {
        List<Student> studentList = db.query("SELECT * FROM STUDENT", new StudentRowMapper());
        ArrayList<Student> students = new ArrayList<>(studentList);
        return students;
    }

    @Override
    public Student getStudentById(int studentId) {
        try {
            return db.queryForObject("SELECT * FROM STUDENT WHERE studentId = ?", new StudentRowMapper(), studentId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
    }

    @Override
    public Student addStudent(Student student) {
        db.update("INSERT INTO STUDENT(studentName, gender, standard) VALUES (?, ?, ?)",
                student.getStudentName(), student.getGender(), student.getStandard());

        Student savedStudent = db.queryForObject(
                "SELECT * FROM STUDENT WHERE studentName = ? AND gender = ? AND standard = ?",
                new StudentRowMapper(), student.getStudentName(), student.getGender(), student.getStandard());

        return savedStudent;
    }

    @Override
    public Student updateStudent(int studentId, Student student) {
        if (student.getStudentName() != null) {
            db.update("UPDATE STUDENT SET studentName = ? WHERE studentId = ?", student.getStudentName(),
                    studentId);
        }
        if (student.getGender() != null) {
            db.update("UPDATE STUDENT SET gender = ? WHERE studentId = ?", student.getGender(), studentId);
        }
        if (student.getStandard() != 0) {
            db.update("UPDATE STUDENT SET standard = ? WHERE studentId = ?", student.getStandard(), studentId);
        }
        return getStudentById(studentId);
    }

    @Override
    public void deleteStudent(int studentId) {
        db.update("DELETE FROM STUDENT WHERE studentId = ?", studentId);
    }

    @Override
    public int addStudents(ArrayList<Student> students) {
        String sql = "INSERT INTO STUDENT(studentName, gender, standard) VALUES (?, ?, ?)";
        List<Object[]> batchArgs = new ArrayList<>();

        for (Student student : students) {
            Object[] values = { student.getStudentName(), student.getGender(), student.getStandard() };
            batchArgs.add(values);
        }

        int[] affectedRows = db.batchUpdate(sql, batchArgs);
        int count = Arrays.stream(affectedRows).sum();
        return count;
    }
}
