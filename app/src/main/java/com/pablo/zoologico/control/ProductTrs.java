package com.pablo.zoologico.control;

import com.pablo.zoologico.modelo.Product;

import java.util.ArrayList;

public class ProductTrs {
    public static ArrayList<Product>listProduct=new ArrayList<>();

    public void modify(Product oldP,Product newP){
        listProduct.set(listProduct.indexOf(oldP),newP);

    }
}
