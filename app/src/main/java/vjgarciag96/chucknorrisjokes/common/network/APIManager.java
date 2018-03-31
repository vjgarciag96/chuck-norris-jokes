package vjgarciag96.chucknorrisjokes.common.network;

import vjgarciag96.chucknorrisjokes.data.remote.service.RandomJokeService;

/**
 * Created by victor on 31/03/18.
 */

public class APIManager {

    public static final String BASE_URL = "https://api.chucknorris.io/";

    public static RandomJokeService getRandomJokeService(){
        return RetrofitClient.getClient(BASE_URL).create(RandomJokeService.class);
    }

}
