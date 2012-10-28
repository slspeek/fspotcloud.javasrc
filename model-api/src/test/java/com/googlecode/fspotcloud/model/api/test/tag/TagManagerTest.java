/*
 * Copyright 2010-2012 Steven L. Speek.
 * This program is free software; you can redistribute it
                and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free
                Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is
                distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
                warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public
                License for more details.
 * You should have received a copy of the GNU General Public License
 * along
                with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330,
                Boston, MA 02111-1307, USA.
 *
 */

package com.googlecode.fspotcloud.model.api.test.tag;

import com.google.guiceberry.junit4.GuiceBerryRule;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.model.api.test.EmptyGuiceBerryEnv;
import com.googlecode.fspotcloud.server.model.api.Tag;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.main.TagNode;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class TagManagerTest {
    private final Logger log = Logger.getLogger(TagManagerTest.class.getName());
    @Rule
    public GuiceBerryRule guiceBerry = new GuiceBerryRule(EmptyGuiceBerryEnv.class);
    @Inject
    private TagDao tagManager;

    @After
    public void setUp() throws Exception {
        tagManager.deleteBulk(100);
    }

    @Test
    public void testGetTags() {
        createSaveTag("21");

        List<TagNode> tags = tagManager.getTags();
        TagNode node = tags.get(0);
        assertEquals("21", node.getId());
    }

    private Tag createSaveTag(String id) {
        Tag tag = tagManager.findOrNew(id);
        tag.setId(id);
        tagManager.save(tag);

        return tag;
    }

    @Test
    public void modifyPhotoList() {
        Tag subject = createSaveTag("9");
        TreeSet<PhotoInfo> list = subject.getCachedPhotoList();
        list.add(new PhotoInfo("1", "desc", new Date(10000)));
        subject.setCachedPhotoList(list);
        tagManager.save(subject);
        assertEquals("desc",
                tagManager.find("9").getCachedPhotoList().first().getDescription());
    }

    @Test
    public void getImportedTags() {
        Tag tag = createSaveTag("21");
        tag.setImportIssued(true);
        tagManager.save(tag);
        createSaveTag("22");

        List<Tag> importedTags = tagManager.getImportedTags();
        assertEquals(1, importedTags.size());
        assertEquals("21", importedTags.get(0).getId());
    }
}
