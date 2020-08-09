package com.azuxdario.spacexlaunches;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String NEXT_LAUNCH_URL = "https://api.spacexdata.com/v4/launches/next";
    TextView textField;
    TextView rocketName;
    TextView rocketFlightNumber;
    TextView rocketDetails;
    Button getDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textField = (TextView) findViewById(R.id.textField);
        rocketName = (TextView) findViewById(R.id.rocketName);
        rocketFlightNumber = (TextView) findViewById(R.id.rocketFlightNumber);
        rocketDetails = (TextView) findViewById(R.id.rocketDetails);
        getDataButton = (Button) findViewById(R.id.getDataButton);
    }

    public void getData(View v) {
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(NEXT_LAUNCH_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            textField.setText(result);
            JSONObject jObj;
            try {
                jObj = new JSONObject(result);

                if(jObj.isNull("name")) {
                    rocketName.setText(getString(R.string.rocket_name_format, getString(R.string.not_available)));
                } else {
                    rocketName.setText(getString(R.string.rocket_name_format, jObj.getString("name")));
                }
                if(jObj.isNull("flight_number")) {
                    rocketFlightNumber.setText(getString(R.string.flight_number_format, getString(R.string.not_available)));
                } else {
                    rocketFlightNumber.setText(getString(R.string.flight_number_format, jObj.getString("flight_number")));
                }
                if(jObj.isNull("details")) {
                    rocketDetails.setText(getString(R.string.details_format, getString(R.string.not_available)));
                } else {
                    rocketDetails.setText(getString(R.string.details_format, jObj.getString("details")));
                }
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "ProgressDialog",
                    "Wait for data");
        }
    }
}