package rfid.app.service.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="More than one product found.")
public class MoreThanOneProductFoundException extends RuntimeException {
    public MoreThanOneProductFoundException(){
        super("More than one product found.");
    }
}
