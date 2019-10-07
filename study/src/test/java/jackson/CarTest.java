package jackson;

import java.util.Objects;

public class CarTest {
    private String color;
    private String type;

    public CarTest() {
    }

    public CarTest(String color, String type) {
        this.color = color;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarTest carTest = (CarTest) o;
        return Objects.equals(color, carTest.color) &&
                Objects.equals(type, carTest.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }

    @Override
    public String toString() {
        return "CarTest{" +
                "color='" + color + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
