package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.inject.Inject;

@GwtCompatible
public class DemoBuilder {

    private Demo demo;
    private final ButtonDefinitions buttonDefinitions;
    private final EventBus eventBus;
    private final HelpContentGenerator helpContentGenerator;
    private final KeyboardPreferences keyboardPreferences;

    @Inject
    private DemoBuilder(ButtonDefinitions buttonDefinitions,
                        EventBus eventBus,
                        HelpContentGenerator helpContentGenerator,
                        KeyboardPreferences keyboardPreferences) {
        this.buttonDefinitions = buttonDefinitions;
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
        ActionDef actionDef = buttonDefinitions.getAction(actionId);
        return addStep(actionDef, pauseMilles);
    }

    public DemoBuilder addStep(ActionDef actionDef, int pauseMilles) {
        ActionDemoStep step = new ActionDemoStep(actionDef, eventBus, pauseMilles, getContent(actionDef));
        demo.getStepList().add(step);
        return this;
    }

    public DemoBuilder addStep(String actionId, int pauseMilles, String overridingDescription) {
        ActionDef actionDef = buttonDefinitions.getAction(actionId);
        return addStep(actionDef, pauseMilles, overridingDescription);
    }

    public DemoBuilder addStep(ActionDef actionDef, int pauseMilles, String overridingDescription) {
        SafeHtml content = new SafeHtmlBuilder().appendEscaped(overridingDescription).toSafeHtml();
        ActionDemoStep step = new ActionDemoStep(actionDef, eventBus, pauseMilles, content);
        demo.getStepList().add(step);
        return this;
    }

    public DemoBuilder addStep(DemoStep step) {
        demo.getStepList().add(step);
        return this;
    }

    private SafeHtml getContent(ActionDef actionDef) {
        KeyStroke[] keyStrokes = keyboardPreferences.getDefaultKeysForAction(actionDef.getId());
        SafeHtml result = helpContentGenerator.getHelpText(actionDef, keyStrokes);
        return result;
    }
}
