package com.googlecode.fspotcloud.client.main.event;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.client.main.gin.RasterHeight;
import com.googlecode.fspotcloud.client.main.gin.RasterWidth;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.RasterState;
import com.googlecode.fspotcloud.client.place.Rasterer;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.place.api.IRasterer;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class ZoomViewEventHandlerImplTest {
	@Inject
	private ZoomViewEventHandlerImpl zoomViewEventHandler;
	@Inject
	private IPlaceController placeController;
	@Inject
	private ArgumentCaptor<Place> placeCaptor;
	@Inject
	private RasterState rasterState;

	public static class Module extends JukitoModule {

		@Override
		protected void configureTest() {
			bind(Integer.class).annotatedWith(RasterWidth.class).toInstance(3);
			bind(Integer.class).annotatedWith(RasterHeight.class).toInstance(2);
			bind(RasterState.class).in(Singleton.class);
			bind(IRasterer.class).to(Rasterer.class);
		}
	}

	@Test
	public void testToOneByOne() throws Exception {
		final BasePlace value = new BasePlace("1", "1", 4, 4, true);
		when(placeController.where()).thenReturn(value);
		zoomViewEventHandler.onEvent(new ZoomViewEvent("1", "2"));
		verify(placeController).where();
		verify(placeController).goTo(placeCaptor.capture());
		BasePlace basePlace = (BasePlace) placeCaptor.getValue();
		//System.out.println(basePlace);
		assertEquals("2", basePlace.getPhotoId());
		assertEquals("1", basePlace.getTagId());
		assertEquals(1, basePlace.getColumnCount());
		assertEquals(1, basePlace.getRowCount());
	}

	@Test
	public void testToDefaultRasterSize() throws Exception {
		final BasePlace value = new BasePlace("1", "1", 1, 1, true);
		when(placeController.where()).thenReturn(value);
		zoomViewEventHandler.onEvent(new ZoomViewEvent("1", "2"));
		verify(placeController).where();
		verify(placeController).goTo(placeCaptor.capture());
		BasePlace basePlace = (BasePlace) placeCaptor.getValue();
		//System.out.println(basePlace);
		assertEquals("2", basePlace.getPhotoId());
		assertEquals("1", basePlace.getTagId());
		assertEquals(rasterState.rasterWidth, basePlace.getColumnCount());
		assertEquals(rasterState.rasterHeight, basePlace.getRowCount());
	}
}
