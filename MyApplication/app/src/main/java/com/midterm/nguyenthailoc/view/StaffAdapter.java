package com.midterm.nguyenthailoc.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.midterm.nguyenthailoc.DetailsActivity;
import com.midterm.nguyenthailoc.MainActivity;
import com.midterm.nguyenthailoc.R;
import com.midterm.nguyenthailoc.model.Staff;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {
    private List<Staff> staffs;
    public StaffAdapter(ArrayList<Staff> data,Context context){
        staffs = data;
        this.context = context;
    }
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvtitle;
        private TextView tvdesc;
        private TextView tvtimestamp;


        public ViewHolder(View view) {
            super(view);
            tvtitle = view.findViewById(R.id.tv_title);
            tvdesc = view.findViewById(R.id.tv_desc);
            tvtimestamp = view.findViewById(R.id.tv_timestamp);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.staffitem, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Staff staff = staffs.get(position);
        holder.tvtitle.setText(staff.getTitle());
        holder.tvdesc.setText(staff.getDesc());
        holder.tvtimestamp.setText(staff.getTimeStamp());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("desc" , staff.getDesc());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return staffs.size();
    }
}
