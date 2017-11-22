package com.products.safetyfirst.login;

import android.support.annotation.Nullable;

import com.products.safetyfirst.Pojos.UserModel;

/**
 * Created by vikas on 22/11/17.
 */

public class LoginActivityPresenter implements LoginActivityMVP.Presenter{

    @Nullable
    private LoginActivityMVP.View view;
    private LoginActivityMVP.Model model;

    public LoginActivityPresenter(LoginActivityMVP.Model model) {
        this.model = model;
    }

    public void setView(LoginActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void loginButtonClicked() {
        if(view != null){
            if(view.getEmail().trim().equals("") || view.getPassword().trim().length()<6 ){
                view.showInputError();
            }else{
                model.loginUser(view.getEmail(), view.getPassword());
            }
        }
    }

    @Override
    public void forgotPasswordClicked() {
        if(view != null){
            view.showForgotPass();
        }
    }

    @Override
    public void googleLogin() {
        if(view != null){

        }
    }

    @Override
    public void getCurrentUser() {
        UserModel user = model.getUser();

        if(user == null){
            if(view != null){
                view.showUserNotAvailable();
            }
        }else{
            if(view != null){
                view.showHomeScreen();
            }
        }
    }

    @Override
    public void skipButtonClicked() {
        if(view != null){
            view.skip();
        }
    }

    @Override
    public void signupButtonClicked() {
        if(view!= null){
            view.showSignup();
        }
    }
}
