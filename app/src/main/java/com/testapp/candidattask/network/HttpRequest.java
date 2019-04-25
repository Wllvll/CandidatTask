package com.testapp.candidattask.network;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    //HttpRequest методы
    public static enum Method{
        POST,PUT,DELETE,GET;
    }
    private URL url;
    private HttpURLConnection con;
    private OutputStream os;

    public HttpRequest(URL url)throws IOException{
        this.url=url;
        con = (HttpURLConnection)this.url.openConnection();
    }

    public HttpRequest(String url)throws IOException{ this(new URL(url)); Log.d("response_url", url); }

    private void prepareAll(Method method)throws IOException{
        con.setDoInput(true);
        con.setRequestMethod(method.name());
        if(method== Method.POST||method== Method.PUT){
            con.setDoOutput(true);
            os = con.getOutputStream();
        }
    }

    //prepare request in GET
    public HttpRequest prepare() throws IOException{
        prepareAll(Method.GET);
        return this;
    }

    public static String data(HashMap<String, String> params){
        StringBuilder result=new StringBuilder();
        for(Map.Entry<String,String>entry : params.entrySet()){
            result.append((result.length()>0 ? "&":"?")+entry.getKey()+"="+entry.getValue());//appends: key=value (for first param) OR &key=value(second and more)
        }
        return String.valueOf(result);
    }

    //Преобразуем массив в GET запрос
    public static String dataconvert(String[] arrays){
        StringBuilder result=new StringBuilder();
        String key = "albumId=";
        if(arrays.length != 0){
            result.append("?"+ key + arrays[0]);
            int size = arrays.length;
            for(int i = 1; i<size; i++) {
                result.append("&"+ key + arrays[i]);
            }
        }
        return String.valueOf(result);
    }

    //Разбираем ответ
    public String sendAndReadString() throws IOException{
        BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response=new StringBuilder();
        for(String line;(line=br.readLine())!=null;)response.append(line+"\n");
        return response.toString();
     }
}
