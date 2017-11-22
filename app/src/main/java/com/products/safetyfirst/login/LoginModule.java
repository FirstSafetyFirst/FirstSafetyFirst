package com.products.safetyfirst.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vikas on 22/11/17.
 */
@Module
public class LoginModule  {
    @Provides
    public LoginActivityMVP.Presenter provideLoginActivityPresenter(LoginActivityMVP.Model model){
        return new LoginActivityPresenter(model);
    }

    @Provides
    public LoginActivityMVP.Model provideLoginActivityModel(LoginRepository repository){
        return new LoginModel(repository);
    }

    @Provides
    public LoginRepository provideLoginRepository(){
        return new FirebaseRepository();
    }

}
