package com.example.givinghandproject.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.givinghandproject.utilities.enums.UserType;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Column(name = "Full name" , nullable = false)
    private String fullName;

    @Column(name = "Email" , nullable = false)
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Password" , nullable = false)
    private String password;

    @Column(name = "Birth Date" ,nullable = false)
    private LocalDate  birthDate;

    @Column(name = "Bio")
    private String bio;

    @Column(name = "Role" , nullable = false)
    @Enumerated(EnumType.STRING) // saved in DB as String
    private UserType role;

    @ElementCollection
    @CollectionTable(
            name = "user_donation_history",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "log_entry")
    private List<String> donationLogHistory = new ArrayList<>();

    // Navigation properties
    @OneToMany(mappedBy = "organization")
    private List<Campaign> campaigns;

    @OneToMany(mappedBy = "organization")
    private List<Warehouse> warehouses;

    @OneToMany(mappedBy = "donor")
    private List<Donation> donations;

    // JPA have to use a default constructor
    public User(){}

    // Getters and Setters to JSON Ser&Des


    public String getFullName(){return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public UserType getRole() { return role; }
    public void setRole(UserType role) { this.role = role; }

    // for Donation


    // setters and getters for navigation relations

    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    // Donation requirement
    public void setDonationLogHistory(List<String> donationLogHistory) {
        this.donationLogHistory = donationLogHistory;
    }
    public List<String> getDonationLogHistory() {
        return donationLogHistory;
    }
}
