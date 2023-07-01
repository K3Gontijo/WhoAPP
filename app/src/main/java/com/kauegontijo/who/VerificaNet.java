package com.kauegontijo.who;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class VerificaNet {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }

        return false;
    }
}

