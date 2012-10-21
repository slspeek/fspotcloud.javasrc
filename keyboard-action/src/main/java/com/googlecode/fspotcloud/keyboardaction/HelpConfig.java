package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.safehtml.shared.SafeHtml;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class HelpConfig {

    private final String title;
    private final List<ActionCategory> firstColumn = newArrayList();
    private final List<ActionCategory> secondColumn = newArrayList();
    private final SafeHtml optionalContent;
    private final String width;
    private final String height;

    public HelpConfig(String width, String height,String title, SafeHtml optionalContent) {
        this.title = title;
        this.optionalContent = optionalContent;
        this.width = width;
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public HelpConfig(String width, String height,String title) {
        this(width, height, title, null);

    }

    public void addToFirstColumn(ActionCategory... actionCategory
    ) {
        for (ActionCategory aCategory : actionCategory) {
            firstColumn.add(aCategory);
        }
    }

    public void addToSecondColumn(ActionCategory... actionCategory
    ) {
        for (ActionCategory aCategory : actionCategory) {
            secondColumn.add(aCategory);
        }
    }

    public String getTitle() {
        return title;
    }

    public List<ActionCategory> getFirstColumn() {
        return firstColumn;
    }

    public List<ActionCategory> getSecondColumn() {
        return secondColumn;
    }

    public SafeHtml getOptionalContent() {
        return optionalContent;
    }
}
