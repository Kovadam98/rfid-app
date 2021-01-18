package rfid.app.service.production.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rfid.app.service.common.model.Component;
import rfid.app.service.production.dto.ProductDto;
import rfid.app.service.production.dto.Result;
import rfid.app.service.production.dto.ResultType;
import rfid.app.service.production.util.Converter;
import rfid.app.service.common.exception.*;
import rfid.app.service.common.model.Product;
import rfid.app.service.common.repository.ComponentRepository;
import rfid.app.service.common.repository.ProductRepository;
import rfid.app.service.common.service.WebPushService;
import rfid.app.service.production.util.Validator;
import rfid.app.service.production.websocket.NotificationService;

import javax.transaction.Transactional;
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
    ){
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

    @Transactional
    @PostMapping(value = "/production/ids")
    public void postIds(@RequestBody Set<Integer> detectedIds){
        Set<Integer> ids = sanitizeIds(detectedIds);

        Collection<Component> components = componentRepository.getRealComponentsFromIds(ids);
        Product product = getCurrentProduct();

        Result result = validator.Validate(components,product);

        if(result.getType() != ResultType.ERROR) {
            Component nextRealComponent = getNextRealComponentByDetectedIds(detectedIds);
            Component nextVirtualComponent = getNextComponent(product);

            nextRealComponent.setProduct(product);
            product.getComponents().remove(nextVirtualComponent);
            product.getComponents().add(nextRealComponent);
            componentRepository.save(nextRealComponent);
            componentRepository.delete(nextVirtualComponent);

            notificationService.notifyNewComponents(getNext());
        }
        notificationService.notifyMessage(result);
    }

    private Component getNextRealComponentByDetectedIds(Set<Integer> ids){
        List<Component> components = componentRepository.getRealComponentsFromIds(ids);
        if(components.size() < 1){
            throw new NoNewComponentFoundException();
        }
        else if(components.size() > 1){
            throw new MoreThanOneNewComponentFoundException();
        }
        return components.get(0);
    }

    private Component getNextComponent(Product product){
        long nextInAssembly = product.getComponents().stream().filter(Component::isReal).count() + 1;
        return product.getComponents().stream()
                .filter(component -> component.getType().getAssemblyOrder() == nextInAssembly)
                .findFirst().orElseThrow(RuntimeException::new);
    }
}
