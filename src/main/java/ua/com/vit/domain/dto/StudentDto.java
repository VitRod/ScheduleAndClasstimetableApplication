package ua.com.vit.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

public class StudentDto {

    private int id;

    @NotBlank(message = "A student should has at least the first name")
    private String firstName;

    private String lastName;

    @Min(value = 0, message = "The student must be enrolled in a specific faculty")
    private int facultyId;

    private Set<Integer> coursesId;

    public int getId() {
        return id;
    }

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

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public Set<Integer> getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(Set<Integer> coursesId) {
        this.coursesId = coursesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDto that = (StudentDto) o;
        return id == that.id && facultyId == that.facultyId && Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) && Objects.equals(coursesId, that.coursesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, facultyId, coursesId);
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", facultyId=" + facultyId +
                ", coursesId=" + coursesId +
                '}';
    }

}
