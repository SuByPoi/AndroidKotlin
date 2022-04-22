package com.midterm.nguyenthailoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.midterm.nguyenthailoc.databinding.ActivityDetailsBinding;
import com.midterm.nguyenthailoc.databinding.ActivityMainBinding;
import com.midterm.nguyenthailoc.model.Staff;
import com.midterm.nguyenthailoc.room.StaffDao;
import com.midterm.nguyenthailoc.room.StaffDatabase;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private Staff staff;
    private StaffDao staffDao;
    private StaffDatabase staffDatabase;
    String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View viewroot = binding.getRoot();
        setContentView(viewroot);

        desc = getIntent().getStringExtra("desc");
        staffDatabase = StaffDatabase.getInstance(this);
        staffDao = staffDatabase.roomDao();
        staff = staffDao.getStaffBydesc(desc);
        binding.tvTitle.setText(staff.getTitle());
        binding.tvAddr.setText(staff.getAddr());
        binding.tvE.setText(staff.getE());
        binding.tvLat.setText(staff.getLat());
        binding.tvTimestamp.setText(staff.getTimeStamp());
        binding.tvDesc.setText(staff.getDesc());
        binding.tvLng.setText(staff.getLng());
        binding.tvZip.setText(staff.getZip());
    }
}