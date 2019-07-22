// Jenkinsfile for Turing Machine in CS 4110
pipeline {
	agent {
        node { label 'master' }
	}

	stages {
		stage('Build') {
			steps {
				echo 'Building..'
				//##############################################################
				// Uncomment exactly one of the "sh" lines below and change 
				//   FILE to the relative path to your source file(s).
				//   Add or delete FILE names as needed for your app
				//   Make no other changes to this section whatsoever. 

				//sh 'cs4110turingmachinebuild script "./FILE1" "./FILE2" '
				sh 'cs4110turingmachinebuild java "/src/turingMachine/TuringMachine.java"'
				//sh 'cs4110turingmachinebuild c# "./FILE1" "./FILE2" '
				//sh 'cs4110turingmachinebuild swift "./FILE1" "./FILE2" '

				// Touch nothing else in this file or receive a zero grade.
				//##############################################################
			}
			post {
				failure {
					echo 'Sending console log to submitter'
					sh 'cs4110sendlog "Turing Machine Build failed"'
				}
			}
		}
		stage('Test') {
			steps {
				echo 'Testing..'
				sh 'cs4110cucumber'
			}
			post {
				always {
					echo 'Reporting to the student....'
					sh "cs4110report"
					echo 'Grading the assignment....'
					sh "cs4110grade"
				}
			}
		}
	}
}
