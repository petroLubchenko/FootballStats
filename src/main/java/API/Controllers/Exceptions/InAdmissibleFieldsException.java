package API.Controllers.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InAdmissibleFieldsException extends RuntimeException {
    public InAdmissibleFieldsException(String message){
        super(message);
    }
}
