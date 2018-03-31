package vjgarciag96.chucknorrisjokes.ui;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vjgarciag96.chucknorrisjokes.R;
import vjgarciag96.chucknorrisjokes.common.network.APIManager;
import vjgarciag96.chucknorrisjokes.data.model.APIResponse;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Home";

    @BindView(R.id.joke_content) TextView jokeContentTextView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.chuck_face)
    public void onNewJokeButtonClicked() {
        cleanJoke();
        showProgressBar();
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
}
