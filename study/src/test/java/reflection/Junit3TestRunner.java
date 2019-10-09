package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class Junit3TestRunner {
    private static final Logger logger = LoggerFactory.getLogger(Junit3TestRunner.class);

    @Test
    public void run() throws Exception {
        final Class<Junit3Test> clazz = Junit3Test.class;

        // TODO Junit3Test에서 test로 시작하는 메소드 실행
        Stream.of(clazz.getMethods()).filter(x -> x.getName().startsWith("test"))
                                    .forEach(x -> {
                                        try {
                                            x.invoke(clazz.getDeclaredConstructor().newInstance());
                                        } catch (Exception e) {
                                            logger.error(e.getMessage());
                                        }
                                    });

    }
}