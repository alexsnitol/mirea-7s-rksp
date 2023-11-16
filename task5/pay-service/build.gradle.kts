plugins {
    java
    id("org.springframework.boot") version "2.7.17"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.web3j.solidity") version "0.4.0"
    id("org.web3j") version "4.10.3"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

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
}

web3j {
    generatedPackageName = "com.example.payservice.contracts.generated"
}

extra["springCloudVersion"] = "2021.0.8"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework:spring-jdbc")
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.7.0")
    implementation("org.web3j:core:4.10.3")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
