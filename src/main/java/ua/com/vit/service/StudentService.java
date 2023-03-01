package ua.com.vit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.com.vit.domain.converters.LessonConverter;
import ua.com.vit.domain.converters.StudentConverter;
import ua.com.vit.domain.dto.LessonDto;
import ua.com.vit.domain.dto.StudentDto;
import ua.com.vit.repository.dao.LessonRepository;
import ua.com.vit.repository.dao.StudentRepository;
import ua.com.vit.repository.entities.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;
    private final StudentConverter converterStudent;
    private final LessonConverter converterLesson;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository, LessonRepository lessonRepository,
                          StudentConverter converterStudent, LessonConverter converterLesson) {
        this.lessonRepository = lessonRepository;
        this.studentRepository = studentRepository;
        this.converterStudent = converterStudent;
        this.converterLesson = converterLesson;
    }

    public List<Student> getAll() {
        List<Student> allStudents;
        allStudents = studentRepository.findAll();
        logger.info("getAll():\n {}", allStudents);
        return allStudents;
    }

    public List<StudentDto> getAllAsDto() {
        return getAll()
                .stream()
                .map(converterStudent::toDto)
                .collect(Collectors.toList());
    }

    public Student getById(int id) {
        Student student = studentRepository.findById(id).orElse(null);
        logger.info("getById: student = {}", student);
        return student;
    }

    public StudentDto getByIdAsDto(int id) {
        return converterStudent.toDto(getById(id));
    }

    public void create(Student student) {
        logger.info("create: student = {}", student);
        studentRepository.saveAndFlush(student);
    }

    public void createFromDto(StudentDto student) {
        create(converterStudent.toEntity(student));
    }

    public void update(Student student) {
        logger.info("update: student = {}", student);
        studentRepository.saveAndFlush(student);
    }

    public void updateFromDto(StudentDto student) {
        update(converterStudent.toEntity(student));
    }

    public void delete(Student student) {
        logger.info("delete: student = {}", student);
        studentRepository.delete(student);
    }

    public void deleteFromDto(StudentDto student) {
        delete(converterStudent.toEntity(student));
    }

    public void deleteById(int id) {
        logger.info("delete: student with ID = {}", id);
        studentRepository.deleteById(id);
    }

    public List<LessonDto> receiveLessonsOnDateRange(int id, LocalDate beginDate, LocalDate endDate) {
        logger.info("receiveLessonsOnDateRange: student id = {}, begin date = {}," +
                " end date = {}.", id, beginDate, endDate);
        List<LessonDto> lessons = lessonRepository.getLessonsForStudentOnDateRange(id, beginDate, endDate)
                .stream()
                .map(converterLesson::toDto)
                .collect(Collectors.toList());
        if (lessons.isEmpty()) {
            logger.warn("The student with id = {} has no lessons for this date range.", id);
        } else {
            logger.info("The student with id = {} has {} lessons for this date range:", id, lessons.size());
            logger.info("Lessons:\n {}", lessons);
        }
        return lessons;
    }

    public List<Integer> receiveStudentsIdsWithCertainCourse(int courseId) {
        List<Integer> studentsIdsWithCertainCourse = studentRepository.getStudentStudyingCourse(courseId);
        logger.info("receiveStudentsIdsWithCertainCourse: course id = {}, courses ids = {}",
                courseId, studentsIdsWithCertainCourse);
        return studentsIdsWithCertainCourse;
    }

}
