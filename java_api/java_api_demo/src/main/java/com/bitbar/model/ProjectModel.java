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

public class ProjectModel {

	
	private Project prjPojo;
	private APIUser me;
	private APIProject prj;
	private int defaultWaitTime = 60;
	private int timeOut = 600;
	private String workDir = null;
	
	/**
	 * PrepareProject - parameterized constructor.
	 * @param me - APIUser
	 * @param inp - Project pojo with basic information
	 * @param workDir
	 * @throws Exception
	 */
	
	public ProjectModel(APIUser me, Project inp, String workDir) throws Exception
	{
		prjPojo = inp;
		this.me = me;
		this.workDir = workDir;
		
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
		APIUserFile apiUserFile = me.uploadFile(new File(workDir+"/"+prjPojo.getFilesApp()[0]));
		fileId.add(apiUserFile.getId());
		if(prjPojo.getFilesTest()[0] !=null && !prjPojo.getFilesTest()[0].isEmpty())
		{
			apiUserFile = me.uploadFile(new File(workDir+"/"+prjPojo.getFilesTest()[0]));
			fileId.add(apiUserFile.getId());
		}
		
		prjPojo.setFileIds(fileId.toArray(new Long[0]));
		return prjPojo;
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
		prjPojo.setProjectId(prj.getId());
		return prjPojo;
	}

	/**
	 * Generates file configs for the project app and test file
	 * @return List of APIFileConfig
	 * @throws Exception
	 */

	public List<APIFileConfig> generateAPIFileConfigs() throws Exception {

		List<APIFileConfig> listFile = new ArrayList<APIFileConfig>();
		//for application
		APIFileConfig appPackage = new APIFileConfig(prjPojo.getFileIds()[0], APIFileConfig.Action.INSTALL);
		APIFileConfig testPackage = new APIFileConfig(prjPojo.getFileIds()[1], APIFileConfig.Action.RUN_TEST);
		listFile.add(appPackage);
		listFile.add(testPackage);
		prjPojo.setFileConfigs(listFile);
		return listFile;
	}


	/**
	 * Create a new test run
	 * @param testRunConfig
	 * @return
	 * @throws APIException
	 */
	
	public APITestRun createTestRun(APITestRunConfig testRunConfig) throws APIException {

		APITestRun test_run = me.startTestRun(testRunConfig);
		prjPojo.setTestRun(test_run);
		return test_run;
	}


	/**
	 * Downloads the results for a given testrun
	 * @param testrun
	 * @throws Exception
	 */
	public void downloadLogs(APITestRun testrun) throws Exception {
		
		FileHandler poll = new FileHandler();
		APITestRun latestRun = poll.pollForCompletion(prj, testrun.getId());
		
		String resultDir = workDir+"/results";
		if(latestRun != null) {
		for(APIDeviceSession session : latestRun.getDeviceSessionsResource().getEntity().getData())
		{
			String deviceName = session.getDevice().getDisplayName();
			
			new FileHandler().dowloadStreamAsFile(String.format("%s_%d", deviceName, latestRun.getId()), session.getOutputFiles(), resultDir);
		}
		}
		else {
			throw new Exception("Time out when waiting for the run to complete");
		}
		
		
	}
}
