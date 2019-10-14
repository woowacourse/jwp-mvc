package support.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestServerRunner {
    private static final Logger log = LoggerFactory.getLogger(TestServerRunner.class);

    public static void run() {
        Runnable server = new TestServerRunnable();
        ExecutorService es = Executors.newFixedThreadPool(100);

        es.execute(server);

        es.shutdown();
    }
}
