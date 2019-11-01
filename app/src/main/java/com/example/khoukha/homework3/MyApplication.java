package com.example.khoukha.homework3;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    Realm realm;
    @Override
    public void onCreate()
    {
        super.onCreate();
        Realm.init(this);

    }

    public void openRealm() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
    }

    public void closeRealm() {
        realm.close();
    }

    public Realm getRealmLoc() {

        return realm;
    }
}

