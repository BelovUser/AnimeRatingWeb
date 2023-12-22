package com.example.animerating.services;

import com.example.animerating.kitsuapicalls.KitsuApiCalls;
import com.example.animerating.models.Anime;
import com.example.animerating.models.KitsuAnimeResponse;
import com.example.animerating.repositories.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {

    private final AnimeRepository animeRepository;
    private KitsuApiCalls kitsuApiCalls;
    private static final String BASE_URL = "https://kitsu.io/api/edge/";


    @Autowired
    public AnimeService(AnimeRepository animeRepository) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        kitsuApiCalls = retrofit.create(KitsuApiCalls.class);

        this.animeRepository = animeRepository;
    }


    public KitsuAnimeResponse getAnimeById(String id) {
        Call<KitsuAnimeResponse> call = kitsuApiCalls.getAnimeById(id);
        try {
            retrofit2.Response<KitsuAnimeResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public Optional<Anime> getById(Long id) {
        return animeRepository.findById(id);
    }

    public List<Anime> getAll() {
        return (List<Anime>) animeRepository.findAll();
    }

    public void save(Anime anime) {
        animeRepository.save(anime);
    }
}
