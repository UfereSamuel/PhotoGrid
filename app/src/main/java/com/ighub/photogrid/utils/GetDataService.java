package com.ighub.photogrid.utils;


import com.ighub.photogrid.model.PhotoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/photos")
    Call<List<PhotoModel>> getAllPhotos();
}
