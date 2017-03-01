pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh 'sbt clean compile test'
            }
        }
        stage('assembly') {
            steps {
                sh 'sbt assembly'
            }
        }
    }
}