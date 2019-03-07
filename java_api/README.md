Intro
=====

This is a project to demostrate the use of Testroid API to interact with [https://cloud.bitbar.com] to create a project, upload the application and test files, start a test run and collect the logs/results.

Documentation
-------------
[Link to Bitbar API Documentation] (http://docs.bitbar.com/testing/api/index.html)

Pre-Reqs
--------
*   [Java](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) and [Maven](https://maven.apache.org/users/index.html) installed, preferrably latest versions.
*   The application, test package and a `config.json` in the same directory.

Contents
--------
*   `java_api_demo` - A maven project.
*   `app.apk` and `test.apk` - are the application and test package. The packages may vary according to the OS Type and framework type.
*   `config.json` - A configuration file used by the project to create testruns in bitbar cloud.
```json
{
    "osType":"ANDROID",
    "files_app":["app.apk"],
    "files_test": ["test.apk"],
    "frameworkId":252,
    "deviceGroupId":39554,
    "project_name":"Java_API_FinalV1"
}
```
&nbsp;&nbsp;&nbsp;`osType` can be either one of ANDROID, IOS and DESKTOP<br />

&nbsp;&nbsp;&nbsp;`files_app` is the application name to be tested.<br />

&nbsp;&nbsp;&nbsp;`files_test` is the test package that is used to test the app.<br />

&nbsp;&nbsp;&nbsp;`frameworkId` please refer [documentation](http://docs.bitbar.com/testing/products/public.html)<br />

&nbsp;&nbsp;&nbsp;`deviceGroupId` is the device group against which you want to perform the tests. Can be found in the your cloud.bitbar account.<br />

&nbsp;&nbsp;&nbsp;`project_name` The name of the project you want to create.<br />

*   `./java_api_demo/pm.xml` - to resolve dependecies 
```xml
    <dependency>
      <groupId>com.testdroid</groupId>
      <artifactId>testdroid-api</artifactId>
      <version>2.72</version>
    </dependency>
    <dependency>
      <groupId>com.google.code.gson</groupId>
      <artifactId>gson</artifactId>
      <version>2.8.5</version>
      <scope>compile</scope>
    </dependency>

```

Run
---
*   From the directory of `pom.xml` run
```sh
mvn exec:java -Dexec.mainClass=com.bitbar.java_api.App -Dexec.args="<api_key> <work_dir>"
```
*   [api_key](https://cloud.bitbar.com/#user/my-account) is your secret token, work_dir is the directory where config.json is stored.
*   The results will be downloaded on successful completions in the work_dir.