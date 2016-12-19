# OpenCompareFORMS_PDL_1A

##The project
This project is developed by five students of M1 MIAGE Rennes as part of a school project for the improvement of the
[OpenCompare](https://opencompare.org/) platform.
Its aim is to provide an application that facilitates the addition of a new product in a comparison matrix of
OpenCompare.

## The result
From a comparison matrix, the application will generate a modal with a form that will enable the user to add a new
product by filling in the requested fields.

## Licence
Open Source

## Technology used
Java

HTML (generated through Java classes)

Javascript

### Development tools
[Maven](https://maven.apache.org/)

[IntelliJ] (https://www.jetbrains.com/idea/)

## Project structure
```
OpenCompareFORMS_PDL_1A
    .idea
    pcms // contains the PCMs
    src
        main
            java
                org.opencompare
                    Analyzer.java // analyses the different PCMs and provides the required information to classes
                    responsible for the creation of the HTML form.
                    HTMLGenerator.java // creates the required tags.
                    HTMLCreator.java // creates the form and contains the script associated.
                    FormGenerator.java // generates the form from a pcm.
                    FormGeneratorGUI.java
        test
            java
                org.opencompare
                    AnalyzerTest
                    HTMLGeneratorTest
                    OpCompTypeTest
```

## Installation
*Clone project from GitHub onto an IDE (eg. intelliJ)

Or

Download zip form GitHub

Extract the file

Open the project in an IDE as a Maven Project (make sure the the "pom.xml" file is recognised as a maven file.

If it is not the case, right click -> add as maven).

Run FormGeneratorGui

  Select the PCM that you want to edit

  Select the output for the HTML file
