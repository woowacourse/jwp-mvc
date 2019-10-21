package nextstep.mvc.mock;

public class StringTestUser {
    private String userId;
    private String password;

    public StringTestUser() {

    }

    public StringTestUser(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public String toString() {
        return "TestUser{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
