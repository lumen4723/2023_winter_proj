import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	kotlin("plugin.jpa") version "1.7.22"
}

group = "com.eyo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework:spring-beans:6.0.4")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	// implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin")
	implementation("org.jetbrains.kotlin:kotlin-script-runtime")
	implementation("org.hibernate.orm:hibernate-core:6.2.0.CR2")
	implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
	implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")
	implementation("jakarta.interceptor:jakarta.interceptor-api:2.1.0")
	implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
	implementation("org.mariadb.jdbc:mariadb-java-client:3.1.1")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	// https://mvnrepository.com/artifact/org.mariadb.jdbc/mariadb-java-client
	implementation("org.mariadb.jdbc:mariadb-java-client:3.1.2")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
