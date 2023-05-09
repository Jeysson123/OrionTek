package com.orientek.app.Utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.orientek.app.Models.Clients;
import com.orientek.app.Models.Directions;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClientsDeserializer implements JsonDeserializer<Clients> {

    @Override
    public Clients deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        long id = jsonObject.get("id").getAsLong();
        String name = jsonObject.get("name").getAsString();
        String enterprise = jsonObject.get("enterprise").getAsString();

        Clients clients = new Clients(id, name, enterprise);

        if (jsonObject.has("listDirections")) {
            JsonArray directionsArray = jsonObject.getAsJsonArray("listDirections");
            List<Directions> directionsList = new ArrayList<>();

            for (JsonElement element : directionsArray) {
                JsonObject directionObject = element.getAsJsonObject();

                long directionId = directionObject.get("id").getAsLong();
                String address = directionObject.get("address").getAsString();

                Directions direction = new Directions(directionId, address, clients);

                directionsList.add(direction);
            }

            clients.setListDirections(directionsList);
        }

        return clients;
    }
}

