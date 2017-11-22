package com.products.safetyfirst.login;

import com.products.safetyfirst.Pojos.UserModel;

/**
 * Created by vikas on 22/11/17.
 */

public interface LoginActivityMVP {

    interface View {
        String getEmail();
        String getPassword();

        void showUserNotAvailable();
        void showInputError();
        void showHomeScreen();
        void skip();
        void showSignup();
        void showForgotPass();

    }

    interface Presenter {
        void setView(LoginActivityMVP.View view);
        void getCurrentUser();
        void skipButtonClicked();
        void signupButtonClicked();
        void loginButtonClicked();
        void forgotPasswordClicked();
        void googleLogin();
    }

    interface Model {

        void loginUser(String name, String password);
        UserModel getUser();
    }
}
