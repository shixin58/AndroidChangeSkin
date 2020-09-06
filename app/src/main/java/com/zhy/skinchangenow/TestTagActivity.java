package com.zhy.skinchangenow;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.changeskin.SkinManager;

import org.jetbrains.annotations.NotNull;

public class TestTagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tag);

        SkinManager.getInstance().register(this);
    }

    public void addNewView(View view) {
        //建议通过xml inflater
        TextView tv = new TextView(this);
        tv.setTag("skin:item_text_color:textColor");
        tv.setTextColor(AppCompatResources.getColorStateList(this, R.color.item_text_color));
        tv.setText("dymaic add!");

        ((ViewGroup) findViewById(R.id.id_container)).addView(tv);
        SkinManager.getInstance().injectSkin(tv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_tag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
