package com.newlogin.mainpage;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.newlogin.Config;
import com.newlogin.R;
import com.newlogin.RegisterUserClass;

import java.util.HashMap;

public class DonatesBlood extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextbloodgrp;
    private EditText editTextdate;
    private EditText editTextaddress;
    private EditText editTextcontactno;

    private Button buttonRegister;

    private static final String REGISTER_URL = Config.DONOR_URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donateblood);

        editTextName = (EditText) findViewById(R.id.et1);
        editTextbloodgrp = (EditText) findViewById(R.id.et2);
        editTextdate = (EditText) findViewById(R.id.et3);
        editTextaddress = (EditText) findViewById(R.id.et4);
        editTextcontactno = (EditText) findViewById(R.id.et5);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();

        }
    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim().toLowerCase();
        String bloodgrp = editTextbloodgrp.getText().toString().trim().toLowerCase();
        String date = editTextdate.getText().toString().trim().toLowerCase();
        String address = editTextaddress.getText().toString().trim().toLowerCase();
        String contactno = editTextcontactno.getText().toString().trim().toLowerCase();

        register(name, bloodgrp, date, address,contactno);
    }

    private void register(String name, String bloodgrp, String date, String address, String contactno) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DonatesBlood.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("name", params[0]);
                data.put("bloodgrp", params[1]);
                data.put("date", params[2]);
                data.put("address", params[3]);
                data.put("contactno", params[4]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name, bloodgrp, date, address, contactno);
    }
}