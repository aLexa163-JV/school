package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.info("Был вызван метод для создания студента {}", student);
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.info("Был вызван метод для поиска студента id №{}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Long id, Student student) {
        logger.info("Был вызван метод для изменения студента");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.info("Был вызван метод для удаления студента id №{}",id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        logger.info("Был вызван метод для поиска всех студентов");
        return studentRepository.findAll();
    }

    public Collection<Student> findStudentsAgeMinMax(int min, int max) {
        logger.info("Был вызван метод для поиска студентов в возрасте от {} до {} лет", min, max);
        return studentRepository.findStudentByAgeBetween(min, max);
    }

    public Integer getTotalStudents() {
        logger.info("Был вызван метод количество студентов");
        return studentRepository.getTotalStudents();
    }

    public Integer getAVGStudents() {
        logger.info("Был вызван метод средний возраст студентов");
        return studentRepository.getAVGStudents();
    }

    public List getLastFiveStudents() {
        logger.info("Был вызван метод 5 последних студентов");
        return studentRepository.getLastFiveStudents();
    }
}
