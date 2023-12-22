package com.example.animerating.kitsuapicalls;
import com.example.animerating.models.Anime;
import com.example.animerating.models.KitsuAnimeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface KitsuApiCalls {

    @GET("anime/{id}")
    Call<Anime> getAnimeById(@Path("id") Long id);
}
