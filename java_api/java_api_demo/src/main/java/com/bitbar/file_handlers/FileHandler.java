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

	int default_wait_time = 60;
	int time_out = 480;
	
	public Project getFileAsJson(String work_dir) throws Exception
	{
		try
		{
			System.out.println(System.getProperty("user.dir"));
			System.out.println("***Reading config.json at **** "+work_dir);
			Gson gson = new Gson();
			BufferedReader file_buffer = new BufferedReader(new FileReader(work_dir+"/config.json"));
			Project json = gson.fromJson(file_buffer, Project.class);
			System.out.println(json.toString());
			
//			BufferedReader file_buffer = new BufferedReader(new FileReader("./src/main/java/com/bitbar/resources/config.json"));
//			Project json = gson.fromJson(file_buffer, Project.class);
//			System.out.println(json.toString());
//			System.out.println(json.getClass());
			
			return json;
		}catch(Exception e){
			throw e;
		}
	}
	
	public boolean dowloadStreamAsFile(String res, InputStream in, String res_dir ) throws Exception
	{
		String file_name = res+"results.zip";
		OutputStream ostream = null;
		File x = new File(res_dir);
		x.mkdir();
		ostream = new FileOutputStream(new File(x, file_name));
		IOUtils.copy(in, ostream);
		ostream.close();
		
		
		
		
		return false;
	}
	
	public APITestRun pollForCompletion(APIProject x, long test_run_id) throws InterruptedException, APIException
	{
		
		int time_elapsed = 0;
		while(true)
		{
			APITestRun testrun = x.getTestRun(test_run_id);
			if(testrun.getState().equals(APITestRun.State.FINISHED))
			{
				System.out.println("Testrun "+test_run_id+" : Finished");
				return testrun;
			}
			Thread.sleep(default_wait_time * 1000);
			time_elapsed = time_elapsed + default_wait_time;
			System.out.println("Time elapsed in sec: "+ time_elapsed);
			if(time_elapsed > time_out)
			{
				System.out.println("Time out when waiting for the run to complete " + time_out + "seconds");
				return null;
			}
			else
			{
				continue;
			}
		}
		
		
	}
	
}
