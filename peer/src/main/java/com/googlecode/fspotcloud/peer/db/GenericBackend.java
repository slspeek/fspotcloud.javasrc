package com.googlecode.fspotcloud.peer.db;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.googlecode.fspotcloud.peer.ImageData;
import com.googlecode.fspotcloud.shared.peer.ImageSpecs;
import com.googlecode.fspotcloud.shared.peer.PhotoData;
import com.googlecode.simpleblobstore.BlobKey;
import com.googlecode.simpleblobstore.client.BlobstoreClient;

public abstract class GenericBackend implements Backend {
	static final Logger LOGGER = Logger.getLogger(FSpotBackend.class.getName());

	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Driver not found", e);
		}
	}

	protected String jdbcURL;
	protected final String photoDirectoryOverride;
	protected final String photoDirectoryOriginalPath;
	protected final BlobstoreClient blobClient;
	protected final ImageData imageData = new ImageData();
	private Connection connection;

	public GenericBackend(String jdbcURL, BlobstoreClient blobClient) {
		this.jdbcURL = jdbcURL;
		this.blobClient = blobClient;
		this.photoDirectoryOverride = System.getProperty("photo.dir.override");
		this.photoDirectoryOriginalPath = System
				.getProperty("photo.dir.original");
	}

	@Override
	@VisibleForTesting
	public void setJDBCUrl(String jdbcURL) throws SQLException {
		// LOGGER.info("setting: " + jdbcURL);
		this.jdbcURL = jdbcURL;

		if (connection != null) {
			// connection.close();
			connection = null;
		}
	}

	protected Connection getConnection() throws SQLException {
		if (connection == null) {
			LOGGER.info("Opening new connection: " + jdbcURL);
			connection = DriverManager.getConnection(jdbcURL);
		}
		return connection;
	}

	@Override
	public int getCount(String kind) throws SQLException {
		Connection conn = null;
		int result;
		conn = getConnection();

		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT count(id) FROM " + kind);

		if (rs.next()) {
			result = rs.getInt(1);
		} else {
			throw new SQLException("Result for count query was empty");
		}

		return result;
	}

	public abstract String getImageURL(String key) throws SQLException;

	@Override
	public byte[] getFullsizePhotoData(String imageKey) throws IOException,
			SQLException, URISyntaxException {
		String url = getImageURL(imageKey);
		URI fileURI = new URI(url);
		File imageFile = new File(fileURI);

		return Files.toByteArray(imageFile);
	}

	@Override
	public void uploadImages(ImageSpecs imageSpecs, List<PhotoData> photos) throws Exception {
		// collect all byte[]
		Map<String, byte[]> uploadData = Maps.newHashMap();
		for (PhotoData photo: photos) {
			 String id = photo.getPhotoId();
			 String url = getImageURL(id);
             byte[] image = imageData.getScaledImageData(url,
                     new Dimension(imageSpecs.getWidth(),
                             imageSpecs.getHeight()));
             byte[] thumb = imageData.getScaledImageData(url,
                     new Dimension(imageSpecs.getThumbWidth(),
                             imageSpecs.getThumbHeight()));
             uploadData.put(id +"_thumb", thumb);
             uploadData.put(id +"_image", image);
		}
		// call blobstoreclient.upload
		Map<String, List<BlobKey>> upload = blobClient.upload(uploadData);
		// modify photos with the result 
		for (PhotoData photo: photos) {
			 String id = photo.getPhotoId();
			 photo.setThumbBlobKey(upload.get(id +"_thumb").get(0).getKeyString());	
			 photo.setImageBlobKey(upload.get(id +"_image").get(0).getKeyString());
		}
	}
}