// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static com.azure.samples.communication.calling.contracts.Constants.INVITE_ANOTHER_DEVICE;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.app.NotificationManagerCompat;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.azure.android.communication.ui.calling.CallComposite;
import com.azure.android.communication.ui.calling.CallCompositeBuilder;
import com.azure.android.communication.ui.calling.CallCompositeEventHandler;
import com.azure.android.communication.ui.calling.models.CallCompositeErrorCode;
import com.azure.android.communication.ui.calling.models.CallCompositeErrorEvent;
import com.azure.android.communication.ui.calling.models.CallCompositeRemoteOptions;
import com.azure.samples.communication.calling.AzureCalling;
import com.azure.samples.communication.calling.R;
import com.azure.samples.communication.calling.contracts.Constants;
import com.azure.samples.communication.calling.contracts.SampleErrorMessages;
import com.azure.samples.communication.calling.externals.calling.CallingContext;
import com.azure.samples.communication.calling.utilities.AppSettings;
import com.azure.samples.communication.calling.views.components.ErrorInfoBar;
import com.microsoft.fluentui.widget.Button;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class InvitationActivity extends AppCompatActivity {
    Button notifyBtn;

    NotificationManagerCompat notificationManagerCompat;
    Notification notification;

    Connection connect;
    String connectionresult = "";
    String tokencord;

    private Button startCallButton;
    private Button shareCallButton;

    private Activity activity;
    private CallingContext callingContext;
    private CallCompositeRemoteOptions options;
    private SharedPreferences sharedPreferences;
    private ErrorInfoBar errorInfoBar;
    private AppSettings appSettings;

    private CallCompositeEventHandler<CallCompositeErrorEvent> callCompositeEventHandler =
        new CallCompositeEventHandler<CallCompositeErrorEvent>() {
            @Override
            public void handle(final CallCompositeErrorEvent eventArgs) {
                getSupportActionBar().hide();
                if (eventArgs.getErrorCode().equals(CallCompositeErrorCode.CALL_JOIN_FAILED)) {
                    errorInfoBar.displayErrorInfoBar(
                            activity.getWindow().getDecorView().findViewById(android.R.id.content),
                            SampleErrorMessages.CALL_COMPOSITE_JOIN_CALL_FAILED);
                } else if (eventArgs.getErrorCode().equals(CallCompositeErrorCode.CALL_END_FAILED)) {
                    errorInfoBar.displayErrorInfoBar(
                            activity.getWindow().getDecorView().findViewById(android.R.id.content),
                            SampleErrorMessages.CALL_COMPOSITE_END_CALL_FAILED);
                } else if (eventArgs.getErrorCode().equals(CallCompositeErrorCode.TOKEN_EXPIRED)) {
                    errorInfoBar.displayErrorInfoBar(
                            activity.getWindow().getDecorView().findViewById(android.R.id.content),
                            SampleErrorMessages.CALL_COMPOSITE_TOKEN_EXPIRED);
                }
            }
        };

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        activity = this;
        appSettings = ((AzureCalling) getApplication()).getAppSettings();

        final ActionBar ab = getSupportActionBar();
        // Disable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(INVITE_ANOTHER_DEVICE);
        }

        sharedPreferences = this.getSharedPreferences(Constants.ACS_SHARED_PREF, Context.MODE_PRIVATE);
        errorInfoBar = new ErrorInfoBar();
        startCallSetup();
        initializeUI();
    }

    public void push(final View view) {
        notificationManagerCompat.notify(1, notification);
    }

    private void initializeUI() {
        startCallButton = findViewById(R.id.start_call_continue_button);
        startCallButton.setOnClickListener(l -> makeCall());

        shareCallButton = findViewById(R.id.share_button);
        shareCallButton.setOnClickListener(l -> openShareDialogue());
    }

    private void startCallSetup() {
        ((AzureCalling) getApplicationContext()).createCallingContext();
        callingContext = ((AzureCalling) getApplicationContext()).getCallingContext();

        options = ((AzureCalling) getApplicationContext())
                .getCallingContext()
                .getCallCompositeRemoteOptions(appSettings.getUserProfile().getDisplayName());
    }

    private void openShareDialogue() {
        final Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, callingContext.getJoinId());
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Group Call ID");
        sendIntent.setType("text/plain");
        final Intent shareIntent = Intent.createChooser(sendIntent, null);
        shareIntent.setFlags(FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(shareIntent);
        //Added by Deepak
        //inserttotable();
    }

    private void makeCall() {
        //Added by Deepak
        inserttotable();
        sendnotification();
        final CallComposite composite = new CallCompositeBuilder().build();
        composite.addOnErrorEventHandler(callCompositeEventHandler);
        composite.launch(this, options);
    }

    //Added by Deepak
    public void sendnotification() {



        final String locregby = MyAdapter2.cordname;

        final String locpatname = MyAdapter2.patname;

        final String locregdate = MyAdapter2.regdate;



        try {
            final ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();

            if (connect != null) {
                //final String query = "select * from dbo.tokenstorage order by  srlno asc";
                final String query = "select * from dbo.tokenstorage where coordinatorname = '"
                        + locregby + "' and status = 'Waiting'" + "and patientname = '" + locpatname
                        +  "' and regdate = '" + locregdate + "'";
                final Statement st = connect.createStatement();
                final ResultSet rs = st.executeQuery(query);

                while (rs.next()) {

                    tokencord = rs.getString(4);

                }
                //final String title = "Join Call";
                final String title = locpatname;
                final String body = VCHLoginActivity.doctorname + " is now ready for " + locpatname;

                Log.d("toooo", "sendtoken:" + tokencord);
                final FcmNotificationsSender fcmNotificationsSender = new FcmNotificationsSender(tokencord,
                        title,
                        body, getApplicationContext(), InvitationActivity.this);
                fcmNotificationsSender.sendnotifications();

                /* final String updquery = "update dbo.tokenstorage set status = 'Completed' where coordinatorname = '"
                        + loccoremail + "'";
                final Statement updst = connect.createStatement();
                final ResultSet updrs = st.executeQuery(updquery); */



            } else {
                connectionresult = "Check Connection";
            }

        } catch (Exception ex) {



        }

    }






    public void inserttotable() {


        try {
            final ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {

                final String sqlinsert = "Insert into dbo.groupcallid values ('" + callingContext.getJoinId()
                        + "','N')";

                final Statement st = connect.createStatement();
                final ResultSet rs = st.executeQuery(sqlinsert);

            } else {
                connectionresult = "Check Connection";
            }

        } catch (Exception ex) {

        }

    }



}


