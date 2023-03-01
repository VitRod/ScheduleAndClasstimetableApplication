package ua.com.vit.domain.converters;

import org.springframework.stereotype.Component;
import ua.com.vit.domain.dto.StudentDto;
import ua.com.vit.repository.dao.CourseRepository;
import ua.com.vit.repository.dao.FacultyRepository;
import ua.com.vit.repository.entities.Course;
import ua.com.vit.repository.entities.Student;

import java.util.HashSet;
import java.util.Set;

@Component
public class StudentConverter {

    private final FacultyRepository facultyRepository;
    private final CourseRepository courseRepository;

    public StudentConverter(FacultyRepository facultyRepository, CourseRepository courseRepository) {
        this.facultyRepository = facultyRepository;
        this.courseRepository = courseRepository;
    }

    public StudentDto toDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setFacultyId(student.getFaculty().getId());
        Set<Integer> coursesId = new HashSet<>();
        student.getStudentCourses().forEach(course -> coursesId.add(course.getId()));
        dto.setCoursesId(coursesId);
        return dto;
    }

    public Student toEntity(StudentDto dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setFaculty(facultyRepository.findById(dto.getFacultyId()).orElse(null));
        Set<Course> courses = new HashSet<>();
        dto.getCoursesId().forEach(integer -> courses.add(courseRepository.findById(integer).orElse(null)));
        student.setStudentCourses(courses);
        return student;
    }

}
