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
        stage('test coverage') {
            steps {
                sh 'sbt coverage test'
                sh 'sbt coverageReport'
                sh 'cp -R target/scala-2.11/scoverage-report ./report/'
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
        stage('deploy') {
            steps {
                sh 'echo deploy'
            }
        }
    }
}