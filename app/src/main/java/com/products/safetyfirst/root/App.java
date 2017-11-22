package com.products.safetyfirst.root;

import android.app.Application;

import com.products.safetyfirst.login.LoginModule;
import com.products.safetyfirst.root.ApplicationComponent;
import com.products.safetyfirst.root.ApplicationModule;

/**
 * Created by vikas on 22/11/17.
 */

public class App extends Application {

     private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .loginModule(new LoginModule())
                .build();
    }

    public ApplicationComponent getComponent(){
        return component;
    }
}
