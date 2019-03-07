package com.bitbar.pojo;

import java.util.Arrays;
import java.util.List;

import com.testdroid.api.model.APIFileConfig;
import com.testdroid.api.model.APITestRun;

public class Project {
	
	public String osType;
	private long projectId;
	private int frameworkId, deviceGroupId;
	private String[] filesApp;
	private String[] filesTest;
	private Long[] fileIds;
	private APITestRun testRun;
	private List<APIFileConfig> fileConfigs;
	private String projectName;
	
	@Override
	public String toString() {
		return "Project [osType=" + osType + ", projectId=" + projectId + ", frameworkId=" + frameworkId
				+ ", deviceGroupId=" + deviceGroupId + ", filesApp=" + Arrays.toString(filesApp) + ", filesTest="
				+ Arrays.toString(filesTest) + ", fileIds=" + Arrays.toString(fileIds) + ", testRun=" + testRun
				+ ", fileConfigs=" + fileConfigs + ", projectName=" + projectName + "]";
	}
	
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
	public String[] getFilesApp() {
		return filesApp;
	}
	public void setFilesApp(String[] filesApp) {
		this.filesApp = filesApp;
	}
	public String[] getFilesTest() {
		return filesTest;
	}
	public void setFilesTest(String[] filesTest) {
		this.filesTest = filesTest;
	}
	public Long[] getFileIds() {
		return fileIds;
	}
	public void setFileIds(Long[] fileIds) {
		this.fileIds = fileIds;
	}
	public APITestRun getTestRun() {
		return testRun;
	}
	public void setTestRun(APITestRun testRun) {
		this.testRun = testRun;
	}
	public List<APIFileConfig> getFileConfigs() {
		return fileConfigs;
	}
	public void setFileConfigs(List<APIFileConfig> fileConfigs) {
		this.fileConfigs = fileConfigs;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	

}
