package umte.fim.uhk.cz.ctapplication.weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import umte.fim.uhk.cz.ctapplication.CTActivity;
import umte.fim.uhk.cz.ctapplication.R;
import umte.fim.uhk.cz.ctapplication.utils.MyMonitorLog;
import umte.fim.uhk.cz.ctapplication.utils.SocketData;

/**
 * cele pocasi je inspirovano z
 * http://www.androidmads.info/2018/11/how-to-create-weather-app-using.html
 * <p>
 * prace s gps souradnicemi je ze cviceni nebo vlastni
 */

public class WeatherFragment extends Fragment implements OnSuccessListener<Location> {

    public static final String appId = "27129157dbb3087603ac11746ee9eedf";
    public static final String apiUrl = "http://api.openweathermap.org/";

    double latitude;
    double longitude;

    private Button btnUpdate;
    private TextView txtTempApi;
    private TextView txtLat;
    private TextView txtLon;
    private TextView txtArduinoTemp;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private SocketData socketData;
    private DataOutputStream outputStream;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        setLocation();

        txtTempApi = view.findViewById(R.id.api_temp);
        txtLat = view.findViewById(R.id.lat);
        txtLon = view.findViewById(R.id.lon);
        txtArduinoTemp = view.findViewById(R.id.arduino_temp);

        btnUpdate = view.findViewById(R.id.btn_update_temperature);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromArduino();
                getCurrentData();
            }
        });

        socketData = ((CTActivity) Objects.requireNonNull(getActivity())).getSocketData();
        outputStream = socketData.getOutputStream();

        return view;
    }


    private void setLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 10);
            Toast.makeText(getActivity(), "neni opravneni", Toast.LENGTH_LONG).show();

            return;
        }
        fusedLocationProviderClient
                .getLastLocation()
                .addOnSuccessListener(this);
    }

    private void setLabels() {
        txtLat.setText(String.valueOf(latitude));
        txtLon.setText(String.valueOf(longitude));
    }

    private void getCurrentData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = weatherService.getCurrentData(String.valueOf(latitude), String.valueOf(longitude), appId);
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
        new Thread() {
            @Override
            public void run() {
                try {
                    outputStream.writeBytes("T?\r\n");
                    CTActivity.monitorLogs.add(new MyMonitorLog("T?\r\n", false));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error ctfragmentClick IO");
                }
            }
        }.start();

        txtArduinoTemp.setText(String.format("%s°C", CTActivity.lightImpl.getCT().getTemperature()));
    }

    @Override
    public void onSuccess(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        Toast.makeText(getActivity(), "location changed", Toast.LENGTH_LONG).show();
        setLabels();
    }
}
