package com.example.sharemybike.mainPanel.ui.nav_date;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharemybike.databinding.FragmentDateBinding;
import com.example.sharemybike.dto.BikesContent;

public class DateFragment extends Fragment {

    private FragmentDateBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DateViewModel dateViewModel =
                new ViewModelProvider(this).get(DateViewModel.class);

        binding = FragmentDateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month++;
                binding.txtDate.setText("Date: " + dayOfMonth + "/" + month + "/" + year);
                BikesContent.date = dayOfMonth + "/" + month + "/" + year;
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}