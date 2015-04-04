package com.googlecode.fspotcloud.peer.db;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import com.googlecode.fspotcloud.shared.peer.ImageSpecs;
import com.googlecode.fspotcloud.shared.peer.PhotoData;
import com.googlecode.fspotcloud.shared.peer.TagData;

public interface Backend {

	void setJDBCUrl(String jdbcURL) throws SQLException;

	int getCount(String kind) throws SQLException;

	Object[] getMetaData() throws SQLException;

	List<TagData> getTagData(List<String> tagIdList) throws SQLException;

	List<TagData> getTagData() throws SQLException;

	List<String> getTagPhotos(String tagId) throws Exception;

	String getImageURL(String photoId) throws SQLException;

	List<PhotoData> getPhotoData(ImageSpecs imageSpecs, List<String> imageKeys)
			throws SQLException;

	int getPhotoDefaultVersion(String photoId) throws SQLException;

	boolean isPhotoInTag(String tagId, String photoId) throws SQLException;

	byte[] getFullsizePhotoData(String imageKey) throws IOException,
			SQLException, URISyntaxException;

}