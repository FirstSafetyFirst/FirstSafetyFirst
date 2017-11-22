package com.products.safetyfirst.root;

import com.products.safetyfirst.activity.SplashActivity;
import com.products.safetyfirst.login.LoginActivity;
import com.products.safetyfirst.login.LoginModule;
import com.products.safetyfirst.root.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vikas on 22/11/17.
 */
@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class})
public interface ApplicationComponent {

    void inject (LoginActivity  target);

}
