package com.example.finalproject_geodatapart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GeoMain extends AppCompatActivity {
    EditText lat,lon;
    String lat1,lon1;
    double getLat,getLon;
    Button goSearch;
    LocationManager locationManager;
    String locationProvider;
    String URLSEC="https://api.geodatasource.com/cities?key=QXLEJE4O05BPDXWJJWOVXRAJIUBEZHXG&";
    ListView myList;
    String ForResult;
    ArrayList<MyList> cityList=new ArrayList<>();
    ArrayList<MyList> resultList=new ArrayList<>();
    SQLiteDatabase db;
    MyAdapter adapter;
    //@SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_main);
        goSearch=(Button)findViewById(R.id.GoSearch);
        lat=(EditText)findViewById(R.id.lat);
        lon=(EditText)findViewById(R.id.lon);
        //myList=(ListView)findViewById(R.id.Results);
        Log.i("URLSEC",URLSEC);
        myList=(ListView)findViewById(R.id.Results);
        MyDBOpener dbOpener=new MyDBOpener(this);
        db=dbOpener.getWritableDatabase();
        String[] columns={MyDBOpener.COL_ID,MyDBOpener.COL_COUNTRY,MyDBOpener.COL_CITY,MyDBOpener.COL_REGION,MyDBOpener.COL_CURRENCY,MyDBOpener.COL_LAT,MyDBOpener.COL_LON};
        Cursor results=db.query(false,MyDBOpener.TABLE_NAME,columns,null,null,null,null,null,null,null);
        int CountryCID=results.getColumnIndex(MyDBOpener.COL_COUNTRY);
        int CityCID=results.getColumnIndex(MyDBOpener.COL_CITY);
        int IDX=results.getColumnIndex(MyDBOpener.COL_ID);
        int RegionCID=results.getColumnIndex(MyDBOpener.COL_REGION);
        int CurrencyCID=results.getColumnIndex(MyDBOpener.COL_CURRENCY);
        int latCID=results.getColumnIndex(MyDBOpener.COL_LAT);
        int lonCID=results.getColumnIndex(MyDBOpener.COL_LON);
        while(results.moveToNext()){
            String CountryDT=results.getString(CountryCID),CityDT=results.getString(CityCID),RegionDT=results.getString(RegionCID),CurrencyDT=results.getString(CurrencyCID);
            long idDT=results.getLong(IDX);
            double latDT=results.getDouble(latCID),lonDT=results.getDouble(lonCID);
            resultList.add(new MyList(CountryDT,RegionDT,CityDT,CurrencyDT,latDT,lonDT,idDT));
            adapter=new MyAdapter();
            myList.setSelection(adapter.getCount()-1);
            adapter.notifyDataSetChanged();
        }
        adapter=new MyAdapter();
        myList.setAdapter(adapter);
        myList.setSelection(adapter.getCount()-1);
        goSearch.setOnClickListener(click->{
            if(lat1==null||lon1==null){
                lat1=lat.getText().toString();
                lon1=lon.getText().toString();
            }
            URLSEC += "lng=" + lat1 + "&lat=" + lon1 + "&format=JSON";
            Log.i("URLSEC2",URLSEC);
            new Thread(){
                @Override
                public void run(){
                    ForResult=getHttpResult();
                }
            }.start();
            if(ForResult!=null){
                if(cityList.size()>=5) {
                    for (int i = 0; i <=5; i++) {
                        adapter = new MyAdapter();
                        myList.setAdapter(adapter);
                        //adapter.notifyDataSetChanged();
                        myList.setSelection(adapter.getCount() - 1);
                        ContentValues newRow = new ContentValues();
                        MyList temp = cityList.get(i);
                        Log.i("country is", temp.getCountry());
                        Log.i("city is", temp.getCity());
                        Log.i("region is", temp.getRegion());
                        newRow.put(MyDBOpener.COL_COUNTRY, temp.getCountry());
                        newRow.put(MyDBOpener.COL_CITY, temp.getCity());
                        newRow.put(MyDBOpener.COL_REGION, temp.getRegion());
                        newRow.put(MyDBOpener.COL_CURRENCY, temp.getCurrency());

                        newRow.put(MyDBOpener.COL_LAT, temp.getLat());
                        newRow.put(MyDBOpener.COL_LON, temp.getLon());
                        long newId = db.insert(MyDBOpener.TABLE_NAME, null, newRow);
                        if (newId != -1) {
                            MyList newCity = new MyList(temp.getCountry(), temp.getRegion(), temp.getCity(), temp.getCurrency(), temp.getLat(), temp.getLon(), newId);
                            resultList.add(newCity);
                            adapter = new MyAdapter();
                            adapter.notifyDataSetChanged();
                            myList.setSelection(adapter.getCount() - 1);
                        } else {
                            Toast.makeText(this, "db error", Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    for(int i=0;i<cityList.size();i++){
                        adapter = new MyAdapter();
                        myList.setAdapter(adapter);
                        //adapter.notifyDataSetChanged();
                        myList.setSelection(adapter.getCount() - 1);
                        ContentValues newRow = new ContentValues();
                        MyList temp = cityList.get(i);
                        Log.i("country is", temp.getCountry());
                        Log.i("city is", temp.getCity());
                        Log.i("region is", temp.getRegion());
                        newRow.put(MyDBOpener.COL_COUNTRY, temp.getCountry());
                        newRow.put(MyDBOpener.COL_CITY, temp.getCity());
                        newRow.put(MyDBOpener.COL_REGION, temp.getRegion());
                        newRow.put(MyDBOpener.COL_CURRENCY, temp.getCurrency());

                        newRow.put(MyDBOpener.COL_LAT, temp.getLat());
                        newRow.put(MyDBOpener.COL_LON, temp.getLon());
                        long newId = db.insert(MyDBOpener.TABLE_NAME, null, newRow);
                        if (newId != -1) {
                            MyList newCity = new MyList(temp.getCountry(), temp.getRegion(), temp.getCity(), temp.getCurrency(), temp.getLat(), temp.getLon(), newId);
                            resultList.add(newCity);
                            adapter = new MyAdapter();
                            adapter.notifyDataSetChanged();
                            myList.setSelection(adapter.getCount() - 1);
                        } else {
                            Toast.makeText(this, "db error", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }else{
                Toast.makeText(this,getResources().getString(R.string.error2),Toast.LENGTH_LONG).show();
            }
        });

        myList.setOnItemClickListener((p,b,pos,id)->{
            MyList og=resultList.get(pos);
            Intent intent=new Intent(GeoMain.this,City.class);
            double latT,lonT;
            latT=og.getLat();
            lonT=og.getLon();
            String info=og.getInfo();
            intent.putExtra("info",info);
            intent.putExtra("lat",latT);
            intent.putExtra("lon",lonT);
            startActivityForResult(intent,30);
        });


    }
    public String getHttpResult(){
        String data = "";
        String resu=null;
        MyList tempListF=new MyList();
        //String urlStr="https://api.lyrics.ovh/v1/"+a+"/"+b;
        try {
            URL url = new URL(URLSEC);
            Log.i("url in getHttp",URLSEC);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

            String temp = "";
            while (temp != null) {
                temp = bufferedReader.readLine();
                if(temp!=null) {
                    data = data + temp;
                }
            }
            //JSONObject jsonObject=new JSONObject(data);
            //resu=jsonObject.getString("lyrics");
            Log.w("data is",data);
            JSONArray jsonArray=new JSONArray(data);
            //JSONObject jsonObject=new JSONObject(data);
            //String aaa=jsonObject.toString();
            //Log.i("json is",aaa);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String tempCountry,tempRegion,tempCity,tempCurrency;
                double tempLat,tempLon;
                tempCountry=jsonObject.getString("country");
                //Log.i("country is",tempCountry);
                tempCity=jsonObject.getString("city");
                //Log.i("city is",tempCity);
                tempRegion=jsonObject.getString("region");
               // Log.i("region is",tempRegion);
                tempCurrency=jsonObject.getString("currency_name");
          //  Log.i("currency is",tempCurrency);
               tempLat=jsonObject.getDouble("latitude");
        //    Log.i("lat is", String.valueOf(tempLat));
            tempLon=jsonObject.getDouble("longitude");
        //    Log.i("lon is", String.valueOf(tempLon));
                MyList tempList=new MyList(tempCountry,tempCity,tempRegion,tempCurrency,tempLat,tempLon);
                cityList.add(tempList);
                //tempListF.add(tempList);
            }
            return "Done";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return e.toString();
        }
        //return null;
    }

    protected class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public MyList getItem(int i) {
            return resultList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            View rowView;
            TextView rowMessage;
            MyList thisRow = getItem(i);
            rowView = inflater.inflate(R.layout.show, viewGroup, false);
            rowMessage = rowView.findViewById(R.id.msg);
            ImageView opt = rowView.findViewById(R.id.img);
            opt.setImageResource(R.drawable.geo);
            //rowMessage.setText(thisRow.getLyric());
            rowMessage.setText((thisRow.getInfo()));
            return rowView;
            //return null;
        }
    }

}