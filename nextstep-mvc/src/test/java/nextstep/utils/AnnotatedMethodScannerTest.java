package nextstep.utils;

import nextstep.web.annotation.RequestMapping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotatedMethodScannerTest {
    private static final AnnotatedMethodScanner scanner = AnnotatedMethodScanner.getInstance();

    @Test
    @DisplayName("타겟 클래스가 1개일 경우")
    void scan_singleTargetClass() throws NoSuchMethodException {
        List<Method> annotatedMethods = scanner.scan(Class1.class, RequestMapping.class);

        assertThat(annotatedMethods.size() == 1).isTrue();
        assertThat(annotatedMethods.get(0)).isEqualTo(Class1.class.getMethod("annotatedMethod"));
    }

    @Test
    @DisplayName("타겟 클래스가 여러개일 경우")
    void scan_multipleTargetClasses() throws NoSuchMethodException {
        List<Method> annotatedMethods = scanner.scan(Arrays.asList(Class1.class, Class2.class), RequestMapping.class);
        List<Method> expectedMethods = Arrays.asList(
                Class1.class.getMethod("annotatedMethod"),
                Class2.class.getMethod("annotatedMethod")
        );

        assertThat(annotatedMethods.size()).isEqualTo(expectedMethods.size());
        for (Method expectedMethod : expectedMethods) {
            assertThat(annotatedMethods.contains(expectedMethod)).isTrue();
        }
    }

    class Class1 {
        @RequestMapping
        public void annotatedMethod() {
        }

        public void notAnnotatedMethod() {
        }
    }

    class Class2 {
        @RequestMapping
        public void annotatedMethod() {
        }

        public void notAnnotatedMethod() {
        }
    }
}