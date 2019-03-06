package API.Services;

import API.Additional.Result;
import API.Controllers.Exceptions.InternalServerException;
import API.Controllers.Exceptions.NotFoundException;
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
        try {
            if (footballer == null)
                return false;

            if (footballerRepository.existsById(footballer.getId()))
                return false;

            Footballer f = footballerRepository.save(footballer);
            return f != null;
        }
        catch (Exception e){
            throw new InternalServerException("Unsuccessful operation. Please check data and try again.");
        }
    }

    public Result delete(long id){
        if (!footballerRepository.existsById(id))
            throw new NotFoundException("Object with id = \'" + id + "\' does not exist.");
        try {
            footballerRepository.deleteById(id);
            if (!footballerRepository.existsById(id))
                return Result.True;
            throw new Exception();
        }
        catch (Exception e){
            throw new InternalServerException("Object has not been deleted. Please try again");
        }
    }

    public Result update(Footballer footballer){
        try {
            if (!footballerRepository.existsById(footballer.getId()))
                throw new NotFoundException("Object with id = \'" + footballer.getId() + "\' does not exist.");
            Footballer res = footballerRepository.save(footballer);
            if (res != null)
                return Result.True;

            throw new Exception();
        }
        catch (Exception e){
            throw new InternalServerException("An error occured during adding team with id = \'" + footballer.getId() +
                    "\'. Please check data and try again.");
        }
    }
}
