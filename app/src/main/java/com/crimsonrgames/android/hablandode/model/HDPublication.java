package com.crimsonrgames.android.hablandode.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aldo Rangel on 25/04/2016.
 */
public class HDPublication {
    private String publicationUrl;
    private String thumbnailUrl;
    private String title;
    private String description;


    public HDPublication() {
    }

    public HDPublication(JSONObject json) throws JSONException {
        publicationUrl = json.getString("idpffUrl");
        thumbnailUrl = json.getString("thumbnailUrl");
        title = json.getString("nameHdpPublication");
        description = json.getString("description");

    }

    public HDPublication(String publicationUrl, String thumbnailUrl, String title, String description) {
        this.publicationUrl = publicationUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
        this.description = description;
    }

    public String getPublicationUrl() {
        return publicationUrl;
    }

    public void setPublicationUrl(String publicationUrl) {
        this.publicationUrl = publicationUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
