package com.furkanbayraktar.databindingprivate.app.model.news;

import com.furkanbayraktar.databinding.model.BasePOJO;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/19/14.
 * Copyright Valensas A.S.
 */
public class Entry extends BasePOJO{

    private String title;
    private String link;
    private String author;
    private String publishedDate;
    private String contentSnippet;
    private String content;
    private MediaGroup[] mediaGroups;

    public Entry(String title, String link, String author, String publishedDate, String contentSnippet, String content, MediaGroup[] mediaGroups) {
        this.title = title;
        this.link = link;
        this.author = author;
        this.publishedDate = publishedDate;
        this.contentSnippet = contentSnippet;
        this.content = content;
        this.mediaGroups = mediaGroups;
    }

    public Entry() {
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

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getContentSnippet() {
        return contentSnippet;
    }

    public String getContent() {
        return content;
    }

    public MediaGroup[] getMediaGroups() {
        return mediaGroups;
    }
}
