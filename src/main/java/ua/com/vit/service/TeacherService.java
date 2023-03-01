package ua.com.vit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.com.vit.domain.converters.LessonConverter;
import ua.com.vit.domain.converters.TeacherConverter;
import ua.com.vit.domain.dto.LessonDto;
import ua.com.vit.domain.dto.TeacherDto;
import ua.com.vit.repository.dao.LessonRepository;
import ua.com.vit.repository.dao.TeacherRepository;
import ua.com.vit.repository.entities.Teacher;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;
    private final TeacherConverter converterTeacher;
    private final LessonConverter converterLesson;
    private final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    public TeacherService(TeacherRepository teacherRepository, LessonRepository lessonRepository,
                          TeacherConverter converterTeacher, LessonConverter converterLesson) {
        this.teacherRepository = teacherRepository;
        this.lessonRepository = lessonRepository;
        this.converterTeacher = converterTeacher;
        this.converterLesson = converterLesson;
    }

    public List<Teacher> getAll() {
        List<Teacher> allTeachers;
        allTeachers = teacherRepository.findAll();
        logger.info("getAll():\n {}", allTeachers);
        return allTeachers;
    }

    public List<TeacherDto> getAllAsDto() {
        return getAll()
                .stream()
                .map(converterTeacher::toDto)
                .collect(Collectors.toList());
    }

    public Teacher getById(int id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        logger.info("getById: teacher = {}", teacher);
        return teacher;
    }

    public TeacherDto getByIdAsDto(int id) {
        return converterTeacher.toDto(getById(id));
    }

    public void create(Teacher teacher) {
        logger.info("create: teacher = {}", teacher);
        teacherRepository.saveAndFlush(teacher);
    }

    public void createFromDto(TeacherDto teacher) {
        create(converterTeacher.toEntity(teacher));
    }

    public void update(Teacher teacher) {
        logger.info("update: teacher = {}", teacher);
        teacherRepository.saveAndFlush(teacher);
    }

    public void updateFromDto(TeacherDto teacher) {
        update(converterTeacher.toEntity(teacher));
    }

    public void delete(Teacher teacher) {
        logger.info("delete: teacher = {}", teacher);
        teacherRepository.delete(teacher);
    }

    public void deleteFromDto(TeacherDto teacher) {
        delete(converterTeacher.toEntity(teacher));
    }

    public void deleteById(int id) {
        logger.info("delete: teacher with ID = {}", id);
        teacherRepository.deleteById(id);
    }

    public List<LessonDto> receiveLessonsOnDateRange(int id, LocalDate beginDate, LocalDate endDate) {
        logger.info("receiveLessonsOnDateRange: teacher id = {}, begin date = {}, end date = {}."
                , id, beginDate, endDate);
        List<LessonDto> lessons = lessonRepository.getLessonsForTeacherOnDateRange(id, beginDate, endDate)
                .stream()
                .map(converterLesson::toDto)
                .collect(Collectors.toList());
        if (lessons.isEmpty()) {
            logger.warn("The teacher with id = {} has no lessons for this date range.", id);
        } else {
            logger.info("Teacher with id = {} has {} lessons for this date range:", id, lessons.size());
            logger.info("Lessons:\n {}", lessons);
        }
        return lessons;
    }

}
