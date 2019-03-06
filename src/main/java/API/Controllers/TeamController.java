package API.Controllers;

import API.Additional.Result;
import API.Controllers.Exceptions.InternalServerException;
import API.Controllers.Exceptions.NotFoundException;
import API.Models.Team;
import API.Services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static API.Controllers.ErrorHandlers.inadmissibleNullFieldHandler;
import static API.Controllers.ErrorHandlers.notFoundHandler;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    TeamService teamService;

    @GetMapping("/")
    public ResponseEntity<List<Team>> getAll(){
        return new ResponseEntity<>(teamService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getById(@PathVariable long id){
        Team team = teamService.getById(id);

        if (team != null)
            return new ResponseEntity<>(team, HttpStatus.OK);

        throw new NotFoundException(generateNotFoundMessage(id));
        //return notFoundHandler(generateNotFoundMessage(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        Result result = teamService.delete(id);

        if (result == Result.Not_Found)
            throw new NotFoundException(generateNotFoundMessage(id));
            //return notFoundHandler(generateNotFoundMessage(id));

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Team team){
        if (team == null)
        {
            HttpHeaders hh = new HttpHeaders();
            hh.add("Reason", "In body must be \"Team\" entity");
            return  new ResponseEntity(hh, HttpStatus.BAD_REQUEST);
        }

        if (!team.isValid(false)){
            List<String> field = new ArrayList<>();

            if (team.getName() == null)
                field.add("name");

            if(team.getStadiumname() == null)
                field.add("stadiumname");

            return inadmissibleNullFieldHandler(field, this.getClass());
        }

        boolean issuccess = teamService.addTeam(team);

        if (issuccess) {
            ResponseEntity re = new ResponseEntity(HttpStatus.CREATED);
            return re;
        }

        throw new InternalServerException("Unsuccessful operation. Check object and try to add again.");
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody Team team){
        if (team == null){
            HttpHeaders hh = new HttpHeaders();
            hh.add("Reason", "No entity in body"); // TODO delete after overwriting handler
            return new ResponseEntity(hh, HttpStatus.BAD_REQUEST);
        }

        if (!team.isValid(true)){
            List<String> field = new ArrayList<>();

            if (team.getId() == 0L)
                field.add("id");

            if (team.getName() == null)
                field.add("name");

            if(team.getStadiumname() == null)
                field.add("stadiumname");

            return inadmissibleNullFieldHandler(field, this.getClass());
        }


        Result res =  teamService.update(team);

        switch (res){
            case Not_Found:
                throw new NotFoundException(generateNotFoundMessage(team.getId()));
            case False:
                throw new InternalServerException("Unsuccessful operation. Check object and try to add again.");
            case True:
                return new ResponseEntity(HttpStatus.OK);
        }

        throw new InternalServerException("Unexpected result");
    }


    private String generateNotFoundMessage(long id){
        return "Team with id = \'" + id + "\' does not exist";
    }
}
