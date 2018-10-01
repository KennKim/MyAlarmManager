package com.project.tki.myalarmmanager.admob;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.tki.myalarmmanager.R;

public class RewardedVideoFragment extends Fragment {

    public RewardedVideoFragment() {
    }

    public static RewardedVideoFragment newInstance() {

        Bundle args = new Bundle();

        RewardedVideoFragment fragment = new RewardedVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rewarded_video, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
