package rfid.app.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "component_type")
public class ComponentType {
    @Id
    private int id;

    @Size(max = 50)
    @Column(nullable = false)
    private String name;

    @Column(name = "assembly_order",nullable = false)
    private int assemblyOrder;

    @Column(name = "image_url",nullable = false)
    private String imageUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_type_id",nullable = false)
    private ProductType productType;

    @JsonIgnore
    @OneToMany(mappedBy = "type")
    private Set<Component> instances;

    @ManyToMany(mappedBy = "componentTypes")
    private Set<ColorType> colorTypes;

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

    public int getAssemblyOrder() {
        return assemblyOrder;
    }

    public void setAssemblyOrder(int assemblyOrder) {
        this.assemblyOrder = assemblyOrder;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Set<Component> getInstances() {
        return instances;
    }

    public void setInstances(Set<Component> instances) {
        this.instances = instances;
    }

    public Set<ColorType> getColorTypes() {
        return colorTypes;
    }

    public void setColorTypes(Set<ColorType> colorTypes) {
        this.colorTypes = colorTypes;
    }
}
