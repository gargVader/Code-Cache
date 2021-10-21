package com.example.codechefeventsapp.data;


import java.util.HashMap;
import java.util.Map;

public final class Constants {

    public static final String[] sites = {
            "CodeForces",
            "CodeChef",
            "AtCoder",
            "LeetCode",
            "HackerRank",
            "HackerEarth",
            "Kick Start",
            "TopCoder"
    };

    public static final Map<String, Boolean> getDefault = new HashMap<String, Boolean>() {{
        put(sites[0], true);
        put(sites[1], true);
        put(sites[2], true);
        put(sites[3], true);
        put(sites[4], false);
        put(sites[5], false);
        put(sites[6], false);
        put(sites[7], false);
    }};


}
