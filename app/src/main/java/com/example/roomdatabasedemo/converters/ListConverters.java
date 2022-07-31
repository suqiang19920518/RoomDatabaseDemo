package com.example.roomdatabasedemo.converters;

import android.text.TextUtils;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListConverters {

    /**
     * 必须是public
     */
    @TypeConverter
    public String listToString(List<String> names) {
        if (names == null || names.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < names.size(); i++) {
            sb.append(",").append(names.get(i));
        }
        return sb.toString().substring(1);
    }

    /**
     * 必须是public
     */
    @TypeConverter
    public List<String> stringToList(String names) {
        if (TextUtils.isEmpty(names)) {
            return new ArrayList<>();
        }
        return Arrays.asList(names.split(","));
    }

}
