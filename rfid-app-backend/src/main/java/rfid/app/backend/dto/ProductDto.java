package rfid.app.backend.dto;

import java.util.Collection;

public class ProductDto {
    private int id;
    private String name;
    private Collection<ComponentDto> components;

    public ProductDto(){ }

    public ProductDto(int id, String name, Collection<ComponentDto> components){
        this.id = id;
        this.name = name;
        this.components = components;
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
}
