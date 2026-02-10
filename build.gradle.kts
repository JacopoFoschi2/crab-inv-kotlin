plugins {
    java
    application
    id("com.gradleup.shadow") version "9.3.1"
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
    testImplementation("org.mockito:mockito-core:5.+")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    for (platform in supportedPlatforms) {
        for (module in javaFXModules) {
            implementation("org.openjfx:javafx-$module:$javaFxVersion:$platform")
        }
    }
    implementation("com.google.code.gson:gson:2.10.1")
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
    testCompileOnly("org.projectlombok:lombok:1.18.42")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.42")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("it.unibo.crabinv.App\$Main")
}