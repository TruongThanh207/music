package com.example.music.Service;

public class APIservice {

    public static Dataservice getService()
    {
        String base_url = "https://corporal-cheek.000webhostapp.com/Server/";
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }
}
