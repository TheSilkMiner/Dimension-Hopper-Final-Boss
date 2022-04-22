import wtf.gofancy.fancygradle.patch.Patch
import wtf.gofancy.fancygradle.script.extensions.createDebugLoggingRunConfig
import wtf.gofancy.fancygradle.script.extensions.curse
import wtf.gofancy.fancygradle.script.extensions.curseForge
import wtf.gofancy.fancygradle.script.extensions.deobf

import java.time.format.DateTimeFormatter
import java.time.Instant

// TODO("Remove when ForgeGradle does this itself OR when IntelliJ fixes the -1 bug")
buildscript {
    configurations.classpath.configure {
        resolutionStrategy {
            force(
                "org.apache.logging.log4j:log4j-api:2.11.2",
                "org.apache.logging.log4j:log4j-core:2.11.2"
            )
        }
    }
}

plugins {
    java
    eclipse
    idea
    `maven-publish`
    id("net.minecraftforge.gradle") version "5.1.+"
    id("wtf.gofancy.fancygradle") version "1.1.0-0"
}

version = "1.3.0"
group = "mods.thecomputerizer.dimensionhoppertweaks"

minecraft {
    mappings("stable", "39-1.12")

    runs {
        createDebugLoggingRunConfig("client")
        createDebugLoggingRunConfig("server") { args("nogui") }
    }
}

fancyGradle {
    patches {
        asm
        codeChickenLib
        coremods
        resources
    }
}

idea.module.inheritOutputDirs = true

repositories {
    mavenCentral()
    curseForge()
    maven {
        name = "Progwml6"
        url = uri("https://dvs1.progwml6.com/files/maven/")
    }
    maven {
        name = "ModMaven"
        url = uri("https://modmaven.k-4u.nl")
    }
    maven {
        name = "CurseMaven"
        url = uri("https://cfa2.cursemaven.com")
    }
}

dependencies {
    minecraft(group = "net.minecraftforge", name = "forge", version = "1.12.2-14.23.5.2860")

    implementation(fg.deobf(curse(mod = "fermion-lib", projectId = 345538L, fileId = 3186519L)))
    implementation(fg.deobf(curse(mod = "codechicken-lib", projectId = 242818L, fileId = 2779848L)))
    implementation(fg.deobf(curse(mod = "avaritia", projectId = 261348L, fileId = 3143349L)))
    implementation(fg.deobf(curse(mod = "sgcraft", projectId = 289115L, fileId = 3044648L)))
    implementation(fg.deobf(curse(mod = "reskillable", projectId = 286382L, fileId = 2815686L)))
    runtimeOnly(fg.deobf(group = "mezz.jei", name = "jei_1.12.2", version = "4.16.1.302"))
}

sourceSets.main {
    resources.outputDir = java.outputDir
}

tasks {
    withType<Jar> {
        archiveBaseName.set("dimension-hopper-tweaks")
        finalizedBy("reobfJar")

        manifest {
            attributes(
                "Specification-Title" to project.name,
                "Specification-Version" to project.version,
                "Specification-Vendor" to "TheComputerizer",
                "Implementation-Title" to "${project.group}.${project.name.toLowerCase().replace(' ', '_')}",
                "Implementation-Version" to project.version,
                "Implementation-Vendor" to "TheComputerizer",
                "Implementation-Timestamp" to DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            )
        }
    }

    withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
    }

    withType<Wrapper> {
        gradleVersion = "7.4.2"
        distributionType = Wrapper.DistributionType.ALL
    }
}
