plugins {
    application
    java
}

application {
    mainClass.value("org.purpurmc.purformance.App")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
}
