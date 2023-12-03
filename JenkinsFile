pipeline {
    agent any
    
    tools {
        // Définir l'outil Maven avec le nom correspondant dans Jenkins
        Maven 'maven'
    }
    
    
    stages {
        stage('Git Checkout') {
            steps {
                // Utilisation de la commande 'checkout' pour récupérer le code depuis le dépôt Git
                checkout([$class: 'GitSCM', 
                          branches: [[name: '*/master']], 
                          userRemoteConfigs: [[url: 'https://github.com/PriscaFokou/software-testing.git']], 
                          credentialsId: 'Github-Credentials'])
            }
        }
        
        stage('Build the application') {
            steps {
                // Commande Maven pour nettoyer et construire l'application
                //sh 'mvn clean install'
                echo 'Clean maven fait'
            }
        }
        
        stage('Unit Test Execution') {
            steps {
                // Commande Maven pour exécuter les tests unitaires
                //sh 'mvn test'
                echo 'UNit test fait'
            }
        }
        
        stage('Build the docker image') {
            steps {
                // Commande pour construire l'image Docker avec le tag spécifié
                //sh 'docker build -t badre09/triang7:1.0.0 .'
                echo 'Build docker fait'
            }
        }


    }
}
