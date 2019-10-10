package support.test;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class NsWebServer {
    static {
        new Thread(() -> {
            String webappDirLocation = "webapp/";
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(8080);

            tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());

            try {
                tomcat.start();
            } catch (LifecycleException e) {
                e.printStackTrace();
            }
            tomcat.getServer().await();
        }).start();
    }
}
