import com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer

plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.purpurmc"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("com.github.Minestom:Minestom:f09d3db999")

    implementation("org.apache.logging.log4j:log4j-api:2.23.1")
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.23.1")

    implementation("org.jline:jline-terminal-jansi:3.21.0")

    implementation("net.minecrell:terminalconsoleappender:1.3.0")
}

application {
    mainClass.value("org.purpurmc.purformance.Main")
}

tasks {
    withType<Jar> {
        manifest {
            attributes["Main-Class"] = "org.purpurmc.purformance.Main"
        }
    }

    shadowJar {
        transform(Log4j2PluginsCacheFileTransformer::class.java)
    }
}


