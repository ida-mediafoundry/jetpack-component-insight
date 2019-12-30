[![Build Status](https://travis-ci.org/ida-mediafoundry/jetpack-component-insight.svg?branch=master)](https://travis-ci.org/ida-mediafoundry/jetpack-component-insight)[![codecov](https://codecov.io/gh/ida-mediafoundry/jetpack-component-insight/branch/master/graph/badge.svg)](https://codecov.io/gh/ida-mediafoundry/jetpack-component-insight)

## Jetpack - Component Insight tool powered by IDA

### Description

Provide the authors and developers a visual interface see the usage, relations and inheritance structure of the deployed components on their AEM instance.

### Pre-requisites

### Tool Manaul

Go to /jetpack/component-insight to access the tool

### User configuration

### Remote API

http://localhost:4502/services/insight/tree
http://localhost:4502/services/insight/insight  

### Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi services, Sling Models and WCMCommand.
* ui.apps: contains the /apps part containing the html, js, css and .content.xml files.

### How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

If you have a running AEM instance you can build and package the whole project and deploy into AEM with  

    mvn clean install -PautoInstallPackage
    
Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallPackagePublish
    
Or alternatively

    mvn clean install -PautoInstallPackage -Daem.port=4503

Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

### Testing

There are three levels of testing contained in the project:

unit test in core: this show-cases classic unit testing of the code contained in the bundle. To test, execute:

    mvn clean test
