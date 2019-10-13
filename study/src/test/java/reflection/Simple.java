package reflection;

public class Simple {
    public String publicString;
    private int privateInt;

    public String toString(){
        return "hello";
    }

    public String withArg(int arg) {
        return "withArg";
    }

    public String withExceptions() throws NullPointerException, IllegalAccessException {
        return "widhExceptions";
    }
}
