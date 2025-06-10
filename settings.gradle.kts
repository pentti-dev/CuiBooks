// settings.gradle.kts
pluginManagement {
    val springBootVersion = "3.5.0"
    val depMgmtVersion = "1.1.7"
    val asciidoctorVersion = "3.3.2"
    val spotlessVersion = "7.0.4"

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version depMgmtVersion
        id("org.asciidoctor.jvm.convert") version asciidoctorVersion
        id("com.diffplug.spotless") version spotlessVersion
    }
}

rootProject.name = "your-project-name"

rootProject.name = "fahabook"
