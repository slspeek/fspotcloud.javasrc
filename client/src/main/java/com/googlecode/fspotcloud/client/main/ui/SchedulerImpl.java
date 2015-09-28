package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.core.client.Scheduler;
import com.googlecode.fspotcloud.client.main.view.api.IScheduler;

public class SchedulerImpl implements IScheduler {
	@Override
	public void schedule(final Runnable command) {
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			@Override
			public void execute() {
				command.run();
			}
		});
	}
}
