package com.orientek.app.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import com.orientek.app.ClientDetailedActivity;
import com.orientek.app.MainActivity;
import com.orientek.app.Models.Clients;
import com.orientek.app.Models.Directions;
import com.orientek.app.R;
import com.orientek.app.Services.ClientsService;
import com.orientek.app.Services.DirectionsService;
import com.orientek.app.Utils.DialogsUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DirectionsAdapter extends RecyclerView.Adapter<DirectionsAdapter.ViewHolder> {

    private List<Directions> ListDirections = new ArrayList<>();
    private Clients client;
    private Context context;
    private Resources resources;
    private DialogsUtils dialogUtils;
    private DirectionsService directionsService;

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView AddressField;
        private ImageButton EditButtonAddress, DeleteButtonAddress;

        public ViewHolder(View itemView){
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            AddressField = itemView.findViewById(R.id.client_address);
            EditButtonAddress = itemView.findViewById(R.id.buttonEditAddress);
            DeleteButtonAddress = itemView.findViewById(R.id.buttonDeleteAddress);

        }
    }



    public DirectionsAdapter(Context ctx, List<Directions> drcs, Clients cl){
        this.context = ctx;
        this.ListDirections = drcs;
        this.client = cl;
        this.resources=this.context.getResources();
        this.dialogUtils = new DialogsUtils(ctx);
        this.directionsService = new DirectionsService(ctx);
    }


    @NonNull
    @Override
    public DirectionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client_detailed,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DirectionsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.AddressField.setText(ListDirections.get(position).getAddress());

        holder.EditButtonAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogUtils.ShowInput("update", client, true, ListDirections.get(position), new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ClientDetailedActivity activity = (ClientDetailedActivity) context;
                            if (activity != null && activity.ListRecyclerDirections != null) {
                                activity.RefreshListByClient(true, client, context, directionsService.GetAllDirectionsByClient(client.getId()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        holder.DeleteButtonAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    boolean isDeleted = directionsService.DeleteDirections(ListDirections.get(position).getId());

                    if(isDeleted){

                        Toast.makeText(context,resources.getString(R.string.updated).replace("0","Direccion").replace("1","Eliminada"), Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(context,resources.getString(R.string.notUpdated).replace("0","Eliminar").replace("1","Direccion"), Toast.LENGTH_LONG).show();
                    }
                    ClientDetailedActivity activity = (ClientDetailedActivity) context;
                    if (activity != null && activity.ListRecyclerDirections != null) {
                        activity.RefreshListByClient(true, client, context, directionsService.GetAllDirectionsByClient(client.getId()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return ListDirections.size();
    }


}
