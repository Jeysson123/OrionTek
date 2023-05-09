package com.orientek.app.Services;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.orientek.app.Models.Clients;
import com.orientek.app.Models.Directions;
import com.orientek.app.Uis.DirectionsServiceUI;
import com.orientek.app.Utils.ClientsDeserializer;
import com.orientek.app.Utils.DirectionsDeserializer;
import com.orientek.app.Utils.UrlUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DirectionsService implements DirectionsServiceUI {

    private String URL = "";

    private OkHttpClient HttpClient = new OkHttpClient();

    public DirectionsService(Context ctx){

        URL = UrlUtils.GetUrl(ctx, "directions");
    }

    @Override
    public Boolean SaveDirection(Directions direction) throws Exception {

        final boolean[] statusRegister = {false};
        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                GsonBuilder builder = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                builder.registerTypeAdapter(Clients.class, new ClientsDeserializer());
                builder.registerTypeAdapter(Directions.class, new DirectionsDeserializer());
                Gson gson = builder.create();
                String jsonString = gson.toJson(direction.getClient());
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);

                // Request object
                URL=URL+"?address="+direction.getAddress();
                Request request = new Request.Builder()
                        .url(URL)
                        .post(requestBody)
                        .build();

                try (Response response = HttpClient.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected response code " + response);
                    }
                    else{
                        statusRegister[0] = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return statusRegister[0];

    }

    @Override
    public List<Directions> GetAllDirectionsByClient(long id) throws Exception {

        final List<Directions>[] directionsList = new List[]{null};
        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url(URL+"/check?id="+id)
                        .build();

                try (Response response = HttpClient.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(Directions.class, new DirectionsDeserializer())
                                .create();
                        Type listType = new TypeToken<List<Directions>>(){}.getType();
                        directionsList[0] = gson.fromJson(response.body().string(), listType);
                    } else {
                        throw new IOException("Unexpected response code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return directionsList[0];
    }

    @Override
    public Boolean UpdateDirections(long id, Directions directions) throws Exception {

        final Boolean[] statusUpdate = {false};

        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {

                GsonBuilder builder = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                builder.registerTypeAdapter(Clients.class, new ClientsDeserializer());
                Gson gson = builder.create();
                String jsonString = gson.toJson(directions);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);

                Request request = new Request.Builder()
                        .url(URL+"/refresh/"+id)
                        .put(requestBody)
                        .build();

                try (Response response = HttpClient.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected response code " + response);
                    }
                    else{
                        statusUpdate[0] = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return statusUpdate[0];
    }

    @Override
    public Boolean DeleteDirections(long id) throws Exception {

        final Boolean[] statusDelete = {false};

        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url(URL+"/remove/"+id)
                        .delete()
                        .build();

                try (Response response = HttpClient.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected response code " + response);
                    }
                    else{
                        statusDelete[0] = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        return statusDelete[0];
    }

}
