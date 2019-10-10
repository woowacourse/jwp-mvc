package support.test;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class TestServerRunnable implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(TestServerRunnable.class);

    @Override
    public void run() {
        String webappDirLocation = "webapp/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        log.info("configuring app with basedir: {}", new File("./" + webappDirLocation).getAbsolutePath());

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            log.error(e.getMessage());
        }
        tomcat.getServer().await();
    }
}
