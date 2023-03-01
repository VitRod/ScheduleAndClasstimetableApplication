package ua.com.vit.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.vit.repository.entities.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    String GET_STUDENTS_STUDYING_COURSE_BY_COURSE_ID =
            "SELECT * FROM students_courses WHERE course_id = :courseId";

    @Query(value = GET_STUDENTS_STUDYING_COURSE_BY_COURSE_ID,
            nativeQuery = true)
    List<Integer> getStudentStudyingCourse(@Param("courseId") Integer courseId);

}
