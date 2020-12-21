package rfid.app.service.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Component Types not matching.")
public class ComponentTypesNotMatchingException extends RuntimeException {
    public ComponentTypesNotMatchingException(){
        super("Component missing.");
    }
}
