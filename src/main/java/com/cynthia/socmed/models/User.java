package com.cynthia.socmed.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity

public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    private String passwordc;
    private String salt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;
    private int age;
   @ManyToOne
    private Country country;
    private File profilePicture;
    private String profilePicturePath;
    @ManyToMany
    private List <Post> likedPosts;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<User> friends;

    @ManyToOne
    private Role role ;

    @ManyToMany
    private List<User> blocked;



    private String resetPassword;
    private String resetToken;
    @OneToOne
    private ConfirmationToken confirmationToken;
    private boolean friendListIsPublic;

    public User() {
    }

    public User(int id, String username, String email, String password, String passwordc, String salt, LocalDate birthdate,
                int age, Country country, File profilePicture,
                String profilePicturePath, Role role,
                String resetPassword, String resetToken,
                ConfirmationToken confirmationToken,
                boolean friendListIsPublic) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordc = passwordc;
        this.salt = salt;
        this.birthdate = birthdate;
        this.age = age;
        this.country = country;
        this.profilePicture = profilePicture;
        this.profilePicturePath = profilePicturePath;
        this.role = role;
        this.resetPassword = resetPassword;
        this.resetToken = resetToken;
        this.confirmationToken = confirmationToken;
        this.friendListIsPublic = friendListIsPublic;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordc() {
        return passwordc;
    }

    public void setPasswordc(String passwordc) {
        this.passwordc = passwordc;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public File getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(File profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }





    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }







    public String getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(String resetPassword) {
        this.resetPassword = resetPassword;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public ConfirmationToken getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(ConfirmationToken confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public boolean isFriendListIsPublic() {
        return friendListIsPublic;
    }

    public void setFriendListIsPublic(boolean friendListIsPublic) {
        this.friendListIsPublic = friendListIsPublic;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +

                ", birthdate=" + birthdate +
                ", profilePicture=" + profilePicture +
                ", country=" + country +
                ", resetPassword='" + resetPassword + '\'' +
                ", confirmationToken=" + confirmationToken +
                ", friendListIsPublic=" + friendListIsPublic +

                '}';
    }
}
