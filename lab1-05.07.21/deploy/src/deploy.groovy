pipeline {
    agent none
    parameters {
        string(name: 'API_PORT', defaultValue: '8090', description: 'Port of backend', trim: true)
    }
    // TODO: креды брать из credentioals
    // TODO: выполнять на agent any
    stages {
        stage('Build') {}
        stage('Send') {}
        stage('Start') {}
        stage('Test connection') {}
    }
}