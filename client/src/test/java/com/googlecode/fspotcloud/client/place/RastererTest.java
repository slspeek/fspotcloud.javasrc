package com.googlecode.fspotcloud.client.place;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.RasterHeight;
import com.googlecode.fspotcloud.client.main.gin.RasterWidth;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class RastererTest {
	public static final String TAG_ID = "1";
	public static final String PHOTO_ID = "10";

	public static class Module extends JukitoModule {
		@Override
		protected void configureTest() {
			bind(Integer.class).annotatedWith(RasterWidth.class).toInstance(3);
			bind(Integer.class).annotatedWith(RasterHeight.class).toInstance(2);
		}
	}

	@Inject
	private Rasterer rasterer;
	@Inject
	private IPlaceController placeController;
	@Inject
	private RasterState rasterState;

	private BasePlace here = new BasePlace("1", "1");

	@Before
	public void setUp() throws Exception {
		when(placeController.where()).thenReturn(here);
		rasterState.setRowCount(10);
		rasterState.setColumnCount(20);
	}

	@Test
	public void testIncreaseRasterWidth() throws Exception {

		rasterer.increaseRasterWidth(1);
		assertEquals(21, rasterState.getColumnCount());
		verify(placeController).goTo(new BasePlace("1", "1", 21, 10, false));
	}

	@Test
	public void testIncreaseRasterHeight() throws Exception {
		rasterer.increaseRasterHeight(1);
		assertEquals(11, rasterState.getRowCount());
		verify(placeController).goTo(new BasePlace("1", "1", 20, 11, false));
	}

	@Test
	public void testSetRasterDimension() throws Exception {
		rasterer.setRasterDimension(2, 3);
		assertEquals(3, rasterState.getRowCount());
		assertEquals(2, rasterState.getColumnCount());
		verify(placeController).goTo(new BasePlace("1", "1", 2, 3, false));

	}

	@Test
	public void testResetRasterSize() throws Exception {
		rasterer.resetRasterSize();

		assertEquals(2, rasterState.getRowCount());
		assertEquals(3, rasterState.getColumnCount());

		verify(placeController).goTo(new BasePlace("1", "1", 3, 2, false));
	}

	@Test
	public void testToggleRasterView(ArgumentCaptor<Place> captor) {
		BasePlace tagViewingPlace = new BasePlace(TAG_ID, PHOTO_ID, 2, 1);
		when(placeController.where()).thenReturn(tagViewingPlace);
		rasterer.toggleRasterView();
		verify(placeController).goTo(captor.capture());
		BasePlace newPlace = (BasePlace) captor.getValue();
		assertEquals(TAG_ID, newPlace.getTagId());
		assertEquals(PHOTO_ID, newPlace.getPhotoId());
		assertEquals(1, newPlace.getColumnCount());
		assertEquals(1, newPlace.getRowCount());
	}

	@Test
	public void testGetFullscreen(ArgumentCaptor<Place> captor) {
		BasePlace tagViewingPlace = new BasePlace(TAG_ID, PHOTO_ID, 13, 41);
		when(placeController.where()).thenReturn(tagViewingPlace);
		rasterer.goOneByOne();
		verify(placeController).goTo(captor.capture());
		BasePlace newPlace = (BasePlace) captor.getValue();
		assertEquals(TAG_ID, ((BasePlace) newPlace).getTagId());
		assertEquals(PHOTO_ID, ((BasePlace) newPlace).getPhotoId());
		assertEquals(1, newPlace.getColumnCount());
		assertEquals(1, newPlace.getRowCount());
	}

	@Test
	public void testZoomIn() {
		BasePlace tagViewingPlace = new BasePlace(TAG_ID, PHOTO_ID, 3, 3);
		BasePlace newPlace = rasterer.zoom(tagViewingPlace, Navigator.Zoom.IN);
		assertEquals(TAG_ID, ((BasePlace) newPlace).getTagId());
		assertEquals(PHOTO_ID, ((BasePlace) newPlace).getPhotoId());
		assertEquals(2, newPlace.getColumnCount());
		assertEquals(2, newPlace.getRowCount());
		newPlace = rasterer.zoom(newPlace, Navigator.Zoom.IN);
		assertEquals(TAG_ID, ((BasePlace) newPlace).getTagId());
		assertEquals(PHOTO_ID, ((BasePlace) newPlace).getPhotoId());
		assertEquals(1, newPlace.getColumnCount());
		assertEquals(1, newPlace.getRowCount());

		newPlace = rasterer.zoom(newPlace, Navigator.Zoom.IN);
		assertEquals(1, newPlace.getColumnCount());
		assertEquals(1, newPlace.getRowCount());

		newPlace = rasterer.zoom(newPlace, Navigator.Zoom.IN);
		assertEquals(1, newPlace.getColumnCount());
		assertEquals(1, newPlace.getRowCount());
	}

	@Test
	public void testZoomOut() {
		BasePlace tagViewingPlace = new BasePlace(TAG_ID, PHOTO_ID, 2, 2);

		BasePlace newPlace = rasterer.zoom(tagViewingPlace, Navigator.Zoom.OUT);
		assertEquals(TAG_ID, ((BasePlace) newPlace).getTagId());
		assertEquals(PHOTO_ID, ((BasePlace) newPlace).getPhotoId());
		assertEquals(3, newPlace.getColumnCount());
		assertEquals(3, newPlace.getRowCount());
		newPlace = rasterer.zoom(newPlace, Navigator.Zoom.OUT);
		assertEquals(TAG_ID, ((BasePlace) newPlace).getTagId());
		assertEquals(PHOTO_ID, ((BasePlace) newPlace).getPhotoId());
		assertEquals(4, newPlace.getColumnCount());
		assertEquals(4, newPlace.getRowCount());
	}
}
