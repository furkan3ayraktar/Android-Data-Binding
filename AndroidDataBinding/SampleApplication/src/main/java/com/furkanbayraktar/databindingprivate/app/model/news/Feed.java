package com.furkanbayraktar.databindingprivate.app.model.news;

import com.furkanbayraktar.databinding.model.BasePOJO;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/19/14.
 * Copyright Valensas A.S.
 */
public class Feed extends BasePOJO{

    private String feedUrl;
    private String title;
    private String link;
    private String author;
    private String description;
    private String type;
    private Entry[] entries;

    public Feed(String feedUrl, String title, String link, String author, String description, String type, Entry[] entries) {
        this.feedUrl = feedUrl;
        this.title = title;
        this.link = link;
        this.author = author;
        this.description = description;
        this.type = type;
        this.entries = entries;
    }

    public Feed() {
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Entry[] getEntries() {
        return entries;
    }
}
