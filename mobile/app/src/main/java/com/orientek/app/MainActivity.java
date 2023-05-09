package com.orientek.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.orientek.app.Adapters.ClientsAdapter;
import com.orientek.app.Models.Clients;
import com.orientek.app.Services.ClientsService;
import com.orientek.app.Services.DirectionsService;
import com.orientek.app.Utils.DialogsUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ClientsService ClientsService;
    private DirectionsService DirectionsService;
    private ImageButton ButtonAddClient;
    private Resources generalResource;
    public RecyclerView ListRecyclerClients;
    private DialogsUtils DialogUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            ClientsService = new ClientsService(this);

            DirectionsService = new DirectionsService(this);

            DialogUtils = new DialogsUtils(this);

            generalResource = this.getResources();

            ButtonAddClient = (ImageButton) findViewById(R.id.insert_client);

            ListRecyclerClients = (RecyclerView) findViewById(R.id.recyclerViewClients);
            ListRecyclerClients.setLayoutManager(new LinearLayoutManager(this));

            RefreshList(false, null, this);

            ButtonAddClient.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    DialogUtils.ShowInput("add", null, false, null, new Runnable(){

                        @Override
                        public void run() {
                            try {
                                RefreshList(false, null, getApplicationContext());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void RefreshList(Boolean fromUpdated, List<Clients> cls, Context ctx) throws Exception {

        List<Clients> ListClients = null;
        Context currentContext = null;

        if (fromUpdated) {
            ListClients = cls;
            currentContext = ctx;
            RecyclerView recyclerViewUpdated = new RecyclerView(currentContext);
            recyclerViewUpdated.setId(R.id.recyclerViewClients); // set a unique ID
            recyclerViewUpdated.setLayoutManager(new LinearLayoutManager(this));
            ClientsAdapter clientsAdapterUpdated = new ClientsAdapter(currentContext, ListClients);
            recyclerViewUpdated.setAdapter(clientsAdapterUpdated);

            // Replace old RecyclerView with the updated one
            ViewGroup parentView = (ViewGroup) ListRecyclerClients.getParent();
            if (parentView != null) {
                int index = parentView.indexOfChild(ListRecyclerClients);
                if (index >= 0) {
                    parentView.removeView(ListRecyclerClients);
                    parentView.addView(recyclerViewUpdated, index);
                } else {
                    parentView.addView(recyclerViewUpdated);
                }
            }

            ListRecyclerClients = recyclerViewUpdated; // update reference to the new RecyclerView
        } else {
            ListClients = ClientsService.GetClients();
            currentContext = this;
            ClientsAdapter clientsAdapter = new ClientsAdapter(currentContext, ListClients);
            ListRecyclerClients.setAdapter(clientsAdapter);
            clientsAdapter.notifyDataSetChanged();
        }
    }
}
