package com.ban.banlot;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ban.banlot.adapter.LotRecyclerAdapter;
import com.ess.filepicker.FilePicker;
import com.ess.filepicker.model.EssFile;
import com.ess.filepicker.util.Const;
import com.ess.filepicker.util.FileUtils;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import java.util.ArrayList;
import java.util.LinkedList;

public class ScrollingActivity extends AppCompatActivity {
    private static final String TAG = "ScrollingActivity";
    private static final int REQUEST_CODE_CHOOSE = 13;
    private FloatingActionButton lot;
    private FloatingActionButton rollBack;
    private CollapsingToolbarLayout toolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        initList();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        lot = (FloatingActionButton) findViewById(R.id.lot);
        lot.setOnClickListener(view -> lot());

        rollBack = (FloatingActionButton) findViewById(R.id.roll_back);
        rollBack.setVisibility(View.INVISIBLE);
        rollBack.setOnClickListener(view -> rollBack());

        juggButtonHideAndShow();
    }

    private String fileName;
    private JsonArray mAllData;//原始数据
    private JsonArray mCheckData;//当前题库
    private LinkedList<String> mPassData = new LinkedList<>();//已抽过的
    private JsonElement mCurrentQuestion = JsonNull.INSTANCE;//当前的问题

    private void refreshDate() {
        String progress = fileName + " " + mCheckData.size() + "/" + mAllData.size();
        toolbarLayout.setTitle(progress);
        mLotAdapter.notifyDataSetChanged();
        juggButtonHideAndShow();
    }


    private LotRecyclerAdapter mLotAdapter;
    private RecyclerView listView;

    private void initList() {
        mLotAdapter = new LotRecyclerAdapter(this, mPassData);
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
            FilePicker.from(this)
                    .chooseForBrowser()
                    .setMaxCount(2)
                    .setFileTypes("json")
                    .requestCode(REQUEST_CODE_CHOOSE)
                    .start();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHOOSE) {
            ArrayList<EssFile> essFileList = data.getParcelableArrayListExtra(Const.EXTRA_RESULT_SELECTION);
            StringBuilder builder = new StringBuilder();
            for (EssFile file : essFileList) {
                builder.append(file.getMimeType()).append(" | ").append(file.getName()).append("\n\n");
            }
            readJsonFromFile(essFileList.get(0));
        }
    }

    /**
     * 从json文件中读取数据,准备好数据开始抽签
     *
     * @param f
     */
    private void readJsonFromFile(EssFile f) {
        fileName = f.getName().split("\\.")[0];

        String s = FileUtils.readText(f.getAbsolutePath());
        Gson gson = new Gson();
        mAllData = gson.fromJson(s, JsonArray.class);
        mCheckData = mAllData.deepCopy();
        refreshDate();
    }

    /**
     * 抽签
     */
    private void lot() {
        String show = "";
        if (mCurrentQuestion.isJsonArray()) {//如为列表,为连续的问题,单抽当前项
            JsonArray ja = mCurrentQuestion.getAsJsonArray();
            if (ja.size() == 0) {
                mCurrentQuestion = JsonNull.INSTANCE;
                lot();
                return;
            }
            show = ja.get(0).getAsString();
            ja.remove(0);
        } else if (mCheckData.size() != 0) {
            int randomNumber = (int) (Math.random() * mCheckData.size());
            mCurrentQuestion = mCheckData.get(randomNumber);
            mCheckData.remove(randomNumber);
            if (mCurrentQuestion.isJsonPrimitive()) {//为单项的
                show = mCurrentQuestion.getAsString();
            } else {
                lot();
                return;
            }
        } else {
            refreshDate();
            toolbarLayout.setTitle(getString(R.string.title_finish));
            return;
        }
        mPassData.addFirst(show);
        refreshDate();
    }

    /**
     * 回滚
     */
    private void rollBack() {
    }


    private void juggButtonHideAndShow() {
        if (mCheckData != null && mCheckData.size() != 0 || mCurrentQuestion.isJsonArray()) {
            lot.setVisibility(View.VISIBLE);
        } else {
            lot.setVisibility(View.INVISIBLE);
        }
//        if () {
//            rollBack.setVisibility(View.VISIBLE);
//        } else {
//            rollBack.setVisibility(View.INVISIBLE);
//        }
    }

}