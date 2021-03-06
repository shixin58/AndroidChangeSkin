package com.zhy.skinchangenow;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhy.changeskin.SkinManager;
import com.zhy.changeskin.callback.ISkinChangingCallback;
import com.zhy.skinchangenow.sample.MyApplication;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class LeftMenuFragment extends Fragment implements View.OnClickListener {
    private String mSkinPkgPath = MyApplication.INSTANCE.getExternalFilesDir(null) + File.separator + "skin_plugin.apk";
    private View mInnerChange01;
    private View mInnerChange02;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_left_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInnerChange01 = view.findViewById(R.id.id_rl_innerchange01);
        mInnerChange01.setOnClickListener(this);

        mInnerChange02 = view.findViewById(R.id.id_rl_innerchange02);
        mInnerChange02.setOnClickListener(this);

        view.findViewById(R.id.id_restore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.zhy.changeskin.SkinManager.getInstance().removeAnySkin();
            }
        });

        view.findViewById(R.id.id_changeskin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().changeSkin(mSkinPkgPath, "com.imooc.skin_plugin", new ISkinChangingCallback() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getActivity(), "换肤失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(getActivity(), "换肤成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(@NotNull View v) {
        switch (v.getId()) {
            case R.id.id_rl_innerchange01:
                SkinManager.getInstance().changeSkin("red");
                break;
            case R.id.id_rl_innerchange02:
                SkinManager.getInstance().changeSkin("green");
                break;
        }
    }
}
