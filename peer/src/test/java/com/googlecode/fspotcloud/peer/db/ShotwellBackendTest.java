package com.googlecode.fspotcloud.peer.db;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import com.googlecode.fspotcloud.shared.peer.TagData;

public class ShotwellBackendTest extends BackendTests {

	public ShotwellBackendTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("photo.dir.original", "/home/fspot/Photos");
		System.setProperty("photo.dir.override",
				"" + System.getProperty("user.dir")
						+ "/../peer/src/test/resources/Photos");
		URL testDatabase = ClassLoader.getSystemResource("shotwell.db");
		String path = testDatabase.getPath();
		data = new ShotwellBackend("jdbc:sqlite:" + path);
	}
	
	public void testGetPhotoCount() throws SQLException {
	    int count = data.getCount("PhotoTable");
	    assertEquals(28, count);
	}
	
	public void testGetTagCount() throws SQLException {
	    int count = data.getCount("TagTable");
	    assertEquals(5, count);
	}
	
	public void testGetTagList() throws SQLException {
		List<TagData> result = data.getTagData();
		assertEquals(5, result.size());
		assertEquals(new TagData("1", "Furniture","0",  9), result.get(0));
		assertEquals(new TagData("2", "Computers","0",  16), result.get(1));
		assertEquals(new TagData("3", "Mac","2",  2), result.get(2));
		assertEquals(new TagData("4", "PC","2",  14), result.get(3));
		assertEquals(new TagData("5", "Glass","0",  1), result.get(4));
	}
}
