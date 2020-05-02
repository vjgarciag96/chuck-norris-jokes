package com.vjgarcia.chucknorrisjokes.data.remote.service;

import retrofit2.Call;
import retrofit2.http.GET;
import com.vjgarcia.chucknorrisjokes.data.model.APIResponse;


/**
 * Created by victor on 31/03/18.
 */

public interface RandomJokeService {
    @GET("jokes/random")
    Call<APIResponse> getRandomJoke();
}
