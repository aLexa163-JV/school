package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Был вызван метод создания факультета");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        logger.info("Был вызван метод поиска факультета по id №{}", id);
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Long id, Faculty faculty) {
        logger.info("Был вызван метод изменения факультета");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        logger.info("Был вызван метод удаления факультета по id №{}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Был вызван метод поиска факультета");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findFacultyByName(String name) {
        logger.info("Был вызван метод поиска факультета по имени = {}", name);
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }

    public Collection<Faculty> findFacultyByColor(String color) {
        logger.info("Был вызван метод поиска факультета по цвету = {}", color);
        return facultyRepository.findFacultyByColorIgnoreCase(color);
    }
}
