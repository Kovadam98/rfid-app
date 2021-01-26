package rfid.app.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name="product_type")
public class ProductType{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 50)
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "productType")
    private Set<ComponentType> componentTypes;

    @JsonIgnore
    @OneToMany(mappedBy = "type")
    private Set<Product> instances;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ComponentType> getComponentTypes() {
        return componentTypes;
    }

    public void setComponentTypes(Set<ComponentType> componentTypes) {
        this.componentTypes = componentTypes;
    }

    public Set<Product> getInstances() {
        return instances;
    }

    public void setInstances(Set<Product> instances) {
        this.instances = instances;
    }
}
