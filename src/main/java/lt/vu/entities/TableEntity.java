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
        @NamedQuery(name = "TableEntity.findAll", query = "select a from TableEntity as a")
})
@Table(name="TABLE_ENTITY")
@Getter
@Setter
public class TableEntity {
    public TableEntity() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CAPACITY")
    private Integer capacity;

    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name="RESTAURANT_ID")
    private Restaurant restaurant;

    @ManyToMany(mappedBy="tables")
    private List<Customer> customers = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableEntity table = (TableEntity) o;
        return Objects.equals(id, table.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.getTables().remove(this);
    }
}
