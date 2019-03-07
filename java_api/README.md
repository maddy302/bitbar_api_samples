Intro
=====

This is a project to demostrate the use of Testroid API to interact with https://cloud.bitbar.com to create a project, upload the application and test files, start a test run and collect the logs/results.

Documentation
-------------
[Link to Bitbar API Documentation] (http://docs.bitbar.com/testing/api/index.html)

Pre-Reqs
--------
* [Java](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) and [Maven](https://maven.apache.org/users/index.html) installed, preferrably latest versions.
* The application, test package and a `config.json` in the same directory.

Contents
--------
* `java_api_demo` - A maven project.
* `app.apk` and `test.apk` - are the application and test package. The packages may vary according to the OS Type and framework type.
* `config.json` - A configuration file used by the project to create testruns in bitbar cloud.
...{
..."osType":"ANDROID",
..."files_app":["app.apk"],
..."files_test": ["test.apk"],
..."frameworkId":252,
..."deviceGroupId":39554,
..."project_name":"Java_API_FinalV1"
...}