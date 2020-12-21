package rfid.app.service.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="No new component found.")
public class NoNewComponentFoundException extends RuntimeException {
    public NoNewComponentFoundException(){
        super("No new component found.");
    }
}
