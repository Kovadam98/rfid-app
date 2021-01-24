package rfid.app.service.production.util;

import org.springframework.stereotype.Service;
import rfid.app.service.common.model.Component;
import rfid.app.service.common.model.Product;
import rfid.app.service.production.dto.Result;
import rfid.app.service.production.dto.ResultType;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Validator {

    private Set<String> errorMessages;

    private Collection<Component> detectedComponents;
    private Set<Component> oldComponents;

    private Product currentProduct;
    private Set<Component> newComponents; //the new id(s) that weren't detected before there should be only 1
    private Component nextComponent; //the next id according to the ordered product

    public Result Validate(Collection<Component> detectedComponents, Product currentProduct) {
        errorMessages = new HashSet<>();
        this.currentProduct = currentProduct;
        this.detectedComponents = detectedComponents;
        this.oldComponents = getOldComponents();
        this.nextComponent = getNextComponent();
        this.newComponents = getNewComponents();
        checkNoComponentMissing();
        checkMaxOneNewComponent();
        checkMinOneNewComponent();
        Result result = new Result();

        if (errorMessages.isEmpty()) {
            boolean lastComponent = checkOnlyOneComponentLeft();
            if (lastComponent) {
                result.setType(ResultType.FINISHED);
                result.getMessages().add("Finished.");
            }
            else {
                result.setType(ResultType.SUCCESS);
                result.getMessages().add("Success.");
            }
        }
        else {
            result.setType(ResultType.ERROR);
            result.setMessages(errorMessages);
        }

        return result;
    }

    private boolean checkOnlyOneComponentLeft() {
        return currentProduct.getComponents().stream()
                .filter(component -> !component.isReal())
                .count() == 1;
    }

    //All the real component ids in for the product is in the detected ids
    private void checkNoComponentMissing() {
        Set<Integer> detectedIds = detectedComponents.stream()
                .map(Component::getId)
                .collect(Collectors.toSet());

        Set<Integer> oldIds = oldComponents.stream()
                .map(Component::getId)
                .collect(Collectors.toSet());

        if (!detectedIds.containsAll(oldIds)) {
            errorMessages.add("An already equipped component is missing.");
        }
    }

    private void checkMaxOneNewComponent() {
        if (newComponents.size() > 1) {
            errorMessages.add("More than one new component was detected.");
        }
    }

    private void checkMinOneNewComponent() {
        if (newComponents.size() == 0) {
            errorMessages.add("No new component was detected.");
        }
        else{
            checkNewComponentCorrect();
        }
    }

    private void checkNewComponentCorrect() {
        int newComponentTypeId = newComponents.iterator().next().getType().getId();
        int newComponentColorId = newComponents.iterator().next().getColorType().getId();
        int nextComponentTypeId = nextComponent.getType().getId();
        int nextComponentColorId = nextComponent.getColorType().getId();
        if (newComponentTypeId != nextComponentTypeId || newComponentColorId != nextComponentColorId) {
            errorMessages.add("New detected component is incorrect.");
        }
    }

    private Set<Component> getNewComponents() {
        Set<Component> newComponents = new HashSet<>(detectedComponents);
        newComponents.removeAll(oldComponents);
        return newComponents;
    }

    private Component getNextComponent() {
        return currentProduct.getComponents().stream()
                .filter(component -> !component.isReal())
                .min(Comparator.comparingInt(c -> c.getType().getAssemblyOrder()))
                .orElse(null);
    }

    private Set<Component> getOldComponents() {
        return currentProduct.getComponents().stream()
                .filter(Component::isReal)
                .collect(Collectors.toSet());
    }
}
