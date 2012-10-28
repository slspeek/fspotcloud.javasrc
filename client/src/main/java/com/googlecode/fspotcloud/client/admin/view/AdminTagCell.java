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

package com.googlecode.fspotcloud.client.admin.view;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.client.main.view.TagCell;
import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.logging.Logger;


public class AdminTagCell extends AbstractCell<TagNode> {
    private final Logger log = Logger.getLogger(TagCell.class.getName());
    private static final MyTemplates TEMPLATES = GWT.create(MyTemplates.class);
    private final Resources resources;

    public AdminTagCell(Resources resources) {
        this.resources = resources;
    }

    @Override
    public void render(com.google.gwt.cell.client.Cell.Context arg0,
                       TagNode value, SafeHtmlBuilder sb) {
        log.info("" + value.isImportIssued());

        if (value.isImportIssued()) {
            SafeHtml snippetHtml = TEMPLATES.message(value.getTagName(),
                    resources.style().importedTag());
            log.info(snippetHtml.asString());
            sb.append(snippetHtml);
        } else {
            sb.appendEscaped(value.getTagName());
        }
    }

    public interface MyTemplates extends SafeHtmlTemplates {
        @Template("<span style=\"color: green\" class=\"{1}\">{0}</span>")
        SafeHtml message(String message, String style);
    }
}
