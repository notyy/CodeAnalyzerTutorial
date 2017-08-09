pipeline {
    agent any
    stages {
        stage('compile') {
            steps {
                sh 'echo build number is ${env.BUILD_NUMBER}'
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
                sh 'cp -R target/scala-2.12/scoverage-report ./report/'
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
        stage('performance test') {
            steps {
                sh 'echo performance test'
            }
        }
        stage('assembly') {
            steps {
                sh 'sbt assembly'
                sh 'echo assembly successfully'
            }
        }
    }
}