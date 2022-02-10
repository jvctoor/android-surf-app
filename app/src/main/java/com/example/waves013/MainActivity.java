package com.example.waves013;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static NestedScrollView nestedScrollView;
    TextView tv_height, tv_wind, tv_period, tv_temp, tv_height2, tv_height3, tv_height4, tv_height5,tv_pico, tv_hora
            ,tv_wind2, tv_wind3, tv_wind4, tv_wind5, tv_period2, tv_period3, tv_period4, tv_period5, tv_temp2,
            tv_temp3, tv_temp4, tv_temp5, tv_label_fav, date1, date2, date3, date4;
    LinearLayout linear_wind;
    FloatingActionButton btn;
    ConstraintLayout constraintHeader;
    ArrayList<Pico> picosList, picosListFav;
    MaterialDivider divider;
    ArrayList<String> dataArray;
    SharedPreferences sharedPreferences;
    String api_key = BuildConfig.API_KEY;
    DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        RequestQueue queue = Volley.newRequestQueue(this);

        tv_height = findViewById(R.id.tv_height);
        tv_height2 = findViewById(R.id.tv_height2);
        tv_height3 = findViewById(R.id.tv_height3);
        tv_height4 = findViewById(R.id.tv_height4);
        tv_height5 = findViewById(R.id.tv_height5);
        tv_period = findViewById(R.id.tv_period);
        tv_period2 = findViewById(R.id.tv_period2);
        tv_period3 = findViewById(R.id.tv_period3);
        tv_period4 = findViewById(R.id.tv_period4);
        tv_period5 = findViewById(R.id.tv_period5);
        tv_temp = findViewById(R.id.tv_temp);
        divider = findViewById(R.id.divider);
        tv_label_fav = findViewById(R.id.tv_label_fav);
        tv_temp2 = findViewById(R.id.tv_temp2);
        tv_temp3 = findViewById(R.id.tv_temp3);
        tv_temp4 = findViewById(R.id.tv_temp4);
        tv_temp5 = findViewById(R.id.tv_temp5);
        tv_wind = findViewById(R.id.tv_wind);
        tv_wind2 = findViewById(R.id.tv_wind2);
        tv_wind3 = findViewById(R.id.tv_wind3);
        tv_wind4 = findViewById(R.id.tv_wind4);
        tv_wind5 = findViewById(R.id.tv_wind5);
        tv_pico = findViewById(R.id.tv_pico);
        tv_hora = findViewById(R.id.tv_hora);
        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        date3 = findViewById(R.id.date3);
        date4 = findViewById(R.id.date4);
        linear_wind = findViewById(R.id.linear_wind);
        btn = findViewById(R.id.btn);
        nestedScrollView = findViewById(R.id.scrooll);
        constraintHeader = findViewById(R.id.constrainteee);
        recyclerView = findViewById(R.id.rv_lista_fav);

        databaseHelper = new DatabaseHelper(this);
        picosList = databaseHelper.getData();

        sharedPreferences = getSharedPreferences("databasePicos", Context.MODE_PRIVATE);
        boolean validarFirst = sharedPreferences.getBoolean("first", true);
        if (validarFirst) {
            popularPicos();
        }

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if(id != -1) {
            atualizarDados(picosList.get(id));
        } else {
            atualizarDados(picosList.get(0));
        }

       ArrayList<String> dayList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM");
        for (int i = 0; i < 6; i++) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String day = sdf.format(calendar.getTime());
            dayList.add(day);
        }

        tv_hora.setText("Agora: " + dayList.get(0));
        date1.setText(dayList.get(1));
        date2.setText(dayList.get(2));
        date3.setText(dayList.get(3));
        date4.setText(dayList.get(4));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PraiasActivity.class));
                //nestedScrollView.scrollTo(0, 0);
            }
        });

    }

    public void consultarAPI(String nome, String lat, String lng) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.stormglass.io/v2/weather/point?lat="+ lat +"&lng="+ lng + "&params=waveHeight,swellPeriod,windSpeed,waterTemperature";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        //tv.setText(response);
                        try {

                            JSONArray array = response.getJSONArray("hours");

                            int[] indexes = {9, 33, 57, 81, 105};
                            ArrayList<String> dataArray = new ArrayList<String>();

                            for(int cont = 0; cont < 5; cont++){
                                JSONObject objeto_tamanho = array.getJSONObject(indexes[cont]).getJSONObject("waveHeight");
                                JSONObject objeto_periodo = array.getJSONObject(indexes[cont]).getJSONObject("swellPeriod");
                                JSONObject objeto_vento = array.getJSONObject(indexes[cont]).getJSONObject("windSpeed");
                                JSONObject objeto_ar = array.getJSONObject(indexes[cont]).getJSONObject("waterTemperature");
                                String tamanho = objeto_tamanho.getString("icon");
                                String periodo = objeto_periodo.getString("icon");
                                String vento = objeto_vento.getString("icon");
                                String ar = objeto_ar.getString("noaa");
                                dataArray.add(tamanho);
                                dataArray.add(periodo);
                                dataArray.add(vento);
                                dataArray.add(ar);
                            }


                            tv_height.setText(dataArray.get(0)+"m");
                            tv_wind.setText(dataArray.get(2));
                            tv_period.setText(dataArray.get(1));
                            tv_temp.setText(dataArray.get(3)+"º");
                            tv_height2.setText(dataArray.get(4));
                            tv_period2.setText(dataArray.get(5));
                            tv_wind2.setText(dataArray.get(6));
                            tv_temp2.setText(dataArray.get(7)+"º");
                            tv_height3.setText(dataArray.get(8));
                            tv_period3.setText(dataArray.get(9));
                            tv_wind3.setText(dataArray.get(10));
                            tv_temp3.setText(dataArray.get(11)+"º");
                            tv_height4.setText(dataArray.get(12));
                            tv_period4.setText(dataArray.get(13));
                            tv_wind4.setText(dataArray.get(14));
                            tv_temp4.setText(dataArray.get(15)+"º");
                            tv_height5.setText(dataArray.get(16));
                            tv_period5.setText(dataArray.get(17));
                            tv_wind5.setText(dataArray.get(18));
                            tv_temp5.setText(dataArray.get(19)+"º");

                            Toast.makeText(MainActivity.this, "Dados atualizados: "+nome, Toast.LENGTH_SHORT).show();

                            //Toast.makeText(MainActivity.this, "Dados atualizados", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //tv_hora.setText(e.toString());
                            Toast.makeText(MainActivity.this, "Erro na requisição de "+nome, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR","error => "+error.toString());
                        //Toast.makeText(MainActivity.this, "Falha na requisição de "+nome, Toast.LENGTH_SHORT).show();
                        Random rand = new Random();
                        Toast.makeText(MainActivity.this, "Números gerados aleatoriamente para fins demonstrativos: API indisponível ", Toast.LENGTH_LONG).show();
                        int randup = 2;
                        int randup2 = 10;
                        tv_height.setText(rand.nextInt(randup)+"."+rand.nextInt(randup2)+"m");
                        tv_height2.setText("0.56");
                        tv_height3.setText("0.4");
                        tv_height4.setText("0.8");
                        tv_height5.setText("0.79");
                        tv_wind.setText("3.56");
                        tv_wind2.setText("4.8");
                        tv_wind3.setText("3.9");
                        tv_wind4.setText("5");
                        tv_wind5.setText("5.3");
                        tv_period.setText("7.2");
                        tv_period2.setText("9.9");
                        tv_period3.setText("11");
                        tv_period4.setText("8.7");
                        tv_period5.setText("9.8");
                        tv_temp.setText("27.3º");
                        tv_temp2.setText("32.3º");
                        tv_temp3.setText("31.9º");
                        tv_temp4.setText("30º");
                        tv_temp5.setText("31.5º");
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", api_key);
                return params;
            }
        };
        queue.add(getRequest);
    }

    public void atualizarDados(@NonNull Pico pico){
        String lat = pico.getLat();
        String lng = pico.getLng();
        tv_pico.setText(pico.getNome());
        switch(pico.getId()) {
            case 0:
                constraintHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.cacimbabg));
                break;
            case 1:
                constraintHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.macaronibg));
                break;
            case 2:
                constraintHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.maresiasbg));
                break;
            case 3:
                constraintHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.mavericksbg));
                break;
            case 4:
                constraintHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.banzaibg));
                break;
            case 5:
                constraintHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.uluwatubg));
                break;
            case 6:
                constraintHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.bundoranbg));
                break;
            case 7:
                constraintHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.watergatebg));
                break;
            case 8:
                constraintHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.jeffreybg));
                break;
            case 9:
                constraintHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.macaraipebg));
                break;
            case 10:
                constraintHeader.setBackground(ContextCompat.getDrawable(this, R.drawable.praiarosabg));
                break;
            default:
                constraintHeader.setBackgroundColor(0x18181F);
                break;

        }
        consultarAPI(pico.getNome(), lat, lng);
    }

    public void popularPicos() {
        Pico p1 = new Pico(0, "Cacimba do Padre", "-3.8494", "-32.4390", "Pernambuco", false);
        Pico p2 = new Pico(1, "Macaroni", "-2.7757", "99.9826", "Ilhas Mentaway, Indonésia", false);
        Pico p3 = new Pico(2, "Maresias", "-23.7923", "-45.5695", "São Sebastião", false);
        Pico p4 = new Pico(3, "Mavericks Beach", "37.4950", "-122.4965", "Half Moon Bay, EUA", false);
        Pico p5 = new Pico(4, "Banzai Pipeline", "21.6645", "-158.0523", "Hawaii, EUA", false);
        Pico p6 = new Pico(5, "Uluwatu Beach", "-8.8148", "115.0882", "Bali, Indonésia", false);
        Pico p7 = new Pico(6, "Bundoran Beach", "54.4834", "-8.2806", "Donegal, Irlanda", false);
        Pico p8 = new Pico(7, "Watergate Bay", "50.4450", "-5.0472", "Cornwall, Inglaterra", false);
        Pico p9 = new Pico(8, "Baía de Jeffrey", "-34.0587", "24.9266", "África do Sul", false);
        Pico p10 = new Pico(9, "Maracaípe", "-8.5254", "-35.0067", "Pernambuco", false);
        Pico p11 = new Pico(10, "Praia do Rosa", "-28.1228", "-48.6383", "Santa Catarina", false);
        picosList.add(p1); picosList.add(p2) ; picosList.add(p3);
        picosList.add(p4); picosList.add(p5) ; picosList.add(p6);
        picosList.add(p7); picosList.add(p8) ; picosList.add(p9);
        picosList.add(p10); picosList.add(p11) ;
        //Toast.makeText(MainActivity.this, "Populado", Toast.LENGTH_SHORT).show();
        databaseHelper.saveData(picosList);
        sharedPreferences = getSharedPreferences("databasePicos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("first", false);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        databaseHelper = new DatabaseHelper(this);
        picosList = databaseHelper.getData();
        picosListFav = new ArrayList<>();

        for (Pico p : picosList) {
            if(p.isFavorite()) {
                picosListFav.add(p);
            }
        }

        if (picosListFav.size() == 0) {
            divider.setVisibility(View.INVISIBLE);
            tv_label_fav.setVisibility(View.INVISIBLE);
        } else {
            divider.setVisibility(View.VISIBLE);
            tv_label_fav.setVisibility(View.VISIBLE);
        }

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new PicosAdapterFavs(picosListFav, MainActivity.this);
        recyclerView.setAdapter(recyclerAdapter);

    }

    public void rolarUp() {
        nestedScrollView.fullScroll(View.FOCUS_UP);

    }
}

