package nextstep.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.doesNotHave;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class TypeConverterTest {

    @Test
    void int_형변환() {
        // given
        final int expected1 = 10;
        final Integer expected2 = 10;

        // when
        final Object actual1 = TypeConverter.to(int.class).apply(String.valueOf(expected1));
        final Object actual2 = TypeConverter.to(Integer.class).apply(String.valueOf(expected2));

        // then
        assertThat(actual1).isEqualTo(expected1);
        assertThat(actual2).isEqualTo(expected2);
    }

    @Test
    void long_형변환() {
        // given
        final long expected1 = 10L;
        final Long expected2 = 10L;

        // when
        final Object actual1 = TypeConverter.to(long.class).apply(String.valueOf(expected1));
        final Object actual2 = TypeConverter.to(Long.class).apply(String.valueOf(expected2));

        // then
        assertThat(actual1).isEqualTo(expected1);
        assertThat(actual2).isEqualTo(expected2);
    }

    @Test
    void String_일경우_형변환_X() {
        // given
        final String expected = "ax";

        // when
        final Object actual = TypeConverter.to(String.class).apply(expected);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}