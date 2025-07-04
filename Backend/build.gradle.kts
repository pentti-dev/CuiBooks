val javaVersion = JavaVersion.VERSION_21
val lombokVersion = "1.18.32"
val mapstructVersion = "1.6.3"
val lombokMapstructBindingVersion = "0.2.0"
val springDocVersion = "2.8.8"
val graphqlPlaygroundVersion = "1.6.30"
val cloudinaryVersion = "1.38.0"
val cloudinaryTaglibVersion = "1.36.0"
val dotenvVersion = "2.2.4"
val mysqlConnectorVersion = "9.3.0"
val testNgVersion = "7.10.2"
val springWebfluxVersion = "6.1.14"
val apacheCommonsVersion = "3.12.0"
val apachePoiVersion = "5.4.1"
val jBcryptVersion = "0.4"
val graphQLExtendScalarsVersion = "22.0"

plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.asciidoctor.jvm.convert")
    id("com.diffplug.spotless")
}

group = "vn.edu.hcmuaf.fit"
version = "0.0.1-SNAPSHOT"


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion.majorVersion.toInt()))
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


val snippetsDir = layout.buildDirectory.dir("generated-snippets")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.springframework:spring-context-support")
    implementation("org.springframework.security:spring-security-crypto")
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("org.springframework:spring-webflux:$springWebfluxVersion")
    implementation("com.cloudinary:cloudinary-http44:$cloudinaryVersion")
    implementation("com.cloudinary:cloudinary-taglib:$cloudinaryTaglibVersion")
    implementation("io.github.cdimascio:dotenv-java:$dotenvVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")
    implementation("org.webjars.npm:graphql-playground-html:$graphqlPlaygroundVersion")
    implementation("com.graphql-java:graphql-java-extended-scalars:$graphQLExtendScalarsVersion")
    implementation("org.apache.commons:commons-lang3:$apacheCommonsVersion")
    implementation("org.mindrot:jbcrypt:$jBcryptVersion")
    implementation("org.apache.poi:poi:$apachePoiVersion")
    implementation("org.apache.poi:poi-ooxml:$apachePoiVersion")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    compileOnly("org.mapstruct:mapstruct:$mapstructVersion")

    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:$lombokMapstructBindingVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("com.mysql:mysql-connector-j:$mysqlConnectorVersion")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testng:testng:$testNgVersion")
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
