package com.crimsonrgames.android.hablandode;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Aldo Rangel on 22/03/2016.
 */
public class DataManager {
    private static String BASE_URL = "http://www.hablando-de.com/rest/";
    private static String PUBLICATION_PATH = "Publication/";
    private RequestQueue rq;
    private Context context;

    private static DataManager instance;

    private DataManager(Context context){
        this.context=context;
        rq = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }

    public interface HDJSONObjectCallbackListener{
        void onSuccess(JSONObject response);
        void onFailure(VolleyError error);
    }

    public interface HDJSONArraytCallbackListener{
        void onSuccess(JSONArray response);
        void onFailure(VolleyError error);
    }

    private RequestQueue getRequestQueue(){
        if(rq == null){
            rq = Volley.newRequestQueue(context.getApplicationContext());
        }
        return rq;
    }

    public void getPublications(final HDJSONArraytCallbackListener completitionListener){
        JsonArrayRequest request = new JsonArrayRequest( BASE_URL + PUBLICATION_PATH, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("DATAMANAGER", response.toString());
                completitionListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("DATAMANAGER", error.toString());
                completitionListener.onFailure(error);
            }
        });
        rq.add(request);

    }

}
