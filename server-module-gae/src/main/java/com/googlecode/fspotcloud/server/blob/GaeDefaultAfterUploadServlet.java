package com.googlecode.fspotcloud.server.blob;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.SerializationUtils;

import com.google.inject.Guice;
import com.google.inject.Singleton;
import com.googlecode.simpleblobstore.BlobKey;
import com.googlecode.simpleblobstore.BlobService;
import com.googlecode.simpleblobstore.gae.GaeSimpleBlobstoreModule;

@SuppressWarnings("serial")
@Singleton
public class GaeDefaultAfterUploadServlet extends HttpServlet {

	
	BlobService blobService = Guice.createInjector(new GaeSimpleBlobstoreModule()).getInstance(BlobService.class);

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, List<BlobKey>> uploads = blobService.getUploads(request);
		OutputStream out = response.getOutputStream();
		SerializationUtils.serialize((Serializable) uploads, out);
		out.close();
	}
}