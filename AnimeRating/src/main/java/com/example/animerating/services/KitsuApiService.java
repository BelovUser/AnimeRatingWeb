package com.example.animerating.services;

import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
@Service
public class KitsuApiService {

    private static final String BASE_URL = "https://kitsu.io/api/edge/";

    public static KitsuApiService createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(KitsuApiService.class);
    }
}
