package co.com.biciu.modules.users.persistence.entities;

import co.com.biciu.annotations.Id;
import co.com.biciu.modules.tickets.persistence.entities.Ticket;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User {
    // P-DNI or S-DNI
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("DNI")
    private String DNI;

    @JsonProperty("type")
    private UserType type;

    // name lastname
    @JsonProperty("fullName")
    private String fullName;

    //over 18
    @JsonProperty("age")
    private Integer age;

    @JsonProperty("tickets")
    private List<Ticket> tickets;

    public User() {
    }

    public User(String DNI, UserType type, String fullName, Integer age, List<Ticket> tickets) {
        this.DNI = DNI;
        this.type = type;
        this.fullName = fullName;
        this.age = age;
        this.tickets = tickets;
    }

    public User(String id, String DNI, UserType type, String fullName, Integer age, List<Ticket> tickets) {
        this.id = id;
        this.DNI = DNI;
        this.type = type;
        this.fullName = fullName;
        this.age = age;
        this.tickets = tickets;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public String getId() {
        return id;
    }

    public String getDNI() {
        return DNI;
    }

    public UserType getType() {
        return type;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getAge() {
        return age;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", DNI='" + DNI + '\'' +
                ", type=" + type +
                ", fullName='" + fullName + '\'' +
                ", age=" + age +
                ", tickets=" + tickets +
                '}';
    }
}
