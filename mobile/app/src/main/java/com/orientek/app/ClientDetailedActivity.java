package com.orientek.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.orientek.app.Adapters.ClientsAdapter;
import com.orientek.app.Adapters.DirectionsAdapter;
import com.orientek.app.Models.Clients;
import com.orientek.app.Models.Directions;
import com.orientek.app.Services.DirectionsService;
import com.orientek.app.Utils.DialogsUtils;

import org.w3c.dom.Text;

import java.util.List;

public class ClientDetailedActivity extends AppCompatActivity {

    private Clients client;
    private DirectionsService DirectionService;
    private TextView TextClient;
    public RecyclerView ListRecyclerDirections;
    private ImageButton ButtonAddDirections;
    private Resources resources;
    private DialogsUtils DialogUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detailed);

        client = (Clients) getIntent().getParcelableExtra("Client");

        DirectionService = new DirectionsService(this);

        DialogUtils = new DialogsUtils(this);

        TextClient = findViewById(R.id.text_client_detailed);

        ListRecyclerDirections = findViewById(R.id.recyclerViewDirections);

        ButtonAddDirections = findViewById(R.id.buttonDirections);

        ListRecyclerDirections.setLayoutManager(new LinearLayoutManager(this));

        resources = this.getResources();

        if(client != null){

            try {
                RefreshListByClient(false, client, getApplicationContext(), null);
                TextClient.setText(resources.getString(R.string.welcome).replace("client", client.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        ButtonAddDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogUtils.ShowInput("add", client, true, null, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            RefreshListByClient(false, client, getApplicationContext(), null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }

    public void RefreshListByClient(Boolean fromUpdated, Clients client, Context ctx, List<Directions> drcs) throws Exception{

        Context currentContext = null;
        List<Directions> ListDirections = null;

        if (fromUpdated) {
            currentContext = ctx;
            ListDirections=drcs;
            RecyclerView recyclerViewUpdated = new RecyclerView(currentContext);
            recyclerViewUpdated.setId(R.id.recyclerViewDirections); // set a unique ID
            recyclerViewUpdated.setLayoutManager(new LinearLayoutManager(this));
            DirectionsAdapter directionsAdapterUpdated = new DirectionsAdapter(currentContext, ListDirections, client);
            recyclerViewUpdated.setAdapter(directionsAdapterUpdated);

            // Replace old RecyclerView with the updated one
            ViewGroup parentView = (ViewGroup) ListRecyclerDirections.getParent();
            if (parentView != null) {
                int index = parentView.indexOfChild(ListRecyclerDirections);
                if (index >= 0) {
                    parentView.removeView(ListRecyclerDirections);
                    parentView.addView(recyclerViewUpdated, index);
                } else {
                    parentView.addView(recyclerViewUpdated);
                }
            }

            ListRecyclerDirections = recyclerViewUpdated; // update reference to the new RecyclerView

        } else {

            ListDirections = DirectionService.GetAllDirectionsByClient(client.getId());

            DirectionsAdapter directionsAdapter = new DirectionsAdapter(this, ListDirections, client);

            ListRecyclerDirections.setAdapter(directionsAdapter);

            directionsAdapter.notifyDataSetChanged();
        }


        if(ListDirections.size()==0) Toast.makeText(this, resources.getString(R.string.directionsNotFound), Toast.LENGTH_SHORT).show();

    }
}