package vjgarciag96.chucknorrisjokes.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vjgarciag96.chucknorrisjokes.R;
import vjgarciag96.chucknorrisjokes.common.network.APIManager;
import vjgarciag96.chucknorrisjokes.data.model.APIResponse;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Home";

    private MediaPlayer mediaPlayer;

    private ProgressBar progressBar;
    private TextView jokeContentTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeMediaPlayer();
        progressBar = findViewById(R.id.progressBar);
        jokeContentTextView = findViewById(R.id.jokeContent);
        ImageView chuckFace = findViewById(R.id.chuck_face);
        chuckFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewJokeButtonClicked();
            }
        });
    }

    @Override
    protected void onDestroy() {
        stopMediaPlayer();
        super.onDestroy();
    }

    public void onNewJokeButtonClicked() {
        cleanJoke();
        showProgressBar();
        startChuckSound();
        APIManager.getRandomJokeService().getRandomJoke().enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                Log.d(TAG, "Response code = " + String.valueOf(response.code()));

                hideProgressBar();
                if (response != null && response.isSuccessful() &&
                        response.body() != null && response.body().getValue() != null
                        && !TextUtils.isEmpty(response.body().getValue()))
                    showJoke(response.body().getValue());
                else
                    showToast(getString(R.string.network_error));
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Log.d(TAG, "Error on API request = " + t.getMessage());
                hideProgressBar();
                showToast(getString(R.string.network_error));
            }
        });
    }

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showJoke(final String jokeContent){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                jokeContentTextView.setText(jokeContent);
            }
        });
    }

    private void cleanJoke(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                jokeContentTextView.setText("");
            }
        });
    }

    private void initializeMediaPlayer(){
        mediaPlayer = MediaPlayer.create(this, R.raw.chuck_norris_sound);
    }

    private void startChuckSound(){
        mediaPlayer.start();
    }

    private void stopMediaPlayer(){
        mediaPlayer.stop();
    }

}
