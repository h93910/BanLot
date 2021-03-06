package com.ban.banlot.tool;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Ban on 2017/5/10.
 */

public class DataTool {
    private Context context;
    private File file;

    public DataTool(Context context) {
        this.context = context;
        FileHelper helper = FileHelper.getInstance(context, "/Ban/BanLot");
        file = new File(helper.getAbsoluteFilePath("date"));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getText() {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(s + System.lineSeparator());
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private boolean setText(String s) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWritter = new FileWriter(file);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(s);
            bufferWritter.close();

            System.out.println("Done");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean save(ArrayList<String> mAllData, ArrayList<String> 已选项) {
        Gson gson = new Gson();
        String s = gson.toJson(mAllData) + "\n" + gson.toJson(已选项);
        return setText(s);
    }
}
