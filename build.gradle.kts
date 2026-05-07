import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    kotlin("jvm") version "2.+"
    id("com.gradleup.shadow") version "9.+"
    id("xyz.jpenilla.run-paper") version "2.+"
    id("de.eldoria.plugin-yml.paper") version "0.7.+"
    kotlin("plugin.serialization") version "2.+"
    id("maven-publish")
}

val mcVersion = properties["minecraftVerions"] as String
val projectVersion = properties["version"] as String
val projectName = properties["name"] as String
val groupID = properties["group"] as String
val mainClass = properties["main"] as String
val projectDescription = properties["description"] as String
val twilightVersion = properties["twilightVersion"] as String
val commandAPIVersion = properties["commandAPIVersion"] as String

group = groupID
version = projectVersion

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven("https://repo.flyte.gg/releases")
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Paper
    compileOnly("io.papermc.paper:paper-api:$mcVersion")

    // Twilight
    implementation("gg.flyte:twilight:${twilightVersion}")

    // Command API
    compileOnly("dev.jorel:commandapi-paper-core:$commandAPIVersion")
    implementation("dev.jorel:commandapi-paper-shade:$commandAPIVersion")
    implementation("dev.jorel:commandapi-kotlin-paper:$commandAPIVersion")
}

kotlin {
    jvmToolchain(25)
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks {
    runServer {
        minecraftVersion(mcVersion)
        serverJar(File("/home/xyzjesper/Dokumente/GitHub/Color-Status/run/paper-26.1.2-60.jar"))
    }
}

publishing {
    repositories {
        maven {
            name = "Reposilite"
            url = uri("https://repo.jespersen.zip/releases")
            credentials {
                username = System.getenv("REPOSILITE_USER") ?: System.getProperty("REPOSILITE_USER") ?: "USERNAME"
                password = System.getenv("REPOSILITE_TOKEN") ?: System.getProperty("REPOSILITE_TOKEN") ?: "TOKEN"
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("reposilite") {
            from(components["java"])
            artifactId = "xtransfer"
            groupId = groupID
            version = projectVersion
        }
    }
}

paper {
    name = projectName
    version = projectVersion
    foliaSupported = true
    description = projectDescription
    main = mainClass
    load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
    authors = listOf("jespersen")
    apiVersion = "1.21"
}
