pipeline {
  agent any
  environment {
      DOCKER_ACCOUNT = 'firestarthehack'
      IMAGE_VERSION = '1.01'
      IMAGE_NAME = 'videoconverter'
      RANCHER_STACK_NAME = 'AnimeCap'
      RANCHER_SERVICE_NAME = 'videoconverter'
      RANCHER_SERVICE_URL = 'http://34.215.0.188:8080/v2-beta'
    }
  stages {
    stage('BeginProcess') {
      steps {
        sh 'rm -rf dockerbuild/'
      }
    }
    stage('Build') {
      steps {
        sh 'chmod 0755 ./gradlew;./gradlew clean build --refresh-dependencies'
      }
    }
    stage('Docker Build') {
      steps {
        parallel(
          "Build Docker Image": {
            sh "mkdir dockerbuild/ && cp build/libs/*.jar dockerbuild/app.jar && cp Dockerfile dockerbuild/Dockerfile"
            sh "cd dockerbuild/ && docker build -t ${env.DOCKER_ACCOUNT}/${env.IMAGE_NAME}:${env.IMAGE_VERSION} ./"
          },
          "Save Artifact": {
            archiveArtifacts(artifacts: 'build/libs/*.jar', onlyIfSuccessful: true)
          }
        )
      }
    }
    stage('Publish Latest Image') {
      steps {
        sh "docker push ${env.DOCKER_ACCOUNT}/${env.IMAGE_NAME}:${env.IMAGE_VERSION}"
      }
    }
    stage('Deploy') {
      steps {
        rancher(environmentId: '1a5', ports: '', environments: '1i12214', confirm: true, image: "${env.DOCKER_ACCOUNT}/${env.IMAGE_NAME}:${env.IMAGE_VERSION}", service: "${env.RANCHER_STACK_NAME}/${env.RANCHER_SERVICE_NAME}", endpoint: "${env.RANCHER_SERVICE_URL}", credentialId: 'rancher-server')
      }
    }
  }
}