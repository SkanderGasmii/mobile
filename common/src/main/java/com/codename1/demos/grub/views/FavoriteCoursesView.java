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

import com.codename1.demos.grub.interfaces.Account;
import com.codename1.demos.grub.models.CourseModel;
import com.codename1.rad.models.Entity;
import com.codename1.rad.models.EntityList;
import com.codename1.rad.nodes.Node;
import com.codename1.rad.ui.AbstractEntityView;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;

import java.util.List;

public class FavoriteCoursesView extends AbstractEntityView {

    Container coursesContainer;
    Node viewNode;

    public FavoriteCoursesView(Entity account, Node viewNode) {
        super(account);
        setUIID("FavoriteCoursesView");
        this.viewNode = viewNode;
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Label headerLabel = new Label("FAVORITES", "FavoriteHeaderLabel");
        Container headerCnt = BorderLayout.center(headerLabel);
        headerCnt.setUIID("FavoriteHeaderCnt");
        add(headerCnt);
        coursesContainer = new Container();
//        ((GridLayout)coursesContainer.getLayout()).
        coursesContainer.setScrollableY(true);
        coursesContainer.setScrollVisible(false);
        add(coursesContainer);

        update();
    }

    @Override
    public void update() {
        coursesContainer.removeAll();
        if (getEntity().get(Account.favoriteCourses) instanceof EntityList){
            EntityList<Entity> coursesList = (EntityList<Entity>)getEntity().get(Account.favoriteCourses);
            final int coursesListSize = coursesList.size();
            if (coursesListSize > 0){
                int landScapeRows = coursesListSize / 2;
                if (coursesListSize % 2 != 0){
                    landScapeRows++;
                }
                coursesContainer.setLayout(new GridLayout(coursesListSize, 1, landScapeRows, 2));
                for(Entity course : coursesList){
                    coursesContainer.add(new FavoriteCourseView((CourseModel) course, viewNode));
                }
            }
        }
    }

    @Override
    public void commit() {

    }

    @Override
    public Node getViewNode() {
        return null;
    }

    public void addFavorite(Entity course){
        coursesContainer.add(new FavoriteCourseView((CourseModel) course, viewNode));
        update();
    }

    public void removeFavorite(Entity course){
        List<Component> courses = coursesContainer.getChildrenAsList(true);
        for(Component currCourse: courses){
            if (currCourse instanceof FavoriteCourseView){
                if (((FavoriteCourseView) currCourse).getEntity() == course){
                    coursesContainer.removeComponent(currCourse);
                }
            }
        }
        update();
    }
}
