package com.addonnet.entities;

import java.util.ArrayList;

/**
 * Created by pita on 9/25/2016.
 */
public class CategoryData
{
private ArrayList<Categories> Categories=new ArrayList<>();

    public ArrayList<com.addonnet.entities.Categories> getCategories() {
        return Categories;
    }

    public void setCategories(ArrayList<com.addonnet.entities.Categories> categories) {
        Categories = categories;
    }
}
