package ru.astondevs.worldnews.presentation.headlines.tablayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ru.astondevs.worldnews.presentation.headlines.tablayout.business.screen.BusinessNewsFragment;
import ru.astondevs.worldnews.presentation.headlines.tablayout.general.screen.GeneralNewsFragment;
import ru.astondevs.worldnews.presentation.headlines.tablayout.traveling.screen.TravelingNewsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity activity) {
        super(activity);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new BusinessNewsFragment();
            case 2:
                return new TravelingNewsFragment();
            default:
                return new GeneralNewsFragment();
        }
    }
}






