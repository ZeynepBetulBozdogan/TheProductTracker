package com.example.myapplication;
import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.myapplication.Client.RetrofitClient2;
import com.example.myapplication.model.request.ItemRequest;
import com.example.myapplication.model.response.ItemResponse;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity3 extends AppCompatActivity {
    Button scanBtn, previous3, getItemInfo;
    TextView SitemID1, SBarcode1, SItemName1, SPrice1, ItemID, Barcode, ItemName, Price, Result;
    EditText userBarcode2;
    Connection con;
    MediaPlayer ring;
    MediaPlayer ringc;
    String token;
    TableLayout tableLayout;
    long differenceInMilliSeconds, differenceInSeconds, differenceInMinutes,differenceInHours;
    String time2;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String timeNow = dtf.format(now);
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().setTitle("MINISO");
        getSupportActionBar().setTitle("MINISO");
        scanBtn = (Button) findViewById(R.id.scanbtn1);
        SitemID1 = (TextView) findViewById(R.id.SitemID1);
        SBarcode1 = (TextView) findViewById(R.id.SBarcode1);
        getItemInfo = (Button) findViewById(R.id.getItemInfo);
        userBarcode2 = (EditText) findViewById(R.id.userBarcode2);
        SItemName1 = (TextView) findViewById(R.id.SitemName1);
        SPrice1 = (TextView) findViewById(R.id.SPrice1);
        previous3 = (Button) findViewById(R.id.previous3);
        ItemID = findViewById(R.id.ItemID);
        Result = findViewById(R.id.Result);
        Barcode = findViewById(R.id.barcode4);
        ItemName = findViewById(R.id.ItemName);
        Price = findViewById(R.id.Price);
        tableLayout = findViewById(R.id.table);
        ring = MediaPlayer.create(MainActivity3.this, R.raw.errorsound);
        ringc = MediaPlayer.create(MainActivity3.this, R.raw.correctsound);
        Intent intent3 = getIntent();
        token = intent3.getStringExtra("token2");
        System.out.println(isOnline());
        setContentView(R.layout.activity_main3);
        tokenTime();
        previous3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        getItemInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (TextUtils.isEmpty(userBarcode2.getText())) {
                    Toast.makeText(MainActivity3.this, "Lütfen Barkod Giriniz.", Toast.LENGTH_LONG).show();
                }
                String itemcode = userBarcode2.getText().toString();
                System.out.println(token);
                ItemInfo(itemcode, token);
            }
        });
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity3.this);
                integrator.setCaptureActivity(Portrait.class);
                integrator.setOrientationLocked(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan Your Barcode");
                integrator.initiateScan();
            }
        });

    }
@RequiresApi(api = Build.VERSION_CODES.O)
public void tokenTime(){
            Intent time = getIntent();
            if(time.getExtras() != null){
                time2 = getIntent().getStringExtra("time");
            }
            SimpleDateFormat simpleDateFormat
                    = new SimpleDateFormat("HH:mm:ss");
            try {
                Date date1 = simpleDateFormat.parse(timeNow);
                Date date2 = simpleDateFormat.parse(time2);
                differenceInMilliSeconds
                        = Math.abs(date2.getTime() - date1.getTime());
                differenceInHours
                        = (differenceInMilliSeconds / (60 * 60 * 1000))
                        % 24;
                differenceInMinutes
                        = (differenceInMilliSeconds / (60 * 1000)) % 60;
                differenceInSeconds
                        = (differenceInMilliSeconds / 1000) % 60;
                System.out.println("Minutes"+differenceInMinutes);
                System.out.println("Hours"+differenceInHours);
                if(differenceInMinutes ==59){
                    Intent intent31 = new Intent(MainActivity3.this,MainActivity.class);
                    startActivity(intent31);
                    finish();
                }
                else{System.out.println("Minutes between Durations = ");}
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ItemInfo(String itemcode, String token) {
        userBarcode2.getText().clear();
        SitemID1.setText(" ");
        SBarcode1.setText(" ");
        SItemName1.setText(" ");
        SPrice1.setText(" ");
        Result.setText("Sonuç");
        Result.setBackgroundResource(R.drawable.textlines3);
        SitemID1.setBackgroundResource(R.drawable.textlines3);
        SBarcode1.setBackgroundResource(R.drawable.textlines3);
        SItemName1.setBackgroundResource(R.drawable.textlines3);
        SPrice1.setBackgroundResource(R.drawable.textlines3);
        ItemID.setBackgroundResource(R.drawable.textlines3);
        Barcode.setBackgroundResource(R.drawable.textlines3);
        ItemName.setBackgroundResource(R.drawable.textlines3);
        Price.setBackgroundResource(R.drawable.textlines3);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ItemRequest itemRequest = new ItemRequest(itemcode);
                    Call<ItemResponse> call = RetrofitClient2.getApiService(token, itemcode).item("bearer " + token, itemRequest);
                    call.enqueue(new Callback<ItemResponse>() {
                        @Override
                        public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                            System.out.println(call);
                            if (response.code() == 200) {
                                if (response.body().getMessage().equals("Başarılı")) {
                                    SitemID1.setText(response.body().getItemId());
                                    SBarcode1.setText(response.body().getItemBarCode());
                                    SItemName1.setText(response.body().getItemName());
                                    SPrice1.setText(response.body().getPrice());
                                    Result.setText(response.body().getMessage());
                                    Result.setTypeface(null, Typeface.BOLD);
                                    MediaPlayer ringc = MediaPlayer.create(MainActivity3.this, R.raw.correctsound);
                                    ringc.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                        public void onCompletion(MediaPlayer ringc) {
                                            ringc.release();
                                        }
                                    });
                                    ringc.start();
                                    Result.setBackgroundResource(R.drawable.textlines2);
                                    SitemID1.setBackgroundResource(R.drawable.textlines2);
                                    SBarcode1.setBackgroundResource(R.drawable.textlines2);
                                    SItemName1.setBackgroundResource(R.drawable.textlines2);
                                    SPrice1.setBackgroundResource(R.drawable.textlines2);
                                    ItemID.setBackgroundResource(R.drawable.textlines2);
                                    Barcode.setBackgroundResource(R.drawable.textlines2);
                                    ItemName.setBackgroundResource(R.drawable.textlines2);
                                    Price.setBackgroundResource(R.drawable.textlines2);

                                } else {
                                    if (response.body().getMessage().equals("Fiyat bilgisi bulunamadı!")) {
                                        Result.setText(response.body().getMessage());
                                        Result.setTypeface(null, Typeface.BOLD);
                                        MediaPlayer ring = MediaPlayer.create(MainActivity3.this, R.raw.errorsound);
                                        ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                            public void onCompletion(MediaPlayer ring) {
                                                ring.release();
                                            }
                                        });
                                        ring.start();
                                        Toast.makeText(MainActivity3.this, "Fiyat Bilgisi Bulunamadı.", Toast.LENGTH_LONG).show();
                                        Result.setBackgroundResource(R.drawable.textlines);
                                        SitemID1.setBackgroundResource(R.drawable.textlines);
                                        SBarcode1.setBackgroundResource(R.drawable.textlines);
                                        SItemName1.setBackgroundResource(R.drawable.textlines);
                                        SPrice1.setBackgroundResource(R.drawable.textlines);
                                        ItemID.setBackgroundResource(R.drawable.textlines);
                                        Barcode.setBackgroundResource(R.drawable.textlines);
                                        ItemName.setBackgroundResource(R.drawable.textlines);
                                        Price.setBackgroundResource(R.drawable.textlines);
                                    } else if (response.body().getMessage().equals("Ürün bulunamadı!") && response.body().getStatus().equals("false")) {
                                        Result.setText(response.body().getMessage());
                                        Result.setTypeface(null, Typeface.BOLD);
                                        MediaPlayer ring = MediaPlayer.create(MainActivity3.this, R.raw.errorsound);
                                        ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                            public void onCompletion(MediaPlayer ring) {
                                                ring.release();
                                            }
                                        });
                                        ring.start();
                                        Toast.makeText(MainActivity3.this, "Ürün Bulunamadı!", Toast.LENGTH_LONG).show();
                                        Result.setBackgroundResource(R.drawable.textlines4);
                                        SitemID1.setBackgroundResource(R.drawable.textlines4);
                                        SBarcode1.setBackgroundResource(R.drawable.textlines4);
                                        SItemName1.setBackgroundResource(R.drawable.textlines4);
                                        SPrice1.setBackgroundResource(R.drawable.textlines4);
                                        ItemID.setBackgroundResource(R.drawable.textlines4);
                                        Barcode.setBackgroundResource(R.drawable.textlines4);
                                        ItemName.setBackgroundResource(R.drawable.textlines4);
                                        Price.setBackgroundResource(R.drawable.textlines4);

                                    } else if (response.body().getMessage().equals("Kullanıcı ürün sorgulama yetkisine sahip değil!")) {
                                        Result.setText(response.body().getMessage());
                                        Result.setTypeface(null, Typeface.BOLD);
                                        MediaPlayer ring = MediaPlayer.create(MainActivity3.this, R.raw.errorsound);
                                        ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                            public void onCompletion(MediaPlayer ring) {
                                                ring.release();
                                            }
                                        });
                                        ring.start();
                                        Toast.makeText(MainActivity3.this, "Kullanıcı ürün sorgulama yetkisine sahip değil!", Toast.LENGTH_LONG).show();
                                        Result.setBackgroundResource(R.drawable.textlines5);
                                        SitemID1.setBackgroundResource(R.drawable.textlines5);
                                        SBarcode1.setBackgroundResource(R.drawable.textlines5);
                                        SItemName1.setBackgroundResource(R.drawable.textlines5);
                                        SPrice1.setBackgroundResource(R.drawable.textlines5);
                                        ItemID.setBackgroundResource(R.drawable.textlines5);
                                        Barcode.setBackgroundResource(R.drawable.textlines5);
                                        ItemName.setBackgroundResource(R.drawable.textlines5);
                                        Price.setBackgroundResource(R.drawable.textlines5);
                                    }
                                }
                            } else if (response.code() == 401) {
                                new AlertDialog.Builder(MainActivity3.this)
                                        .setTitle("Bildirim")
                                        .setMessage("Oturumunuzun süresi dolmuştur.Lütfen tekrar giriş yapınız.")
                                        .setCancelable(false)
                                        .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }).show();
                                Intent goBack = new Intent(MainActivity3.this, MainActivity.class);
                                startActivity(goBack);
                            }
                        }

                        @Override
                        public void onFailure(Call<ItemResponse> call, Throwable t) {
                            Toast.makeText(MainActivity3.this, "Servise Bağlanılamıyor.", Toast.LENGTH_LONG).show();

                        Log.e("Error", t.getMessage());
                        }
                    });
                }

            });
        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "İptal edildi.", Toast.LENGTH_SHORT).show();
            } else {
                ItemInfo(intentResult.getContents(),token);
            }
        }
    }


}