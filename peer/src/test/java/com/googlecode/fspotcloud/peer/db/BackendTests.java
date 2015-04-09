package com.googlecode.fspotcloud.peer.db;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import junit.framework.TestCase;

import com.google.common.collect.ImmutableList;
import com.googlecode.fspotcloud.shared.peer.ImageSpecs;
import com.googlecode.fspotcloud.shared.peer.PhotoData;
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
		System.setProperty("photo.dir.original", "/home/steven/Photos");
		System.setProperty("photo.dir.override",
				"" + System.getProperty("user.dir")
						+ "/../peer/src/test/resources/Photos");
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
		assertEquals(new TagData("1", "Furniture", "0", 9), result.get(0));
	}

	public void testGetTagListMac() throws SQLException {
		List<TagData> result = data.getTagData(ImmutableList.of("3"));
		assertEquals(1, result.size());
		assertEquals(new TagData("3", "Mac", "2", 2), result.get(0));
	}

	public void testGetTagList() throws SQLException {
		List<TagData> result = data.getTagData();
		assertEquals(5, result.size());
		assertEquals(new TagData("1", "Furniture", "0", 9), result.get(0));
		assertEquals(new TagData("2", "Computers", "0", 12), result.get(1));
		assertEquals(new TagData("3", "Mac", "2", 2), result.get(2));
		assertEquals(new TagData("4", "PC", "2", 14), result.get(3));
		assertEquals(new TagData("5", "Glass", "0", 1), result.get(4));
	}

	public void testGetPhotoKeysForTag() throws Exception {
		List<String> result = data.getTagPhotos("5");
		assertEquals("3", result.get(0));
	}

	public void testGetImageURL() throws MalformedURLException, SQLException {
		String url = data.getImageURL("20");
		log.info("getImageURL url:" + url);
		assertEquals("file://" + System.getProperty("user.dir")
				+ "/../peer/src/test/resources/Photos/"  +"2010/06/22/img_0859-1.jpg",
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

	public void testGetPhotoData() throws Exception {
		PhotoData pd = data.getPhotoData(new ImageSpecs(1024, 768, 512, 384),
				ImmutableList.of("3")).get(0);
		assertEquals("5", pd.getTagList().get(0));
		assertEquals(74556, pd.getImageData().length);
		assertEquals(31450, pd.getThumbData().length);
	}
}