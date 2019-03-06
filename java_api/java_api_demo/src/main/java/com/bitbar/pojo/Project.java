package com.bitbar.pojo;

import java.util.Arrays;
import java.util.List;

import com.testdroid.api.model.APIFileConfig;
import com.testdroid.api.model.APITestRun;

public class Project {
	String osType;
	long projectId;
	int frameworkId, deviceGroupId;
	String[] files_app;
	String[] files_test;
	Long[] file_ids;
	APITestRun test_run;
	List<APIFileConfig> file_configs;
	String project_name;
	
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public int getFrameworkId() {
		return frameworkId;
	}
	public void setFrameworkId(int frameworkId) {
		this.frameworkId = frameworkId;
	}
	public int getDeviceGroupId() {
		return deviceGroupId;
	}
	public void setDeviceGroupId(int deviceGroupId) {
		this.deviceGroupId = deviceGroupId;
	}
	public String[] getFiles_app() {
		return files_app;
	}
	public void setFiles_app(String[] files_app) {
		this.files_app = files_app;
	}
	public String[] getFiles_test() {
		return files_test;
	}
	public void setFiles_test(String[] files_test) {
		this.files_test = files_test;
	}
	@Override
	public String toString() {
		return "Project [osType=" + osType + ", projectId=" + projectId + ", frameworkId=" + frameworkId
				+ ", deviceGroupId=" + deviceGroupId + ", files_app=" + Arrays.toString(files_app) + ", files_test="
				+ Arrays.toString(files_test) + ", file_ids=" + Arrays.toString(file_ids) + ", test_run=" + test_run
				+ ", file_configs=" + file_configs + ", project_name=" + project_name + "]";
	}
	public Long[] getFile_ids() {
		return file_ids;
	}
	public void setFile_ids(Long[] longs) {
		this.file_ids = longs;
	}
	public APITestRun getTest_run() {
		return test_run;
	}
	public void setTest_run(APITestRun test_run) {
		this.test_run = test_run;
	}
	public List<APIFileConfig> getFile_configs() {
		return file_configs;
	}
	public void setFile_configs(List<APIFileConfig> file_configs) {
		this.file_configs = file_configs;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	
	 

}
