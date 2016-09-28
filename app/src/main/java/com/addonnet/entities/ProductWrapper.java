package com.addonnet.entities;

import java.util.ArrayList;

/**
 * Created by PravinK on 28-09-2016.
 */
public class ProductWrapper
{
    private ArrayList<Products> Products=new ArrayList<>();

    public ArrayList<com.addonnet.entities.Products> getProducts() {
        return Products;
    }

    public void setProducts(ArrayList<com.addonnet.entities.Products> products) {
        Products = products;
    }
}
