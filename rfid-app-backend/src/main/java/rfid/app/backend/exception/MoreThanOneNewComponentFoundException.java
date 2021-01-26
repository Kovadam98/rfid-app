package rfid.app.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="More than one new component found.")
public class MoreThanOneNewComponentFoundException extends RuntimeException {
    public MoreThanOneNewComponentFoundException(){
        super("More than one new component found.");
    }
}
