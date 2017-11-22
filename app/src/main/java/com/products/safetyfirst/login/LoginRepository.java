package com.products.safetyfirst.login;

import com.products.safetyfirst.Pojos.UserModel;

/**
 * Created by vikas on 22/11/17.
 */

public interface LoginRepository {
    UserModel getUser();

    void loginUser(String name, String password);
}
