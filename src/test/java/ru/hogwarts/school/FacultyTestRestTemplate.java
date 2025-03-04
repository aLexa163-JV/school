package ru.hogwarts.school;

import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyTestRestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    @AfterEach
    public void init() {
        facultyRepository.deleteAll();//чистит БД
    }

    @Test
    public void testGetFacultyById() {
        Faculty faculty = facultyRepository.save(new Faculty("Гриф", "красный"));

        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.getForEntity
                ("http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class);

        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty facultyResult = facultyResponseEntity.getBody();
        assertThat(facultyResult).isNotNull();
        assertThat(facultyResult.getName()).isEqualTo(faculty.getName());
        assertThat(facultyResult.getColor()).isEqualTo(faculty.getColor());

    }

    @Test
    public void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty("Гриф", "синий");

        Faculty facultyResult = testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        assertThat(facultyResult).isNotNull();
        assertThat(facultyResult.getName()).isEqualTo(faculty.getName());
        assertThat(facultyResult.getColor()).isEqualTo(faculty.getColor());
        assertThat(facultyRepository.findById(facultyResult.getId()).get()).isNotNull();
    }

    @Test
    public void testEditFaculty() {
        Faculty faculty = facultyRepository.save(new Faculty("Гриф", "красный"));
        faculty.setColor("Зеленый");
        faculty.setName("Слизерин");

        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.exchange("http://localhost:" + port + "/faculty/" + faculty.getId(),
                HttpMethod.PUT, new HttpEntity<Faculty>(faculty), Faculty.class);

        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty facultyResult = facultyResponseEntity.getBody();
        assertThat(facultyResult).isNotNull();
        assertThat(facultyResult.getName()).isEqualTo(faculty.getName());
        assertThat(facultyResult.getColor()).isEqualTo(faculty.getColor());

    }

    @Test
    public void testDeleteFaculty() {
        testRestTemplate.delete("/faculty/1");
        ResponseEntity<Faculty> response = testRestTemplate.getForEntity("/faculty/1", Faculty.class);
        assertThat(response.getStatusCode());

    }

}
