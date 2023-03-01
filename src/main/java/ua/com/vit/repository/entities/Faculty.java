package ua.com.vit.repository.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "faculties")
public class Faculty extends CommonEntity {

    @Column(name = "faculty_name")
    @NotBlank(message = "Name is mandatory")
    private String facultyName;

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty)) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(facultyName, faculty.facultyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facultyName);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + getId() +
                ", facultyName='" + facultyName + '\'' +
                '}';
    }

}
