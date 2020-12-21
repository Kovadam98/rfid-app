package rfid.app.service.common.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType type;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private Set<Component> components;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }
}
