import org.jetbrains.kotlin.de.undercouch.gradle.tasks.download.Download
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("com.microsoft.azure.azurewebapp") version "1.10.0"
    id("de.undercouch.download") version "5.5.0"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.allopen") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
    kotlin("kapt") version "1.8.22"
}

group = "com.wienenergie"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api-kotlin
    runtimeOnly("org.apache.logging.log4j:log4j-api-kotlin:1.3.0")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += "-Xjsr305=strict"
    }
}


task<DefaultTask>("downloadNewrelic") {
    val url = "https://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic-java.zip"
    val dest = File("newrelic-java.zip")
    task<Download>("download-newrelic-task") {
        src(url)
        dest(dest)
    }
    dependsOn("download-newrelic-task")
}

tasks.register<Copy>("unzipNewrelic") {
    group = "build"
    description = "Unzips the New Relic agent"

    val newRelicDir = layout.projectDirectory
    val newRelicZip = newRelicDir.file("newrelic-java.zip")

    dependsOn("downloadNewrelic")

    from(zipTree(newRelicZip))
    into(layout.projectDirectory)

    doLast {
        // Clean up: Delete the downloaded ZIP file
        newRelicZip.asFile.delete()
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

azurewebapp {
    subscription = ""
    resourceGroup = ""
    appName = ""
    pricingTier = ""
    region = "westeurope"
    setRuntime(closureOf<com.microsoft.azure.gradle.configuration.GradleRuntimeConfig> {
        os("Linux")
        webContainer("Java SE")
        javaVersion("Java 17")
    })
    setAppSettings(closureOf<MutableMap<String, String>> {
        put("NEW_RELIC_APP_NAME", "Calculator")
        put("NEW_RELIC_ENVIRONMENT", "production")
        put("NEW_RELIC_LICENSE_KEY", "")
        put("JAVA_OPTS", "-javaagent:/home/site/wwwroot/apm/newrelic/newrelic.jar")
    })
    setAuth(closureOf<com.microsoft.azure.gradle.auth.GradleAuthConfig> {
        type = "azure_cli"
    })
}