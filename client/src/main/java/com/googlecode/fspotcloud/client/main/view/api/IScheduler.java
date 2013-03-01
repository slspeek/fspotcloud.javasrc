package com.googlecode.fspotcloud.client.main.view.api;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public interface IScheduler {
    void schedule(Runnable command);
}
