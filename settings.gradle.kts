pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "A_1"
include(":exp_2_1&2_2")
include(":exp_3_1")
include(":exp_3_2")
include(":exp_4_1")
include(":exp_3_3")

include(":order")
include(":exp_5_2")
include(":exp_5_3")
include(":exp_6_1")
include(":exp_7_5")
