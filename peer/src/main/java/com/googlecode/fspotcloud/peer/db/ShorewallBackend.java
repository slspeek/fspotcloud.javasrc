package com.googlecode.fspotcloud.peer.db;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.googlecode.fspotcloud.shared.peer.ImageSpecs;
import com.googlecode.fspotcloud.shared.peer.PhotoData;
import com.googlecode.fspotcloud.shared.peer.TagData;

public class ShorewallBackend extends GenericBackend {
	static final Logger LOGGER = Logger.getLogger(ShorewallBackend.class.getName());
	@Inject
	public ShorewallBackend(@Named("JDBC URL") String jdbcURL) {
		super(jdbcURL);
	}

	@Override
	public Object[] getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TagData> getTagData(List<String> tagIdList) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TagData> getTagData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getTagPhotos(String tagId) throws Exception {
		   List<String> result = new ArrayList<String>();
	        Connection conn = null;
	        ResultSet rs = null;

	        try {
	            conn = getConnection();

	            Statement stmt = conn.createStatement();

	            rs = stmt.executeQuery(
	                    "SELECT photo_id_list FROM TagTable WHERE id=\"" +
	                            tagId + "\"");

	            while (rs.next()) {
	                String idsCommaSeparated = rs.getString(1);
	                String[] ids = idsCommaSeparated.split(",");
	                for (int i = 0; i < ids.length; i++) {
	                	String id = ids[i];
	                	id = fromHex(id);
	                	LOGGER.info("Got from TagTable: " + id );
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
            String query = "SELECT filename " +
                    "FROM PhotoTable WHERE id = " + photoId;
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                 url = rs.getString( 1);

                            }
        } finally {
            rs.close();
        }

        if (photoDirectoryOverride != null) {
            url = url.replaceFirst(photoDirectoryOriginalPath,
                    photoDirectoryOverride);
        }

        url = "file://" + url;
        LOGGER.info("URL-String: " + url + " override: " + photoDirectoryOverride);
        return url;
	}

	@Override
	public List<PhotoData> getPhotoData(ImageSpecs imageSpecs,
			List<String> imageKeys) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPhotoDefaultVersion(String photoId) throws SQLException {
		// Shotwell does not support versions
		return 1;
	}

	@Override
	public boolean isPhotoInTag(String tagId, String photoId)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte[] getFullsizePhotoData(String imageKey) throws IOException,
			SQLException, URISyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

}
