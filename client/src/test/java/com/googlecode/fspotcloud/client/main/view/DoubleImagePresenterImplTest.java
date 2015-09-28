package com.googlecode.fspotcloud.client.main.view;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.DoubleImageView;
import com.googlecode.fspotcloud.client.main.view.api.IScheduler;
import com.googlecode.fspotcloud.client.place.SlideshowPlace;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.place.api.Slideshow;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.mockito.Mockito.verify;

@RunWith(JukitoRunner.class)
public class DoubleImagePresenterImplTest {
	@Inject
	private DoubleImageView imageView;
	@Inject
	private Navigator navigator;
	@Inject
	private Slideshow slideshow;
	@Inject
	private IScheduler scheduler;
	@Inject
	DoubleImagePresenterImpl presenter;
	private SlideshowPlace slideshowPlace = new SlideshowPlace("1", "1");
	private SlideshowPlace slideshowPlaceNext = new SlideshowPlace("1", "2");
	private PhotoInfo photoInfo = new PhotoInfo("1", "Me again", new Date(0));

	@Test
	public void testInit() throws Exception {
		presenter.init();
	}

	@Test
	public void testSetImage() throws Exception {
		presenter.setImage();
	}

	@Test
	public void testImageClicked() throws Exception {
		presenter.imageClicked();

	}

	@Test
	public void testSetCurrentPlace() throws Exception {
		presenter.setCurrentPlace(slideshowPlace);
		verify(navigator).getPageAsync(slideshowPlace.getTagId(),
				slideshowPlace.getPhotoId(), 1, presenter);

	}

	@Test
	public void testSetCurrentPlaceTwice() throws Exception {
		presenter.setCurrentPlace(slideshowPlace);
		verify(navigator).getPageAsync(slideshowPlace.getTagId(),
				slideshowPlace.getPhotoId(), 1, presenter);
		presenter.setCurrentPlace(slideshowPlaceNext);
		verify(navigator).getPageAsync(slideshowPlaceNext.getTagId(),
				slideshowPlaceNext.getPhotoId(), 1, presenter);
		presenter.setImage();
	}

	@Test
	public void testOnFailure() throws Exception {
		presenter.onFailure(new Exception());
	}

	@Test
	public void testOnSuccess() throws Exception {
		//presenter.onSuccess(newArrayList(photoInfo));

	}
}
