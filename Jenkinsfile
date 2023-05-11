pipeline {
    agent any

    stages {
        stage('Maven test') {
            steps {
                sh 'mvn test -P jenkins'
            }
        }
        stage('Maven clean package') {
            steps {
                sh 'mvn clean package -P jenkins'
            }
        }
        stage('Upload to Nexus') {
            steps {
                script {
                    def buildNumber = env.BUILD_NUMBER
                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: 'http://3.87.223.250:8081/repository/petstore/',
                        groupId: 'com.mycompany',
                        version: "1.0-${buildNumber}",
                        repository: 'maven-snapshots',
                        credentialsId: 'nexus-credentials',
                        artifacts: [
                            [artifactId: 'my-artifact',
                            classifier: '',
                            file: "target/my-artifact-1.0-${buildNumber}.war",
                            type: 'war']
                        ]
                    )
                }
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
