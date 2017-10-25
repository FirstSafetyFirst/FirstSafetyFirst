package com.products.safetyfirst.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.adapters.KnowItAdapter;
import com.products.safetyfirst.impementations.presenter.KnowItPresenterImpl;
import com.products.safetyfirst.interfaces.presenter.KnowItPresenter;
import com.products.safetyfirst.interfaces.view.KnowItView;
import com.products.safetyfirst.models.KnowItItem;

import java.util.ArrayList;

public class KnowIt_Fragment extends Fragment implements KnowItView{

    public final static String tool = "tool";

    private RecyclerView itemsRecycler;
    //private FastItemAdapter itemsAdapter;
    private ArrayList<KnowItItem> items;
    private KnowItPresenter presenter;
    private KnowItAdapter knowItAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter= new KnowItPresenterImpl(this);
        knowItAdapter= new KnowItAdapter(getContext());
        knowItAdapter.request();
        View rootView = inflater.inflate(R.layout.activity_know_it, container, false);
       /* setSupportActionBar((Toolbar) rootView.findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Know It");
        }*/

        itemsRecycler = (RecyclerView) rootView.findViewById(R.id.know_it_recycler);
        //itemsAdapter = new FastItemAdapter();
        items = new ArrayList<>();
        itemsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //itemsRecycler.setAdapter(itemsAdapter);
        itemsRecycler.setAdapter(knowItAdapter);

        /**String[] titles = getResources().getStringArray(R.array.item_title);
        TypedArray images = getResources().obtainTypedArray(R.array.item_picture);
        for(int i = 0; i < titles.length; i++) {
            items.add(new knowititem(titles[i], images.getDrawable(i)));
        }
        images.recycle();
         **/
        knowItAdapter.addAllItems(items);

        return rootView;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void navigateToHome() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {
        Log.e("Know_itFragment","Error populating views");
    }

    /**private class knowititem extends AbstractItem<knowititem, ViewHolder> {

        private String title;
        private Drawable image;

        knowititem(String title, Drawable image){
            this.title = title;
            this.image = image;
        }

        @Override
        public ViewHolder getViewHolder(View v) {
            return new ViewHolder(v);
        }

        @Override
        public int getType() {
            return R.id.know_it_item;
        }

        @Override
        public int getLayoutRes() {
            return R.layout.know_it_item;
        }

        @Override
        public void bindView(ViewHolder holder, List<Object> payloads) {
            super.bindView(holder, payloads);

            holder.title.setText(title);
            holder.image.setImageDrawable(image);
        }

        @Override
        public void unbindView(ViewHolder holder) {
            super.unbindView(holder);

            holder.title.setText(null);
            holder.image.setImageDrawable(null);
        }
    }
    private static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public View mainView;

        public ViewHolder(View view) {
            super(view);
            mainView = view.findViewById(R.id.know_it_item);
            title = (TextView) view.findViewById(R.id.know_it_item_title);
            image = (ImageView) view.findViewById(R.id.know_it_item_image);
        }
    }
        **/
}
