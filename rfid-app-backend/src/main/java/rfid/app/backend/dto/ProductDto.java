package rfid.app.backend.dto;

import java.util.Collection;

public class ProductDto {
    private int id;
    private String name;
    private Collection<ComponentDto> components;

    private int componentCount;

    public ProductDto(){ }

    public ProductDto(int id, String name, Collection<ComponentDto> components, int componentCount){
        this.id = id;
        this.name = name;
        this.components = components;
        this.componentCount = componentCount;
    }

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

    public Collection<ComponentDto> getComponents() {
        return components;
    }

    public void setComponents(Collection<ComponentDto> components) {
        this.components = components;
    }

    public int getComponentCount() {
        return componentCount;
    }

    public void setComponentCount(int componentCount) {
        this.componentCount = componentCount;
    }
}
