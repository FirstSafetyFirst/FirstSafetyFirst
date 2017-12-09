package com.products.safetyfirst.root;

import com.products.safetyfirst.activity.SplashActivity;
import com.products.safetyfirst.postDetail.PostDetailActivity;
import com.products.safetyfirst.root.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vikas on 22/11/17.
 */
@Singleton
@Component(modules = {ApplicationModule.class, PostDetailActivity.class})
public interface ApplicationComponent {

    void inject (PostDetailActivity target);

}
