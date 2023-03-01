package ua.com.vit.domain.converters;

import org.springframework.stereotype.Component;
import ua.com.vit.domain.dto.TeacherDto;
import ua.com.vit.repository.dao.CourseRepository;
import ua.com.vit.repository.dao.FacultyRepository;
import ua.com.vit.repository.entities.Course;
import ua.com.vit.repository.entities.Teacher;

import java.util.HashSet;
import java.util.Set;

@Component
public class TeacherConverter {

    private final FacultyRepository facultyRepository;
    private final CourseRepository courseRepository;

    public TeacherConverter(FacultyRepository facultyRepository, CourseRepository courseRepository) {
        this.facultyRepository = facultyRepository;
        this.courseRepository = courseRepository;
    }

    public TeacherDto toDto(Teacher teacher) {
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setFacultyId(teacher.getFaculty().getId());
        Set<Integer> coursesId = new HashSet<>();
        teacher.getTeacherCourses().forEach(course -> coursesId.add(course.getId()));
        dto.setCoursesId(coursesId);
        return dto;
    }

    public Teacher toEntity(TeacherDto dto) {
        Teacher teacher = new Teacher();
        teacher.setId(dto.getId());
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setFaculty(facultyRepository.findById(dto.getFacultyId()).orElse(null));
        Set<Course> courses = new HashSet<>();
        dto.getCoursesId().forEach(integer -> courses.add(courseRepository.findById(integer).orElse(null)));
        teacher.setTeacherCourses(courses);
        return teacher;
    }   

}
