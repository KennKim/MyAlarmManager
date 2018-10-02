package com.project.tki.myalarmmanager.admob;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.project.tki.myalarmmanager.R;
import com.project.tki.myalarmmanager.databinding.FragmentRewardedVideoBinding;

public class RewardedVideoFragment extends Fragment {
    private FragmentRewardedVideoBinding b;

    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
    private static final String APP_ID = "ca-app-pub-3940256099942544~3347511713";
    private static final long COUNTER_TIME = 5;
    private static final int GAME_OVER_REWARD = 1;

    private int coinCount;
    private CountDownTimer countDownTimer;
    private boolean gameOver;
    private boolean gamePaused;
    private RewardedVideoAd rewardedVideoAd;
    private long timeRemaining;

    private RewardedVideoAdListener listener = new RewardedVideoAdListener() {
        @Override
        public void onRewardedVideoAdLeftApplication() {
            Toast.makeText(getActivity(), "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdClosed() {
            Toast.makeText(getActivity(), "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
            // Preload the next video ad.
            loadRewardedVideoAd();
        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int errorCode) {
            Toast.makeText(getActivity(), "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdLoaded() {
            Toast.makeText(getActivity(), "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdOpened() {
            Toast.makeText(getActivity(), "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewarded(RewardItem reward) {
            Toast.makeText(getActivity(), String.format(" onRewarded! currency: %s amount: %d", reward.getType(), reward.getAmount()), Toast.LENGTH_SHORT).show();
            addCoins(reward.getAmount());
        }

        @Override
        public void onRewardedVideoStarted() {
            Toast.makeText(getActivity(), "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoCompleted() {
            Toast.makeText(getActivity(), "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
        }
    };

    public RewardedVideoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rewarded_video, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        b = FragmentRewardedVideoBinding.bind(getView());
        b.setFragment(this);


        // Initialize the Mobile Ads SDK.
//        MobileAds.initialize(getActivity(), APP_ID);

        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getActivity());
        rewardedVideoAd.setRewardedVideoAdListener(listener);
        loadRewardedVideoAd();


        // Create the "retry" button, which tries to show an interstitial between game plays.
        b.btn1.setVisibility(View.INVISIBLE);
        b.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });

        // Create the "show" button, which shows a rewarded video if one is loaded.
        b.btn2.setVisibility(View.INVISIBLE);
        b.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRewardedVideo();
            }
        });

        // Display current coin count to user.
        coinCount = 0;
        b.tv1.setText("Coins: " + coinCount);

        startGame();
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseGame();
        rewardedVideoAd.pause(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!gameOver && gamePaused) {
            resumeGame();
        }
        rewardedVideoAd.resume(getActivity());
    }


    private void startGame() {
        // Hide the retry button, load the ad, and start the timer.
        b.btn1.setVisibility(View.INVISIBLE);
        b.btn2.setVisibility(View.INVISIBLE);
        loadRewardedVideoAd();
        createTimer(COUNTER_TIME);
        gamePaused = false;
        gameOver = false;
    }

    // Create the game timer, which counts down to the end of the level
    // and shows the "retry" button.
    private void createTimer(long time) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(time * 1000, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                timeRemaining = ((millisUnitFinished / 1000) + 1);
                b.tv2.setText("seconds remaining: " + timeRemaining);
            }

            @Override
            public void onFinish() {
                if (rewardedVideoAd.isLoaded()) {
                    b.btn2.setVisibility(View.VISIBLE);
                }
                b.tv2.setText("You Lose!");
                addCoins(GAME_OVER_REWARD);
                b.btn1.setVisibility(View.VISIBLE);
                gameOver = true;
            }
        };
        countDownTimer.start();
    }

    private void showRewardedVideo() {
        b.btn2.setVisibility(View.INVISIBLE);
        if (rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.show();
        }
    }

    private void pauseGame() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        gamePaused = true;
    }

    private void resumeGame() {
        createTimer(timeRemaining);
        gamePaused = false;
    }

    private void loadRewardedVideoAd() {
        if (!rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.loadAd(AD_UNIT_ID, new AdRequest.Builder().build());
        }
    }

    private void addCoins(int coins) {
        coinCount += coins;
        b.tv1.setText("Coins: " + coinCount);
    }


}