pipeline {
    agent any

    stages {
        stage('Maven test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Maven clean package') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Docker build and push') {
            steps {
                script {
                    def buildNumber = env.BUILD_NUMBER
                    def image = docker.build("boomer12/petstore:${buildNumber}")
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                        image.push()
                    }
                }
            }
        }
    }
    post {
        success {
            slackSend (channel: '#java-project', color: '#FF0000', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
        failure {
            slackSend (channel: '#java-project', color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
    }
}