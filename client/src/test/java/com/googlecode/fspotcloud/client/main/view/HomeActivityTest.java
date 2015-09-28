package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.HomeView;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.verify;

@RunWith(JukitoRunner.class)
public class HomeActivityTest {

	public static final String RESULT = "Foo";
	@Inject
	private HomeActivity activity;

	@Inject
	private HomeView homeView;
	@Inject
	private Navigator navigator;

	@Inject
	SimpleEventBus eventBus;
	@Inject
	private AcceptsOneWidget acceptsOneWidget;
	@Inject
	ArgumentCaptor<AsyncCallback<String>> captor;

	@Test
	public void testStartSuccess() throws Exception {
		activity.start(acceptsOneWidget, eventBus);
		verify(navigator).goToLatestTag(captor.capture());
		captor.getValue().onSuccess(RESULT);
		verify(homeView).setStatusText(RESULT);
		verify(acceptsOneWidget).setWidget(homeView);

	}

	@Test
	public void testStartFailure() throws Exception {
		activity.start(acceptsOneWidget, eventBus);
		verify(navigator).goToLatestTag(captor.capture());
		captor.getValue().onFailure(new Exception("Foo"));
		verify(homeView).setStatusText(HomeActivity.SERVER_ERROR + "Foo");
		verify(acceptsOneWidget).setWidget(homeView);

	}
}
