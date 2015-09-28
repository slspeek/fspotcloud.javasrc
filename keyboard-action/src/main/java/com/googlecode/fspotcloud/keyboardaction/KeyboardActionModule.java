package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.keyboardaction.gwt.*;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class KeyboardActionModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(IPlaceController.class).to(PlaceControllerImpl.class);
		bind(IModeController.class).toProvider(ModeControllerProvider.class)
				.in(Singleton.class);
		bind(IActionManager.class).toProvider(ActionManagerFactory.class).in(
				Singleton.class);
		bind(DemoPopupView.class).to(DemoPopup.class);
		bind(ConfigBuilder.class).in(Singleton.class);
		bind(ActionUIRegistry.class).in(Singleton.class);
		bind(NativePreviewHandler.class).in(Singleton.class);
		bind(KeyboardPreferences.class).in(Singleton.class);
		bind(ActionHandlerRegistry.class).in(Singleton.class);
		//bind(DemoBuilder.class);
		bind(DemoBuilderFactory.class).in(Singleton.class);
		bind(WidgetRegistry.class).in(Singleton.class);
		//bind(TwoColumnHelpPopup.class);
		bind(HelpActionsFactory.class).in(Singleton.class);
		bind(PlaceContext.class).toProvider(PlaceContextProvider.class);
		bind(TimerInterface.class).annotatedWith(NextTimer.class).to(
				TimerImpl.class);
		bind(TimerInterface.class).annotatedWith(ActionTimer.class).to(
				TimerImpl.class);
		bind(TimerInterface.class).annotatedWith(PreviewTimer.class).to(
				TimerImpl.class);
		bind(IHelpContentGenerator.class).to(HelpContentGenerator.class);
		bind(KeyboardActionFactory.class).asEagerSingleton();//.in(Singleton.class);
		install(new GinFactoryModuleBuilder().build(ToolbarFactory.class));
	}

	@Provides
	@Singleton
	public List<ActionCategory> getActionCategoryList() {
		List<ActionCategory> r = newArrayList();
		return r;
	}

	@Provides
	@Singleton
	public List<Demo> getDemoList() {
		List<Demo> r = newArrayList();
		return r;
	}
}
