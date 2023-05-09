package com.orientek.app.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.orientek.app.Models.Clients;
import com.orientek.app.Models.Directions;
import com.orientek.app.R;
import com.orientek.app.Services.ClientsService;
import com.orientek.app.Services.DirectionsService;


public class DialogsUtils {

    private Resources generalResource;
    private ClientsService ClientsService;
    private DirectionsService DirectionService;
    private Context context;

    public DialogsUtils(Context ctx){
        this.context = ctx;
    }

    public void ShowInput(String action, Clients clients, boolean isDirections, Directions directions, Runnable callback){

        if (context != null) {

            // Create the AlertDialog here
            generalResource = context.getResources();
            ClientsService = new ClientsService(context);
            DirectionService = new DirectionsService(context);

            String title = null, postiveButtonMsg = null;

            switch (action){

                case "add":
                    title = generalResource.getString(R.string.form_client_title).replace("Title", "ADDICION");
                    postiveButtonMsg = generalResource.getString(R.string.form_client_button_positive).replace("Positive", "Agregar");
                    break;

                case "update":
                    title = generalResource.getString(R.string.form_client_title).replace("Title", "ACTUALIZACION");
                    postiveButtonMsg = generalResource.getString(R.string.form_client_button_positive).replace("Positive", "Actualizar");
                    break;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);

            // Set up the input
            // Create a LinearLayout container for the EditText views
            LinearLayout layout = new LinearLayout(context);

            layout.setOrientation(LinearLayout.VERTICAL);

            final EditText inputName = new EditText(context);

            final EditText inputEnterprise = new EditText(context);

            //Set placeholder values
            inputName.setHint("Nombre");
            inputEnterprise.setHint("Empresa");

            //Fill aumatic theses fields, because is for update
            if(action.equals("update")){
                inputName.setText(clients.getName());
                inputEnterprise.setText(clients.getEnterprise());
            }

            //Check if is Directions
            if(isDirections){

                inputName.setHint("Direccion");

                if(action.equals("update")){

                    inputName.setText(directions.getAddress());
                }
            }

            //Add views into layout
            layout.addView(inputName);
            if(!isDirections) layout.addView(inputEnterprise);

            //Edittext to container
            builder.setView(layout);

            // Set up the buttons
            builder.setPositiveButton(postiveButtonMsg, (dialog, which) -> {});
            builder.setNegativeButton(generalResource.getString(R.string.form_client_button_negative), (dialog, which) -> {});


            final AlertDialog dialog = builder.create();


            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try {

                        boolean nameValid = ValidationRegisterClient(inputName);

                        boolean enterpriseValid = ValidationRegisterClient(inputEnterprise);

                        boolean finalCondition = false;

                        if(isDirections){

                            finalCondition = nameValid;
                        }
                        else{
                            finalCondition = nameValid && enterpriseValid;
                        }

                        if(finalCondition){

                            if(action.equals("add")){

                                boolean isAdded;

                                if(isDirections){


                                    isAdded = DirectionService.SaveDirection(new Directions(inputName.getText().toString(), clients));

                                    if(isAdded){
                                        Toast.makeText(context,generalResource.getString(R.string.registerSuccess), Toast.LENGTH_LONG).show();

                                    }
                                    else{
                                        Toast.makeText(context,generalResource.getString(R.string.registerError).replace("0","Direccion"), Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{

                                    isAdded = ClientsService.SaveClient(new Clients(inputName.getText().toString(), inputEnterprise.getText().toString()));

                                    if(isAdded){
                                        Toast.makeText(context,generalResource.getString(R.string.registerSuccess), Toast.LENGTH_LONG).show();

                                    }
                                    else{
                                        Toast.makeText(context,generalResource.getString(R.string.registerError).replace("0","Cliente"), Toast.LENGTH_LONG).show();
                                    }
                                }

                            }
                            else{

                                boolean isUpdated;

                                if(isDirections){

                                    isUpdated = DirectionService.UpdateDirections(directions.getId(), new Directions(inputName.getText().toString(), clients));

                                    if(isUpdated){

                                        Toast.makeText(context,generalResource.getString(R.string.updated).replace("0","Direccion").replace("1","Actualizada"), Toast.LENGTH_LONG).show();

                                    }
                                    else{
                                        Toast.makeText(context,generalResource.getString(R.string.notUpdated).replace("0","Actualizar").replace("1","Direccion"), Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{

                                    isUpdated = ClientsService.UpdateClient(clients.getId(), new Clients(inputName.getText().toString(), inputEnterprise.getText().toString()));

                                    if(isUpdated){

                                        Toast.makeText(context,generalResource.getString(R.string.updated).replace("0","Cliente").replace("1","Actualizado"), Toast.LENGTH_LONG).show();

                                    }
                                    else{
                                        Toast.makeText(context,generalResource.getString(R.string.notUpdated).replace("0","Actualizar").replace("1","Cliente"), Toast.LENGTH_LONG).show();
                                    }
                                }

                            }

                            // Invoke the callback if it's not null
                            if (callback != null) {
                                callback.run();
                            }

                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(context,generalResource.getString(R.string.checkFields), Toast.LENGTH_LONG).show();
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();
                }
            });


        }


    }

    private Boolean ValidationRegisterClient(EditText field){

        boolean validRegister = false;

        if(field.getText().toString()==null || field.getText().toString().isEmpty()){
            field.setHint(generalResource.getString(R.string.fieldEmpty));
        }

        if(field.getText().toString().length() < 5){
            field.setHint(generalResource.getString(R.string.fieldShort));
        }

        if(field.getText().toString().length() > 25){
            field.setHint(generalResource.getString(R.string.fieldLong));
        }
        else{
            validRegister=true;
        }


        return validRegister;

    }


}
