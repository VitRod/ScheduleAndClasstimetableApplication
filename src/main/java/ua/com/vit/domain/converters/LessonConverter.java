package ua.com.vit.domain.converters;

import org.springframework.stereotype.Component;
import ua.com.vit.domain.dto.LessonDto;
import ua.com.vit.repository.dao.ClassroomRepository;
import ua.com.vit.repository.dao.CourseRepository;
import ua.com.vit.repository.dao.TeacherRepository;
import ua.com.vit.repository.entities.Lesson;

@Component
public class LessonConverter {

    private final ClassroomRepository classroomRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public LessonConverter(ClassroomRepository classroomRepository, CourseRepository courseRepository,
                           TeacherRepository teacherRepository) {
        this.classroomRepository = classroomRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    public LessonDto toDto(Lesson lesson) {
        LessonDto dto = new LessonDto();
        dto.setId(lesson.getId());
        dto.setDate(lesson.getDate());
        dto.setStartTime(lesson.getStartTime());
        dto.setEndTime(lesson.getEndTime());
        dto.setClassroomId(lesson.getClassroom().getId());
        dto.setCourseId(lesson.getCourse().getId());
        dto.setTeacherId(lesson.getTeacher().getId());
        return dto;
    }

    public Lesson toEntity(LessonDto dto) {
        Lesson lesson = new Lesson();
        lesson.setId(dto.getId());
        lesson.setDate(dto.getDate());
        lesson.setStartTime(dto.getStartTime());
        lesson.setEndTime(dto.getEndTime());
        lesson.setClassroom(classroomRepository.findById(dto.getClassroomId()).orElse(null));
        lesson.setCourse(courseRepository.findById(dto.getCourseId()).orElse(null));
        lesson.setTeacher(teacherRepository.findById(dto.getTeacherId()).orElse(null));
        return lesson;
    }

}
