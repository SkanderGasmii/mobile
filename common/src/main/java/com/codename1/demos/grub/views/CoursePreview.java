/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Codename One through http://www.codenameone.com/ if you
 * need additional information or have any questions.
 */

package com.codename1.demos.grub.views;

import com.codename1.components.ScaleImageButton;
import com.codename1.demos.grub.interfaces.Course;
import com.codename1.rad.models.Entity;
import com.codename1.rad.models.Property;
import com.codename1.rad.nodes.ActionNode;
import com.codename1.rad.nodes.Node;
import com.codename1.rad.ui.AbstractEntityView;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;

import static com.codename1.ui.CN.convertToPixels;
import static com.codename1.ui.util.Resources.getGlobalResources;

public class CoursePreview extends AbstractEntityView {

    Property nameProp, categoryProp, ratingProp, deliveryTimeProp, pictureProp, distanceProp;
    Node viewNode;

    private static EncodedImage placeHolder = EncodedImage.createFromImage(getGlobalResources().getImage("placeholder.png"), false);

    public CoursePreview(Entity entity, Node viewNode){
        super(entity);
        setLayout(new BorderLayout());
        setUIID("CoursePreview");
        this.viewNode = viewNode;

        nameProp = entity.findProperty(Course.name);
        categoryProp = entity.findProperty(Course.category);
        ratingProp = entity.findProperty(Course.rating);
        deliveryTimeProp = entity.findProperty(Course.estimatedDeliveryTime);
        distanceProp = entity.findProperty(Course.distance);

        pictureProp = entity.findProperty(Course.picture);

        Image courseImage = entity.createImageToStorage(pictureProp, placeHolder);

        ScaleImageButton courseImageButton = new ScaleImageButton(courseImage);
        courseImageButton.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);

        courseImageButton.addActionListener(evt -> {
            evt.consume();
            ActionNode action = viewNode.getInheritedAction(HomeView.ENTER_COURSE);
            if (action != null) {
                action.fireEvent(entity, CoursePreview.this);
            }
        });

        setLeadComponent(courseImageButton);

        Label courseNameLabel = new Label(entity.getText(nameProp), "RestPreviewNameLabel");
        Label courseCategoryLabel = new Label(entity.getText(categoryProp), "RestPreviewCategoryLabel");

        Container courseTopView = new Container(new LayeredLayout());
        courseTopView.add(courseImageButton);

        Container nameAndCategoryCnt = new Container(new BorderLayout());
        nameAndCategoryCnt.setUIID("RestaurantPreviewDetailsCnt");
        Container courseDetails = BoxLayout.encloseY(courseNameLabel, courseCategoryLabel);
        nameAndCategoryCnt.add(BorderLayout.SOUTH, courseDetails);
        nameAndCategoryCnt.setUIID("BottomShadowContainer");
        courseTopView.add(nameAndCategoryCnt);

        Label estimatedDeliveryTimeLabel = new Label(" " + entity.getInt(deliveryTimeProp) + " mins", "RestPreviewDeliveryTime");
        estimatedDeliveryTimeLabel.setIcon(getGlobalResources().getImage("delivery-time-icon.png").scaled(convertToPixels(4), convertToPixels(4)));
        Label ratingLabel = new Label(" " + entity.getDouble(ratingProp) + "/5", "RestPreviewRating");
        ratingLabel.setIcon(getGlobalResources().getImage("rating-icon.png").scaled(convertToPixels(4), convertToPixels(4)));
        Label distanceLabel = new Label(" " + entity.getDouble(distanceProp) + "km", "RestPreviewDistance");
        distanceLabel.setIcon(getGlobalResources().getImage("distance-icon.png").scaled(convertToPixels(4), convertToPixels(4)));

        add(BorderLayout.SOUTH, FlowLayout.encloseCenter(estimatedDeliveryTimeLabel, ratingLabel, distanceLabel));
        add(BorderLayout.CENTER, courseTopView);

    }

    @Override
    public void update() {

    }

    @Override
    public void commit() {

    }

    @Override
    public Node getViewNode() {
        return viewNode;
    }
}
