package com.appstone.retrofit;

import org.json.JSONObject;

public class DummyClass {

    public String id;


    public static DummyClass parseJSON(JSONObject jsonObject){
        DummyClass item = new DummyClass();
        item.id = jsonObject.optString("id");
        return item;
    }
}
