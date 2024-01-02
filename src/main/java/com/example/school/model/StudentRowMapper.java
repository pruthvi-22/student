package com.example.school.model;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student(
                rs.getInt("studentId"),
                rs.getString("studentName"),
                rs.getString("gender"),
                rs.getInt("standard"));
        return student;
    }
}
