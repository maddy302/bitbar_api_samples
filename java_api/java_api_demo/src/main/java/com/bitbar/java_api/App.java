package com.bitbar.java_api;

import java.util.List;

import com.bitbar.file_handlers.FileHandler;
import com.bitbar.model.PrepareProject;
import com.bitbar.pojo.Project;
import com.testdroid.api.APIKeyClient;
import com.testdroid.api.model.APIDevice;
import com.testdroid.api.model.APIFileConfig;
import com.testdroid.api.model.APITestRun;
import com.testdroid.api.model.APITestRunConfig;
import com.testdroid.api.model.APIUser;

/**
 * Hello world!
 *
 */
public class App 
{
	static String work_dir = null;
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String cloud_url = "https://cloud.bitbar.com/";
        String api_key = args[0];
        work_dir = args[1];
        
        try {
        	APIKeyClient api_instance = new APIKeyClient(cloud_url, api_key);
        	APIUser me = api_instance.me();
        	System.out.println(me);

        	FileHandler y = new FileHandler();
        	
        	//Prase json file
        	Project json = y.getFileAsJson(work_dir);
        	
        	//prepares a project by creating project, uploading application files - ready to start test runs.
        	PrepareProject prep = new PrepareProject(me,json, work_dir);
        	json = prep.uploadFiles();
        	json = prep.createProject(json.getProject_name());
        	
        	//setting up configuration for testrun
        	APITestRunConfig test_run_config = new APITestRunConfig();
        	
        	test_run_config.setProjectId(json.getProjectId());
        	if(json.getOsType().equalsIgnoreCase("android"))
        		test_run_config.setOsType(APIDevice.OsType.ANDROID);
        	else if(json.getOsType().equalsIgnoreCase("ios"))
        		test_run_config.setOsType(APIDevice.OsType.IOS);
        	else if(json.getOsType().equalsIgnoreCase("desktop"))
        		test_run_config.setOsType(APIDevice.OsType.DESKTOP);
        	
        	test_run_config.setFrameworkId((long) json.getFrameworkId());
        	test_run_config.setDeviceGroupId((long) json.getDeviceGroupId());
        	List<APIFileConfig> list_file_configs = prep.generate_APIFileConfigs();
        	test_run_config.setFiles(list_file_configs);
        	
        	//running the test run with config
        	System.out.println("**** config ****"+json.toString());
        	
        	//create a test run
        	APITestRun testrun = prep.createTestRun(test_run_config);
        	System.out.println("**** Test run created ****");
        	System.out.println("Project : "+json.getProjectId() + " Test Run ID: "+testrun.getId() );

        	
        	//APIListResource<APIDeviceSession> device_sessions = testrun.getDeviceSessionsResource();
        	//download the results
        	prep.downloadLogs(testrun);
        	
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
    }
}
