package com.googlecode.fspotcloud.client.useraction;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.*;

public abstract class AbstractBinder {


    @Inject
    protected ConfigBuilder configBuilder;

    protected final ActionCategory category;

    public AbstractBinder(ActionCategory category
    ) {
        this.category = category;
    }

    public abstract void build();

    protected void bind(ActionDef
                                actionDef, IActionHandler handler, KeyboardBinding binding) {
        configBuilder.addBinding(category, actionDef, handler, binding);
    }
}
