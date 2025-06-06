plugins {
    java
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    id("com.diffplug.spotless") version "7.0.4"
}

group = "vn.edu.hcmuaf.fit"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

// Định nghĩa biến snippetsDir từ extra properties
val snippetsDir: File by extra(file("${buildDir.path}/generated-snippets"))

dependencies {
    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

    // Core libraries
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.springframework:spring-context-support")
    implementation("org.springframework.security:spring-security-crypto")
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("org.springframework:spring-webflux:6.1.14")

    // Lombok & MapStruct
    val lombokVersion = "1.18.32"
    val mapstructVersion = "1.6.3"
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    // Database drivers
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("com.h2database:h2:2.2.224")

    // Cloudinary & dotenv
    implementation("com.cloudinary:cloudinary-http44:1.38.0")
    implementation("com.cloudinary:cloudinary-taglib:1.36.0")
    implementation("io.github.cdimascio:dotenv-java:2.2.4")

    // OpenAPI & GraphQL UI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    implementation("org.webjars.npm:graphql-playground-html:1.6.30")
    implementation("com.graphql-java:graphql-java-extended-scalars:22.0")

    // Other third-party
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("org.apache.poi:poi:5.4.1")
    implementation("org.apache.poi:poi-ooxml:5.4.1")

    // Devtools & Docker Compose
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Spring configuration processor
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testng:testng:7.10.2")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mysql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
    outputs.dir(snippetsDir)
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
}

spotless {
    java {
        palantirJavaFormat()
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
        importOrder("java", "jakarta", "org", "com", "com.diffplug")
    }
}
