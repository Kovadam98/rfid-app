package rfid.app.service.production.util;

import rfid.app.service.common.model.ColorType;
import rfid.app.service.common.model.Component;
import rfid.app.service.common.model.ComponentType;
import rfid.app.service.common.model.Product;
import rfid.app.service.production.dto.ComponentDto;
import rfid.app.service.production.dto.ProductDto;

import java.util.Collection;
import java.util.stream.Collectors;

public class Converter {
    private Converter(){}

    public static ProductDto createProductDto(Product product){
        if(product == null){
            return null;
        }
        int id = product.getId();
        String name = product.getType().getName();
        Collection<ComponentDto> components = product
                .getComponents()
                .stream()
                .map(Converter::createComponentDto)
                .collect(Collectors.toList());
        return new ProductDto(id,name,components);
    }

    public static ComponentDto createComponentDto(Component component){
        if(component == null){
            return null;
        }

        ColorType colorType = component.getColorType();
        ComponentType componentType = component.getType();

        int id = component.getId();
        boolean isReal = component.isReal();
        String name = componentType.getName();
        String url = componentType.getImageUrl();
        String color = colorType.getName();
        int hue = colorType.getHue();
        int brightness = colorType.getBrightness();
        int saturation = colorType.getSaturation();

        return new ComponentDto(
                id, isReal, name, color, url, hue, brightness, saturation
        );
    }
}
