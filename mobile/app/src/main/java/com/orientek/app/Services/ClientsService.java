package com.orientek.app.Services;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.orientek.app.Models.Clients;
import com.orientek.app.Uis.ClientsServiceUI;
import com.orientek.app.Utils.ClientsDeserializer;
import com.orientek.app.Utils.UrlUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ClientsService implements ClientsServiceUI {

    private String URL = "";

    private OkHttpClient HttpClient = new OkHttpClient();

    public ClientsService(Context ctx){

        URL = UrlUtils.GetUrl(ctx, "clients");
    }

    @Override
    public List<Clients> GetClients() throws Exception {

        final CountDownLatch latch = new CountDownLatch(1);
        final List<Clients>[] clientsList = new List[]{null};

        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(URL)
                        .build();

                try (Response response = HttpClient.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        GsonBuilder builder = new GsonBuilder();
                        builder.registerTypeAdapter(Clients.class, new ClientsDeserializer());
                        Gson gson = builder.create();
                        Type listType = new TypeToken<List<Clients>>(){}.getType();
                        String responseBody = response.body().string();
                        clientsList[0] = gson.fromJson(responseBody, listType);
                        Log.d("Response", responseBody);
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

        return clientsList[0];

    }

    @Override
    public Boolean SaveClient(Clients clients) throws Exception {

        final boolean[] statusRegister = {false};
        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {

                GsonBuilder builder = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                builder.registerTypeAdapter(Clients.class, new ClientsDeserializer());
                Gson gson = builder.create();
                String jsonString = gson.toJson(clients);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);

                // Request object
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
    public Clients GetClient(long id) throws Exception {

        final Clients[] client = {null};

        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url(URL+"/check/"+id)
                        .build();

                try (Response response = HttpClient.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        client[0] = gson.fromJson(response.body().string(), Clients.class);
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

       return client[0];
    }

    @Override
    public Boolean UpdateClient(long id, Clients clients) throws Exception {

        final Boolean[] statusUpdate = {false};

        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {

                GsonBuilder builder = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                builder.registerTypeAdapter(Clients.class, new ClientsDeserializer());
                Gson gson = builder.create();
                String jsonString = gson.toJson(clients);
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
    public Boolean DeleteClient(long id) throws Exception {

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
