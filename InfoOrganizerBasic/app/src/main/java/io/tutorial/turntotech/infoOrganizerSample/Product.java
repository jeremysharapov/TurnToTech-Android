package io.tutorial.turntotech.infoOrganizerSample;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Jeremy on 7/27/2017.
 */

@DatabaseTable(tableName = "product")
public class Product {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String LogoURL;

    @DatabaseField
    private String ProductURL;

    @DatabaseField(columnName = "company", foreign = true, foreignAutoRefresh = true)
    private Company company;

    public Product() {
    }

//    public Product(String name, Company company){
//        this.name = name;
//        this.company = company;
//    }

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

    public Company getCompany(){
        return company;
    }

    public void setCompany(Company company){
        this.company = company;
    }

    public void setLogoURL(String logoURL) {
        LogoURL = logoURL;
    }

    public String getLogoURL() {
        return LogoURL;
    }

    public String getProductURL() {
        return ProductURL;
    }

    public void setProductURL(String productURL) {
        ProductURL = productURL;
    }

    //    int id, company_id;
//    String product_name, logoURL, productURL;
//
    public Product(String s, String URL, String web, Company comp){
        name = s;
        LogoURL = URL;
        ProductURL = web;
        company = comp;
    }
}
