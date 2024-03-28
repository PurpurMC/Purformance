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
    implementation("com.github.Minestom:Minestom:17fd82a5c1")

    implementation("org.slf4j:slf4j-api:2.0.12")
    implementation("org.slf4j:slf4j-simple:2.0.12")

    implementation("org.jline:jline-terminal:3.21.0")
    implementation("org.jline:jline-reader:3.21.0")

    implementation("net.minecrell:terminalconsoleappender:1.3.0")
}

application {
    mainClass.value("org.purpurmc.purformance.Main")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.purpurmc.purformance.Main"
    }
}
