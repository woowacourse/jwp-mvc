package nextstep.mvc.mock;

public class TestUser {
    private String userId;
    private String password;
    private Integer age;

    public TestUser() {

    }

    public TestUser(String userId, String password, Integer age) {
        this.userId = userId;
        this.password = password;
        this.age = age;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }
}
