package com.project.tki.myalarmmanager.admob;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;
import com.project.tki.myalarmmanager.R;
import com.project.tki.myalarmmanager.databinding.ActivityAdmobTestBinding;

import java.util.ArrayList;
import java.util.List;

public class AdmobTest extends AppCompatActivity implements BannerFragment.OnFragmentInteractionListener {
    private ActivityAdmobTestBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_admob_test);
        b.setActivity(this);

        MobileAds.initialize(this, getString(R.string.admob_id));

/* AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
//    Add adView to your view hierarchy.
*/


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BannerFragment(), "Banner");
        adapter.addFragment(new InterstitialFragment(), "Inter");
        adapter.addFragment(new RewardedVideoFragment(), "Video");
        b.vp.setAdapter(adapter);

        b.tab.setupWithViewPager(b.vp);


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> listFrags = new ArrayList<>();
        private final List<String> listTitles = new ArrayList<>();

          ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listFrags.get(position);
        }

        @Override
        public int getCount() {
            return listFrags.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTitles.get(position);
        }
        public void addFragment(Fragment fragment, String title) {
            listFrags.add(fragment);
            listTitles.add(title);
        }
    }

}

