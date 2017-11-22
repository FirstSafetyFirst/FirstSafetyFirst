package com.products.safetyfirst.login;

import com.products.safetyfirst.Pojos.UserModel;

/**
 * Created by vikas on 22/11/17.
 */

public class LoginModel implements LoginActivityMVP.Model {

    private LoginRepository repository;

    public LoginModel(LoginRepository repository) {
        this.repository = repository;
    }


    @Override
    public void loginUser(String name, String password) {
        repository.loginUser(name, password);
    }

    @Override
    public UserModel getUser() {
        return repository.getUser();
    }
}
