package rfid.app.service.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "color_type")
public class ColorType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Size(max = 50)
    private String name;
    //HSL
    private int hue;
    private int saturation;
    private int brightness;

    @JsonIgnore
    @OneToMany(mappedBy = "colorType")
    private Set<Component> components;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "color_type_join",
            joinColumns = @JoinColumn(name = "color_type_id"),
            inverseJoinColumns = @JoinColumn(name = "component_type_id")
    )
    private Set<ComponentType> componentTypes;

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

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }

    public Set<ComponentType> getComponentTypes() {
        return componentTypes;
    }

    public void setComponentTypes(Set<ComponentType> componentTypes) {
        this.componentTypes = componentTypes;
    }
}
