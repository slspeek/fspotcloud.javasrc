package com.googlecode.fspotcloud.client.useraction.about;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;

public class AboutBinder extends AbstractBinder {


    @Inject
    public AboutBinder(
            CategoryDef categoryDef) {
        super(categoryDef.NAVIGATION);

    }


    @Override
    public void build() {

    }

}
