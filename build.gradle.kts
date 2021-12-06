import com.google.protobuf.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("org.springframework.boot") version "2.5.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    id("com.google.protobuf") version "0.8.17"
    kotlin("plugin.serialization") version "1.6.0"

}


group = "com.kuzmin"
version = "0.0.2-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
var springCloudVersion = "2020.0.4"

var grpcVersion = "1.41.0" // CURRENT_GRPC_VERSION
var protobufVersion = "3.17.2"
var protocVersion = protobufVersion

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springframework.session:spring-session-data-redis")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.projectlombok:lombok:1.18.20")
    //implementation("org.springframework.retry:spring-retry")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")

    //gRPC
    implementation("io.grpc:grpc-protobuf:${grpcVersion}")
    implementation("io.grpc:grpc-stub:${grpcVersion}")
    implementation("com.google.protobuf:protobuf-java-util:${protobufVersion}")
    runtimeOnly("io.grpc:grpc-netty-shaded:${grpcVersion}")
    testImplementation("io.grpc:grpc-testing:${grpcVersion}")
    implementation("io.grpc:protoc-gen-grpc-kotlin:1.2.0")
    implementation("io.grpc:grpc-kotlin-stub:1.2.0")

    testImplementation("org.testcontainers:mongodb:1.15.2")
    testImplementation("org.testcontainers:junit-jupiter:1.16.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
    }
}

tasks.withType<BootBuildImage> {
    imageName = "kuzmin35/${project.name}:${project.version}"
    environment = mapOf("BP_JVM_VERSION" to "11.0.13")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${protobufVersion}"
    }
    //generatedFilesBaseDir = "$projectDir/src/main/kotlin/com/kuzmin/bookcatalog/grpc/generated"
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.2.0:jdk7@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}