package reflection.helper;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpectedField {
    private final Class<?> declaringClass;
    private final String name;
    private final Class<?> type;
    private final int modifiers;

    public ExpectedField(Class<?> declaringClass, String name, Class<?> type, int modifiers) {
        this.declaringClass = declaringClass;
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
    }

    public static class Builder {
        private Class<?> declaringClass;
        private String name;
        private Class<?> type;
        private int modifiers;

        public ExpectedField build() {
            return new ExpectedField(declaringClass, name, type, modifiers);
        }

        public Builder declaringClass(Class<?> declaringClass) {
            this.declaringClass = declaringClass;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(Class<?> type) {
            this.type = type;
            return this;
        }

        public Builder modifiers(int modifiers) {
            this.modifiers = modifiers;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public void assertField(Field field) {
        assertThat(declaringClass).isEqualTo(field.getDeclaringClass());
        assertThat(name).isEqualTo(field.getName());
        assertThat(type).isEqualTo(field.getType());
        assertThat(modifiers).isEqualTo(field.getModifiers());
    }
}
