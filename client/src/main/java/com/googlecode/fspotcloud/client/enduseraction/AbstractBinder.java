package com.googlecode.fspotcloud.client.enduseraction;

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

    protected void bind(ActionUIDef
                                actionUIDef, IActionHandler handler, KeyboardBinding binding) {
        configBuilder.addBinding(category, actionUIDef, handler, binding);
    }
}
