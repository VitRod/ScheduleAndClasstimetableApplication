package ua.com.vit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.com.vit.domain.converters.LessonConverter;
import ua.com.vit.domain.dto.LessonDto;
import ua.com.vit.repository.dao.LessonRepository;
import ua.com.vit.repository.entities.Lesson;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonConverter converterLesson;
    private final Logger logger = LoggerFactory.getLogger(LessonService.class);

    public LessonService(LessonRepository lessonRepository, LessonConverter converterLesson) {
        this.lessonRepository = lessonRepository;
        this.converterLesson = converterLesson;
    }

    public List<Lesson> getAll() {
        List<Lesson> allLessons;
        allLessons = lessonRepository.findAll();
        logger.info("getAll():\n {}", allLessons);
        return allLessons;
    }

    public List<LessonDto> getAllAsDto() {
        return getAll()
                .stream()
                .map(converterLesson::toDto)
                .collect(Collectors.toList());
    }

    public Lesson getById(int id) {
        Lesson lesson = lessonRepository.findById(id).orElse(null);
        logger.info("getById: lesson = {}", lesson);
        return lesson;
    }

    public LessonDto getByIdAsDto(int id) {
        return converterLesson.toDto(getById(id));
    }

    public void create(Lesson lesson) {
        logger.info("create: lesson = {}", lesson);
        lessonRepository.saveAndFlush(lesson);
    }

    public void createFromDto(LessonDto lesson) {
        create(converterLesson.toEntity(lesson));
    }

    public void update(Lesson lesson) {
        logger.info("update: lesson = {}", lesson);
        lessonRepository.saveAndFlush(lesson);
    }

    public void updateFromDto(LessonDto lesson) {
        update(converterLesson.toEntity(lesson));
    }

    public void delete(Lesson lesson) {
        logger.info("delete: lesson = {}", lesson);
        lessonRepository.delete(lesson);
    }

    public void deleteFromDto(LessonDto lesson) {
        delete(converterLesson.toEntity(lesson));
    }

    public void deleteById(int id) {
        logger.info("delete: lesson with ID = {}", id);
        lessonRepository.deleteById(id);
    }

    public List<Lesson> getLessonsByTemporalConditions(
            LocalDate date, LocalTime beginningOfRange, LocalTime endOfRange) {
        List<Lesson> lessonsByTemporalConditions = lessonRepository.getLessonsByTemporalConditions(
                date, beginningOfRange, endOfRange);
        logger.info("getLessonsByTemporalConditions: " +
                        "date = {}, beginningOfRange = {}, endOfRange = {}, lessonsByTemporalConditions = {}",
                date, beginningOfRange, endOfRange, lessonsByTemporalConditions);
        return lessonsByTemporalConditions;
    }
}
