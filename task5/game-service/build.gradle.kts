plugins {
    java
    id("org.springframework.boot") version "2.7.17"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    `maven-publish`
}

group = "org.example"
version = "1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    mavenLocal()
}

extra["springCloudVersion"] = "2021.0.8"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    implementation("org.springframework.boot:spring-boot-starter-webflux") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    implementation("org.springframework.boot:spring-boot-starter-actuator") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    implementation("org.springframework.cloud:spring-cloud-starter-config") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework:spring-jdbc")
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.7.0")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("org.graylog2.log4j2:log4j2-gelf:1.3.1")
    implementation("com.example:payservice-api:0.0.1-SNAPSHOT")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    implementation("org.mockito:mockito-core:5.6.0")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.example"
            artifactId = "gameservice"
            version = "0.0.1-SNAPSHOT"

            from(components["java"])
        }
    }
}