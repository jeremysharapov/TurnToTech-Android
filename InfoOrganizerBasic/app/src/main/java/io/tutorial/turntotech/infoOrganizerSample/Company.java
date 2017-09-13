package io.tutorial.turntotech.infoOrganizerSample;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

/**
 * Created by Jeremy on 7/27/2017.
 */

@DatabaseTable(tableName = "company")
public class Company {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String logoURL;

    @DatabaseField
    private String stock_ticker;

    @DatabaseField
    private String stock_price;

    @ForeignCollectionField(columnName = "products", eager = true)
    private ForeignCollection<Product> products;

    public Company() {

    }

    public Company(String name){
        this.name = name;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ForeignCollection<Product> getProducts(){
        return products;
    }

    public void setProducts(ForeignCollection<Product> products){
        this.products = products;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public String getStock_price() {
        return stock_price;
    }

    public String getStock_ticker() {
        return stock_ticker;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public void setStock_price(String stock_price) {
        this.stock_price = stock_price;
    }

    public void setStock_ticker(String stock_ticker) {
        this.stock_ticker = stock_ticker;
    }

    //    String company_name, logoURL, stock_ticker, stock_price;

//
    public Company(String s, String URL, String stock){
        name = s;
        logoURL = URL;
        stock_ticker = stock;
    }
}
