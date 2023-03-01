package ua.com.vit.repository.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "buildings")
public class Building extends CommonEntity {

    @Column(name = "building_name")
    @NotBlank(message = "Name is mandatory")
    private String buildingName;

    public Building() {
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Building)) return false;
        Building building = (Building) o;
        return buildingName.equals(building.buildingName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingName);
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + getId() +
                ", buildingName='" + buildingName + '\'' +
                '}';
    }
}
