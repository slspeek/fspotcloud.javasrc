package com.googlecode.fspotcloud.client.place;

public class TagPlaceTokenizerTest extends TokenizerTest<DashboardPlace> {
	public TagPlaceTokenizerTest() {
		super(new DashboardPlace.Tokenizer(), new DashboardPlace("1"), "1");
	}
}
