package com.orientek.app.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Parcelable;
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
import com.orientek.app.R;
import com.orientek.app.Services.ClientsService;
import com.orientek.app.Utils.DialogsUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ViewHolder> {

    private List<Clients> ListClients = new ArrayList<>();
    private Context context;
    private Resources resources;
    private DialogsUtils dialogUtils;
    private ClientsService clientsService;

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView NameField, EnterpriseField;
        private ImageButton EditButton, DeleteButton;

        public ViewHolder(View itemView){
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Clients cl = ListClients.get(getAdapterPosition());

                    Intent i = new Intent(v.getContext(), ClientDetailedActivity.class);

                    i.putExtra("Client", (Parcelable) cl);

                    v.getContext().startActivity(i);

                }
            });

            NameField = itemView.findViewById(R.id.client_name);
            EnterpriseField = itemView.findViewById(R.id.client_address);
            EditButton = itemView.findViewById(R.id.buttonEditAddress);
            DeleteButton = itemView.findViewById(R.id.buttonDeleteAddress);

        }
    }



    public ClientsAdapter(Context ctx, List<Clients> cls){
        this.context = ctx;
        this.ListClients = cls;
        this.resources=this.context.getResources();
        this.dialogUtils = new DialogsUtils(ctx);
        this.clientsService = new ClientsService(ctx);
    }


    @NonNull
    @Override
    public ClientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clients,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.NameField.setText(ListClients.get(position).getName());

        holder.EnterpriseField.setText(ListClients.get(position).getEnterprise());

        holder.EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                    dialogUtils.ShowInput("update", ListClients.get(position), false, null, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                MainActivity activity = (MainActivity) context;
                                if (activity != null && activity.ListRecyclerClients != null) {
                                    activity.RefreshList(true, clientsService.GetClients(), context);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

            }
        });


        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    boolean isDeleted = clientsService.DeleteClient(ListClients.get(position).getId());

                    if(isDeleted){

                        Toast.makeText(context,resources.getString(R.string.updated).replace("0","Cliente").replace("1","Eliminado"), Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(context,resources.getString(R.string.notUpdated).replace("0","Eliminar").replace("1","Cliente"), Toast.LENGTH_LONG).show();
                    }

                    MainActivity activity = (MainActivity) context;
                    if (activity != null && activity.ListRecyclerClients != null) {
                        activity.RefreshList(true, clientsService.GetClients(), context);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return ListClients.size();
    }


}
