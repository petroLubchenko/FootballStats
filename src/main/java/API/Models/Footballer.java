package API.Models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;



@Entity
@Table(name = "FOOTBALLER")
public class Footballer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FOOTBALLER_ID")
    private long id;
    @Column(name = "FOOTBALLER_FIRSTNAME")
    private String firstname;
    @Column(name = "FOOTBALLER_SURNAME")
    private String surname;

    @JoinColumn(name = "FOOTBALLER_TEAM")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team team;

    @Column(name = "FOOTBALLER_AGE")
    private int age;
    @Column(name = "FOOTBALLER_GAMES")
    private int games;
    @Column(name = "FOOTBALLER_GOALS")
    private int goals;
    @Column(name = "FOOTBALLER_ASSISTS")
    private int assists;


    public Footballer(){
        firstname = null;
        surname = null;
        // TODO team
        age = 15;
        games = 0;
        goals = 0;
        assists = 0;
    }

    public Footballer(String firstname, String surname){
        this.firstname = firstname;
        this.surname = surname;
        // TODO team
        age = 15;
        games = 0;
        goals = 0;
        assists = 0;
    }

    public Footballer(String firstname, String surname, int age, int games, int goals, int assists){
        this.firstname = firstname;
        this.surname = surname;
        // TODO team
        this.age = age;
        this.games = games;
        this.goals = goals;
        this.assists = assists;
    }

    public Footballer(long id, String firstname, String surname, int age, int games, int goals, int assists){
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        // TODO team
        this.age = age;
        this.games = games;
        this.goals = goals;
        this.assists = assists;
    }

    public Footballer(String firstname, String surname, Team team, int age, int games, int goals, int assists){
        this.firstname = firstname;
        this.surname = surname;
        this.team = team;
        this.age = age;
        this.games = games;
        this.goals = goals;
        this.assists = assists;
    }

    public Footballer(long id, String firstname, String surname, Team team, int age, int games, int goals, int assists){
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.team = team;
        this.age = age;
        this.games = games;
        this.goals = goals;
        this.assists = assists;
    }


    @Override
    public boolean equals(Object object){
        if (object != null)
            if (object instanceof Footballer)
                return this.id == ((Footballer) object).id && this.age == ((Footballer) object).age &&
                        this.games == ((Footballer) object).games && this.goals == ((Footballer) object).goals &&
                        Objects.equals(this.firstname, ((Footballer) object).firstname) && this.assists == ((Footballer) object).assists &&
                        Objects.equals(this.surname, ((Footballer) object).surname) && this.team.equals(((Footballer) object).team);

         return false;
    }

    public boolean isValid(boolean checkId){

        if (checkId && id == 0L)
            return false;

        if (firstname == null || surname == null || age <= 15)
            return false;

        return true;
    }


    // Getters
    public int getAge() {
        return age;
    }

    public int getAssists() {
        return assists;
    }

    public int getGames() {
        return games;
    }

    public int getGoals() {
        return goals;
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public Team getTeam() {
        return team;
    }

    // Setters
    public void setAge(int age) {
        this.age = age;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
