package slipp.dto;

public class UserLoginDto {
    private String userId;
    private String password;

    public UserLoginDto() {
    }

    public UserLoginDto(String userId, String password) {
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
