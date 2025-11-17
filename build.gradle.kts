plugins {
    id("fabric-loom") version "1.13-SNAPSHOT"
    id("maven-publish")
    id("com.modrinth.minotaur") version "2.+"
    kotlin("jvm") version "2.2.10"
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
    id("dev.kikugie.fletching-table.fabric") version "0.1.0-alpha.22"
}

version = "${project.property("mod_version")}+${stonecutter.current.project}"
group = project.property("maven_group") as String

base.archivesName = project.property("archives_base_name") as String

repositories {
    maven("https://maven.ladysnake.org/releases") { name = "cardinal components" }
    maven("https://maven.terraformersmc.com") { name = "mod menu" }
    maven("https://api.modrinth.com/maven") { name = "modrinth" }
    maven("https://jitpack.io") { name = "jitpack" }
    maven("https://maven.isxander.dev/releases") { name = "Xander Maven" }
}

fabricApi {
    configureDataGeneration {
        client = true
    }
}

dependencies {
    // minecraft and fabric
    fun bundleAPI(dependencyNotation : Any) {
        modApi(dependencyNotation)
        include(dependencyNotation)
    }

    fun compat(dependencyNotation : String) {
        modCompileOnly(dependencyNotation) {
            exclude("net.fabricmc.fabric-api")
        }
        modRuntimeOnly(dependencyNotation) {
            exclude("net.fabricmc.fabric-api")
        }
    }

    minecraft("com.mojang:minecraft:${stonecutter.current.project}")
    mappings("net.fabricmc:yarn:${property("deps.yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("deps.fabric_api")}")

    bundleAPI("dev.onyxstudios.cardinal-components-api:cardinal-components-base:${project.property("deps.cca")}")
    bundleAPI("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:${project.property("deps.cca")}")

    modApi("dev.isxander:yet-another-config-lib:${project.property("deps.yacl")}")
    modApi("com.terraformersmc:modmenu:${project.property("deps.modmenu")}")

    modRuntimeOnly("maven.modrinth:sodium:${project.property("deps.sodium")}")

    compat("com.github.CrimsonDawn45:Fabric-Shield-Lib:v${project.property("deps.fabric_shield_lib")}")
    compat("com.github.Chocohead:Fabric-ASM:v2.3")
    compat("maven.modrinth:midnightlib:${project.property("deps.midnightlib")}")
}

fletchingTable {
    mixins.create("main") {
        // Default matches the default value in the annotation
        mixin("default", "${project.property("archives_base_name")}.mixins.json")
    }
    mixins.all {
        automatic = true
    }
}

tasks.processResources {
    val modVersion = project.version
    val minecraftVersion = stonecutter.current.version
    inputs.property("version", modVersion)
    inputs.property("minecraft", minecraftVersion)

    filesMatching("fabric.mod.json") {
        expand(
            mapOf(
                "version" to modVersion,
                "minecraft" to minecraftVersion
            )
        )
    }
}

loom {
    runConfigs.all {
        ideConfigGenerated(true)
        runDir = "../../run"
    }

    runConfigs["client"].apply {
        programArgs("--username=Survivalblock", "--uuid=c45e97e6-94ef-42da-8b5e-0c3209551c3f")
    }

    fabricModJsonPath = rootProject.file("src/main/resources/fabric.mod.json")
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(21)
}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()

    val java = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5"))
        JavaVersion.VERSION_21 else JavaVersion.VERSION_17

    targetCompatibility = java
    sourceCompatibility = java
}

tasks.jar {
    inputs.property("archivesName", project.base.archivesName)

    from("LICENSE") {
        rename { "${it}_${base.archivesName}"}
    }
}