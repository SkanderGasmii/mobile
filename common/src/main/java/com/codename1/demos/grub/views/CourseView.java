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

import com.codename1.components.ScaleImageLabel;
import com.codename1.demos.grub.interfaces.Course;
import com.codename1.demos.grub.interfaces.FoodCategory;
import com.codename1.demos.grub.models.AccountModel;
import com.codename1.demos.grub.models.FoodCategoryModel;
import com.codename1.rad.controllers.ActionSupport;
import com.codename1.rad.controllers.FormController;
import com.codename1.rad.models.Entity;
import com.codename1.rad.models.EntityList;
import com.codename1.rad.models.Property;
import com.codename1.rad.nodes.ActionNode;
import com.codename1.rad.nodes.Node;
import com.codename1.rad.ui.AbstractEntityView;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;

import static com.codename1.ui.CN.*;
import static com.codename1.ui.util.Resources.getGlobalResources;

public class CourseView extends AbstractEntityView{

    private Node viewNode;
    private Property nameProp, pictureUrlProp, categoryProp, ratingProp, menuProp, orderProp, estimatedDeliveryTimeProp, deliveryFeeProp, distanceProp;

    private Image courseThumbnailImage;
    private Container courseInfo;
    private static EncodedImage placeHolder =  EncodedImage.createFromImage(getGlobalResources().getImage("placeholder.png").scaled(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayWidth() / 2), false);

    public static final ActionNode.Category SHOW_ORDER = new ActionNode.Category();
    public static final ActionNode.Category ADD_TO_FAVORITE = new ActionNode.Category();
    public static final ActionNode.Category REMOVE_FAVORITE = new ActionNode.Category();

    public CourseView(Entity course, Entity account, Node node, Node mainWindowNode) {
        super(course);
        viewNode = node;
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setScrollableY(true);
        setScrollVisible(false);

        nameProp = course.findProperty(Course.name);
        pictureUrlProp = course.findProperty(Course.picture);
        categoryProp = course.findProperty(Course.category);
        ratingProp = course.findProperty(Course.rating);
        menuProp = course.findProperty(Course.menu);
        distanceProp = course.findProperty(Course.distance);
        orderProp = course.findProperty(Course.order);
        deliveryFeeProp = course.findProperty(Course.deliveryFee);
        estimatedDeliveryTimeProp = course.findProperty(Course.estimatedDeliveryTime);

        courseThumbnailImage = course.createImageToStorage(pictureUrlProp, placeHolder);

        courseInfo = new Container(new LayeredLayout());

        Button backButton = new Button(FontImage.MATERIAL_KEYBOARD_ARROW_LEFT);
        backButton.setUIID("RestBackButton");
        backButton.addActionListener(evt -> {
            evt.consume();
            ActionSupport.dispatchEvent(new FormController.FormBackEvent(backButton));
        });

        Button cart = new Button(FontImage.MATERIAL_SHOPPING_CART, "RestaurantActionButton");
        cart.addActionListener(evt -> {
            evt.consume();
            ActionNode action = viewNode.getInheritedAction(SHOW_ORDER);
            if (action != null) {
                action.fireEvent(course, CourseView.this);
            }
        });

        Style likeStyle = UIManager.getInstance().getComponentStyle("RestaurantActionButton");
        CheckBox like = CheckBox.createToggle(FontImage.createMaterial(FontImage.MATERIAL_FAVORITE_BORDER, likeStyle));
        like.setSelected(((AccountModel)account).isFavorite(course));
        like.setUIID("RestaurantActionButton");
        like.setPressedIcon(FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, likeStyle));
        AccountModel accountModel = (AccountModel) account;
        accountModel.isFavorite(course);

        like.addActionListener(evt -> {
            evt.consume();
            if(like.isSelected()){
                ActionNode action = mainWindowNode.getInheritedAction(ADD_TO_FAVORITE);
                if (action != null) {
                    action.fireEvent(course, CourseView.this);
                }
            }else{
                ActionNode action = mainWindowNode.getInheritedAction(REMOVE_FAVORITE);
                if (action != null) {
                    action.fireEvent(course, CourseView.this);
                }
            }
        });

        ScaleImageLabel courseImageLabel = new ScaleImageLabel(courseThumbnailImage);
        courseImageLabel.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);

        courseImageLabel.setUIID("RestImage");
        Container emptyCnt = new Container(new BorderLayout()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(getDisplayWidth(), getDisplayHeight() / 10);
            }
        };

        Container shadowContainer = new Container();
        shadowContainer.setUIID("ShadowContainer");

        emptyCnt.setUIID("emptyRestaurantCnt");
        Container imageContainer = BorderLayout.center(LayeredLayout.encloseIn(courseImageLabel, shadowContainer));
        imageContainer.add(BorderLayout.SOUTH, emptyCnt);
        courseInfo.add(imageContainer);

        Label courseName = new Label(course.getText(nameProp));
        courseName.setUIID("RestaurantNameHeader");

        Label courseCategory = new Label(course.getText(categoryProp));
        courseCategory.setUIID("RestaurantCategory");

        Label estimatedDeliveryTimeLabel = new Label(" " + course.getInt(estimatedDeliveryTimeProp) + " mins", "RestPreviewDeliveryTime");
        estimatedDeliveryTimeLabel.setIcon(getGlobalResources().getImage("delivery-time-icon.png").scaled(convertToPixels(4), convertToPixels(4)));
        Label ratingLabel = new Label(" " + course.getDouble(ratingProp) + "/5", "RestPreviewRating");
        ratingLabel.setIcon(getGlobalResources().getImage("rating-icon.png").scaled(convertToPixels(4), convertToPixels(4)));
        Label distanceLabel = new Label(" " + course.getDouble(distanceProp) + "km", "RestPreviewDistance");
        distanceLabel.setIcon(getGlobalResources().getImage("distance-icon.png").scaled(convertToPixels(4), convertToPixels(4)));

        Container courseDetails = new Container(new BoxLayout(BoxLayout.Y_AXIS), "RestDetails");
        Container timeRating = new Container(new FlowLayout(Component.CENTER), "RestTimeRatingCnt");
        timeRating.addAll(ratingLabel, estimatedDeliveryTimeLabel, distanceLabel);
        courseDetails.addAll(courseName, courseCategory, timeRating);
        courseInfo.add(BorderLayout.south(courseDetails));
        courseInfo.add(BorderLayout.centerCenterEastWest(null, FlowLayout.encloseRight(like, cart), BorderLayout.north(backButton)));
        add(courseInfo);

        Tabs menuContainer = new Tabs(Component.TOP);
        menuContainer.getTabsContainer().setUIID("RestTabContainer");
        menuContainer.setTabUIID("RestTab");

        if (getEntity().get(menuProp) instanceof EntityList) {
            EntityList<Entity> categoryList = (EntityList) (getEntity().get(menuProp));
            for (Entity category : categoryList) {
                FoodCategoryModel fc = ((FoodCategoryModel) category);
                if (fc.get(FoodCategory.dishes) instanceof EntityList){
                    EntityList dishesList = (EntityList)fc.get(FoodCategory.dishes);
                    menuContainer.addTab(fc.getText(FoodCategory.name), createCategoryView(dishesList));
                }
            }
        }
        add(menuContainer);
    }

    @Override
    public void update() {

    }

    @Override
    public void commit() {

    }

    @Override
    public Node getViewNode() {
        return null;
    }

    private Component createCategoryView(EntityList<Entity> dishes) {
        Container dishesContainer = new Container();
        dishesContainer.setUIID("MenuContainer");

        int numOdDishes = dishes.size();
        int rows = (numOdDishes % 2 == 0) ? numOdDishes / 2 : numOdDishes / 2 + 1;
        int landscapeRows = (numOdDishes % 4 == 0) ? numOdDishes / 4 : numOdDishes / 4 + 1;
        if (CN.isTablet()){
            dishesContainer.setLayout(new GridLayout(landscapeRows, 4));
        }else{
            dishesContainer.setLayout(new GridLayout(rows, 2, landscapeRows, 4));
        }
        for (Entity dishEntity : dishes) {
            DishPreview dish = new DishPreview(dishEntity, viewNode);
            dishesContainer.add(dish);
        }
        return dishesContainer;
    }
}