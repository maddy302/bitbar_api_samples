package com.bitbar.java_api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bitbar.file_handlers.FileHandler;
import com.bitbar.model.PrepareProject;
import com.bitbar.pojo.Project;
import com.google.gson.GsonBuilder;
import com.testdroid.api.APIKeyClient;
import com.testdroid.api.APIListResource;
import com.testdroid.api.DefaultAPIClient;
import com.testdroid.api.model.APIDevice;
import com.testdroid.api.model.APIDevice.OsType;
import com.testdroid.api.model.APIDeviceSession;
import com.testdroid.api.model.APIFileConfig;
import com.testdroid.api.model.APIProject;
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
        	Project json = y.getFileAsJson(work_dir);
        	PrepareProject prep = new PrepareProject(me,json, work_dir);
        	//APIProject prj = me.createProject("JAVA API Demo");
        	json = prep.uploadFiles();
        	json = prep.createProject(json.getProject_name());
        	
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
        	APITestRun testrun = prep.createTestRun(test_run_config);
        	System.out.println("**** Test run created ****");
        	System.out.println("Project : "+json.getProjectId() + " Test Run ID: "+testrun.getId() );

        	
        	//APIListResource<APIDeviceSession> device_sessions = testrun.getDeviceSessionsResource();
        	
        	prep.downloadLogs(testrun);
        	
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
    }
}
