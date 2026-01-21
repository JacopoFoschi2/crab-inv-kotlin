plugins {
    java
    application
}

repositories {
    mavenCentral()
}

val javaFXModules = listOf("base","controls","fxml","swing","graphics", "media")

java { // Useful to set Java version for Gradle tasks
    toolchain { languageVersion.set(JavaLanguageVersion.of(25)) }
}

val javaFxVersion = 25
val supportedPlatforms = listOf("linux", "mac", "win")

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    for (platform in supportedPlatforms) {
        for (module in javaFXModules) {
            implementation("org.openjfx:javafx-$module:$javaFxVersion:$platform")
        }
    }
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
}

tasks.test {
    useJUnitPlatform()
}