package ru.hogwarts.school;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentTestRestTemplate {

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
        Student student = studentRepository.save(new Student(1L,"Гари",12));

        ResponseEntity<Student> studentResponseEntity = testRestTemplate.getForEntity
                ("http://localhost:" + port + "/student/" , Student.class);

        assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student student1 = studentResponseEntity.getBody();
        assertThat(student1).isNotNull();
        assertThat(student1.getId()).isEqualTo(student.getId());
        assertThat(student1.getName()).isEqualTo(student.getName());
        assertThat(student1.getAge()).isEqualTo(student.getAge());

    }

    @Test
    public void testCreateStudent() {
        Student newStudent = new Student(1L,"John Doe", 20);

        ResponseEntity<Student> studentResponse = testRestTemplate.postForEntity("http://localhost:" + port + "/student", newStudent, Student.class);

        Student student1 = studentResponse.getBody();
        assertThat(student1).isNotNull();
        assertThat(student1.getId()).isEqualTo(newStudent.getId());
        assertThat(student1.getName()).isEqualTo(newStudent.getName());
        assertThat(student1.getAge()).isEqualTo(newStudent.getAge());
        assertThat(studentRepository.findById(student1.getId()).get()).isNotNull();
    }

    @Test
    public void testEditStudent() {
        Student updatedStudent = new Student(1L,"John Doe", 20);
        testRestTemplate.put("/student/1", updatedStudent);
        ResponseEntity<Student> response = testRestTemplate.getForEntity("/student/1", Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Дополнительные проверки
    }

    @Test
    public void testDeleteStudent() {
        testRestTemplate.delete("/student/1");
        ResponseEntity<Student> response = testRestTemplate.getForEntity("/student/1", Student.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAllStudents() {
        ResponseEntity<Collection> response = testRestTemplate.getForEntity("/student", Collection.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Дополнительные проверки
    }

    @Test
    public void testFindStudentsAgeMinMax() {
        ResponseEntity<Collection> response = testRestTemplate.getForEntity("/student/min-max?min=18&max=25", Collection.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Дополнительные проверки
    }
}
