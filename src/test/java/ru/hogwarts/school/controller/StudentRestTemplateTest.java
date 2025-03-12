package ru.hogwarts.school.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    public void init() {
        studentRepository.deleteAll();//чистит БД
    }

    @Test
    public void testGetStudentById() {
        Student student = studentRepository.save(new Student("Garry", 12));

        ResponseEntity<Student> studentResponseEntity = testRestTemplate.getForEntity
                ("http://localhost:" + port + "/student/" + student.getId(), Student.class);

        assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student student1 = studentResponseEntity.getBody();
        assertThat(student1).isNotNull();
        assertThat(student1.getId()).isEqualTo(student.getId());
        assertThat(student1.getName()).isEqualTo(student.getName());
        assertThat(student1.getAge()).isEqualTo(student.getAge());

    }

    @Test
    public void testNotGetStudentById() {
        Student student = studentRepository.save(new Student("Garry", 12));

        ResponseEntity<Student> studentResponseEntity = testRestTemplate.getForEntity
                ("http://localhost:" + port + "/student/50", Student.class);

        assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCreateStudent() {
        Student newStudent = new Student("Garry", 20);

        ResponseEntity<Student> studentResponse = testRestTemplate.postForEntity
                ("http://localhost:" + port + "/student", newStudent, Student.class);

        Student student1 = studentResponse.getBody();
        assertThat(student1).isNotNull();
        assertThat(student1.getId()).isNotNull();
        assertThat(student1.getName()).isEqualTo(newStudent.getName());
        assertThat(student1.getAge()).isEqualTo(newStudent.getAge());
        assertThat(studentRepository.findById(student1.getId()).get()).isNotNull();
    }

    @Test
    public void testNotCreateStudent() {
        Student student = studentRepository.save(new Student(null, 12));;

        ResponseEntity<Student> studentResponse = testRestTemplate.postForEntity
                ("http://localhost:" + port + "/student", student, Student.class);

        assertThat(studentResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void testEditStudent() {
        Student student = studentRepository.save(new Student("Garry", 12));
        student.setName("Tom");

        testRestTemplate.put("http://localhost:" + port + "/student/" + student.getId(), student);
        Optional<Student> actual = studentRepository.findById(student.getId());
        assertTrue(actual.isPresent());
        assertThat(actual.get().getName()).isEqualTo(student.getName());

    }

    @Test
    public void testDeleteStudent() {
        Student student = studentRepository.save(new Student("Garry", 12));

        testRestTemplate.delete("http://localhost:" + port + "/student/"+student.getId());

        Optional<Student> actual = studentRepository.findById(student.getId());
        assertFalse(actual.isPresent());
    }

    @Test
    public void testGetAllStudents() {
        Student student = new Student("Garry", 20);
        testRestTemplate.postForEntity("http://localhost:" + port + "/student/",student,Student.class);
        ResponseEntity<Collection> response = testRestTemplate.getForEntity("/student", Collection.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testFindStudentsAgeMinMax() {
        Student student1 = new Student("Garry", 20);
        Student student2 = new Student("Harry", 25);
        testRestTemplate.postForEntity("http://localhost:" + port + "/student/", student1, Student.class);
        testRestTemplate.postForEntity("http://localhost:" + port + "/student/", student2, Student.class);
        ResponseEntity<Collection> response = testRestTemplate.getForEntity("/student/min-max?min=18&max=21", Collection.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }
}
