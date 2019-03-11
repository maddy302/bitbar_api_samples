package com.bitbar.java_api;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.testdroid.api.APIException;
import com.testdroid.api.APIKeyClient;
import com.testdroid.api.model.APIUser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    static APIKeyClient apiInstance;
    static APIUser me;
    @BeforeClass
    public static void setUpBeforeClass()
    {
    	try {
    	String apiKey = "";
    	apiInstance = new APIKeyClient("https://cloud.bitbar.com/", apiKey);
    	me = apiInstance.me();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    	
    }
    
    
    @Before
    public void setUp()
    {
    	
    }
    
    @After
    public void revertChanges()
    {
    	
    }
    
}
