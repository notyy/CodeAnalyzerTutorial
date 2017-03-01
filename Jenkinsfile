pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh 'sbt clean compile'
            }
        }
    }
}