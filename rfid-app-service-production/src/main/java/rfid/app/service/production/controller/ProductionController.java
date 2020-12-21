package rfid.app.service.production.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rfid.app.service.common.model.Component;
import rfid.app.service.production.dto.ProductDto;
import rfid.app.service.production.util.Converter;
import rfid.app.service.common.exception.*;
import rfid.app.service.common.model.Product;
import rfid.app.service.common.repository.ComponentRepository;
import rfid.app.service.common.repository.ProductRepository;
import rfid.app.service.common.service.WebPushService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ProductionController {

    private final ProductRepository productRepository;
    private final ComponentRepository componentRepository;
    private final WebPushService webPushService;

    public ProductionController(
            ProductRepository productRepository,
            ComponentRepository componentRepository,
            WebPushService webPushService){
        this.productRepository = productRepository;
        this.componentRepository = componentRepository;
        this.webPushService = webPushService;
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception ex) {
        webPushService.send(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @GetMapping(value = "production/next")
    public ResponseEntity<ProductDto> getNext(){
        List<Product> products = productRepository.findHasNotRealComponent();
        if(products.size() <= 0){
            throw new NoAvailableOrderException();
        }
        Product product = products.get(0);
        //number of real components and +1 next component
        long nextInAssembly = product.getComponents().stream().filter(Component::isReal).count() + 1;
        Set<Component> visibleComponents = product.getComponents().stream()
                .filter(component -> component.getType().getAssemblyOrder() <= nextInAssembly)
                .collect(Collectors.toSet());
        product.setComponents(visibleComponents);
        return new ResponseEntity<>(Converter.createProductDto(product), HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/production/ids")
    public void postIds(@RequestBody Set<Integer> detectedIds){
        Product product = getProductByDetectedIds(detectedIds);
        Component nextRealComponent = getNextRealComponentByDetectedIds(detectedIds);
        Component nextVirtualComponent = getNextComponent(product);
        Set<Integer> detectedAttachedIds = getDetectedAttachedIds(detectedIds,nextRealComponent.getId());
        Set<Integer> registeredAttachedIds = getRegisteredAttachedIds(product.getComponents());

        validateNoComponentMissing(detectedAttachedIds, registeredAttachedIds);
        validateSubstitutability(nextRealComponent, nextVirtualComponent);

        nextRealComponent.setProduct(product);
        product.getComponents().remove(nextVirtualComponent);
        product.getComponents().add(nextRealComponent);
        componentRepository.save(nextRealComponent);
        componentRepository.delete(nextVirtualComponent);

        if(isFinished(product)) {
            webPushService.send("finished");
        }
        else {
            webPushService.send("success");
        }
    }

    private void validateNoComponentMissing(Set<Integer> detectedAttachedIds, Set<Integer> registeredAttachedIds){
        boolean noComponentMissing = detectedAttachedIds.containsAll(registeredAttachedIds);
        if(!noComponentMissing){
            throw new ComponentMissingException();
        }
    }

    private Set<Integer> getRegisteredAttachedIds(Set<Component> components){
        return components.stream()
                .filter(Component::isReal)
                .map(Component::getId)
                .collect(Collectors.toSet());
    }

    private Set<Integer> getDetectedAttachedIds(Set<Integer> ids, Integer filterId){
        return ids.stream().filter(id -> !id.equals(filterId)).collect(Collectors.toSet());
    }

    private Product getProductByDetectedIds(Set<Integer> ids){
        List<Product> products = productRepository.findProductByComponentIds(ids);
        if(products.size() < 1){
            List<Product> newProducts = productRepository.findHasNotRealComponent();
            if(newProducts.size() <= 0){
                throw new NoAvailableOrderException();
            }
            else return newProducts.get(0);
        }
        else if(products.size() > 1){
            throw new MoreThanOneProductFoundException();
        }
        return products.get(0);
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

    private void validateSubstitutability(Component nextRealComponent, Component nextVirtualComponent){
        boolean typesNotEqual = nextRealComponent.getType().getId() != nextVirtualComponent.getType().getId();
        boolean colorNotEqual = nextRealComponent.getColorType().getId() != nextVirtualComponent.getColorType().getId();
        if(typesNotEqual || colorNotEqual) throw new NewComponentNotAppropriateException();
    }

    private boolean isFinished(Product product){
        return product.getComponents().stream().allMatch(Component::isReal);
    }
}
