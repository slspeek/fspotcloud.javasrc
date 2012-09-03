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

package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.logging.Logger;


public class TagCell extends AbstractCell<TagNode> {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(TagCell.class.getName());

    @Override
    public void render(com.google.gwt.cell.client.Cell.Context arg0,
                       TagNode value, SafeHtmlBuilder sb) {
        sb.appendEscaped(value.getTagName());
    }
}
