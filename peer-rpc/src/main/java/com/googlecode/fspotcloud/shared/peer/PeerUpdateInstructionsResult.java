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

package com.googlecode.fspotcloud.shared.peer;

import com.google.common.base.Objects;
import com.openpojo.business.annotation.BusinessKey;
import net.customware.gwt.dispatch.shared.Result;

import java.io.Serializable;
import java.util.List;


public class PeerUpdateInstructionsResult extends BusinessBase implements Result,
        Serializable {
    private static final long serialVersionUID = -4987610701630937829L;
    @BusinessKey
    private final List<TagUpdate> toBoUpdated;
    @BusinessKey
    private final List<TagRemovedFromPeer> toBoRemovedFromPeer;

    public PeerUpdateInstructionsResult(List<TagUpdate> toBoUpdated,
                                        List<TagRemovedFromPeer> toBoRemovedFromPeer) {
        super();
        this.toBoUpdated = toBoUpdated;
        this.toBoRemovedFromPeer = toBoRemovedFromPeer;
    }

    public List<TagRemovedFromPeer> getToBoRemovedFromPeer() {
        return toBoRemovedFromPeer;
    }

    public List<TagUpdate> getToBoUpdated() {
        return toBoUpdated;
    }

    public String toString() {
        return Objects.toStringHelper(this)
                .add("removedFromPeer", toBoRemovedFromPeer)
                .add("updates", toBoUpdated).toString();
    }
}
