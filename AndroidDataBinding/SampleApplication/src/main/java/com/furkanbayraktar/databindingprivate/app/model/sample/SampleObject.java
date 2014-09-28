package com.furkanbayraktar.databindingprivate.app.model.sample;

import com.furkanbayraktar.databinding.model.BasePOJO;

/**
 * Created by Furkan Bayraktar
 * Created on 9/28/14.
 */
public class SampleObject extends BasePOJO{

    private String title;
    private String description;
    private String method;
    private String alertTitle;
    private String alertDescription;
    private boolean loggingEnabled;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMethod() {
        return method;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public String getAlertDescription() {
        return alertDescription;
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }
}
