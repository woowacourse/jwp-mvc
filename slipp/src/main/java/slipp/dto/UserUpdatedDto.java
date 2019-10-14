package slipp.dto;

import slipp.domain.User;

public class UserUpdatedDto {
    private String password;
    private String name;
    private String email;

    private UserUpdatedDto() {
    }

    public UserUpdatedDto(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User toEntity(String userId) {
        return new User(userId, password, name, email);
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "UserUpdatedDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
