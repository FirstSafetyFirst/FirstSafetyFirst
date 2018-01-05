package com.products.safetyfirst.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.products.safetyfirst.BuildConfig;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.ItemTypeInfoActivity;
import com.products.safetyfirst.activity.KnowItSecondActivity;
import com.products.safetyfirst.modelhelper.UserHelper;
import com.products.safetyfirst.utils.Analytics;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeFragment extends Fragment {

    //private static Integer tool;
    private View mainView;
    private RecyclerView typeRecycler;
    private FastItemAdapter<TypeItem> typeAdapter;
    private List<TypeItem> types;
    private HashMap<String,String> knowItItemTypes;
    private static final int RC_SIGN_IN = 123;

    public TypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_type, container, false);
        typeRecycler = mainView.findViewById(R.id.type_recycler);

        typeAdapter = new FastItemAdapter();
        types = new ArrayList<>();

        typeRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        typeRecycler.setAdapter(typeAdapter);


        knowItItemTypes = ((KnowItSecondActivity) this.getActivity()).getTypes();


        for(HashMap.Entry<String, String> entry : knowItItemTypes.entrySet()){
            types.add(new TypeFragment.TypeItem(entry.getKey(), entry.getValue()));
        }
        //images.recycle();
        typeAdapter.add(types);

        //final String[] finalTitles = titles;
        typeAdapter.withOnClickListener(new FastAdapter.OnClickListener<TypeItem>() {
            @Override
            public boolean onClick(View v, IAdapter<TypeItem> adapter, TypeItem item, int position) {


                Analytics.logEventViewItem(getContext(),"know_it_item_type"+position,item.getTitle(),"know it item type");

                final UserHelper user  = UserHelper.getInstance();
                if(user.isSignedIn()) {
                    Intent intent = new Intent(getContext(), ItemTypeInfoActivity.class);
                    intent.putExtra(ItemTypeInfoActivity.EXTRA_ITEM_NAME, item.getTitle());
                    startActivity(intent);
                } else {
                    Snackbar mySnackbar = Snackbar.make(getView(),
                            R.string.not_signed_in, Snackbar.LENGTH_SHORT);
                    mySnackbar.setAction(R.string.signIn, new MySignInListener());
                    mySnackbar.show();
                }


                return true;
            }
        });
        return mainView;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final ImageView image;
        public final View mainView;

        public ViewHolder(View view) {
            super(view);
            mainView = view.findViewById(R.id.type_item);
            title = view.findViewById(R.id.type_item_title);
            image = view.findViewById(R.id.type_item_image);
        }
    }

    private class TypeItem extends AbstractItem<TypeItem, ViewHolder> {

        private final String title;
        private final String image;

        TypeItem(String title, String image) {
            this.title = title;
            this.image = image;
        }

        @Override
        public TypeFragment.ViewHolder getViewHolder(View v) {
            return new TypeFragment.ViewHolder(v);
        }

        @Override
        public int getType() {
            return R.id.type_item;
        }

        @Override
        public int getLayoutRes() {
            return R.layout.type_item;
        }

        @Override
        public void bindView(TypeFragment.ViewHolder holder, List<Object> payloads) {
            super.bindView(holder, payloads);
            holder.title.setText(title);
            try {
                Glide.with(getContext()).load(new URL(image)).into(holder.image);
            } catch (MalformedURLException e) {
                Log.e("Typefragment","Error loading image");
            }
        }

        public String getTitle(){
            return this.title;
        }

        @Override
        public void unbindView(TypeFragment.ViewHolder holder) {
            super.unbindView(holder);
        }
    }

    public class MySignInListener implements View.OnClickListener{

        public MySignInListener(){
        }

        @Override
        public void onClick(View v) {

            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                    // new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                    // new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setTosUrl("https://superapp.example.com/terms-of-service.html")
                            .setPrivacyPolicyUrl("https://superapp.example.com/privacy-policy.html")
                            .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                            .build(),
                    RC_SIGN_IN);


        }
    }
}