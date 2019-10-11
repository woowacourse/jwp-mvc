package learningtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LambdaTest {
    @Test
    @DisplayName("람다로 전달된 변수는 레퍼런스로 접근이 되는 건지?")
    void test() {
        Car previousCar = new Car("previous");
        System.out.printf("outside callback car: %s\n", previousCar);

//        오호 .. final 안붙이면 경고가 뜨는구나
//        previousCar = new Car("new");

        printCarName(() -> {
            System.out.printf("inside callback car: %s\n", previousCar);
            return previousCar.getName();
        });
    }

    private void printCarName(Supplier<String> getName) {
        System.out.println("getName: " + getName.get());
    }

    class Car {
        private final String name;

        Car(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @Test
    @DisplayName("람다 내부에서 발생한 exception 은 외부에서 보이는지")
    void check_RuntimeExceptionCanBeThrownFromOptionalIfPresent() {
        assertThrows(NullPointerException.class, () -> {
            Optional.of(100).ifPresent((i) -> {
                throw new NullPointerException();
            });
        });
    }


}
