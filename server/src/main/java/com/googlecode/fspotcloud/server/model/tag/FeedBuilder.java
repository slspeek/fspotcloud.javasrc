package com.googlecode.fspotcloud.server.model.tag;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.main.PhotoInfoStore;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.user.inject.ServerAddress;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Text;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javax.inject.Provider;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeedBuilder {

    @javax.inject.Inject
    @ServerAddress
    Provider<String> serverAddressProvider;

    @Inject
    private Logger log;


    public String getFeed(TagNode tagNode) {
        String result = "";
        try {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            result = xmlOutputter.outputString(getFeedDocument(tagNode));
        }   catch (Exception e) {
            log.log(Level.FINE, "Failed outputting xml for feed for node " + tagNode, e);
        }
        return result;
    }

    public Document getFeedDocument(TagNode tagNode) {
        Element rss = new Element("rss");
        Document document = new Document(rss);
        Element channel = new Element("channel");
        rss.addContent(channel);
        Element title = new Element("title");
        title.setText("Feed for category: " + tagNode.getTagName());
        Element description = new Element("description");
        description.setText(tagNode.getDescription());
        channel.addContent(title);
        Element link = new Element("link");
        link.setText(serverAddressProvider.get());
        channel.addContent(link);
        channel.addContent(description);

        PhotoInfoStore store = tagNode.getCachedPhotoList();
        final String tagNodeId = tagNode.getId();
        for (int i = 0; i < store.size(); i++) {
            PhotoInfo photoInfo = store.get(i);
            channel.addContent(getItem(photoInfo, tagNodeId));
        }
        return document;
    }

    private Element getItem(PhotoInfo info, String tagId) {
        Element result = new Element("item");
        Element title = new Element("title");
        title.setContent(new Text(info.getDate().toString()));
        Element description = new Element("description");
        description.setText(info.getDescription());
        String linkText = serverAddressProvider.get() + "/#BasePlace:" + tagId +":"  + info.getId() + ":1:1";
        Element link  = new Element("link");
        link.setText(linkText);
        result.addContent(title);
        result.addContent(link);
        result.addContent(description);
        return result;
    }


}
