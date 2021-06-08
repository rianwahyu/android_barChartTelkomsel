package com.bibitproject.aplikasidatachart.Staff.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bibitproject.aplikasidatachart.R;
import com.bibitproject.aplikasidatachart.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        SectionsPagerHomeAdapter sectionsPagerAdapter = new SectionsPagerHomeAdapter(getContext(), getActivity().getSupportFragmentManager());
        binding.viewPager.setAdapter(sectionsPagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);

        binding.toolbar.setTitle("Ruth Apps");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        //setSupportActionBar(binding.toolbar);

        return binding.getRoot();
    }
}