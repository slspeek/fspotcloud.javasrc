package com.googlecode.fspotcloud.peer.db;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

public class ShotwellBackendTest extends BackendTests {

	public ShotwellBackendTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		URL testDatabase = ClassLoader.getSystemResource("shotwell.db");
		String path = testDatabase.getPath();
		data = new ShorewallBackend("jdbc:sqlite:" + path);
	}
	
	public void testGetPhotoCount() throws SQLException {
	    int count = data.getCount("PhotoTable");
	    assertEquals(28, count);
	}
	
	public void testGetTagCount() throws SQLException {
	    int count = data.getCount("TagTable");
	    assertEquals(5, count);
	}

	public void testGetImageURL() throws MalformedURLException,
	SQLException {
		String url = data.getImageURL("20");
		assertEquals("file:///home/fspot/Photos/2010/06/22/img_0859-1.jpg",
				String.valueOf(url));
	}

	//TODO REMOVE THIS
	public void testIsPhotoInTag() throws Exception {
	}
}
