package API.Controllers;

import API.Controllers.Exceptions.InAdmissibleFieldsException;
import API.Models.Footballer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ErrorHandlers {
    public static ResponseEntity notFoundHandler(String reason){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Reason", reason);
        return new ResponseEntity<>(httpHeaders, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity inadmissibleNullFieldHandler(List<String> fields, Class c){
        if (fields == null || fields.size() == 0)
            throw new NullPointerException("Method called with empty list of null fields");

        HttpHeaders httpHeaders = new HttpHeaders();
        StringBuilder sb = new StringBuilder();
        sb.append("Field(s) ");

        fields.forEach(item -> sb.append(" \'" + item + "\',"));

        sb.deleteCharAt(sb.length() - 1);

        sb.append(" can`t be null.");

        if (c.equals(Footballer.class))
            sb.append(" Field \'age\' should be above 15.");

        HttpHeaders hh = new HttpHeaders();
        hh.add("Reason", sb.toString());

        throw new InAdmissibleFieldsException(sb.toString());
        //return new ResponseEntity(hh, HttpStatus.BAD_REQUEST);
    }

}
