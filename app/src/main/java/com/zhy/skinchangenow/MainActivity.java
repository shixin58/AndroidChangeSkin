package com.zhy.skinchangenow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.zhy.changeskin.SkinManager;
import com.zhy.changeskin.callback.ISkinChangingCallback;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mListView;
    private String mSkinPkgPath = Environment.getExternalStorageDirectory() + File.separator + "skin_plugin.apk";
    private List<String> mDatas = new ArrayList<>(Arrays.asList("Activity", "Service", "Activity", "Service",
            "Activity", "Service", "Activity", "Service", "Activity", "Service", "Activity", "Service",
            "Activity", "Service", "Activity", "Service", "Activity", "Service", "Activity", "Service",
            "Activity", "Service", "Activity", "Service", "Activity", "Service", "Activity", "Service",
            "Activity", "Service", "Activity", "Service", "Activity", "Service", "Activity", "Service",
            "Activity", "Service", "Activity", "Service", "Activity", "Service", "Activity", "Service",
            "Activity", "Service", "Activity", "Service"));
    private ArrayAdapter mAdapter ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // apply发生在setContentView之后，通过post实现
        SkinManager.getInstance().register(this);
        setContentView(R.layout.activity_main);
        initView();
        initEvents();
    }

    private void initEvents() {

        mListView = findViewById(R.id.id_listview);
        mListView.setAdapter(mAdapter = new ArrayAdapter<String>(this, -1, mDatas) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, parent, false);
                }
                SkinManager.getInstance().injectSkin(convertView);
                TextView tv = convertView.findViewById(R.id.id_tv_title);
                tv.setText(getItem(position));
                return convertView;
            }
        });

        mDrawerLayout.addDrawerListener(new DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(@NotNull View drawerView, float slideOffset) {
                View content = mDrawerLayout.getChildAt(0);
                View menu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT")) {

                    float leftScale = 1 - 0.3f * scale;

                    ViewHelper.setScaleX(menu, leftScale);
                    ViewHelper.setScaleY(menu, leftScale);
                    ViewHelper.setAlpha(menu, 0.6f + 0.4f * (1 - scale));

                    ViewHelper.setTranslationX(content, menu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(content, 0);
                    ViewHelper.setPivotY(content, content.getMeasuredHeight() / 2f);
                    ViewHelper.setScaleX(content, rightScale);
                    ViewHelper.setScaleY(content, rightScale);
                    content.invalidate();
                }
            }

            @Override
            public void onDrawerOpened(@NotNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NotNull View drawerView) {
            }
        });
    }

    private void initView() {
        mDrawerLayout = findViewById(R.id.id_drawerLayout);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.id_left_menu_container);
        if (fragment == null) {
            fm.beginTransaction().add(R.id.id_left_menu_container, new MenuLeftFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.id_action_plugin_skinchange:
                SkinManager.getInstance().changeSkin(mSkinPkgPath, "com.imooc.skin_plugin", new ISkinChangingCallback() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(MainActivity.this, "换肤失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, "换肤成功", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.id_action_remove_any_skin:
                SkinManager.getInstance().removeAnySkin();
                break;
            case R.id.id_action_notify_lv:
                for (int i = 0, n = mDatas.size(); i < n; i++) {
                    mDatas.set(i, mDatas.get(i) + " changed");
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.id_action_dynamic:
                Intent intent = new Intent(this,TestTagActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}
