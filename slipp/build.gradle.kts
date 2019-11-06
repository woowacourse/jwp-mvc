plugins {
    idea
}

idea {
    module {
        inheritOutputDirs = false
        outputDir = file("webapp/WEB-INF/classes")
    }
}

dependencies {
    api(project(":nextstep-mvc"))

    val springVersion = rootProject.extra.get("springVersion")
    val tomcatVersion = rootProject.extra.get("tomcatVersion")

    implementation("org.springframework:spring-jdbc:$springVersion")
    implementation("org.springframework:spring-web:$springVersion")

    implementation("org.apache.commons:commons-dbcp2:2.6.0")

    runtimeOnly("com.h2database:h2:1.4.199")

    implementation("org.apache.tomcat.embed:tomcat-embed-core:$tomcatVersion")
    implementation("org.apache.tomcat.embed:tomcat-embed-logging-juli:8.5.2")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:$tomcatVersion")

    implementation("commons-io:commons-io:2.6")
    implementation("ch.qos.logback:logback-access:1.2.3")

    runtimeOnly ("net.rakugakibox.spring.boot:logback-access-spring-boot-starter:2.7.1")

}
