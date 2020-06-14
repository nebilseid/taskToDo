package com.example.todolist.model;

import io.realm.RealmObject;
import io.realm.annotations.Index;

public class DataToDo extends RealmObject {
    @Index
    private String title;
    private String description;
/*    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }*/

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
