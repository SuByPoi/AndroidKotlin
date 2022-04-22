package com.midterm.nguyenthailoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.midterm.nguyenthailoc.databinding.ActivityMainBinding;
import com.midterm.nguyenthailoc.model.MyModel;
import com.midterm.nguyenthailoc.model.Staff;
import com.midterm.nguyenthailoc.room.StaffDao;
import com.midterm.nguyenthailoc.room.StaffDatabase;
import com.midterm.nguyenthailoc.view.StaffAdapter;
import com.midterm.nguyenthailoc.viewmodel.StaffApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private StaffApiService staffApiService;
    private ArrayList<Staff> stafflist;
    private StaffAdapter staffAdapter;
    private RecyclerView rvstaff;
    private StaffDao staffDao;
    private StaffDatabase staffDatabase;
    private MyModel myModel;
    private ActivityMainBinding binding;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_id);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here of search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == ""){
                     stafflist.clear();
                     stafflist.addAll(staffDao.getAll());
                     staffAdapter.notifyDataSetChanged();
                     rvstaff.setAdapter(staffAdapter);
                }
                else
                {
                    stafflist.clear();
                    stafflist.addAll(staffDao.findByDesc(newText));
                    staffAdapter.notifyDataSetChanged();
                    rvstaff.setAdapter(staffAdapter);

                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewroot = binding.getRoot();
        setContentView(viewroot);

        rvstaff = findViewById(R.id.rv_staff);

        stafflist = new ArrayList<>();
        staffAdapter = new StaffAdapter(stafflist,this);
        rvstaff.setAdapter(staffAdapter);

        staffDatabase = StaffDatabase.getInstance(this);
        staffDao = staffDatabase.roomDao();

        myModel = new ViewModelProvider(this).get(MyModel.class);
        myModel.getList().observe(this, new Observer<List<Staff>>() {
            @Override
            public void onChanged(List<Staff> staff) {
                if(stafflist.equals(staff) == false){
                    stafflist.clear();
                    for(int i = 0;i < staff.size() ; i++)
                    {
                        stafflist.add(staff.get(i));
                    }
                    staffAdapter.notifyDataSetChanged();
                }

            }
        });

        staffApiService = StaffApiService.getInstance(this);
        staffApiService.getStaffs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Staff>>() {
                    @Override
                    public void onSuccess(@NonNull List<Staff> staff) {
//                        Toast.makeText(MainActivity.this, "Call api success", Toast.LENGTH_SHORT).show();
                        myModel.AddResult(staff);
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                for(int i = 0 ; i < staff.size();i++){
                                    if(staffDao.GetDogByID(staff.get(i).getId()) == null) {
                                        staffDao.insertAll(staff.get(i));
                                    }
                                }
                            }
                        });
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(MainActivity.this,"Call api Error",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}