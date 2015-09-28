package com.googlecode.fspotcloud.client.enduseraction.navigation.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.RasterHeight;
import com.googlecode.fspotcloud.client.main.gin.RasterWidth;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class AllPhotosHandlerTest {

	public static class Module extends JukitoModule {

		@Override
		protected void configureTest() {
			bind(Integer.class).annotatedWith(RasterWidth.class).toInstance(5);
			bind(Integer.class).annotatedWith(RasterHeight.class).toInstance(4);
		}
	}
	@Inject
	private AllPhotosHandler handler;

	@Inject
	private IPlaceController placeController;

	@Test
	public void testGoAll() throws Exception {
		handler.performAction(null);
		verify(placeController).goTo(new BasePlace("all", null, 5, 4, false));
	}

	@Test
	public void testGoAllFromPlace() throws Exception {
		when(placeController.where()).thenReturn(
				new BasePlace("1", "21", 22, 2, false));
		handler.performAction(null);
		verify(placeController).goTo(new BasePlace("all", "21", 22, 2, false));
	}

}
