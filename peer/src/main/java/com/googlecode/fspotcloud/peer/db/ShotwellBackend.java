package com.googlecode.fspotcloud.peer.db;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.googlecode.fspotcloud.shared.peer.PhotoData;
import com.googlecode.fspotcloud.shared.peer.TagData;
import com.googlecode.simpleblobstore.client.BlobstoreClient;

public class ShotwellBackend extends GenericBackend {
	static final Logger LOGGER = Logger.getLogger(ShotwellBackend.class
			.getName());

	@Inject
	public ShotwellBackend(@Named("JDBC URL") String jdbcURL, BlobstoreClient blobClient) {
		super(jdbcURL, blobClient);
	}

	@Override
	public Object[] getMetaData() throws SQLException {
		return new Object[] { getCount("PhotoTable"), getCount("TagTable") };
	}

	@Override
	public List<TagData> getTagData(List<String> tagIdList) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		List<TagData> tagList;
		tagList = new ArrayList<TagData>();

		for (String id : tagIdList) {
			try {
				conn = getConnection();

				Statement stmt = conn.createStatement();
				rs = stmt
						.executeQuery("SELECT id, name, photo_id_list FROM TagTable WHERE id="
								+ id);

				while (rs.next()) {
					String tagId = rs.getString(1);
					String tagPath = rs.getString(2);
					File tagPathFile = new File(tagPath);
					String tagName = tagPathFile.getName();
					String parentId = getParent(tagPath);

					int photoCount = getPhotoCountFromIds(rs.getString(3));
					tagList.add(new TagData(tagId, tagName, parentId,
							photoCount));
				}
			} finally {
				rs.close();
			}
		}

		return tagList;
	}

	private int getPhotoCountFromIds(String ids) {
		String[] splitted = ids.split(",");
		if (splitted == null) {
			return 0;
		} else {
			return splitted.length;
		}
	}

	private String getParent(String tagName) throws SQLException {
		File tag = new File(tagName);
		String dir = tag.getParent();
		Connection conn = null;
		ResultSet rs = null;
		String parent = null;
		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id FROM TagTable WHERE name=\""
					+ dir + "\"");

			if (rs.next()) {
				parent = rs.getString(1);
			} else {
				parent = "0";
			}
		} finally {
			rs.close();
		}
		return parent;
	}

	@Override
	public List<TagData> getTagData() throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		List<TagData> tagList;
		tagList = new ArrayList<TagData>();

		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			rs = stmt
					.executeQuery("SELECT id, name, photo_id_list FROM TagTable");

			while (rs.next()) {
				String tagId = rs.getString(1);
				String tagPath = rs.getString(2);
				File tagPathFile = new File(tagPath);
				String tagName = tagPathFile.getName();
				String parentId = getParent(tagPath);

				int photoCount = getPhotoCountFromIds(rs.getString(3));
				tagList.add(new TagData(tagId, tagName, parentId, photoCount));
			}
		} finally {
			rs.close();
		}

		return tagList;
	}

	@Override
	public List<String> getTagPhotos(String tagId) throws Exception {
		List<String> result = new ArrayList<String>();
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();

			rs = stmt
					.executeQuery("SELECT photo_id_list FROM TagTable WHERE id=\""
							+ tagId + "\"");

			while (rs.next()) {
				String idsCommaSeparated = rs.getString(1);
				String[] ids = idsCommaSeparated.split(",");
				for (int i = 0; i < ids.length; i++) {
					String id = ids[i];
					id = fromHex(id);
					LOGGER.info("Got from TagTable: " + id);
					result.add(id);
				}
			}
		} finally {
			rs.close();
		}

		return result;
	}

	private String fromHex(String id) {
		id = id.substring(5);
		long l = Long.parseLong(id, 16);
		id = String.valueOf(l);
		return id;
	}

	@Override
	public String getImageURL(String photoId) throws SQLException {
		String url = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			String query = "SELECT filename " + "FROM PhotoTable WHERE id = "
					+ photoId;
			rs = stmt.executeQuery(query);

			if (rs.next()) {
				url = rs.getString(1);

			}
		} finally {
			rs.close();
		}

		url = "file://" + url;
		if (photoDirectoryOverride != null) {
			url = url.replaceFirst(photoDirectoryOriginalPath,
					photoDirectoryOverride);
		}

		LOGGER.info("URL-String: " + url + " override: "
				+ photoDirectoryOverride);
		return url;
	}

	@Override
	public List<PhotoData> getPhotoData(List<String> imageKeys)
			throws SQLException {
		List<PhotoData> result = new ArrayList<PhotoData>();

		for (String imageKey : imageKeys) {
			Connection conn = null;
			ResultSet rs = null;

			try {
				conn = getConnection();

				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT id, comment, timestamp "
						+ "FROM PhotoTable WHERE id=\"" + imageKey + "\"");

				while (rs.next()) {
					String id = rs.getString(1);
					String desc = rs.getString(2);
					long time = rs.getLong(3);
					int version = 1;
					Date date = new Date();
					date.setTime(time * 1000);
					List<String> tagList = getTagsForPhoto(Integer.valueOf(id));
					result.add(new PhotoData(id, desc, date, tagList, version));
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "getPhotoData: ", e);
			} finally {
				rs.close();
			}
		}

		return result;
	}

	private List<String> getTagsForPhoto(int id) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		List<String> tagList = new ArrayList<String>();

		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			String thumbName = getThumbName(id);
			rs = stmt.executeQuery("SELECT id "
					+ "FROM TagTable WHERE photo_id_list LIKE '%" + thumbName
					+ "%'");

			while (rs.next()) {
				String tagId = rs.getString(1);
				tagList.add(tagId);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "getPhotoData: ", e);
		} finally {
			rs.close();
		}

		return tagList;
	}

	private String getThumbName(int id) {
		int i = Integer.valueOf(id);
		String result = String.format("thumb%016x", i);
		return result;
	}

	@Override
	public int getPhotoDefaultVersion(String photoId) throws SQLException {
		// Shotwell does not support versions
		return 1;
	}

	@Override
	public boolean isPhotoInTag(String tagId, String photoId)
			throws SQLException {
		boolean result = false;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id "
					+ "FROM TagTable WHERE photo_id_list LIKE \"%"
					+ getThumbName(Integer.valueOf(photoId)) + "%\" AND id=\""
					+ tagId + "\"");

			if (rs.next()) {
				result = true;
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "isPhotoInTag: ", e);
		} finally {
			rs.close();
		}

		return result;
	}

}
