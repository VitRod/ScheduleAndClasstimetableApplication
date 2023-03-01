package ua.com.vit.repository.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends CommonEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    @NotBlank(message = "A teacher should has at least the first name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    private Faculty faculty;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "teachers_courses",
            joinColumns = {@JoinColumn(name = "teacher_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private Set<Course> teacherCourses = new HashSet<>();

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Set<Course> getTeacherCourses() {
        return teacherCourses;
    }

    public void setTeacherCourses(Set<Course> teacherCourses) {
        this.teacherCourses = teacherCourses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id && Objects.equals(firstName, teacher.firstName) &&
                Objects.equals(lastName, teacher.lastName) && Objects.equals(faculty, teacher.faculty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, faculty);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", faculty=" + faculty +
                '}';
    }
}
