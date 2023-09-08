package com.ms.hht.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HHLogger {
    private static HHLogger INSTANCE = null;
    private static Context mContext;
    private static String TAG = "HHLogger";
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference myLogRef;
    private boolean ENABLE_LOGGING = false;

    public static HHLogger getINSTANCE(Context context) {
        TAG = context.toString();
        return INSTANCE != null ? INSTANCE : (INSTANCE = new HHLogger(context));
    }

    HHLogger(Context context) {
        this.mContext = context;
        initiateFirebase();
    }

    private void initiateFirebase() {
        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        myLogRef = database.getReference("LOG");
        myRef.setValue("TestMessage: Hello, World!😀");
        // Read from the database
        if (ENABLE_LOGGING)
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                    Log.d(TAG, "Value is: " + value);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
    }

    public void REQUEST(String TAG, String api, String paramJson, Map<String, Object> paramMap) {
        if (database == null || myRef == null)
            initiateFirebase();
        String mTime = System.currentTimeMillis() + "";
        HashMap<String, Object> requestMAP = new HashMap<>();
        requestMAP.put("url", api);
        requestMAP.put("REQUEST", "true");
        requestMAP.put("time", new Date().toInstant());
        requestMAP.put("param", paramMap);
        requestMAP.put("paramJson", paramJson);
        requestMAP.put("TAG", TAG);
        try {
            myLogRef.child(android.os.Build.MODEL).child(mTime).setValue(requestMAP).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("keyssss..", "Update request api =>" + api);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("keyssss..", "Failed to update request api =>" + api);
                }
            });
        } catch (DatabaseException e) {
            e.printStackTrace();
            if (ENABLE_LOGGING)
                myLogRef.child(android.os.Build.MODEL).child(mTime).setValue(new Gson().toJson(requestMAP)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("keyssss..", "Update request api =>" + api);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("keyssss..", "Failed to update request api =>" + api);
                    }
                });
        }
    }

    public void RESPONSE(String TAG, String api, String responseJson, Object responseMap, int successCode) {
        if (database == null || myRef == null)
            initiateFirebase();
        String mTime = System.currentTimeMillis() + "";
        HashMap<String, Object> requestMAP = new HashMap<>();
        requestMAP.put("url", api);
        requestMAP.put("RESPONSE", "true");
        requestMAP.put("time", new Date().toInstant());
        requestMAP.put("param", responseMap);
        requestMAP.put("paramJson", responseJson);
        requestMAP.put("statuscode", successCode);
        requestMAP.put("TAG", TAG);
        if (ENABLE_LOGGING)
            myLogRef.child(android.os.Build.MODEL).child(mTime).setValue(requestMAP).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("keyssss..", "Update response of api =>" + api);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("keyssss..", "Failed to update response api =>" + api);
                }
            });
    }

    public void LOG(String TAG, String someData, String about) {
        if (database == null || myRef == null)
            initiateFirebase();
        String mTime = System.currentTimeMillis() + "";
        HashMap<String, Object> requestMAP = new HashMap<>();
        requestMAP.put(about, someData);
        requestMAP.put("REQUEST", "false");
        requestMAP.put("time", new Date().toInstant());
        requestMAP.put("TAG", TAG);
        if (ENABLE_LOGGING)
            myLogRef.child(android.os.Build.MODEL).child(mTime).setValue(requestMAP).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("keyssss..", "Update somedata  =>" + someData);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("keyssss..", "Failed to update somedata  =>" + someData);
                }
            });
    }

    public void EXCEPTION(String TAG, String exception) {
        if (database == null || myRef == null)
            initiateFirebase();
        String mTime = System.currentTimeMillis() + "";
        HashMap<String, Object> requestMAP = new HashMap<>();
        requestMAP.put("exception", exception);
        requestMAP.put("REQUEST", "false");
        requestMAP.put("time", new Date().toInstant());
        requestMAP.put("TAG", TAG);
        if (ENABLE_LOGGING)
            myLogRef.child(android.os.Build.MODEL).child(mTime).setValue(requestMAP).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("keyssss..", "Update exception  =>" + exception);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("keyssss..", "Failed to update exception  =>" + exception);
                }
            });
    }
}
