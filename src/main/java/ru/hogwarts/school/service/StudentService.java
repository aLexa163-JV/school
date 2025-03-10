package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        logger.info("Был вызван метод для удаления студента id №{}", id);
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


    public List<String> getStudentNamesStartingWithA() {
        logger.info("Был вызван метод для получения всех имен всех студентов, чье имя начинается с буквы А");
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAverageAgeOfStudents() {
        logger.info("Был вызван метод который возвращает средний возраст всех студентов");
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }

    private List<String> student = Arrays.asList("студент", "студент1", "студент2", "студент3", "студент4", "студент5");

    public void printStudentsParallel() {
        logger.info("Был вызван метод который выводит в консоль имена всех студентов в параллельном режиме");

        System.out.println(student.get(0));
        System.out.println(student.get(1));

        new Thread(() -> {
            System.out.println(student.get(2));
            System.out.println(student.get(3));
        }).start();

        new Thread(() -> {
            System.out.println(student.get(4));
            System.out.println(student.get(5));
        }).start();
    }

    private synchronized void printStudentName(String name) {
        System.out.println(name);
    }

    public void printStudentsSynchronized() {
        logger.info("Был вызван метод который выводит в консоль имена всех студентов в синхронном режиме");
        printStudentName(student.get(0));
        printStudentName(student.get(1));

        new Thread(() -> {
            printStudentName(student.get(2));
            printStudentName(student.get(3));
        }).start();

        new Thread(() -> {
            printStudentName(student.get(4));
            printStudentName(student.get(5));
        }).start();
    }
}
