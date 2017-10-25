package com.products.safetyfirst.fragment;


import android.content.Intent;
import android.os.Bundle;
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
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.ItemTypeInfoActivity;
import com.products.safetyfirst.models.KnowItItem;
import com.products.safetyfirst.models.KnowItItemType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
    private KnowItItem knowItItem;
    private HashMap<String,KnowItItemType> knowItItemTypeHashMap;
    public TypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_type, container, false);
        //tool = getArguments().getInt(KnowIt_Fragment.tool);
        knowItItem= getArguments().getParcelable("KnowItItem");
        knowItItemTypeHashMap= knowItItem.getKnow_it_item_types();
        typeRecycler = (RecyclerView) mainView.findViewById(R.id.type_recycler);

        typeAdapter = new FastItemAdapter();
        types = new ArrayList<>();

        typeRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        typeRecycler.setAdapter(typeAdapter);

        //Resources res = getResources();
        //TypedArray titleArray = res.obtainTypedArray(R.array.third_title);
        //int titleId = titleArray.getResourceId(tool, 0);
        //String[] titles = res.getStringArray(titleId);

        String titles[] = new String[knowItItemTypeHashMap.size()];
        for(int i=0;i<knowItItemTypeHashMap.size();i++){
            titles=(String[]) (knowItItemTypeHashMap.keySet().toArray());
        }

       /** TypedArray descArray = res.obtainTypedArray(R.array.third_description);
        int descId = descArray.getResourceId(tool, 0);
        String[] descriptions = res.getStringArray(descId);

        TypedArray imageArray = res.obtainTypedArray(R.array.third_image);
        int imageId = imageArray.getResourceId(tool, 0);
        TypedArray images = res.obtainTypedArray(imageId);
        **/
       KnowItItemType knowItItemType;
        for (int i = 0; i < titles.length; i++) {
            knowItItemType= knowItItemTypeHashMap.get(titles[i]);
            types.add(new TypeFragment.TypeItem(titles[i],knowItItemType.getItem_thumb_url() ));
        }
        //images.recycle();
        typeAdapter.add(types);

        final String[] finalTitles = titles;
        typeAdapter.withOnClickListener(new FastAdapter.OnClickListener<TypeItem>() {
            @Override
            public boolean onClick(View v, IAdapter<TypeItem> adapter, TypeItem item, int position) {
                Intent intent = new Intent(getContext(), ItemTypeInfoActivity.class);
               intent.putExtra("KnowItitemType",knowItItemTypeHashMap.get(finalTitles[position]));
                startActivity(intent);
                return true;
            }
        });
        return mainView;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public View mainView;

        public ViewHolder(View view) {
            super(view);
            mainView = view.findViewById(R.id.type_item);
            title = (TextView) view.findViewById(R.id.type_item_title);
            image = (ImageView) view.findViewById(R.id.type_item_image);
        }
    }

    private class TypeItem extends AbstractItem<TypeItem, ViewHolder> {

        private String title;
        private String image;

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

        @Override
        public void unbindView(TypeFragment.ViewHolder holder) {
            super.unbindView(holder);

            holder.title.setText(null);
            holder.image.setImageDrawable(null);
        }
    }
}