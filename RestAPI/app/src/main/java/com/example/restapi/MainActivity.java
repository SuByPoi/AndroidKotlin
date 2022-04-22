package com.example.restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restapi.api.ApiService;
import com.example.restapi.databinding.ActivityMainBinding;
import com.example.restapi.model.Currency;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewroot = binding.getRoot();
        setContentView(viewroot);


        binding.btnCallapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   clickCallApi();
            }
        });
    }

    private void clickCallApi() {
        ApiService.apiService.convertUsdtoVnd("843d4d34ae72b3882e3db642c51e28e6",
                "VND",
                "USD",
                1).enqueue(new Callback<Currency>() {
            @Override
            public void onResponse(Call<Currency> call, Response<Currency> response) {
                Toast.makeText(MainActivity.this,"Call api Succes",Toast.LENGTH_SHORT).show();
                Currency currency = response.body();
                if (currency != null && currency.isSuccess()){
                    binding.tvTerm.setText(currency.getTerms());
                    binding.tvSource.setText((currency.getSource()));
                    binding.tvUsdvnd.setText(String.valueOf(currency.getQuotes().getUSDVND()));
                }
            }

            @Override
            public void onFailure(Call<Currency> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Call api Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}