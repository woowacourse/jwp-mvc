package reflection.helper;

import java.lang.reflect.Method;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpectedMethod {
    private final Class<?> declaringClass;
    private final String name;
    private final int modifiers;
    private final Class<?>[] parameterTypes;
    private final Class<?>[] exceptionTypes;
    private final Class<?> returnType;

    public ExpectedMethod(Class<?> declaringClass, String name, int modifiers, Class<?>[] parameterTypes, Class<?>[] exceptionTypes, Class<?> returnType) {
        this.declaringClass = declaringClass;
        this.name = name;
        this.modifiers = modifiers;
        this.parameterTypes = parameterTypes;
        this.exceptionTypes = exceptionTypes;
        this.returnType = returnType;
    }

    public static class Builder {
        private Class<?> declaringClass;
        private String name;
        private int modifiers;
        private Class<?>[] parameterTypes = {};
        private Class<?>[] exceptionTypes = {};
        private Class<?> returnType;

        public ExpectedMethod build() {
            return new ExpectedMethod(declaringClass, name, modifiers, parameterTypes, exceptionTypes, returnType);
        }

        public Builder declaringClass(Class<?> declaringClass) {
            this.declaringClass = declaringClass;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder modifiers(int modifiers) {
            this.modifiers = modifiers;
            return this;
        }

        public Builder parameterTypes(Class<?>[] parameterTypes) {
            this.parameterTypes = parameterTypes;
            return this;
        }

        public Builder returnType(Class<?> returnType) {
            this.returnType = returnType;
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

    public void assertMethod(Method method) {
        assertThat(declaringClass).isEqualTo(method.getDeclaringClass());
        assertThat(name).isEqualTo(method.getName());
        assertThat(modifiers).isEqualTo(method.getModifiers());
        assertThat(Objects.deepEquals(parameterTypes, method.getParameterTypes())).isTrue();
        assertThat(Objects.deepEquals(exceptionTypes, method.getExceptionTypes())).isTrue();
        assertThat(returnType).isEqualTo(method.getReturnType());
    }
}
