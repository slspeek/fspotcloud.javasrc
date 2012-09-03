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

package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;


public interface Resources extends ClientBundle {
    @Source("version.txt")
    TextResource getVersion();

    @Source("client.css")
    Style style();

    @Source("images/Play.png")
    ImageResource playIcon();

    @Source("images/Pause.png")
    ImageResource pauseIcon();

    @Source("images/Stop.png")
    ImageResource stopIcon();

    @Source("images/Dashboard.png")
    ImageResource dashboardIcon();

    @Source("images/Fullscreen.png")
    ImageResource fullscreenIcon();

    @Source("images/Slower.png")
    ImageResource slowerIcon();

    @Source("images/Faster.png")
    ImageResource fasterIcon();

    @Source("images/Demo.png")
    ImageResource demoIcon();

    @Source("images/Next.png")
    ImageResource nextIcon();

    @Source("images/Back.png")
    ImageResource backIcon();

    @Source("images/License.png")
    ImageResource licenceIcon();

    @Source("images/Author.png")
    ImageResource authorIcon();

    @Source("images/2x2.png")
    ImageResource icon2x2();

    @Source("images/3x3.png")
    ImageResource icon3x3();

    @Source("images/4x4.png")
    ImageResource icon4x4();

    @Source("images/5x5.png")
    ImageResource icon5x5();

    @Source("images/ToggleTabular.png")
    ImageResource tabularIcon();

    @Source("images/Help.png")
    ImageResource helpIcon();

    @Source("images/ProjectSite.png")
    ImageResource projectSiteIcon();

    @Source("images/Maven.png")
    ImageResource mavenIcon();

    @Source("images/Proton.png")
    ImageResource protonIcon();

    @Source("images/Home.png")
    ImageResource homeIcon();

    @Source("images/End.png")
    ImageResource endIcon();

    @Source("images/AddColumn.png")
    ImageResource addColumnIcon();

    @Source("images/RemoveColumn.png")
    ImageResource removeColumnIcon();

    @Source("images/AddRow.png")
    ImageResource addRowIcon();

    @Source("images/RemoveRow.png")
    ImageResource removeRowIcon();

    @Source("images/Reset.png")
    ImageResource resetIcon();

    @Source("images/TreeFocus.png")
    ImageResource treeFocusIcon();

    @Source("images/ToggleButtons.png")
    ImageResource toggleButtonsIcon();

    @Source("images/About.png")
    ImageResource aboutIcon();

    @Source("images/ZoomOut.png")
    ImageResource zoomOutIcon();

    @Source("images/ZoomIn.png")
    ImageResource zoomInIcon();

    @Source("images/F-Spot.png")
    ImageResource fspotIcon();

    @Source("images/PageDown.png")
    ImageResource pageDownIcon();

    @Source("images/PageUp.png")
    ImageResource pageUpIcon();

    @Source("images/RowDown.png")
    ImageResource rowDownIcon();

    @Source("images/RowUp.png")
    ImageResource rowUpIcon();

    @Source("images/HideControls.png")
    ImageResource hideControlsIcon();

    @Source("images/Login.png")
    ImageResource loginIcon();

    @Source("images/Logout.png")
    ImageResource logoutIcon();

    @Source("images/Email.png")
    ImageResource emailIcon();

    public interface Style extends CssResource {
        String slideshow();

        String slideshowControls();

        String footerBlock();

        String image();

        String tagView();

        String button();

        String imagePanelBlock();

        String photoInfoLabel();

        String treeBlock();

        String userInfoLabel();

        String helpCategory();

        String helpDescription();

        String helpKey();

        String helpPopup();

        String helpSeparator();

        String helpTitle();

        String pagerLabel();

        String buttonPanelBlock();

        String selectedImage();

        String importedTag();
    }
}
