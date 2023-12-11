package com.android.calendarmanagerproject_java.data;

import android.graphics.drawable.Drawable;

public class DbExercise {
    String name;
    Drawable image;

    public DbExercise(String name, Drawable image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
