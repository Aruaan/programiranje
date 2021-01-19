package com.example.miwokapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private final String[] tabTitles = new String[] {"Numbers", "Family Members", "Colors", "Phrases"};
    public SimpleFragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }


    public Fragment getItem(int position) {
        if (position==0){
        return new NumbersFragment();
        }
        else if(position==1){
            return new FamilyMembersFragment();
        }
        else if(position==2){
            return new ColorsFragment();
        }
        else{
            return new PhrasesFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
