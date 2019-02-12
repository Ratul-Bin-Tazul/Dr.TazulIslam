package com.ratulbintazul.www.drtazulislam.DataProvider;

/**
 * Created by SAMSUNG on 9/15/2017.
 */

public class PostsDataProvider {
    String title,desc,photoUrl;
    int love = 0;
    boolean loved = false;

    public PostsDataProvider() {

    }

    public PostsDataProvider(String desc, int love, boolean loved, String photoUrl, String title) {
        this.desc = desc;
        this.love = love;
        this.loved = loved;
        this.photoUrl = photoUrl;
        this.title = title;
    }

    public PostsDataProvider(String desc, String photoUrl, String title) {
        this.desc = desc;
        this.photoUrl = photoUrl;
        this.title = title;
    }

    public boolean isLoved() {
        return loved;
    }

    public void setLoved(boolean loved) {
        this.loved = loved;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }



    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
