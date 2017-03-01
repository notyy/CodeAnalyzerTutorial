pipeline {
    agent any
    stages {
        stage('compile') {
            steps {
                sh 'sbt clean compile'
            }
        }
        stage('unit test') {
            steps {
                sh 'sbt "testOnly * -- -l com.github.notyy.codeAnalyzer.FunctionalTest"'
            }
        }
        stage('functional test') {
            steps {
                sh 'sbt "testOnly * -- -n com.github.notyy.codeAnalyzer.FunctionalTest"'
            }
        }
        stage('assembly') {
            steps {
                sh 'sbt assembly'
            }
        }
    }
}