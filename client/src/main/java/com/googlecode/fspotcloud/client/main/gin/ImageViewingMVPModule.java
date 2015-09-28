package com.googlecode.fspotcloud.client.main.gin;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.client.main.ui.*;
import com.googlecode.fspotcloud.client.main.view.*;
import com.googlecode.fspotcloud.client.main.view.api.*;
import com.googlecode.fspotcloud.client.main.view.factory.SingleImageViewProvider;
import com.googlecode.fspotcloud.client.main.view.factory.SlideshowActivityFactoryImpl;
import com.googlecode.fspotcloud.client.main.view.factory.SlideshowDelayPresenterFactoryImpl;
import com.googlecode.fspotcloud.client.main.view.factory.TagActivityFactoryImpl;

@GwtCompatible
public class ImageViewingMVPModule extends AbstractGinModule {
	@Override
	protected void configure() {
		bind(StatusView.class).to(StatusViewImpl.class);
		bind(HomeView.class).to(HomeViewImpl.class);
		bind(HomeView.HomePresenter.class).to(HomeActivity.class);
		bind(ImageView.class).annotatedWith(SingleImageView.class)
				.toProvider(SingleImageViewProvider.class).in(Singleton.class);
		bind(DoubleImageView.class).to(DoubleImageViewImpl.class).in(
				Singleton.class);

		bind(MailFullsizeView.class).to(MailFullsizeViewImpl.class).in(
				Singleton.class);
		bind(MailFullsizeView.MailFullsizePresenter.class).to(
				MailFullsizeActivity.class).in(Singleton.class);
		bind(TagView.class).to(TagViewImpl.class).in(Singleton.class);
		bind(TagActivityFactory.class).to(TagActivityFactoryImpl.class);
		bind(ImageRasterView.class).to(ImageRasterViewImpl.class);
		bind(SlideshowActivityFactory.class).to(
				SlideshowActivityFactoryImpl.class);
		bind(SlideshowView.class).to(SlideshowViewImpl.class);
		bind(SlideshowDelayView.class).to(SlideshowDelayViewImpl.class).in(
				Singleton.class);
		bind(SlideshowDelayView.SlideshowPresenter.class).toProvider(
				SlideshowDelayPresenterFactoryImpl.class);
		install(new GinFactoryModuleBuilder().implement(
				ImageRasterView.ImageRasterPresenter.class,
				ImageRasterPresenterImpl.class).build(
				ImageRasterPresenterFactory.class));
		install(new GinFactoryModuleBuilder().implement(
				ImageView.ImagePresenter.class, ImagePresenterImpl.class)
				.build(ImagePresenterFactory.class));
		install(new GinFactoryModuleBuilder().implement(ImageView.class,
				ImageViewImpl.class).build(ImageViewFactory.class));
		bind(TreeView.class).annotatedWith(BasicTreeView.class)
				.toProvider(BasicTreeViewProvider.class).in(Singleton.class);
		bind(TreeView.TreePresenter.class).annotatedWith(BasicTreeView.class)
				.to(TreePresenterImpl.class).in(Singleton.class);
		bind(ITreeSelectionHandler.class).annotatedWith(BasicTreeView.class)
				.to(TreeSelectionHandler.class).in(Singleton.class);

	}
}
