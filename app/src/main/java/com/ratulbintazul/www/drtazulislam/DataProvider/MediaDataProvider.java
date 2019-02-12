package com.ratulbintazul.www.drtazulislam.DataProvider;

/**
 * Created by SAMSUNG on 9/24/2017.
 */

public class MediaDataProvider {
    String title,mediaUrl,mediaType;


    public MediaDataProvider(String mediaType, String mediaUrl, String title) {
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.title = title;
    }

    public MediaDataProvider() {
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
