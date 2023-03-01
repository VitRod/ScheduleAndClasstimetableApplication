package ua.com.vit.repository.entities;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class CommonEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
