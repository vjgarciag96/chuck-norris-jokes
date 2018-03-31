package vjgarciag96.chucknorrisjokes.data.remote.service;

import retrofit2.Call;
import retrofit2.http.GET;
import vjgarciag96.chucknorrisjokes.data.model.APIResponse;


/**
 * Created by victor on 31/03/18.
 */

public interface RandomJokeService {
    @GET("jokes/random")
    Call<APIResponse> getRandomJoke();
}
