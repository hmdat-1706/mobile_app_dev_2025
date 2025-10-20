package com.example.minhdat_23520249_lab3_studentmanagement;

import java.io.Serializable;

// Implement Serializable để có thể truyền đối tượng Student giữa các Activity
public class Student implements Serializable {
    private long id; // ID trong database (khóa chính)
    private String studentId; // Mã số sinh viên
    private String name;
    private String email;
    private String className;

    // Constructors
    public Student() {
    }

    public Student(long id, String studentId, String name, String email, String className) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.className = className;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
}