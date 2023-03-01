package ua.com.vit.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.vit.repository.entities.Classroom;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {

}
