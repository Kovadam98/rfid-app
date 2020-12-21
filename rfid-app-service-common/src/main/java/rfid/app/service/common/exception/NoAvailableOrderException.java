package rfid.app.service.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="No available order.")
public class NoAvailableOrderException extends RuntimeException {
    public NoAvailableOrderException() { super("No available order.");}
}
