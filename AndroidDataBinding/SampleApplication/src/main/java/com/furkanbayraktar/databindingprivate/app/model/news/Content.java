package com.furkanbayraktar.databindingprivate.app.model.news;

import com.furkanbayraktar.databinding.model.BasePOJO;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/19/14.
 * Copyright Valensas A.S.
 */
public class Content extends BasePOJO {

    private String url;
    private String type;

    public Content(String url, String type) {
        this.url = url;
        this.type = type;
    }

    public Content() {
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }
}
