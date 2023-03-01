package ua.com.vit.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.vit.repository.entities.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {

}
