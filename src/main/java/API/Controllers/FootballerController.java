package API.Controllers;

import API.Additional.Result;
import API.Controllers.Exceptions.NotFoundException;
import API.Models.Footballer;
import API.Services.FootballerService;
import com.sun.deploy.net.HttpResponse;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;

import java.util.List;

@RestController
@RequestMapping("/footballers")
public class FootballerController {
    @Autowired
    FootballerService footballerService;

    @GetMapping("/")
    public List<Footballer> getAll(){
        return footballerService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        Result result = footballerService.delete(id);

        if (result == Result.Not_Found)
            throw new NotFoundException();

    }

    @PostMapping("/add")
    public void create(@RequestBody Footballer footballer){
        footballerService.addFootballer(footballer);

        // TODO throw 500 if false
    }

}
