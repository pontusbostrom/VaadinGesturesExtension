[![Published on Vaadin  Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/hammergestures-add-on)
[![Stars on Vaadin Directory](https://img.shields.io/vaadin-directory/star/hammergestures-add-on.svg)](https://vaadin.com/directory/component/hammergestures-add-on)

# HammerGestures Add-on for Vaadin 7

HammerGestures is a Javascript extension add-on for Vaadin 7. It provides the possibility to add gesture support for any Vaadin component. The gestures are recognized client-side by the Hammer.js library [http://hammerjs.github.io] (released under the MIT license). The gestures supported by this add-on are: pan, swipe, rotate and pinch.  The gestures are recognized client-side, and the gesture event is sent to the server where appropriate listeners can be registered. Parameters to the recognizers can be given to tune the frequency of events are fired (and then sent to the server). The parameters are directly fed to the Hammer library, so see the Hammer documentation for supported options.

Rotate and pinch only work properly on touch devices. By default a emulation of multi-touch in desktop browsers is enabled, see [http://hammerjs.github.io/touch-emulator/].


## Download release

Official releases of this add-on are available at Vaadin Directory. For Maven instructions, download and reviews, go to http://vaadin.com/addon/hammergestures

## Building and running demo

git clone <url of the HammerComponent repository>
cd hammergestures-addon
mvn clean install
cd hammergestures-demo
mvn jetty:run

To see the demo, navigate to http://localhost:8080/

## Development with Eclipse IDE

For further development of this add-on, the following tool-chain is recommended:
- Eclipse IDE
- m2e wtp plug-in (install it from Eclipse Marketplace)
- Vaadin Eclipse plug-in (install it from Eclipse Marketplace)
- Chrome browser

### Importing project

Choose File > Import... > Existing Maven Projects

Note that Eclipse may give "Plugin execution not covered by lifecycle configuration" errors for pom.xml. Use "Permanently mark goal resources in pom.xml as ignored in Eclipse build" quick-fix to mark these errors as permanently ignored in your project. Do not worry, the project still works fine. 

### Debugging server-side

If you have not already compiled the widgetset, do it now by running vaadin:install Maven target for hammergestures-root project.

If you have a JRebel license, it makes on the fly code changes faster. Just add JRebel nature to your hammergestures-demo project by clicking project with right mouse button and choosing JRebel > Add JRebel Nature

To debug project and make code modifications on the fly in the server-side, right-click the hammergestures-demo project and choose Debug As > Debug on Server. Navigate to http://localhost:8080/hammergestures-demo/ to see the application.

### Debugging client-side

Debugging client side code in the hammergestures-demo project:
  - run "mvn vaadin:run-codeserver" on a separate console while the application is running
  - activate Super Dev Mode in the debug window of the application or by adding ?superdevmode to the URL
  - You can access Java-sources and set breakpoints inside Chrome if you enable source maps from inspector settings.
 
## Release notes

### Version 0.0.1-SNAPSHOT
- Provides support for pan, swipe, rotate and pinch gestures
- Can be used to extend any Vaadin component
- Event handled can be registered server side.

## Roadmap

This component is developed as a hobby with no public roadmap or any guarantees of upcoming releases. That said, the following features are planned for upcoming releases:
- ...
- ...

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:
- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

HammerComponent is written by Pontus Boström (pontus@vaadin.com)

# Developer Guide

## Getting started

Here is a simple example on how to try out the add-on component:
see hammergestures-demo/DemoUI.java
