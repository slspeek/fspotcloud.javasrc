package com.googlecode.fspotcloud.server.image;

import com.googlecode.fspotcloud.model.jpa.photo.PhotoEntity;
import com.googlecode.fspotcloud.server.model.api.Photo;
import com.googlecode.simpleblobstore.BlobKey;
import com.googlecode.simpleblobstore.BlobService;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class ImageHelperImplTest {

    public static final String IMAGE_BLOB_KEY = "imageblobkey";
    public static final String FULLSIZE_IMAGE_BLOB_KEY = "fullsize";
    public static final String THUMB_BLOB_KEY = "thumb";
    public static final String KEY_STRING = "FOO";
    @Inject ImageHelperImpl helper;
    @Inject
    BlobService blobService;

    Photo photo = new PhotoEntity();

    @Before
    public void setUp() throws Exception {
        photo.setImageBlobKey(IMAGE_BLOB_KEY);
        photo.setFullsizeImageBlobKey(FULLSIZE_IMAGE_BLOB_KEY);
        photo.setThumbBlobKey(THUMB_BLOB_KEY);
    }

    @Test
    public void testNullPhoto() throws Exception {
        assertNull(helper.getImage(null, ImageHelper.Type.FULLSIZE));
        verifyNoMoreInteractions(blobService);
    }

    @Test
    public void testGetImage() throws Exception {
        byte[] image = helper.getImage(photo, ImageHelper.Type.NORMAL);
        verify(blobService).fetchData(new BlobKey(IMAGE_BLOB_KEY));
        assertNull(image);
        verifyNoMoreInteractions(blobService);
    }

    @Test
    public void testGetThumb() throws Exception {
        byte[] image = helper.getImage(photo, ImageHelper.Type.THUMB);
        verify(blobService).fetchData(new BlobKey(THUMB_BLOB_KEY));
        assertNull(image);
        verifyNoMoreInteractions(blobService);
    }

    @Test
    public void testGetFS() throws Exception {
        byte[] image = helper.getImage(photo, ImageHelper.Type.FULLSIZE);
        verify(blobService).fetchData(new BlobKey(FULLSIZE_IMAGE_BLOB_KEY));
        assertNull(image);
        verifyNoMoreInteractions(blobService);
    }

    @Test
    public void testSaveImage() throws Exception {
        byte[] image = new byte[10];
        when(blobService.save(ImageHelperImpl.IMAGE_JPEG, image)).thenReturn(new BlobKey(KEY_STRING));
        helper.saveImage(photo, ImageHelper.Type.FULLSIZE, image);
        verify(blobService).save(ImageHelperImpl.IMAGE_JPEG, image);
        assertEquals(KEY_STRING, photo.getFullsizeImageBlobKey());
        verifyNoMoreInteractions(blobService);
    }

    @Test
    public void testSaveThumb() throws Exception {
        byte[] image = new byte[10];
        when(blobService.save(ImageHelperImpl.IMAGE_JPEG, image)).thenReturn(new BlobKey(KEY_STRING));
        helper.saveImage(photo, ImageHelper.Type.THUMB, image);
        verify(blobService).save(ImageHelperImpl.IMAGE_JPEG, image);
        assertEquals(KEY_STRING, photo.getThumbBlobKey());
        verifyNoMoreInteractions(blobService);
    }
}
