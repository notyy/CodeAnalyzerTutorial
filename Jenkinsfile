pipeline {
    agent any
    stages {
        stage('compile') {
            steps {
                sh 'sbt clean compile'
            }
        }
        stage('unit test with coverage') {
            steps {
                sh 'sbt coverage "testOnly * -- -l com.github.notyy.codeAnalyzer.FunctionalTest"'
                sh 'sbt coverageReport'
            }
        }
        stage('rebuild without coverage') {
            steps {
                sh 'sbt clean compile'
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