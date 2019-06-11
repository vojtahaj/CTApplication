package umte.fim.uhk.cz.ctapplication.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import umte.fim.uhk.cz.ctapplication.R;

/**
 *  cele pocasi je inspirovano z
 *  http://www.androidmads.info/2018/11/how-to-create-weather-app-using.html
 */

public class WeatherFragment extends Fragment {

    public static final String appId = "27129157dbb3087603ac11746ee9eedf";
    public static final String apiUrl = "http://api.openweathermap.org/";

    public String lat = "15";
    public String lon = "30";

    private Button btnUpdate;
    private TextView txtTempApi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        txtTempApi = view.findViewById(R.id.api_temp);

        btnUpdate = view.findViewById(R.id.btn_update_temperature);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromArduino();
                getCurrentData();
            }
        });

        return view;
    }

    private void getCurrentData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = weatherService.getCurrentData(lat, lon, appId);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    String s = tempToCelsius(weatherResponse.main.temp);

                    txtTempApi.setText(s);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                txtTempApi.setText("Err");
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String tempToCelsius(float temp) {
        float tempToC = temp - 273.15f;
        return tempToC + "°C";
    }

    private void getDataFromArduino() {

        //todo writer.send(T?)
    }
}
