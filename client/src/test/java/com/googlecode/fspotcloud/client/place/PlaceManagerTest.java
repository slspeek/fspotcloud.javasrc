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

package com.googlecode.fspotcloud.client.place;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.RasterHeight;
import com.googlecode.fspotcloud.client.main.gin.RasterWidth;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.place.api.Navigator.Zoom;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(JukitoRunner.class)
public class PlaceManagerTest {
    public static final String TAG_ID1 = "1";
    public static final String PHOTO_ID = "10";
    final boolean autoHide = false;
    @Inject
    private PlaceManager placeManager;
    @Inject
    private RasterState rasterState;
    @Inject
    private IPlaceController placeController;

    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {
            bind(Integer.class).annotatedWith(RasterWidth.class).toInstance(3);
            bind(Integer.class).annotatedWith(RasterHeight.class).toInstance(2);
        }
    }

    @Test
    public void testUnslideshowNoop() {
        BasePlace tagViewingPlace = new BasePlace(TAG_ID1, PHOTO_ID, 2, 1, autoHide);
        when(placeController.where()).thenReturn(tagViewingPlace);
        placeManager.unslideshow();
        verify(placeController).where();
        verifyNoMoreInteractions(placeController);
    }

    @Test
    public void testUnslideshowNoBasePlace() {
        BasePlace tagViewingPlace = new BasePlace(TAG_ID1, PHOTO_ID, 2, 1, autoHide);
        placeManager.unslideshow();

        verify(placeController).where();
        verifyNoMoreInteractions(placeController);
    }

    @Test
    public void testUnslideshow(ArgumentCaptor<Place> captor) {
        SlideshowPlace place = new SlideshowPlace(TAG_ID1, "2");
        when(placeController.where()).thenReturn(place);
        placeManager.unslideshow();
        verify(placeController).goTo(captor.capture());
        Place newPlace = captor.getValue();
        assertFalse(newPlace instanceof SlideshowPlace);
    }

    @Test
    public void testToggleRasterView(ArgumentCaptor<Place> captor) {
        BasePlace tagViewingPlace = new BasePlace(TAG_ID1, PHOTO_ID, 2, 1, autoHide);
        when(placeController.where()).thenReturn(tagViewingPlace);
        placeManager.toggleRasterView();
        verify(placeController).goTo(captor.capture());
        BasePlace newPlace = (BasePlace) captor.getValue();
        assertEquals(TAG_ID1, newPlace.getTagId());
        assertEquals(PHOTO_ID, newPlace.getPhotoId());
        assertEquals(1, newPlace.getColumnCount());
        assertEquals(1, newPlace.getRowCount());
    }

    @Test
    public void testZoomViewOut() {
        BasePlace tagViewingPlace = new BasePlace(TAG_ID1, PHOTO_ID, 1, 1, autoHide);
        BasePlace newPlace = (BasePlace) placeManager.toggleZoomView(tagViewingPlace,
                TAG_ID1, PHOTO_ID);
        assertEquals(TAG_ID1, ((BasePlace) newPlace).getTagId());
        assertEquals(PHOTO_ID, ((BasePlace) newPlace).getPhotoId());
        assertEquals(rasterState.rasterWidth,
                newPlace.getColumnCount());
        assertEquals(rasterState.rasterHeight,
                newPlace.getRowCount());
    }

    @Test
    public void testGetFullscreen(ArgumentCaptor<Place> captor) {
        BasePlace tagViewingPlace = new BasePlace(TAG_ID1, PHOTO_ID, 13, 41, autoHide);
        when(placeController.where()).thenReturn(tagViewingPlace);
        placeManager.goOneByOne();
        verify(placeController).goTo(captor.capture());
        BasePlace newPlace = (BasePlace) captor.getValue();
        assertEquals(TAG_ID1, ((BasePlace) newPlace).getTagId());
        assertEquals(PHOTO_ID, ((BasePlace) newPlace).getPhotoId());
        assertEquals(1, newPlace.getColumnCount());
        assertEquals(1, newPlace.getRowCount());
    }

    @Test
    public void testZoomIn() {
        BasePlace tagViewingPlace = new BasePlace(TAG_ID1, PHOTO_ID, 3, 3, autoHide);
        BasePlace newPlace = placeManager.zoom(tagViewingPlace, Zoom.IN);
        assertEquals(TAG_ID1, ((BasePlace) newPlace).getTagId());
        assertEquals(PHOTO_ID, ((BasePlace) newPlace).getPhotoId());
        assertEquals(2, newPlace.getColumnCount());
        assertEquals(2, newPlace.getRowCount());
        newPlace = placeManager.zoom(newPlace, Zoom.IN);
        assertEquals(TAG_ID1, ((BasePlace) newPlace).getTagId());
        assertEquals(PHOTO_ID, ((BasePlace) newPlace).getPhotoId());
        assertEquals(1, newPlace.getColumnCount());
        assertEquals(1, newPlace.getRowCount());

        newPlace = placeManager.zoom(newPlace, Zoom.IN);
        assertEquals(1, newPlace.getColumnCount());
        assertEquals(1, newPlace.getRowCount());

        newPlace = placeManager.zoom(newPlace, Zoom.IN);
        assertEquals(1, newPlace.getColumnCount());
        assertEquals(1, newPlace.getRowCount());
    }

    @Test
    public void testZoomOut() {
        BasePlace tagViewingPlace = new BasePlace(TAG_ID1, PHOTO_ID, 2, 2, autoHide);

        BasePlace newPlace = placeManager.zoom(tagViewingPlace, Zoom.OUT);
        assertEquals(TAG_ID1, ((BasePlace) newPlace).getTagId());
        assertEquals(PHOTO_ID, ((BasePlace) newPlace).getPhotoId());
        assertEquals(3, newPlace.getColumnCount());
        assertEquals(3, newPlace.getRowCount());
        newPlace = placeManager.zoom(newPlace, Zoom.OUT);
        assertEquals(TAG_ID1, ((BasePlace) newPlace).getTagId());
        assertEquals(PHOTO_ID, ((BasePlace) newPlace).getPhotoId());
        assertEquals(4, newPlace.getColumnCount());
        assertEquals(4, newPlace.getRowCount());
    }
}
