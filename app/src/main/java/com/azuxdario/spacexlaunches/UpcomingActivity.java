package com.azuxdario.spacexlaunches;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UpcomingActivity extends AppCompatActivity {
    public static final String UPCOMING_LAUNCH_URL = "https://api.spacexdata.com/v4/launches/upcoming";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        /*ArrayList<UpcomingItem> exampleList = new ArrayList<>();
        exampleList.add(new UpcomingItem("Line 1", "Line 2"));
        exampleList.add(new UpcomingItem("Line 3", "Line 4"));
        exampleList.add(new UpcomingItem("Line 5", "Line 6"));
        exampleList.add(new UpcomingItem("Line 7", "Line 8"));
        exampleList.add(new UpcomingItem("Line 9", "Line 10"));
        exampleList.add(new UpcomingItem("Line 11", "Line 12"));
        exampleList.add(new UpcomingItem("Line 13", "Line 14"));
        exampleList.add(new UpcomingItem("Line 15", "Line 16"));
        exampleList.add(new UpcomingItem("Line 17", "Line 18"));
        exampleList.add(new UpcomingItem("Line 19", "Line 20"));
        exampleList.add(new UpcomingItem("Line 21", "Line 22"));
        exampleList.add(new UpcomingItem("Line 23", "Line 24"));
        exampleList.add(new UpcomingItem("Line 25", "Line 26"));
        exampleList.add(new UpcomingItem("Line 27", "Line 28"));
        exampleList.add(new UpcomingItem("Line 29", "Line 30"));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new UpcomingAdapter(exampleList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);*/
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        UpcomingActivity.AsyncTaskRunner runner = new UpcomingActivity.AsyncTaskRunner();
        runner.execute();
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(UPCOMING_LAUNCH_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
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
            ArrayList<UpcomingItem> upcomingList = new ArrayList<>();
            JSONArray jArray;
            try {
                jArray = new JSONArray(result);
                for(int i = 0; i < jArray.length(); ++i)
                {
                    JSONObject obj = jArray.getJSONObject(i);
                    String rocketName = obj.isNull("name") ?
                            getString(R.string.not_available) :
                            obj.getString("name");
                    String rocketDate = obj.isNull("date_utc") ?
                            getString(R.string.not_available) :
                            DateParser.getParsedDate(obj.getString("date_utc"), obj.getString("date_precision"));
                    upcomingList.add(new UpcomingItem(rocketName, rocketDate));
                }

                adapter = new UpcomingAdapter(upcomingList);
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(UpcomingActivity.this,
                    "ProgressDialog",
                    "Wait for data");
        }

    }
}