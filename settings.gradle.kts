pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "sqldelight-postgis-pgvector"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val vSqlDelight = "2.2.1"
            plugin("kotlin", "org.jetbrains.kotlin.jvm").version("2.3.0")
            plugin("sqldelight", "app.cash.sqldelight").version(vSqlDelight)
            plugin("flyway", "org.flywaydb.flyway").version("12.0.0")
            library("pgvector", "com.pgvector:pgvector:0.1.6")
            library("postgis", "net.postgis:postgis-jdbc:2025.1.1")
            library("sqldelight-jdbc-driver", "app.cash.sqldelight:jdbc-driver:$vSqlDelight")
            library("sqldelight-postgresql-dialect", "app.cash.sqldelight:postgresql-dialect:$vSqlDelight")
            library("postgresql-jdbc-driver", "org.postgresql:postgresql:42.7.9")
            library("flyway-database-postgresql", "org.flywaydb:flyway-database-postgresql:11.19.1")
            library("google-truth", "com.google.truth:truth:1.4.5")
        }
    }
}
