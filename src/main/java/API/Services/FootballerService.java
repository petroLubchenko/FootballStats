package API.Services;

import API.Additional.Result;
import API.Models.Footballer;
import API.Repository.FootballerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FootballerService {

    private final FootballerRepository footballerRepository;

    @Autowired
    public FootballerService(FootballerRepository footballerRepository){
        this.footballerRepository = footballerRepository;
    }

    public Footballer getById(long id){
        Optional<Footballer> res = footballerRepository.findById(id);

        return res.isPresent() ? res.get() : null;
    }

    public List<Footballer> getAll(){
        return (List<Footballer>)footballerRepository.findAll();
    }

    public boolean addFootballer(Footballer footballer){
        if (footballer == null)
            return false;

        Footballer f = footballerRepository.save(footballer);
        return f != null;
    }

    public Result delete(long id){
        if (!footballerRepository.existsById(id))
            return Result.Not_Found;
        footballerRepository.deleteById(id);
        return footballerRepository.existsById(id) ? Result.False : Result.True;
    }

    public Result update(Footballer footballer){
        if (!footballerRepository.existsById(footballer.getId()))
            return Result.Not_Found;
        Footballer res = footballerRepository.save(footballer);
        return res != null ? Result.True : Result.False;
    }
}
