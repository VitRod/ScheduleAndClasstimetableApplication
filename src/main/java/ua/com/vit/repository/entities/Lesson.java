package ua.com.vit.repository.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "lessons")
public class Lesson extends CommonEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Date is mandatory for a lesson")
    private LocalDate date;

    @Column(name = "start_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "Time of start is mandatory for a lesson")
    private LocalTime startTime;

    @Column(name = "end_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "Time of finish is mandatory for a lesson")
    private LocalTime endTime;

    @ManyToOne
    @NotNull(message = "The lesson must be located in a specific room")
    private Classroom classroom;

    @ManyToOne
    @NotNull(message = "The lesson must be is specified course")
    private Course course;

    @ManyToOne
    @NotNull(message = "A teacher should be assigned to the lesson")
    private Teacher teacher;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return id == lesson.id && Objects.equals(date, lesson.date) && Objects.equals(startTime, lesson.startTime) &&
                Objects.equals(endTime, lesson.endTime) && Objects.equals(classroom, lesson.classroom) &&
                Objects.equals(course, lesson.course) && Objects.equals(teacher, lesson.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, startTime, endTime, classroom, course, teacher);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", classroom=" + classroom +
                ", course=" + course +
                ", teacher=" + teacher +
                '}';
    }

}
