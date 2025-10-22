# Student Report Generator

A Java application that reads student marks from Excel files and displays them in a graphical user interface.

Features
Loads theory and lab marks from .xlsx files in the new_2f/ folder
Shows student details and internal assessment scores
Clean and easy-to-use GUI built with Swing
The excel sheets were formatted again for the ease of reading and stored in the new_2f folder

Apache Maven is a build automation and dependency management tool for Java projects.
Instead of manually downloading JAR files (like poi-ooxml.jar) and adding them to your project, Maven does it for you—automatically.
With Maven, pom.xml file downloads all required libraries (and their dependencies), manages versions, compiles, packages, and runs your code with simple commands

This repository is for the
[Build a Java app with Maven](https://jenkins.io/doc/tutorials/build-a-java-app-with-maven/)
tutorial in the [Jenkins User Documentation](https://jenkins.io/doc/).

The results of these tests are saved to a
JUnit XML report.

The `jenkins` directory contains an example of the `Jenkinsfile` (i.e. Pipeline)
you'll be creating yourself during the tutorial and the `jenkins/scripts` subdirectory
contains a shell script with commands that are executed when Jenkins processes
the "Deliver" stage of your Pipeline.
