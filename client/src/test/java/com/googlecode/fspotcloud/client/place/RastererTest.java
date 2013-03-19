package com.googlecode.fspotcloud.client.place;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.RasterHeight;
import com.googlecode.fspotcloud.client.main.gin.RasterWidth;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class RastererTest {


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
}
