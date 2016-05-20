package com.example.android.network.sync.basicsyncadapter.accounts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountAuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {

        AccountAuthenticator authenticator = new AccountAuthenticator(this);
        return authenticator.getIBinder();
    }
}
