// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.samples.communication.calling.views.activities;

import android.app.Activity;
import android.content.Context;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.azure.samples.communication.calling.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FcmNotificationsSender  {

    String userFcmToken;
    String title;
    String body;
    Context mContext;
    Activity mActivity;


    private RequestQueue requestQueue;
    private final String postUrl = "https://fcm.googleapis.com/fcm/send";
    private final String fcmServerKey = "AAAAce5PdO4:APA91bGLfRYa39Acq6fEjky8epxPcgN164lWmq3-t"
            + "_L0TITi4SE32pT13DPFx3qvB1P-_11uwUW8QSY4R_nd86MJm1HSKNn0uJWxp5yNY8QUGWBr8swoIWs4ACNXg25c-8D_VtV6uhZR";

    public FcmNotificationsSender(final String userFcmToken, final String title, final String body,
                                  final Context mContext, final Activity mActivity) {
        this.userFcmToken = userFcmToken;
        this.title = title;
        this.body = body;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public void sendnotifications() {

        requestQueue = Volley.newRequestQueue(mActivity);
        final JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("to", userFcmToken);
            final JSONObject notiObject = new JSONObject();
            notiObject.put("title", title);
            notiObject.put("body", body);
            notiObject.put("icon", R.drawable.ic_baseline_notifications_active_24);
            // enter icon that exists in drawable only



            mainObj.put("notification", notiObject);

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                       postUrl, mainObj, new Response.Listener<JSONObject>() {
                           @Override
                public void onResponse(final JSONObject response) {

                    // code run is got response

                           }
                           }, new Response.ErrorListener() {
                               @Override
                public void onErrorResponse(final VolleyError error) {
                    // code run is got error

                               }
                           }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {


                    final Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + fcmServerKey);
                    return header;


                }
            };
            requestQueue.add(request);


        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}
