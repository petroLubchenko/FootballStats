package API.Controllers;

import API.Additional.Result;
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

        return notFoundHandler(generateNotFoundMessage(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        Result result = teamService.delete(id);

        if (result == Result.Not_Found)
            return notFoundHandler(generateNotFoundMessage(id));

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

        ResponseEntity re = new ResponseEntity(issuccess ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
        return re;
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
                return notFoundHandler(generateNotFoundMessage(team.getId())); // TODO
            case False:
                return  new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR); // TODO
            case True:
                return new ResponseEntity(HttpStatus.OK); // TODO
        }

        return new ResponseEntity(HttpStatus.EXPECTATION_FAILED); // TODO 503 service unavailable
    }


    private String generateNotFoundMessage(long id){
        return "Team with id = \'" + id + "\' does not exist";
    }
}
