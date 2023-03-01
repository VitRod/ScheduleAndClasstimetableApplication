package ua.com.vit.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.vit.repository.entities.Lesson;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    String GET_LESSONS_BY_DATE_AND_TIME_RANGE =
            "SELECT lessons.id, date, start_time, end_time, classroom_id, course_id, teacher_id " +
                    "FROM lessons " +
                    "WHERE date = :date AND (" +
                    "start_time BETWEEN :beginningOfRange AND :endOfRange " +
                    "OR " +
                    "end_time BETWEEN :beginningOfRange AND :endOfRange" +
                    ")";

    String GET_TEACHER_LESSONS_IN_DATE_RANGE =
            "SELECT lessons.id, date, start_time, end_time, classroom_id, course_id, teacher_id " +
                    "FROM teachers " +
                    "INNER JOIN lessons ON teachers.id = lessons.teacher_id " +
                    "WHERE teachers.id = :id AND date BETWEEN :beginDate AND :endDate";

    String GET_STUDENT_LESSONS_IN_DATE_RANGE =
            "SELECT lessons.id, date, start_time, end_time, classroom_id, lessons.course_id, teacher_id " +
                    "FROM lessons " +
                    "INNER JOIN students_courses ON lessons.course_id = students_courses.course_id " +
                    "INNER JOIN students ON students_courses.student_id = students.id " +
                    "WHERE students.id = :id AND date BETWEEN :beginDate AND :endDate";

    @Query(value = GET_TEACHER_LESSONS_IN_DATE_RANGE,
            nativeQuery = true)
    List<Lesson> getLessonsForTeacherOnDateRange(@Param("id") int id,
                                                 @Param("beginDate") LocalDate beginDate,
                                                 @Param("endDate") LocalDate endDate);

    @Query(value = GET_STUDENT_LESSONS_IN_DATE_RANGE,
            nativeQuery = true)
    List<Lesson> getLessonsForStudentOnDateRange(@Param("id") int id,
                                                 @Param("beginDate") LocalDate beginDate,
                                                 @Param("endDate") LocalDate endDate);

    @Query(value = GET_LESSONS_BY_DATE_AND_TIME_RANGE,
            nativeQuery = true)
    List<Lesson> getLessonsByTemporalConditions(@Param("date") LocalDate date,
                                                @Param("beginningOfRange") LocalTime beginningOfRange,
                                                @Param("endOfRange") LocalTime endOfRange);

}
