package rfid.app.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rfid.app.backend.dto.ProductDto;
import rfid.app.backend.exception.ComponentTypesNotMatchingException;
import rfid.app.backend.model.*;
import rfid.app.backend.repository.ProductRepository;
import rfid.app.backend.repository.ProductTypeRepository;
import rfid.app.backend.dto.ComponentColorDto;
import rfid.app.backend.dto.OrderDto;
import rfid.app.backend.service.NotificationService;
import rfid.app.backend.util.Converter;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class OrderController {
    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    public OrderController(
            ProductTypeRepository productTypeRepository,
            ProductRepository productRepository,
            NotificationService notificationService){
        this.productTypeRepository = productTypeRepository;
        this.productRepository = productRepository;
        this.notificationService = notificationService;
    }

    @GetMapping(value = "order")
    public List<ProductType> get(){
        return productTypeRepository.findAll();
    }

    @Transactional
    @PostMapping(value = "order")
    public ResponseEntity<String> post(@RequestBody OrderDto orderDto){
        int id = orderDto.getProductTypeId();
        ProductType productType = productTypeRepository.findById(id);
        validateComponents(orderDto.getComponents(), productType.getComponentTypes());
        Product product = createProduct(orderDto,productType);
        productRepository.save(product);
        ProductDto productDto = Converter.createProductDto(product);
        notificationService.notifyNewComponents(productDto);
        return ResponseEntity.ok("");
    }

    private Product createProduct(OrderDto orderDto, ProductType productType){
        Product product = new Product();
        product.setType(productType);
        Set<Component> components = createComponents(orderDto.getComponents(), productType.getComponentTypes(),product);
        product.setComponents(components);
        return product;
    }

    private Set<Component> createComponents(List<ComponentColorDto> componentColorDtos, Set<ComponentType> componentTypes, Product product){
        return componentColorDtos.stream().map(componentColorDto -> {
            ComponentType componentType = componentTypes.stream().filter(ct -> ct.getId() == componentColorDto.getComponentTypeId()).findFirst().get();
            ColorType colorType = componentType.getColorTypes().stream().filter(ct -> ct.getId() == componentColorDto.getColorId()).findFirst().get();
            Component component = new Component();
            component.setProduct(product);
            component.setType(componentType);
            component.setColorType(colorType);
            return component;
        }).collect(Collectors.toSet());
    }

    private void validateComponents(List<ComponentColorDto> componentColorDtos, Set<ComponentType> componentTypes){
        boolean sizeEqual = componentColorDtos.size()==componentTypes.size();
        if(!sizeEqual) throw new ComponentTypesNotMatchingException();
        boolean componentTypesMatch = componentTypes.stream().allMatch(componentType ->
            componentColorDtos.stream().anyMatch(componentColorDto ->
                componentType.getId() == componentColorDto.getComponentTypeId() &&
                componentType.getColorTypes().stream().anyMatch(colorType ->
                   colorType.getId() == componentColorDto.getColorId()
                )
            )
        );
        if(!componentTypesMatch) throw new ComponentTypesNotMatchingException();
    }
}
