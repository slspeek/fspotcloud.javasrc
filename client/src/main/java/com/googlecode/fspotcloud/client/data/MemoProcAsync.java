package com.googlecode.fspotcloud.client.data;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@GwtCompatible
public abstract class MemoProcAsync<T> {
    private boolean isCalled = false;
    private final List<AsyncCallback<T>> queue = newArrayList();

    private T data = null;

    public MemoProcAsync() {
    }

    public abstract void getAsyncImpl(AsyncCallback<T> callback);

    public void getAsync(AsyncCallback<T> callback) {
        if (data != null) {
            callback.onSuccess(data);
        } else {
            queue.add(callback);
            if (!isCalled) {
                isCalled = true;
                getAsyncImpl(new AsyncCallback<T>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        for (AsyncCallback<T> asyncCallback : queue) {
                            asyncCallback.onFailure(caught);
                        }
                        queue.clear();
                    }

                    @Override
                    public void onSuccess(T result) {
                        data = result;
                        for (AsyncCallback<T> asyncCallback : queue) {
                            asyncCallback.onSuccess(result);
                        }
                        queue.clear();
                    }
                });
            }
        }
    }

    public void reset() {
        data = null;
        isCalled = false;
    }
}
