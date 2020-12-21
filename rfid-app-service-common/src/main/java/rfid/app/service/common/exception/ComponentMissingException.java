package rfid.app.service.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Component missing.")
public class ComponentMissingException extends RuntimeException {
    public ComponentMissingException(){
        super("Component missing.");
    }
}
