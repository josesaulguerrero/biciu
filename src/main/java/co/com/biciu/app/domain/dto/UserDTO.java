package co.com.biciu.app.domain.dto;

import java.util.List;

public class UserDTO {
    private String userId;
    private String DNI;
    private String userType;
    private String fullName;
    private Integer age;
    private Double credit;
    private List<String> ticketsIds;

    public UserDTO() {
    }

    public UserDTO(String DNI, String userType, String fullName, Integer age, Double credit, List<String> ticketsIds) {
        this.DNI = DNI;
        this.userType = userType;
        this.fullName = fullName;
        this.age = age;
        this.credit = credit;
        this.ticketsIds = ticketsIds;
    }

    public UserDTO(String userId, String DNI, String userType, String fullName, Integer age, Double credit, List<String> ticketsIds) {
        this.userId = userId;
        this.DNI = DNI;
        this.userType = userType;
        this.fullName = fullName;
        this.age = age;
        this.credit = credit;
        this.ticketsIds = ticketsIds;
    }

    public String getUserId() {
        return userId;
    }

    public String getDNI() {
        return DNI;
    }

    public String getUserType() {
        return userType;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getAge() {
        return age;
    }

    public Double getCredit() {
        return credit;
    }

    public List<String> getTicketsIds() {
        return ticketsIds;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public void setTicketsIds(List<String> ticketsIds) {
        this.ticketsIds = ticketsIds;
    }
}
