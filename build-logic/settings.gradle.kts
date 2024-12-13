dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }

    // convention 모듈에서 toml에 있는 의존성을 사용하기 위한 코드
    // 루트 모듈의 settings.gradle.kts에서 convention build-logic:convention 모듈을 포함하는 코드를 삭제했기 때문
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")
