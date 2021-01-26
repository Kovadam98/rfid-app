package rfid.app.backend.dto;

import java.util.List;

public class OrderDto {
    private int productTypeId;
    private List<ComponentColorDto> components;

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public List<ComponentColorDto> getComponents() {
        return components;
    }

    public void setComponents(List<ComponentColorDto> components) {
        this.components = components;
    }
}
