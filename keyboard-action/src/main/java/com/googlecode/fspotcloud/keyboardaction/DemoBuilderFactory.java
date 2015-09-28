package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class DemoBuilderFactory {

	private final Provider<DemoBuilder> demoBuilderProvider;
	private final Provider<Demo> demoProvider;
	private final List<Demo> demoList = newArrayList();

	@Inject
	private DemoBuilderFactory(Provider<DemoBuilder> demoBuilderProvider,
			Provider<Demo> demoProvider) {
		this.demoBuilderProvider = demoBuilderProvider;
		this.demoProvider = demoProvider;
	}

	public DemoBuilder get(ActionUIDef actionUIDef) {
		DemoBuilder demoBuilder = demoBuilderProvider.get();
		final Demo demo = demoProvider.get().withActionUIDef(actionUIDef);
		demoList.add(demo);
		demoBuilder.setDemo(demo);
		return demoBuilder;
	}

	public IActionHandler getStopDemoHandler() {
		IActionHandler actionHandler = new IActionHandler() {
			@Override
			public void performAction(String actionId) {
				for (Demo demo : demoList) {
					demo.stop();
				}
			}
		};
		return actionHandler;
	}
}
