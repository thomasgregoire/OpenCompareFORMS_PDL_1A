# OpenCompareFORMS_PDL_1A

##The project
This project is developed by five students of the M1 MIAGE of Rennes as part of a school project for the improvement of the [OpenCompare](https://opencompare.org/) platform.
It's aim is to provide an application that facilitates the addition of a new product in a comparison matrix of OpenCompare.

## The result
From a comparison matrix, the application will generate a modal with a form that will enable the user to add a new product by filling in the requested fields.

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
    pcms // contains the pcm
    src
        main
            java
                org.opencompare
                    Analyzer.java // analyses the different PCMs and provides the required information to classes responsible for the creation of the HTML form.
                    HTMLGenerator.java // create the required tags.
                    HTMLCreator.java // create the form and contains the script associated.
                    FormGenerator.java // generates the form from a pcm.
        test
            java
                org.opencompare
                    AnalyzerTest
                    HTMLGeneratorTest
                    OpCompTypeTest
```
## Installation