package com.example.myapplication;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Client.RetrofitClient;
import com.example.myapplication.model.request.LoginRequest;
import com.example.myapplication.model.response.LoginResponse;

import java.time.LocalDateTime;
import java.time.LocalTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    EditText Username,Password;
    Button loginbtn;
    CheckBox showPass;
    String time = String.valueOf(LocalTime.now());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.login);
        System.out.println(isOnline());

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Username.getText()) ||TextUtils.isEmpty(Password.getText())) {
                    Toast.makeText(MainActivity.this, "Lütfen Kullanıcı adı ve parola giriniz.", Toast.LENGTH_LONG).show();
                }
                String username = Username.getText().toString();
                String password = Password.getText().toString();
                LoginRequest request = new LoginRequest(username,password);
                login(request);
            }
        });
    }


    private void login(LoginRequest request){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Call<LoginResponse> call = RetrofitClient.getApiService().login(request);
                call.enqueue(new Callback<LoginResponse>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.isSuccessful() && response.body().getStatus().equals("true")){
                            String token = response.body().getToken();
                            System.out.println(token);
                            String type  = response.body().getType();
                            System.out.println(type);
                            String message = response.body().getMessage();
                            System.out.println(message);
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            intent.putExtra("time",time);
                            intent.putExtra("token",token);
                            startActivity(intent);
                        }
                        else{
                            Username.getText().clear();
                            Password.getText().clear();
                            Username.setHint("Kullanıcı adı");
                            Password.setHint("Parola");
                            Toast.makeText(MainActivity.this,"Kullanıcı adı veya parola yanlış",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Servise Bağlanılamıyor.", Toast.LENGTH_LONG).show();
                        Log.e("Error", t.getMessage());
                    }
                });
            }
        });

    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}