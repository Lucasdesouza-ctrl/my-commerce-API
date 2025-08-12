import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.Base64

plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "me.study"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.mindrot:jbcrypt:0.4")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
	implementation ("org.mapstruct:mapstruct:1.5.5.Final")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.mysql:mysql-connector-j")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor ("org.mapstruct:mapstruct-processor:1.5.5.Final")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register("generateJwtKeys") {
	group = "build"
	description = "Generates RSA public and private keys for JWT"

	doLast {
		val keyDir = file("src/main/resources/keys").apply { mkdirs() }

		// Generate RSA Key Pair (2048-bit)
		val keyPairGenerator = KeyPairGenerator.getInstance("RSA").apply {
			initialize(2048)
		}
		val keyPair = keyPairGenerator.generateKeyPair()
		val publicKey = keyPair.public as RSAPublicKey
		val privateKey = keyPair.private as RSAPrivateKey

		// Write keys in PEM format
		fun encodeKeyToPem(key: ByteArray, type: String): String {
			val base64Key = Base64.getEncoder().encodeToString(key)
			val key = base64Key.chunked(64).joinToString("\n");

			return "-----BEGIN $type KEY-----\n${key}\n-----END $type KEY-----"
		}

		val publicPem = encodeKeyToPem(publicKey.encoded, "PUBLIC")
		val privatePem = encodeKeyToPem(privateKey.encoded, "PRIVATE")

		// Save keys to files
		keyDir.resolve("public.pub").writeText(publicPem)
		keyDir.resolve("private.key").writeText(privatePem)

		println("\uD83D\uDD11 Generated JWT keys in ${keyDir.path}")
	}
}

tasks.named("bootJar") {
	dependsOn("generateJwtKeys")
}
