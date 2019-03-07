package com.bitbar.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bitbar.file_handlers.FileHandler;
import com.bitbar.pojo.Project;
import com.testdroid.api.APIException;
import com.testdroid.api.model.APIDeviceSession;
import com.testdroid.api.model.APIFileConfig;
import com.testdroid.api.model.APIProject;
import com.testdroid.api.model.APITestRun;
import com.testdroid.api.model.APITestRunConfig;
import com.testdroid.api.model.APIUser;
import com.testdroid.api.model.APIUserFile;

public class PrepareProject {

	
	private Project prj_file;
	private APIUser me;
	private APIProject prj;
	private int default_wait_time = 60;
	private int time_out = 600;
	private String work_dir = null;
	
	/**
	 * PrepareProject - parameterized constructor.
	 * @param me - APIUser
	 * @param inp - Project pojo with basic information
	 * @param work_dir
	 * @throws Exception
	 */
	
	public PrepareProject(APIUser me, Project inp, String work_dir) throws Exception
	{
		prj_file = inp;
		this.me = me;
		this.work_dir = work_dir;
		
	}
	
	/**
	 * uploadFiles - uploads the files to the bitbar cloud
	 * 
	 * @return Project with file ids.
	 */
	
	public Project uploadFiles() throws Exception
	{
		List<Long> fileId = new ArrayList<Long>();
		
		//upload app
		APIUserFile temp = me.uploadFile(new File(work_dir+"/"+prj_file.getFiles_app()[0]));
		fileId.add(temp.getId());
		if(prj_file.getFiles_test()[0] !=null && !prj_file.getFiles_test()[0].isEmpty())
		{
			temp = me.uploadFile(new File(work_dir+"/"+prj_file.getFiles_test()[0]));
			fileId.add(temp.getId());
		}
		
		prj_file.setFile_ids(fileId.toArray(new Long[0]));
		return prj_file;
	}
	
	/**
	 * Creates a new project with name
	 * @param name
	 * @return
	 * @throws Exception
	 */
	
	
	public Project createProject(String name) throws Exception
	{
		prj = me.createProject(name);
		System.out.println(prj.toJson());
		prj_file.setProjectId(prj.getId());
		return prj_file;
	}

	/**
	 * Generates file configs for the project app and test file
	 * @return List of APIFileConfig
	 * @throws Exception
	 */

	public List<APIFileConfig> generate_APIFileConfigs() throws Exception {

		List<APIFileConfig> list_file = new ArrayList<APIFileConfig>();
		//for application
		APIFileConfig app = new APIFileConfig(prj_file.getFile_ids()[0], APIFileConfig.Action.INSTALL);
		APIFileConfig test_package = new APIFileConfig(prj_file.getFile_ids()[1], APIFileConfig.Action.RUN_TEST);
		list_file.add(app);
		list_file.add(test_package);
		prj_file.setFile_configs(list_file);
		return list_file;
	}


	/**
	 * Create a new test run
	 * @param test_run_config
	 * @return
	 * @throws APIException
	 */
	
	public APITestRun createTestRun(APITestRunConfig test_run_config) throws APIException {

		APITestRun test_run = me.startTestRun(test_run_config);
		prj_file.setTest_run(test_run);
		return test_run;
	}


	/**
	 * Downloads the results for a gievn testrun
	 * @param testrun
	 * @throws Exception
	 */
	public void downloadLogs(APITestRun testrun) throws Exception {
		
		FileHandler x = new FileHandler();
		APITestRun latest_run = x.pollForCompletion(prj, testrun.getId());
		
		String result_dir = work_dir+"/results";
		if(latest_run != null) {
		for(APIDeviceSession session : latest_run.getDeviceSessionsResource().getEntity().getData())
		{
			String device = session.getDevice().getDisplayName();
			
			new FileHandler().dowloadStreamAsFile(device+"_"+latest_run.getId()+"_", session.getOutputFiles(), result_dir);
		}
		}
		else {
			throw new Exception("Time out when waiting for the run to complete");
		}
		
		
	}
}
