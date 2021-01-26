package rfid.app.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="New component not appropriate.")
public class NewComponentNotAppropriateException extends RuntimeException {
    public NewComponentNotAppropriateException(){
        super("New component not appropriate.");
    }
}
