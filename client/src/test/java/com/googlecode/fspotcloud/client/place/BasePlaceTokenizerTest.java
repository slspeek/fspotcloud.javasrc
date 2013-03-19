package com.googlecode.fspotcloud.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

public class BasePlaceTokenizerTest extends TokenizerTest<BasePlace> {
    public BasePlaceTokenizerTest() {
        super(new BasePlace.Tokenizer(), new BasePlace("1", "2", 1, 1, true), "1:2:1:1:true");
    }
}
