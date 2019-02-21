package API.Services;

import API.Models.Footballer;
import API.Models.Team;

import java.util.List;

import static org.junit.Assert.*;

public class FootballerServiceTest {

    private Footballer footballer;
    private Team team;
    private Footballer addedFootballer;

    FootballerService footballerService;

    @org.junit.Before
    public void setUp() throws Exception {
        team = new Team("QQQ", "CCC", "SS", 13);
        footballer = new Footballer("AAA", "BBB",  team, 33, 756, 313, 22);

        footballerService.addFootballer(footballer);

        List<Footballer> footballers = footballerService.getAll();

        addedFootballer = footballers.get(footballers.size() - 1);
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }



    @org.junit.Test
    public void getById() {
        assertEquals(addedFootballer, footballerService.getById(addedFootballer.getId()));
    }

    @org.junit.Test
    public void getAll() {
    }

    @org.junit.Test
    public void addFootballer() {
    }

    @org.junit.Test
    public void delete() {
    }

    @org.junit.Test
    public void update() {
    }
}