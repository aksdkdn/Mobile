package com.android.calendarmanagerproject_java.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ExerciseData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    String date;
    String  name;
    String weight;
    String set;

    public ExerciseData(String date, String name, String weight, String set) {
        this.date = date;
        this.name = name;
        this.weight = weight;
        this.set = set;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }


}
