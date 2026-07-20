plugins {
  `java-library`
  `maven-publish`
}

extra["runapiSlug"] = "fish-audio"

description = "RunAPI Fish Audio Java SDK for Fish Audio workflows."

java {
  withSourcesJar()
  withJavadocJar()
}

dependencies {
  api("ai.runapi:runapi-core:0.2.0")

  testImplementation(platform("org.junit:junit-bom:5.10.3"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
      artifactId = "runapi-fish-audio"
      pom {
        name = "RunAPI Fish Audio Java SDK"
        description = "RunAPI Fish Audio Java SDK for Fish Audio workflows."
        url = "https://runapi.ai/models/fish-audio"
        licenses {
          license {
            name = "Apache License, Version 2.0"
            url = "https://www.apache.org/licenses/LICENSE-2.0"
          }
        }
        developers {
          developer {
            id = "runapi"
            name = "RunAPI"
            email = "contact@runapi.ai"
          }
        }
        scm {
          url = "https://github.com/runapi-ai/fish-audio-sdk"
          connection = "scm:git:https://github.com/runapi-ai/fish-audio-sdk.git"
          developerConnection = "scm:git:ssh://git@github.com/runapi-ai/fish-audio-sdk.git"
        }
      }
    }
  }
}
