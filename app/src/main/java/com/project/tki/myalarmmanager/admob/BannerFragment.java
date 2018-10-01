package com.project.tki.myalarmmanager.admob;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.project.tki.myalarmmanager.MainActivity;
import com.project.tki.myalarmmanager.R;
import com.project.tki.myalarmmanager.databinding.FragmentBannerBinding;

public class BannerFragment extends Fragment {
    private FragmentBannerBinding b;

    private OnFragmentInteractionListener mListener;

    public BannerFragment() {
    }

    public static BannerFragment newInstance(String param1, String param2) {
        BannerFragment fragment = new BannerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_banner, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        b = FragmentBannerBinding.bind(getView());
        b.setFragment(this);


        AdRequest adRequest = new AdRequest.Builder().build();
        b.adView.loadAd(adRequest);
        b.adView2.loadAd(adRequest);
        b.adView3.loadAd(adRequest);
        b.adView4.loadAd(adRequest);  // for Tablet
        b.adView5.loadAd(adRequest);  // for Tablet
        b.adView6.loadAd(adRequest);


        b.adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.i(MainActivity.TAG, "onAdLoaded");
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.i(MainActivity.TAG, "onAdFailedToLoad");
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                Log.i(MainActivity.TAG, "onAdOpened");
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                Log.i(MainActivity.TAG, "onAdLeftApplication");
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                Log.i(MainActivity.TAG, "onAdClosed");
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
