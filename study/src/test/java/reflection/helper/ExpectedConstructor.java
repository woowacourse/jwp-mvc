package reflection.helper;

import java.lang.reflect.Constructor;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpectedConstructor {
    private final int modifiers;
    private final Class<?>[] parameterTypes;
    private final Class<?>[] exceptionTypes;

    public ExpectedConstructor(int modifiers, Class<?>[] parameterTypes, Class<?>[] exceptionTypes) {
        this.modifiers = modifiers;
        this.parameterTypes = parameterTypes;
        this.exceptionTypes = exceptionTypes;
    }

    public static class Builder {
        private int modifiers;
        private Class<?>[] parameterTypes = {};
        private Class<?>[] exceptionTypes = {};

        public ExpectedConstructor build() {
            return new ExpectedConstructor(modifiers, parameterTypes, exceptionTypes);
        }

        public Builder modifiers(int modifiers) {
            this.modifiers = modifiers;
            return this;
        }

        public Builder parameterTypes(Class<?>[] parameterTypes) {
            this.parameterTypes = parameterTypes;
            return this;
        }

        public Builder exceptionTypes(Class<?>[] exceptionTypes) {
            this.exceptionTypes = exceptionTypes;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public void assertConstructor(Constructor<?> constructor) {
        assertThat(modifiers).isEqualTo(constructor.getModifiers());
        assertThat(Objects.deepEquals(parameterTypes, constructor.getParameterTypes())).isTrue();
        assertThat(Objects.deepEquals(exceptionTypes, constructor.getExceptionTypes())).isTrue();
    }

    public boolean isSameSignature(Constructor<?> constructor) {
        if (modifiers != constructor.getModifiers()) {
            return false;
        }

        if (!Objects.deepEquals(parameterTypes, constructor.getParameterTypes())) {
            return false;
        }

        if (!Objects.deepEquals(exceptionTypes, constructor.getExceptionTypes())) {
            return false;
        }

        return true;
    }
}
