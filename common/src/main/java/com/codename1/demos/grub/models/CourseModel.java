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

package com.codename1.demos.grub.models;

import com.codename1.demos.grub.interfaces.FoodCategory;
import com.codename1.demos.grub.interfaces.Course;
import com.codename1.rad.models.*;

import java.util.List;
import java.util.Random;

public class CourseModel extends Entity{
    public static StringProperty name;
    public static StringProperty pictureUrl;
    public static StringProperty iconUrl;
    public static StringProperty category;
    public static IntProperty distance;
    public static DoubleProperty rating;
    public static DoubleProperty deliveryFee;
    public static IntProperty estimatedDeliveryTime;
    public static ListProperty menu;
    public static ListProperty order;

    public static class CourseMenu extends EntityList {}
    public static class CourseOrder extends EntityList {}

    private static final EntityType TYPE = new EntityType() {{
        name = string(tags(Course.name));
        pictureUrl = string(tags(Course.picture));
        iconUrl = string(tags(Course.icon));
        category = string(tags(Course.category));
        rating = Double(tags(Course.rating));
        estimatedDeliveryTime = Integer(tags(Course.estimatedDeliveryTime));
        deliveryFee = Double(tags(Course.deliveryFee));
        distance = Integer(tags(Course.distance));
        menu = list(CourseMenu.class, tags(Course.menu));
        order = list(CourseOrder.class, tags(Course.order));
    }};

    {
        setEntityType(TYPE);
    }

    public CourseModel(String name, String pictureUrl, String category, double rating, double deliveryFee, String icon, int estimatedDeliveryTime, List<Entity> menu) {
        set(this.name, name);
        set(this.pictureUrl, pictureUrl);
        set(this.category, category);
        set(this.rating, rating);
        set(this.order, new CourseOrder());
        set(this.estimatedDeliveryTime, estimatedDeliveryTime);
        set(this.deliveryFee, deliveryFee);
        set(this.iconUrl, icon);

        Random random = new Random();
        int randomDistance = random.nextInt();
        if (randomDistance < 0){
            randomDistance = randomDistance * (-1);
        }
        randomDistance = randomDistance % 20;
        set(this.distance, randomDistance);
        CourseMenu categories = new CourseMenu();
        for (Entity menuCategory : menu){
            categories.add(menuCategory);
        }
        set(this.menu, categories);
    }

    public Entity addToOrder(Entity dish){
        CourseOrder order = (CourseOrder) get(Course.order);
        order.add(dish);
        return this;
    }

    public CourseOrder getOrder(){
        return (CourseOrder) get(Course.order);
    }

    public double getTotalPrice(){
        return getTotalItemPrice() + getDouble(Course.deliveryFee);
    }

    public double getTotalItemPrice(){
        double totalPrice = 0;
        if (get(Course.order) instanceof EntityList) {
            EntityList<Entity> dishList = (EntityList)(get(Course.order));
            for (Entity dishEntity : dishList) {
                totalPrice += ((OrderDishModel)dishEntity).getTotalPrice();
            }
        }
        return totalPrice;
    }

    public int getDishesCount(){
        int totalDishes = 0;

        if (get(Course.menu) instanceof EntityList){
            EntityList<Entity> categories = (EntityList<Entity>) get(Course.menu);
            for(Entity category : categories){
                if(category.get(FoodCategory.dishes) instanceof EntityList){
                    EntityList<Entity> dishes = (EntityList<Entity>) category.get(FoodCategory.dishes);
                    totalDishes += dishes.size();
                }
            }
        }
        return totalDishes;
    }

    public void clearOrder(){
        ((CourseOrder)get(Course.order)).clear();
    }
}
