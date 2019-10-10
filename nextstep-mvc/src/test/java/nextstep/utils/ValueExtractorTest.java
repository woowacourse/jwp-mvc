package nextstep.utils;

import org.junit.jupiter.api.Test;

import java.lang.annotation.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class ValueExtractorTest {

    @Test
    void __() {
        Hello[] hello = {Hello.HELLO};
        assertThat(Objects.deepEquals(hello, new Hello[]{Hello.HELLO})).isTrue();

        Map<String, Object> expected = new HashMap<>() {{
            put("value", "value");
            put("hello", new Hello[]{Hello.HELLO});
        }};
        Map<String, Object> expected2 = Collections.unmodifiableMap(new HashMap<>() {{
            put("value", "value");
            put("hello", new Hello[]{Hello.HELLO});
        }});
        // 아.. mapDeepEquals 에서 []일 때만 처리를 해주고 있음
        // 그래서 map인 경우 그냥 equals 가 호출되고 value() 값이 [] 일때도 기존의 equals 로 적용(주소값비교ㅠ)
        assertThat(Objects.deepEquals(expected, expected2)).isFalse();
    }

    @Test
    void fromAnnotation() {
        ValueTargets targets = ValueTargets.from(new HashMap<>() {{
            put("value", String.class);
            put("hello", Hello[].class);
        }});

        Map<String, Object> expected;

        expected = Collections.EMPTY_MAP;
        assertThat(mapDeepEquals(extractFromTestTarget(EmptyAnnotation.class, targets), expected))
                .isTrue();

        expected = new HashMap<>() {{
            put("value", "value");
        }};
        assertThat(mapDeepEquals(extractFromTestTarget(AnnotationWithValue.class, targets), expected))
                .isTrue();

        expected = new HashMap<>() {{
            put("value", "value");
            put("hello", new Hello[]{Hello.HELLO});
        }};
        assertThat(mapDeepEquals(extractFromTestTarget(AnnotationWithValueAndHello.class, targets), expected)).isTrue();
    }

    @Test
    void fromAnnotation_존재하지_않는_target() {
        ValueTargets targets = ValueTargets.from(new HashMap<>() {{
            put("notExist", String.class);
        }});

        assertThat(mapDeepEquals(extractFromTestTarget(AnnotationWithValueAndHello.class, targets), ValueExtractor.EMPTY));
    }

    @Test
    void fromAnnotation_잘못된_target클래스_target() {
        class WrongTarget {
        }
        ValueTargets targets = ValueTargets.from(new HashMap<>() {{
            put("value", WrongTarget.class);
        }});

        assertThat(mapDeepEquals(extractFromTestTarget(AnnotationWithValueAndHello.class, targets), ValueExtractor.EMPTY));
    }

    private Map<String, Object> extractFromTestTarget(Class<? extends Annotation> annotation, ValueTargets targets) {
        System.out.println(TestTarget.class.getAnnotation(annotation).annotationType());
        return ValueExtractor.extractFromAnnotation(TestTarget.class.getAnnotation(annotation), targets);
    }

    private <T, U> boolean mapDeepEquals(Map<T, U> map1, Map<T, U> map2) {
        for (T key : map1.keySet()) {
            if (!Objects.deepEquals(map1.get(key), map2.get(key))) {
                return false;
            }
        }
        return true;
    }

    // 이름이 잘못된 경우
    // 타겟 클래스가 잘못된 경우
    // 접근자 잘못 설정되어 있는 경우
    enum Hello {
        HELLO
    }

    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface EmptyAnnotation {
    }

    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface AnnotationWithValue {
        String value() default "";
    }

    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface AnnotationWithValueAndHello {
        String value() default "";

        Hello[] hello() default {};
    }

    @EmptyAnnotation
    @AnnotationWithValue(value = "value")
    @AnnotationWithValueAndHello(value = "value", hello = {Hello.HELLO})
    class TestTarget {
    }
}