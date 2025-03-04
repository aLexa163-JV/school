package ru.hogwarts.school.service;


import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {


    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Long id, Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findStudentsAgeMinMax(int min, int max) {
        return studentRepository.findStudentByAgeBetween(min, max);
    }

    public Integer getTotalStudents() {
        return studentRepository.getTotalStudents();
    }

    public Integer getAVGStudents() {
        return studentRepository.getAVGStudents();
    }

    public List getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }
}
