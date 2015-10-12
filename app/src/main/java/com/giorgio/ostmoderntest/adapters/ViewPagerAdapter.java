package com.giorgio.ostmoderntest.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.giorgio.ostmoderntest.R;
import com.giorgio.ostmoderntest.controllers.SetsController;
import com.squareup.picasso.Picasso;

/**
 * Created by Giorgio on 11/10/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter{

    private SetsController setsController;

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.setsController = SetsController.getInstance();
    }

    @Override
    public int getCount() {
        return this.setsController.getSets().size();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString(PageFragment.TITLE, this.setsController.getSets().get(position).getTitle());
        args.putString(PageFragment.BODY, this.setsController.getSets().get(position).getBody());
        args.putInt(PageFragment.FILM_COUNT, this.setsController.getSets().get(position).getFilmCount());
        args.putString(PageFragment.IMAGE, this.setsController.getSets().get(position).getImageUrl());
        fragment.setArguments(args);
        return fragment;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public static class PageFragment extends Fragment{
        public static final String TITLE = "title";
        public static final String BODY = "body";
        public static final String FILM_COUNT = "film_count";
        public static final String IMAGE = "image";


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.item_viewpager, container, false);

            ((TextView) rootView.findViewById(R.id.pager_set_title)).setText(getArguments().getString(TITLE));
            ((TextView) rootView.findViewById(R.id.pager_set_body)).setText(getArguments().getString(BODY));

            if(getArguments().getInt(FILM_COUNT) > 0){
                ((TextView) rootView.findViewById(R.id.pager_set_filmCount)).setText("Film: "+String.valueOf(getArguments().getInt(FILM_COUNT)));
            }

            Picasso.with(getActivity())
                    .load(getArguments().getString(IMAGE))
                    .placeholder(R.drawable.placeholder)
                    .tag(getActivity())
                    .into(((ImageView) rootView.findViewById(R.id.pager_set_image)));

            return rootView;
        }
    }
}
