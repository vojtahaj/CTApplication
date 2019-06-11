package umte.fim.uhk.cz.ctapplication.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/weather?")
    Call<WeatherResponse> getCurrentData(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String appId);

}
