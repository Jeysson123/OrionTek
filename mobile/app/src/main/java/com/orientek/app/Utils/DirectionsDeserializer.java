package com.orientek.app.Utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.orientek.app.Models.Clients;
import com.orientek.app.Models.Directions;

import java.lang.reflect.Type;

public class DirectionsDeserializer implements JsonDeserializer<Directions> {

        @Override
        public Directions deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            long id = 0;
            if (jsonObject.has("id") && !jsonObject.get("id").isJsonNull()) {
                id = jsonObject.get("id").getAsLong();
            }

            String address = null;
            if (jsonObject.has("address") && !jsonObject.get("address").isJsonNull()) {
                address = jsonObject.get("address").getAsString();
            }

            JsonObject clientObject = null;
            if (jsonObject.has("client") && !jsonObject.get("client").isJsonNull()) {
                clientObject = jsonObject.get("client").getAsJsonObject();
            }

            Clients client = null;

            if (clientObject != null) {
                GsonBuilder builder = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                builder.registerTypeAdapter(Clients.class, new ClientsDeserializer());
                Gson gson = builder.create();
                client = gson.fromJson(clientObject, Clients.class);
            }

            return new Directions(id, address, client);
        }
    }



