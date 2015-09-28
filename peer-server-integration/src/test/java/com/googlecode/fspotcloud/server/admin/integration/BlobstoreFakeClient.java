package com.googlecode.fspotcloud.server.admin.integration;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.googlecode.simpleblobstore.BlobKey;
import com.googlecode.simpleblobstore.client.BlobstoreClient;
import com.googlecode.simpleblobstore.client.BlobstoreClientException;

public class BlobstoreFakeClient implements BlobstoreClient {

	private static int counter = 0;
	@Override
	public Map<String, List<BlobKey>> upload(Map<String, byte[]> arg0)
			throws BlobstoreClientException {
		Map<String, List<BlobKey>> result = Maps.newHashMap();
		for (Map.Entry<String, byte[]> entry : arg0.entrySet()) {
			List<BlobKey> keyList = Lists.newArrayList();
			keyList.add(new BlobKey(String.valueOf(counter++)));
			result.put(entry.getKey(), keyList);
		}
		return result;
	}
}
