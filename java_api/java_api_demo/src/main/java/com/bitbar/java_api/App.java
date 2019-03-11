package com.bitbar.java_api;

import java.util.List;

import com.bitbar.file_handlers.FileHandler;
import com.bitbar.model.ProjectModel;
import com.bitbar.pojo.Project;
import com.testdroid.api.APIKeyClient;
import com.testdroid.api.model.APIDevice;
import com.testdroid.api.model.APIFileConfig;
import com.testdroid.api.model.APITestRun;
import com.testdroid.api.model.APITestRunConfig;
import com.testdroid.api.model.APIUser;

/**
 * A java maven project to demonstrate the use of testdroid java api
 * 
 * @author Madhukar
 * @version 1.0 7 March 2019
 *
 */
public class App 
{
	private static String configFileDir = null;
    public static void main( String[] args )
    {
        String cloudUrl = "https://cloud.bitbar.com/";
        String apiKey = args[0];
        configFileDir = args[1];
        
        try {
        	APIKeyClient apiInstance = new APIKeyClient(cloudUrl, apiKey);
        	APIUser me = apiInstance.me();
        	System.out.println(me);

        	FileHandler y = new FileHandler();
        	
        	//Prase json file
        	Project projectPojo = y.parseJsonAsProject(configFileDir);
        	
        	//prepares a project by creating project, uploading application files - ready to start test runs.
        	ProjectModel prep = new ProjectModel(me,projectPojo, configFileDir);
        	projectPojo = prep.uploadFiles();
        	projectPojo = prep.createProject(projectPojo.getProjectName());
        	
        	//setting up configuration for testrun
        	APITestRunConfig testRunConfig = new APITestRunConfig();
        	
        	testRunConfig.setProjectId(projectPojo.getProjectId());
        	if(projectPojo.getOsType().equalsIgnoreCase("desktop"))
        		testRunConfig.setOsType(APIDevice.OsType.DESKTOP);
        	else if(projectPojo.getOsType().equalsIgnoreCase("ios"))
        		testRunConfig.setOsType(APIDevice.OsType.IOS);
        	else
        		testRunConfig.setOsType(APIDevice.OsType.ANDROID);
        	
        	testRunConfig.setFrameworkId((long) projectPojo.getFrameworkId());
        	testRunConfig.setDeviceGroupId((long) projectPojo.getDeviceGroupId());
        	List<APIFileConfig> listFileConfigs = prep.generateAPIFileConfigs();
        	testRunConfig.setFiles(listFileConfigs);
        	
        	System.out.println("**** config ****"+projectPojo.toString());
        	
        	//create a test run
        	APITestRun testrun = prep.createTestRun(testRunConfig);
        	System.out.println("**** Test run created ****");
        	System.out.println(String.format("Project :  %d Test Run ID: %d", projectPojo.getProjectId(), testrun.getId() ));
        	
        	//download the results
        	prep.downloadLogs(testrun);
        	
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
    }
}
