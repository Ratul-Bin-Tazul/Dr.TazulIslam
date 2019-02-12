package com.ratulbintazul.www.drtazulislam.DataProvider;

/**
 * Created by SAMSUNG on 9/29/2017.
 */

public class BookDataProvider {
    String name,pdf,thumbnail;

    public BookDataProvider(String name, String pdf, String thumbnail) {
        this.name = name;
        this.pdf = pdf;
        this.thumbnail = thumbnail;
    }

    public BookDataProvider() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
