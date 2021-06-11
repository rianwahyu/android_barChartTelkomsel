package com.bibitproject.aplikasidatachart.Staff.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bibitproject.aplikasidatachart.R;
import com.bibitproject.aplikasidatachart.databinding.FragmentHomeFaultBinding;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class HomeFaultFragment extends Fragment {

    FragmentHomeFaultBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home_fault, container, false);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_fault, container, false);

        BarDataSet barDataSet1 = new BarDataSet(barEntries1(), "DataSet 1");
        barDataSet1.setColor(Color.RED);
        BarDataSet barDataSet2 = new BarDataSet(barEntries2(), "DataSet 2");
        barDataSet2.setColor(Color.BLUE);
        BarDataSet barDataSet3 = new BarDataSet(barEntries3(), "DataSet 3");
        barDataSet3.setColor(Color.GREEN);
        BarDataSet barDataSet4 = new BarDataSet(barEntries4(), "DataSet 4");
        barDataSet4.setColor(Color.YELLOW);

        BarData data = new BarData(barDataSet1, barDataSet2, barDataSet3, barDataSet4);
        binding.barFault.setData(data);

        String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        XAxis xAxis = binding.barFault.getXAxis();

        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        binding.barFault.setDragEnabled(true);
        binding.barFault.setVisibleXRangeMaximum(3);

        float barSpace = 0.08f;
        float groupSpace = 0.44f;
        data.setBarWidth(0.10f);

        binding.barFault.getXAxis().setAxisMinimum(0);
        binding.barFault.getXAxis().setAxisMinimum(0 +
                binding.barFault.getBarData().getGroupWidth(groupSpace, barSpace)*7);
        binding.barFault.getAxisLeft().setAxisMinimum(0);

        binding.barFault.groupBars(0, groupSpace, barSpace);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        binding.barFault.invalidate();

        return binding.getRoot();
    }

    private ArrayList<BarEntry> barEntries1(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 2000));
        barEntries.add(new BarEntry(2, 1652));
        barEntries.add(new BarEntry(3, 1312));
        barEntries.add(new BarEntry(4, 2019));
        barEntries.add(new BarEntry(5, 2113));
        barEntries.add(new BarEntry(6, 4311));
        barEntries.add(new BarEntry(7, 1231));

        return barEntries;
    }

    private ArrayList<BarEntry> barEntries2(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 2000));
        barEntries.add(new BarEntry(2, 1652));
        barEntries.add(new BarEntry(3, 1312));
        barEntries.add(new BarEntry(4, 2019));
        barEntries.add(new BarEntry(5, 2113));
        barEntries.add(new BarEntry(6, 4311));
        barEntries.add(new BarEntry(7, 1231));

        return barEntries;
    }


    private ArrayList<BarEntry> barEntries3(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 987));
        barEntries.add(new BarEntry(2, 1652));
        barEntries.add(new BarEntry(3, 5634));
        barEntries.add(new BarEntry(4, 5623));
        barEntries.add(new BarEntry(5, 2113));
        barEntries.add(new BarEntry(6, 431));
        barEntries.add(new BarEntry(7, 2442));

        return barEntries;
    }


    private ArrayList<BarEntry> barEntries4(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 1232));
        barEntries.add(new BarEntry(2, 6242));
        barEntries.add(new BarEntry(3, 13123));
        barEntries.add(new BarEntry(4, 624));
        barEntries.add(new BarEntry(5, 86767));
        barEntries.add(new BarEntry(6, 1909));
        barEntries.add(new BarEntry(7, 23234));

        return barEntries;
    }
}