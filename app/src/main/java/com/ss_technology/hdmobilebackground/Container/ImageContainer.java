package com.ss_technology.hdmobilebackground.Container;

import java.io.Serializable;

public class ImageContainer implements Serializable {

    String id;
    String url;
    String largUrl;
    String download;
    String view;
    String like;
    String fav;

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLargUrl() {
        return largUrl;
    }

    public void setLargUrl(String largUrl) {
        this.largUrl = largUrl;
    }

}
