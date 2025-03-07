package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

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

    public Optional<String> getLongestFacultyName() {
        logger.info("Был вызван метод который возвращает самое длинное название факультета");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length));
    }

    public long calculateSumUsingFormulaParallel() {
        logger.info("Был вызван метод который возвращает целочисленное значение(парал.стрим)");
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
        return sum;
    }

    public long calculateSumUsingFormulaNoParallel() {
        logger.info("Был вызван метод который возвращает целочисленное значение");
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        return sum;
    }
}
