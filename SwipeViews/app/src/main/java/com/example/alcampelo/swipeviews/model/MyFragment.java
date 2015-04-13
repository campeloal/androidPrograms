package com.example.alcampelo.swipeviews.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alcampelo.swipeviews.R;

public class MyFragment extends Fragment{

    int mCurrentPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Getting the arguments to the Bundle object */
        Bundle data = getArguments();

        /** Getting integer data of the key current_page from the bundle */
        mCurrentPage = data.getInt("current_page", 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout, container,false);
        return v;
    }

    public static MyFragment newInstance() {

        MyFragment f = new MyFragment();
        Bundle b = new Bundle();

        f.setArguments(b);

        return f;
    }

}