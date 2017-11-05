package com.products.safetyfirst.impementations.presenter;

import com.products.safetyfirst.adapters.BookmarkAdapter;
import com.products.safetyfirst.impementations.Interactor.BookmarkInteractorImpl;
import com.products.safetyfirst.interfaces.adapter.BookmarkAdapterView;
import com.products.safetyfirst.interfaces.interactor.BookmarkInteractor;
import com.products.safetyfirst.interfaces.presenter.BookmarkPresenter;
import com.products.safetyfirst.interfaces.view.BookmarkView;

import java.util.ArrayList;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class BookmarkPresenterImpl implements BookmarkPresenter{
    private BookmarkAdapterView bookmarkAdapterView;
    private BookmarkInteractor bookmarkInteractor;
    private BookmarkView bookmarkView;

    public BookmarkPresenterImpl(BookmarkView bookmarkView){
        this.bookmarkView=bookmarkView;
        this.bookmarkInteractor= new BookmarkInteractorImpl(this);
    }

    public BookmarkPresenterImpl(BookmarkAdapter adapter){
        this.bookmarkAdapterView=adapter;
        this.bookmarkInteractor= new BookmarkInteractorImpl(this);
    }
    @Override
    public void onDestroy() {
        bookmarkView=null;
    }

    @Override
    public void getChildren(ArrayList<Object> items) {
        bookmarkAdapterView.addAllItem(items);
    }

    @Override
    public void OnSuccess() {

    }

    @Override
    public void OnError() {

    }
}
