example
==============

Project to show case a strange bug in my app when using Chrome 69.0.3497.81. The app works fine with previous versions of Chrome, as well as the latest Firefox, Opera and Edge.

The web-app is a Spring Boot application with Spring Boot Security (2.0.4) combined with Vaadin (8.5.1).
You can run it as mvn spring-boot:run

You will see a page with 6 upload buttons that upload a file. Irrespectively of the order, the 6th upload button hangs the tab waiting for an answer from the server. 
