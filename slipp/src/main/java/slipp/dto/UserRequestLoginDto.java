package slipp.dto;

public class UserRequestLoginDto {
    private String userId;
    private String password;

    public UserRequestLoginDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
