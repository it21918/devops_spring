pipeline {
    agent any


    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'it21918-patch-1', url: 'https://github.com/it21918/devops_spring.git'


            }
        }

        stage('Test') {
            steps {
                sh '''
                    cd /var/lib/jenkins/workspace/clone-springboot
                    mvn install
                    mvn test
                    '''
            }
        }
        stage('install ansible prerequisites') {
            steps {

                sh '''
                    mkdir -p ~/workspace/ansible-pipeline/files/certs
                    cd ~/workspace/ansible-pipeline/files/certs
                    openssl req -x509 -newkey rsa:4096 -keyout server.key -out server.crt -days 365 --nodes -subj '/C=GR/O=myorganization/OU=it/CN=myorg.com'
                '''
            }
        }
        stage('Prepare DB') {
            steps {
                sshagent (credentials: ['ssh-deployment-1']) {
                    sh '''
                        pwd
                        echo $WORKSPACE
                        ansible-playbook -i ~/workspace/ansible-pipeline/hosts.yml -l database ~/workspace/ansible-pipeline/playbooks/mysql-database.yml
                        '''
            }
            }
        }
        stage('deploym to vm 1') {
            steps{
                sshagent (credentials: ['ssh-deployment-1']) {
                    sh '''
                        ansible-playbook -i ~/workspace/ansible-pipeline/hosts.yml -l deploymentservers ~/workspace/ansible-pipeline/playbooks/spring-project-install.yml
                    '''
                }

            }

        }
    }
}
