package com.daliltanta.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.airbnb.lottie.L;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmedelshaer on 10/16/17.
 */

public class ActivityUtils {

    public final static String MAIN_ITEM_KEY ="MainItemKey";
    public final static String CATEGPRY_KEY="Category";
    public final static String STRING_KEY = "STRING_KEY";

    ArrayList<String> MainItem = new ArrayList<String>() {{
        add("");
    }};

    public static void addFragmentToActivity (FragmentManager fragmentManager,
                                              Fragment fragment, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }


}
