package com.midterm.nguyenthailoc.viewmodel;

import com.midterm.nguyenthailoc.model.Staff;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface StaffApi {
    @GET("b/625039f3d20ace068f9580fb")
    public Single<List<Staff>> getStaffs();
}
