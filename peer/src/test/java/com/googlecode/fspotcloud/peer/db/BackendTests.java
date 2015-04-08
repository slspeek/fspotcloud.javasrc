package com.googlecode.fspotcloud.peer.db;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import junit.framework.TestCase;

import com.google.common.collect.ImmutableList;
import com.googlecode.fspotcloud.shared.peer.TagData;

public abstract class BackendTests extends TestCase {

	private final Logger log = Logger.getLogger(FSpotBackendTest.class
			.getName());
	protected Backend data;

	public BackendTests(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();

		System.clearProperty("photo.dir.original");
		System.clearProperty("photo.dir.override");

	}

	protected void tearDown() throws Exception {
		super.tearDown();
		data = null;
	}

	public void testGetPhotoCount() throws SQLException {
		int count = data.getCount("photos");
		assertEquals(28, count);
	}

	public void testGetTagCount() throws SQLException {
		int count = data.getCount("tags");
		assertEquals(5, count);
	}
	
	public void testGetMetaData() throws SQLException {
		Object[] meta = data.getMetaData();
		assertEquals(5, meta[1]);
		assertEquals(28, meta[0]);
	}

	public void testGetTagListFurniture() throws SQLException {
		List<TagData> result = data.getTagData(ImmutableList.of("1"));
		assertEquals(1, result.size());
		assertEquals(new TagData("1", "Furniture","0",  9), result.get(0));
	}

	public void testGetTagListMac() throws SQLException {
		List<TagData> result = data.getTagData(ImmutableList.of("3"));
		assertEquals(1, result.size());
		assertEquals(new TagData("3", "Mac","2",  2), result.get(0));
	}
	
	public void testGetPhotoKeysForTag() throws Exception {
		List<String> result = data.getTagPhotos("5");
		log.info("In test" + result);
		assertEquals("3", result.get(0));
	}

	public void testGetImageURL() throws MalformedURLException, SQLException {
		String url = data.getImageURL("20");
		assertEquals("file:///home/steven/Photos/2010/06/22/img_0859-1.jpg",
				String.valueOf(url));
	}

	public void testIsPhotoInTag() throws Exception {
		boolean yep = data.isPhotoInTag("5", "3");
		assertTrue(yep);

		boolean nope = data.isPhotoInTag("5", "4");
		assertFalse(nope);
	}

	public void testPhotoVersion() throws Exception {
		assertEquals(1, data.getPhotoDefaultVersion("3"));
	}
}