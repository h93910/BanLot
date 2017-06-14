package com.ban.banlot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ban.banlot.tool.DataTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Random;

public class ScrollingActivity extends AppCompatActivity {

    private FloatingActionButton lot;
    private FloatingActionButton rollBack;
    private CollapsingToolbarLayout toolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        initList();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        lot = (FloatingActionButton) findViewById(R.id.lot);
        lot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lot();
            }
        });

        rollBack = (FloatingActionButton) findViewById(R.id.roll_back);
        rollBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollBack();
            }
        });

        juggButtonHideAndShow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            initData();
            mLotAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private ArrayList<String> 未选项;
    private ArrayList<String> 已选项;
    private ArrayList<String> mAllData;//总项
    private DataTool mDataTool;

    private void initData() {
        if (mDataTool == null) {
            mDataTool = new DataTool(this);
            未选项 = new ArrayList<>();
        } else {
            未选项.clear();
        }
        String s = mDataTool.getText();
        if (!TextUtils.isEmpty(s)) {
            String[] date = mDataTool.getText().split("\\n");
            Gson gson = new Gson();
            mAllData = gson.fromJson(date[0],
                    new TypeToken<ArrayList<String>>() {
                    }.getType());
            ArrayList<String> 已选 = gson.fromJson(date[1],
                    new TypeToken<ArrayList<String>>() {
                    }.getType());
            if (已选项 == null) {
                已选项 = new ArrayList<>();
            } else {
                已选项.clear();
            }
            已选项.addAll(已选);
            if (mAllData != null) {
                for (String s1 : mAllData) {
                    if (!已选项.contains(s1)) {
                        未选项.add(s1);
                    }
                }
            }
        } else {
            已选项 = new ArrayList<>();
            未选项 = new ArrayList<>();
        }
    }


    private LotRecyclerAdapter mLotAdapter;
    private RecyclerView listView;

    private void initList() {
        mLotAdapter = new LotRecyclerAdapter(this, 已选项);
        listView = (RecyclerView) findViewById(R.id.list);
        listView.setAdapter(mLotAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(this, MainActivity.class), 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 抽签
     */
    private void lot() {
        int randomNumber = (int) (Math.random() * 未选项.size());
        toolbarLayout.setTitle(未选项.get(randomNumber));
        已选项.add(未选项.get(randomNumber));
        未选项.remove(randomNumber);
        saveData();
    }

    /**
     * 回滚
     */
    private void rollBack() {
        未选项.add(已选项.get(已选项.size() - 1));
        已选项.remove(已选项.size() - 1);
        saveData();
    }

    /**
     * 保存数据
     */
    private void saveData() {
        mDataTool.save(mAllData, 已选项);
        juggButtonHideAndShow();
        mLotAdapter.notifyDataSetChanged();
    }

    private void juggButtonHideAndShow() {
        if (未选项 != null && 未选项.size() != 0) {
            lot.setVisibility(View.VISIBLE);
        } else {
            lot.setVisibility(View.INVISIBLE);
        }

        if (已选项 != null && 已选项.size() != 0) {
            rollBack.setVisibility(View.VISIBLE);
        } else {
            rollBack.setVisibility(View.INVISIBLE);
        }
    }

}
