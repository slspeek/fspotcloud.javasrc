package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import java.util.List;

@GwtCompatible
public class DemoBuilder {

    private Demo demo;
    private final ActionUIRegistry actionUIRegistry;
    private final EventBus eventBus;
    private final KeyboardPreferences keyboardPreferences;
    private final IHelpContentGenerator helpContentGenerator;

    @Inject
    private DemoBuilder(ActionUIRegistry actionUIRegistry,
                        EventBus eventBus,
                        KeyboardPreferences keyboardPreferences,
                        HelpResources helpResources,
                        IHelpContentGenerator helpContentGenerator) {
        this.helpContentGenerator = helpContentGenerator;
        this.actionUIRegistry = actionUIRegistry;
        this.eventBus = eventBus;
        this.keyboardPreferences = keyboardPreferences;
        setHelpResources(helpResources);
    }

    public void setHelpResources(HelpResources helpResources) {
        helpContentGenerator.setStyle(helpResources.style());
    }

    public Demo getDemo() {
        return demo;
    }

    public void setDemo(Demo demo) {
        this.demo = demo;
    }

    public DemoBuilder addStep(String actionId, int pauseMilles) {
        ActionUIDef actionUIDef = actionUIRegistry.getAction(actionId);
        return addStep(actionUIDef, pauseMilles);
    }

    public DemoBuilder addStep(ActionUIDef actionUIDef, int pauseMilles) {
        ActionDemoStep step = new ActionDemoStep(actionUIDef, eventBus, pauseMilles, getContent(actionUIDef));
        demo.getStepList().add(step);
        return this;
    }

    public DemoBuilder addStep(String actionId, int pauseMilles, String overridingDescription) {
        ActionUIDef actionUIDef = actionUIRegistry.getAction(actionId);
        return addStep(actionUIDef, pauseMilles, overridingDescription);
    }

    public DemoBuilder addStep(ActionUIDef actionUIDef, int pauseMilles, String overridingDescription) {
        SafeHtml content = new SafeHtmlBuilder().appendEscaped(overridingDescription).toSafeHtml();
        ActionDemoStep step = new ActionDemoStep(actionUIDef, eventBus, pauseMilles, content);
        demo.getStepList().add(step);
        return this;
    }

    public DemoBuilder addStep(DemoStep step) {
        demo.getStepList().add(step);
        return this;
    }

    private SafeHtml getContent(ActionUIDef actionUIDef) {
        List<KeyStroke> keyStrokes = keyboardPreferences.getDefaultKeysForAction(actionUIDef.getId());
        SafeHtml result = helpContentGenerator.getHelpText(actionUIDef, keyStrokes);
        return result;
    }
}
