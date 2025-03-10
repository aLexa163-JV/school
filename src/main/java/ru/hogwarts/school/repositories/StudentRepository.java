package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findStudentByAgeBetween(int min,int max);
    List<Student> findAll();

    //количество студентов
    @Query(value = "SELECT COUNT(name) FROM student", nativeQuery = true)
    Integer getTotalStudents();

    //средний возраст студентов
    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Integer getAVGStudents();

    //пять последних студентов, у кого идентификатор больше других
    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5",nativeQuery = true)
    List<Student> getLastFiveStudents();

}
