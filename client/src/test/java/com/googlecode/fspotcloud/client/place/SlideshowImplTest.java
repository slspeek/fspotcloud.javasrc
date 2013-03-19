package com.googlecode.fspotcloud.client.place;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.fspotcloud.client.main.gin.RasterHeight;
import com.googlecode.fspotcloud.client.main.gin.RasterWidth;
import com.googlecode.fspotcloud.client.main.view.api.TimerInterface;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(JukitoRunner.class)
public class SlideshowImplTest {


    public static class Module extends JukitoModule {

        @Override
        protected void configureTest() {
            bind(Integer.class).annotatedWith(RasterWidth.class).toInstance(5);
            bind(Integer.class).annotatedWith(RasterHeight.class).toInstance(4);
            bind(EventBus.class).to(SimpleEventBus.class);
        }
    }
    @Inject private SlideshowImpl slideshow;
    @Inject private Navigator navigator;
    @Inject private EventBus eventBus;
    @Inject private TimerInterface timer;
    @Inject private ArgumentCaptor<Runnable> runnableCaptor;


    private Runnable getRunnable() {
        verify(timer).setRunnable(runnableCaptor.capture());
        return runnableCaptor.getValue();
    }

    @Test
    public void testStart() throws Exception {
        slideshow.start();
        Runnable runnable = getRunnable();
        assertTrue(slideshow.isRunning());
        verify(timer).scheduleRepeating(4000);
    }

    @Test
    public void testStop() throws Exception {
        slideshow.stop();
        assertFalse(slideshow.isRunning());
        verify(timer).cancel();
        verify(navigator).unslideshow();


    }

    @Test
    public void testPause() throws Exception {
        slideshow.pause();
        assertFalse(slideshow.isRunning());
        verify(timer).cancel();


    }

    @Test
    public void testTogglePause() throws Exception {
        slideshow.togglePause();
        assertTrue(slideshow.isRunning());
        verify(timer).cancel();
        verify(timer).scheduleRepeating(4000);

    }

    @Test
    public void testFaster() throws Exception {

    }

    @Test
    public void testSlower() throws Exception {

    }

    @Test
    public void testDelay() throws Exception {

    }

    @Test
    public void testIsRunning() throws Exception {

    }
}
