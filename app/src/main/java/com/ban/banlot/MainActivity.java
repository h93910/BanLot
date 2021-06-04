package com.ban.banlot;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.ban.banlot.tool.DataTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> save());
    }


    private EditText mAllEditText;
    private EditText mSelectEditText;
    private DataTool mDataTool;

    private void initView() {
        mDataTool = new DataTool(this);
        mAllEditText = (EditText) findViewById(R.id.all);
        mSelectEditText = (EditText) findViewById(R.id.selected);

        String s = mDataTool.getText();
        if (!TextUtils.isEmpty(s)) {
            String[] date = mDataTool.getText().split("\\n");
            Gson gson = new Gson();
            ArrayList<String> temp = gson.fromJson(date[0],
                    new TypeToken<ArrayList<String>>() {
                    }.getType());
            StringBuffer buffer = new StringBuffer();
            for (String s1 : temp) {
                buffer.append(s1 + "\n");
            }
            mAllEditText.setText(buffer.toString());
            temp = gson.fromJson(date[1],
                    new TypeToken<ArrayList<String>>() {
                    }.getType());
            buffer = new StringBuffer();
            for (String s1 : temp) {
                buffer.append(s1 + "\n");
            }
            mSelectEditText.setText(buffer.toString());
        }
    }

    private void save() {
        String alls = mAllEditText.getText().toString();
        ArrayList<String> a = new ArrayList<>();
        if (!TextUtils.isEmpty(alls)) {
            String[] all = alls.trim().split("\\n");
            for (String s : all) {
                a.add(s);
            }
        }
        String selects = mSelectEditText.getText().toString();
        ArrayList<String> b = new ArrayList<>();
        if (!TextUtils.isEmpty(selects)) {
            String[] selected = selects.trim().split("\\n");
            for (String s : selected) {
                b.add(s);
            }
        }

        if (mDataTool.save(a, b)) {
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
