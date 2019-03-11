package com.bitbar.file_handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.bitbar.pojo.Project;
import com.google.gson.Gson;
import com.testdroid.api.APIException;
import com.testdroid.api.model.APIProject;
import com.testdroid.api.model.APITestRun;

public class FileHandler {

	//change these to increase the wait time, in seconds
	public int defaultWaitTime = 60;
	public int timeOut = 480;
	
	/**
	 * parseFileAsJson
	 * Given a json gives the Project pojo object
	 * 
	 * @param configFileDir the directory containing config.json
	 */
	
	public Project parseJsonAsProject(String configFileDir) throws Exception
	{
		try
		{
			System.out.println(System.getProperty("user.dir"));
			System.out.println("***Reading config.json at **** "+configFileDir);
			Gson gson = new Gson();
			BufferedReader fileBuffer = new BufferedReader(new FileReader(configFileDir+"/config.json"));
			Project jsonAsProject = gson.fromJson(fileBuffer, Project.class);
			System.out.println(jsonAsProject.toString());
						
			return jsonAsProject;
		}catch(Exception e){
			throw e;
		}
	}
	
	
	/**
	 * dowloadStreamAsFile
	 * Converts an InputStream to OutputStream with name device_testRun_results.zip
	 * 
	 * @param res - the device + testRun id
	 * @param in - the InputStream to be converted
	 * @param resultsDir - The directory where results are downloaded
	 */
	
	public boolean dowloadStreamAsFile(String res, InputStream in, String resultsDir ) throws Exception
	{
		String fileName = res+"results.zip";
		OutputStream ostream = null;
		File results = new File(resultsDir);
		results.mkdir();
		ostream = new FileOutputStream(new File(results, fileName));
		IOUtils.copy(in, ostream);
		ostream.close();
		
		
		
		
		return false;
	}
	
	/**
	 * pollForCompletion - polls the bitbar cloud for timeOut seconds every defaultWaitTime. Only when the status is 
	 * Finished the results are downloaded.
	 * @param x - The APIProject object
	 * @param testRunId
	 * @return APITestRun with complete testrun details
	 * @throws InterruptedException
	 * @throws APIException
	 */
	
	public APITestRun pollForCompletion(APIProject apiPrj, long testRunId) throws InterruptedException, APIException
	{
		
		int timeElapsed = 0;
		while(true)
		{
			APITestRun testrun = apiPrj.getTestRun(testRunId);
			if(testrun.getState().equals(APITestRun.State.FINISHED))
			{
				System.out.println("Testrun "+testRunId+" : Finished");
				return testrun;
			}
			Thread.sleep(defaultWaitTime * 1000);
			timeElapsed = timeElapsed + defaultWaitTime;
			System.out.println("Time elapsed in sec: "+ timeElapsed);
			if(timeElapsed > timeOut)
			{
				System.out.println(String.format("Time out when waiting for the run to complete %d seconds" , timeOut));
				return null;
			}
			else
			{
				continue;
			}
		}
		
		
	}
	
}
