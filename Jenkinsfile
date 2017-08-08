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
            }
        }
        stage('Sanity check') {
            steps {
                input "confirm to deploy?"
            }
        }
        stage('deploy') {
            steps {
                sh 'cp target/scala-2.12/CodeAnalyzerTutorial-assembly-0.0.1.jar /Users/twer/dev/bin/CodeAnalyzer.jar'
            }
        }
        stage('health check') {
            steps {
                sh 'echo health check'
            }
        }
    }
}