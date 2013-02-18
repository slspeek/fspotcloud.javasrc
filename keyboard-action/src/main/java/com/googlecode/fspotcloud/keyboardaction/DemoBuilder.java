package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.inject.Inject;

@GwtCompatible
public class DemoBuilder {

    private Demo demo;
    private final ActionUIRegistry actionUIRegistry;
    private final EventBus eventBus;
    private final HelpContentGenerator helpContentGenerator;
    private final KeyboardPreferences keyboardPreferences;

    @Inject
    private DemoBuilder(ActionUIRegistry actionUIRegistry,
                        EventBus eventBus,
                        HelpContentGenerator helpContentGenerator,
                        KeyboardPreferences keyboardPreferences) {
        this.actionUIRegistry = actionUIRegistry;
        this.eventBus = eventBus;
        this.helpContentGenerator = helpContentGenerator;
        this.keyboardPreferences = keyboardPreferences;
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
        KeyStroke[] keyStrokes = keyboardPreferences.getDefaultKeysForAction(actionUIDef.getId());
        SafeHtml result = helpContentGenerator.getHelpText(actionUIDef, keyStrokes);
        return result;
    }
}
