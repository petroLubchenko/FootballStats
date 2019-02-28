package API.Services;

import API.Additional.Result;
import API.Models.Team;
import API.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public Team getById(long id){
        Optional<Team> optteam = teamRepository.findById(id);

        return optteam.isPresent() ? optteam.get() : null;
    }

    public List<Team> getAll(){
        return (List<Team>)(teamRepository.findAll());
    }

    public Boolean addTeam(Team team){
        if (team == null)
            return false;

        if (teamRepository.existsById(team.getId()))
            return false;

        return teamRepository.save(team) != null;
    }

    public Result delete(long id){
        if (!teamRepository.existsById(id))
            return Result.Not_Found;

        teamRepository.deleteById(id);

        return teamRepository.existsById(id) ? Result.False : Result.True;
    }

    public  Result update(Team team){
        if (!teamRepository.existsById(team.getId()))
            return Result.Not_Found;

        Team t = teamRepository.save(team);

        return t == null ? Result.False : Result.True;
    }
}
