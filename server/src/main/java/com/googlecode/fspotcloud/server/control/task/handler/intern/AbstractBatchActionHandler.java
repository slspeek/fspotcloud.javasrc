/*
 * Copyright 2010-2012 Steven L. Speek.
 * This program is free software; you can redistribute it
                and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free
                Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is
                distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
                warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public
                License for more details.
 * You should have received a copy of the GNU General Public License
 * along
                with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330,
                Boston, MA 02111-1307, USA.
 *
 */

package com.googlecode.fspotcloud.server.control.task.handler.intern;

import com.googlecode.fspotcloud.server.control.task.actions.intern.AbstractBatchAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.standard.GenericUtils;
import net.customware.gwt.dispatch.shared.DispatchException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Iterator;


public abstract class AbstractBatchActionHandler<V extends AbstractBatchAction<T>, T>
        implements ActionHandler<V, VoidResult> {
    private final TaskQueueDispatch dispatchAsync;
    protected final int MAX_DATA_TICKS;

    @Inject
    public AbstractBatchActionHandler(TaskQueueDispatch dispatchAsync,
                                      @Named("maxTicks")
                                      int MAX_DATA_TICKS) {
        super();
        this.dispatchAsync = dispatchAsync;
        this.MAX_DATA_TICKS = MAX_DATA_TICKS;
    }

    public abstract void doWork(AbstractBatchAction<T> action,
                                Iterator<T> workLoad);

    @Override
    public VoidResult execute(V action, ExecutionContext ec)
            throws DispatchException {
        Iterator<T> it = action.iterator();

        for (int i = 0; (i < MAX_DATA_TICKS) && it.hasNext(); i++) {
            doWork(action, it);
        }

        if (it.hasNext()) {
            dispatchAsync.execute(action);
        }

        return new VoidResult();
    }

    @Override
    public Class<V> getActionType() {
        return (Class<V>) GenericUtils.getFirstTypeParameterDeclaredOnSuperclass(this.getClass());
    }

    @Override
    public void rollback(V a, VoidResult r, ExecutionContext ec)
            throws DispatchException {
    }
}
