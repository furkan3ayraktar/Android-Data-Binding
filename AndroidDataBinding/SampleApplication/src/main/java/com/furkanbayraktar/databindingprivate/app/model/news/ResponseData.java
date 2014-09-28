package com.furkanbayraktar.databindingprivate.app.model.news;

import com.furkanbayraktar.databinding.model.BasePOJO;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/19/14.
 * Copyright Valensas A.S.
 */
public class ResponseData extends BasePOJO{

    private Feed feed;

    public ResponseData(Feed feed) {
        this.feed = feed;
    }

    public ResponseData() {
    }

    public Feed getFeed() {
        return feed;
    }
}
