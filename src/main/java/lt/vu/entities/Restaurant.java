package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Restaurant.findAll", query = "select a from Restaurant as a")
})
@Table(name="RESTAURANT")
@Getter
@Setter
public class Restaurant {

    public Restaurant() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "restaurant", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<TableEntity> tables = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant restaurant = (Restaurant) o;
        return Objects.equals(id, restaurant.id) &&
                Objects.equals(name, restaurant.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
