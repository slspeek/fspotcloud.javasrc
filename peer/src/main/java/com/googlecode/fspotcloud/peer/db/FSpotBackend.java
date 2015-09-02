/*
 * Copyright 2010-2012 Steven L. Speek.
 * This program is free software; you can redistribute it
                and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free
                Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is
                distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
                warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public
                License for more details.
 * You should have received a copy of the GNU General Public License
 * along
                with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330,
                Boston, MA 02111-1307, USA.
 *
 */

package com.googlecode.fspotcloud.peer.db;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import com.googlecode.fspotcloud.shared.peer.ImageSpecs;
import com.googlecode.fspotcloud.shared.peer.PhotoData;
import com.googlecode.fspotcloud.shared.peer.TagData;


public class FSpotBackend extends GenericBackend implements Backend {
    static final Logger LOGGER = Logger.getLogger(FSpotBackend.class.getName());
   
    @Inject
    public FSpotBackend(@Named("JDBC URL")
                String jdbcURL) {
        super(jdbcURL);
    }

    @Override
	public Object[] getMetaData() throws SQLException {
        return new Object[]{getCount("photos"), getCount("tags")};
    }

    @Override
	public List<TagData> getTagData(List<String> tagIdList)
            throws SQLException {
        Connection conn = null;
        ResultSet rs = null;
        List<TagData> tagList;
        tagList = new ArrayList<TagData>();

        for (String id : tagIdList) {
            try {
                conn = getConnection();

                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(
                        "SELECT id, name, category_id FROM tags WHERE id=" +
                                id);

                while (rs.next()) {
                    String tagId = rs.getString(1);
                    String tagName = rs.getString(2);
                    String parentId = rs.getString(3);
                    int photoCount = getPhotoCountForTag(Integer.valueOf(tagId));
                    tagList.add(new TagData(tagId, tagName, parentId, photoCount));
                }
            } finally {
                rs.close();
            }
        }

        return tagList;
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
            rs = stmt.executeQuery("SELECT id, name, category_id FROM tags");

            while (rs.next()) {
                String tagId = rs.getString(1);
                String tagName = rs.getString(2);
                String parentId = rs.getString(3);
                int photoCount = getPhotoCountForTag(Integer.valueOf(tagId));
                tagList.add(new TagData(tagId, tagName, parentId, photoCount));
            }
        } finally {
            rs.close();
        }

        return tagList;
    }

    @Override
	public List<String> getTagPhotos(String tagId)
            throws Exception {
        List<String> result = new ArrayList<String>();
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            Statement stmt = conn.createStatement();

            rs = stmt.executeQuery(
                    "SELECT id FROM photos, photo_tags WHERE photos.id=photo_tags.photo_id AND photo_tags.tag_id=\"" +
                            tagId + "\"");

            while (rs.next()) {
                String id = rs.getString(1);
                result.add(id);
            }
        } finally {
            rs.close();
        }

        return result;
    }

    @Override
	public String getImageURL(String photoId)
            throws SQLException {
        String url = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            Statement stmt = conn.createStatement();
            String query = "SELECT default_version_id, base_uri, filename " +
                    "FROM photos WHERE id = " + photoId;
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                int version = rs.getInt(1);

                if (version == 1) {
                    url = rs.getString(2) + "/" + rs.getString(3);
                } else {
                    stmt = conn.createStatement();
                    query = "SELECT base_uri, filename " +
                            "FROM photo_versions WHERE photo_id =" + photoId +
                            " AND version_id=" + version;
                    rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        url = rs.getString(1) + "/" + rs.getString(2);
                    }
                }
            }
        } finally {
            rs.close();
        }

        if (photoDirectoryOverride != null) {
            url = url.replaceFirst(photoDirectoryOriginalPath,
                    photoDirectoryOverride);
        }

        //LOGGER.info("URL-String: " + url + " override: " + photoDirectoryOverride);
        return url;
    }

    private List<String> getTagsForPhoto(int id) throws SQLException {
        Connection conn = null;
        ResultSet rs = null;
        List<String> tagList = new ArrayList<String>();

        try {
            conn = getConnection();

            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT tag_id, photo_id " +
                    "FROM photo_tags WHERE photo_id=" + String.valueOf(id));

            while (rs.next()) {
                String tagId = rs.getString(1);
                tagList.add(tagId);
            }
        } finally {
            rs.close();
        }

        return tagList;
    }

    private int getPhotoCountForTag(int tagId) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement prep = conn.prepareStatement(
                "SELECT COUNT(photos.id) FROM photo_tags, photos WHERE tag_id=? AND photos.id=photo_tags.photo_id ");
        prep.setInt(1, tagId);

        ResultSet rs = prep.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            rs.close();

            return count;
        }

        rs.close();
        throw new IllegalStateException();
    }

    @Override
	public List<PhotoData> getPhotoData(ImageSpecs imageSpecs,
                                        List<String> imageKeys) throws SQLException {
        List<PhotoData> result = new ArrayList<PhotoData>();

        for (String imageKey : imageKeys) {
            Connection conn = null;
            ResultSet rs = null;

            try {
                conn = getConnection();

                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(
                        "SELECT id, description, time, default_version_id " +
                                "FROM photos WHERE id=\"" + imageKey + "\"");

                while (rs.next()) {
                    String id = rs.getString(1);
                    String desc = rs.getString(2);
                    long time = rs.getLong(3);
                    int version = rs.getInt(4);
                    Date date = new Date();
                    date.setTime(time * 1000);

                    List<String> tagList = getTagsForPhoto(Integer.valueOf(id));
                    String url = getImageURL(id);
                    byte[] image = imageData.getScaledImageData(url,
                            new Dimension(imageSpecs.getWidth(),
                                    imageSpecs.getHeight()));
                    byte[] thumb = imageData.getScaledImageData(url,
                            new Dimension(imageSpecs.getThumbWidth(),
                                    imageSpecs.getThumbHeight()));
                    //TODO: Upload image and thumb to new API
                    result.add(new PhotoData(id, desc, date, 
                            tagList, version));
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "getPhotoData: ", e);
            } finally {
                rs.close();
            }
        }

        return result;
    }

    @Override
	public int getPhotoDefaultVersion(String photoId) throws SQLException {
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(
                    "SELECT id, description, time, default_version_id " +
                            "FROM photos WHERE id=\"" + photoId + "\"");

            if (rs.next()) {
                int version = rs.getInt(4);

                return version;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "getPhotoDefaultVersion: ", e);
        } finally {
            rs.close();
        }

        return -1;
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
            rs = stmt.executeQuery("SELECT photo_id, tag_id " +
                    "FROM photo_tags WHERE photo_id=\"" + photoId +
                    "\" AND tag_id=\"" + tagId + "\"");

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
