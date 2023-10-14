package ru.astondevs.worldnews.presentation.headlines.screen;

import static ru.astondevs.worldnews.utils.UiKt.toolbarSetting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.astondevs.worldnews.R;
import ru.astondevs.worldnews.databinding.FragmentHeadlinesBinding;
import ru.astondevs.worldnews.presentation.headlines.presenter.HeadlinesPresenter;
import ru.astondevs.worldnews.presentation.headlines.presenter.HeadlinesView;
import ru.astondevs.worldnews.presentation.headlines.tablayout.ViewPagerAdapter;

public class HeadlinesFragmentMVP extends MvpAppCompatFragment implements HeadlinesView {

    @InjectPresenter
    HeadlinesPresenter headlinesPresenter;

    private FragmentHeadlinesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHeadlinesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        toolbarSetting(getActivity(), "Headlines", false, R.id.toolbar);

        ViewPager2 viewPager = binding.viewPager;
        viewPager.setAdapter(new ViewPagerAdapter(requireActivity()));

        new TabLayoutMediator(binding.tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setCustomView(R.layout.tab_general);
                    break;
                case 1:
                    tab.setCustomView(R.layout.tab_business);
                    break;
                case 2:
                    tab.setCustomView(R.layout.tab_traveling);
                    break;
                default:
                    tab.setText("Unknown");
            }
        }).attach();

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@Nullable TabLayout.Tab tab) {
                if (tab != null) {
                    View tabItem = tab.getCustomView();
                    TextView tabText = tabItem != null ? tabItem.findViewById(R.id.tab_text) : null;
                    if (tabText != null) {
                        tabText.setTextColor(getResources().getColor(R.color.M3_sys_light_primary));
                    }
                }
            }

            @Override
            public void onTabUnselected(@Nullable TabLayout.Tab tab) {
                if (tab != null) {
                    View tabItem = tab.getCustomView();
                    TextView tabText = tabItem != null ? tabItem.findViewById(R.id.tab_text) : null;
                    if (tabText != null) {
                        tabText.setTextColor(getResources().getColor(R.color.M3_sys_light_on_surface_variant));
                    }
                }
            }

            @Override
            public void onTabReselected(@Nullable TabLayout.Tab tab) {}
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void showData(String data) {

    }

    public static final String FRAGMENT_HEADLINES_TAG = "FRAGMENT_HEADLINES_TAG";

    public static HeadlinesFragmentMVP newInstance() {
        return new HeadlinesFragmentMVP();
    }

}
