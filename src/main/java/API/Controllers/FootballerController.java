package API.Controllers;

import API.Additional.Result;
import API.Controllers.Exceptions.NotFoundException;
import API.Models.Footballer;
import API.Services.FootballerService;
import com.sun.deploy.net.HttpResponse;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;

import java.util.ArrayList;
import java.util.List;

import static API.Controllers.ErrorHandlers.inadmissibleNullFieldHandler;
import static API.Controllers.ErrorHandlers.notFoundHandler;


@RestController
@RequestMapping("/footballers")
public class FootballerController {
    @Autowired
    FootballerService footballerService;

    @GetMapping("/")
    public ResponseEntity<List<Footballer>> getAll(){
        return new ResponseEntity<>(footballerService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        Result result = footballerService.delete(id);

        if (result == Result.Not_Found)
            //throw new NotFoundException();
            return notFoundHandler(generateNotFoundMessage(id));



        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody Footballer footballer){
        if (footballer == null)
        {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Reason", "No entity in body");
            return new ResponseEntity(httpHeaders, HttpStatus.BAD_REQUEST);
        }

        if (footballer.isValid(false)){
            List<String> field = new ArrayList<>();

            if (footballer.getFirstname() == null)
                field.add("firstname");

            if (footballer.getSurname() == null)
                field.add("surname");

            if (footballer.getAge() <= 15)
                field.add("age");

            return inadmissibleNullFieldHandler(field, this.getClass());
        }

        boolean isSuccesful = footballerService.addFootballer(footballer);

        ResponseEntity re = new ResponseEntity(isSuccesful ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
        return re;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Footballer> getFootballer(@PathVariable long id){
        Footballer res = footballerService.getById(id);
        if (res != null)
            return new ResponseEntity<>(res, HttpStatus.OK);
        // TODO throw mistake
        return notFoundHandler(generateNotFoundMessage(id));
    }

    @PostMapping("/update/")
    public ResponseEntity updateFootballer(@RequestBody Footballer footballer){

        if (footballer == null)
        {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Reason", "No entity in body");
            return new ResponseEntity(httpHeaders, HttpStatus.BAD_REQUEST);
        }

        if (footballer.isValid(true)){
            List<String> field = new ArrayList<>();

            if (footballer.getId() == 0L)
                field.add("id");

            if (footballer.getFirstname() == null)
                field.add("firstname");

            if (footballer.getSurname() == null)
                field.add("surname");

            if (footballer.getAge() <= 15)
                field.add("age");

            return inadmissibleNullFieldHandler(field, this.getClass());
        }


        if (footballer.getId() == 0L)
        {
            return notFoundHandler(generateNotFoundMessage(footballer.getId()));
        }

        Result result = footballerService.update(footballer);

        switch (result){
            case False:
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            case True:
                return new ResponseEntity(HttpStatus.OK);
            case Not_Found:
                return notFoundHandler(generateNotFoundMessage(footballer.getId()));
        }

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String generateNotFoundMessage(long id){
        return "Footballer with id = \'" + id + "\' does not exist";
    }


}
