package com.furkanbayraktar.databindingprivate.app.model.news;

import com.furkanbayraktar.databinding.model.BasePOJO;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/19/14.
 * Copyright Valensas A.S.
 */
public class MediaGroup extends BasePOJO {

    private Content[] contents;

    public MediaGroup(Content[] contents) {
        this.contents = contents;
    }

    public MediaGroup() {
    }

    public Content[] getContents() {
        return contents;
    }
}
