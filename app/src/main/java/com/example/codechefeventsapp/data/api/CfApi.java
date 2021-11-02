package com.example.codechefeventsapp.data.api;

import com.example.codechefeventsapp.data.models.cf.CfUserContest;
import com.example.codechefeventsapp.data.models.cf.CfUserSubmissions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CfApi {

    public static final String URL = "https://codeforces.com/api/";

    /**
     * Returns rating history of the specified user.
     * https://codeforces.com/api/user.rating?handle=Fefer_Ivan
     */
    @GET("user.rating")
    Call<CfUserContest> getUserContests(@Query("handle") String handle);

    /**
     * Returns submissions of specified user
     * @param handle Codeforces user handle
     * @param from 	1-based index of the first submission to return.
     * @param count Number of returned submissions.
     * https://codeforces.com/api/user.status?handle=Fefer_Ivan&from=1&count=10
     */
    @GET("user.status")
    Call<CfUserSubmissions> getUserSubmissions(@Query("handle") String handle,
                                               @Query("from") int from,
                                               @Query("count") int count);

}
