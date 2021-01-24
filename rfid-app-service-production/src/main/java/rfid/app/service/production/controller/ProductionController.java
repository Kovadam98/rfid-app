package rfid.app.service.production.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.*;
import rfid.app.service.common.model.Component;
import rfid.app.service.production.dto.ProductDto;
import rfid.app.service.production.dto.Result;
import rfid.app.service.production.dto.ResultType;
import rfid.app.service.production.util.Converter;
import rfid.app.service.common.model.Product;
import rfid.app.service.common.repository.ComponentRepository;
import rfid.app.service.common.repository.ProductRepository;
import rfid.app.service.production.util.Validator;
import rfid.app.service.production.websocket.NotificationService;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ProductionController {
    private static final Integer MAX_EXISTING_ID = 20;

    private final ProductRepository productRepository;
    private final ComponentRepository componentRepository;
    private final NotificationService notificationService;
    private final Validator validator;

    public ProductionController(
            ProductRepository productRepository,
            ComponentRepository componentRepository,
            NotificationService notificationService,
            Validator validator
    ) {
        this.productRepository = productRepository;
        this.componentRepository = componentRepository;
        this.notificationService = notificationService;
        this.validator = validator;
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    public Product getCurrentProduct() {
        List<Product> products = productRepository.findHasNotRealComponent();
        if(products.size() <= 0){
            return null;
        }
        return products.get(0);
    }

    public ProductDto getNext(){
        Product product = getCurrentProduct();
        if(product == null) {
            return new ProductDto(0,"",new ArrayList<>());
        }
        //number of real components and +1 next component
        long nextInAssembly = product.getComponents().stream().filter(Component::isReal).count() + 1;
        Set<Component> visibleComponents = product.getComponents().stream()
                .filter(component -> component.getType().getAssemblyOrder() <= nextInAssembly)
                .collect(Collectors.toSet());
        product.setComponents(visibleComponents);
        return Converter.createProductDto(product);
    }

    private Set<Integer> sanitizeIds(Set<Integer> ids) {
        return ids.stream()
                .filter(id -> id < MAX_EXISTING_ID)
                .collect(Collectors.toSet());
    }

    @SubscribeMapping("/notif/items")
    public void onSubscribe() {
        notificationService.notifyNewComponents(getNext());
    }


    @Transactional
    @PostMapping(value = "/production/ids")
    public void postIds(@RequestBody Set<Integer> detectedIds){
        Set<Integer> ids = sanitizeIds(detectedIds);
        Collection<Component> components = componentRepository.getRealComponentsFromIds(ids);
        Product product = getCurrentProduct();
        if(product == null) return;

        Result result = validator.Validate(components,product);
        if(result.getType() != ResultType.ERROR) {
            equipNextComponent(product);
            notificationService.notifyNewComponents(getNext());
        }
        notificationService.notifyMessage(result);
    }
    private void equipNextComponent(Product product) {
        Component nextVirtualComponent = getNextComponent(product);
        int typeId = nextVirtualComponent.getType().getId();
        Component nextRealComponent = componentRepository.getNextComponentsByTypeId(typeId).get(0);
        nextRealComponent.setProduct(product);
        product.getComponents().remove(nextVirtualComponent);
        product.getComponents().add(nextRealComponent);
        componentRepository.save(nextRealComponent);
        componentRepository.delete(nextVirtualComponent);
    }

    private Component getNextComponent(Product product){
        long nextInAssembly = product.getComponents().stream().filter(Component::isReal).count() + 1;
        return product.getComponents().stream()
                .filter(component -> component.getType().getAssemblyOrder() == nextInAssembly)
                .findFirst().orElseThrow(RuntimeException::new);
    }
}
