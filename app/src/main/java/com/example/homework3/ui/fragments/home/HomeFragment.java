package com.example.homework3.ui.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homework3.data.models.WeatherModel;
import com.example.homework3.data.network.WeatherService;
import com.example.homework3.databinding.FragmentHomeBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private WeatherModel weatherModel;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setWeatherStats();
    }

    private void setWeatherStats() {
        WeatherService.getInstance().getCurrentWeather("Bishkek", "4bbf5a1ed98cd9f688ebb3651474cdd2").enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherModel weatherModel = response.body();
                    Log.e("TAG", "yaai");
                    binding.cityTv.setText("city: " + weatherModel.getName() + " " + weatherModel.getSys().getCountry());
                    binding.cloudlinessProcent.setText(weatherModel.getClouds().getAll() + "%");
                    binding.windNumber.setText(weatherModel.getWind().getDeg() + " " + weatherModel.getWind().getSpeed().toString() + "m/s");
                    binding.humidityPercent.setText(weatherModel.getMain().getHumidity() + "%");
                    binding.pressureNumber.setText(weatherModel.getMain().getPressure() + "mp");
                    Date dateRise = new Date(weatherModel.getSys().getSunrise());
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                    binding.sunriseNumber.setText(df.format(dateRise));
                    Date dateSet = new Date(weatherModel.getSys().getSunset());
                    binding.sunriseNumber.setText(df.format(dateSet));
                    binding.tempMinMaxValues.setText("min: " + weatherModel.getMain().getTempMin() + " " + "max: " + weatherModel.getMain().getTempMax());
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Log.e("tag", t.getLocalizedMessage());
            }
        });
        Date currentTime = new Date(System.currentTimeMillis());
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        binding.time.setText(dfTime.format(currentTime));
        Date currentDate = new Date(System.currentTimeMillis());
        DateFormat dfDate = new SimpleDateFormat("dd-MMM-yyyy");
        binding.date.setText(dfDate.format(currentDate));
    }
}