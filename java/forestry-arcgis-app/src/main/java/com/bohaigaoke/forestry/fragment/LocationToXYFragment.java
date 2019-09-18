package com.bohaigaoke.forestry.fragment;

import java.util.Map;

import com.bohaigaoke.android.model.query.Rows;
import com.bohaigaoke.forestry.R;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 *
 * @author name
 */
public class LocationToXYFragment extends BaseFragment implements OnClickListener {

    View mView;
    private ImageButton mBtnMenu;
    private ImageButton mBtnBack;
    private ImageButton mBtnSearch;
    private ImageButton mBtnList;
    private ImageButton mBtnMap;

    private TextView mTxt_title_name_pop, mPopTextView;
    private PopupWindow mPopOrg; //图层列表下拉框
    private ListView mPop_layer_list_view;


    private TranslateAnimation mShowAnimation, mHiddenAnimation;
    private Context context;

    public LocationToXYFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_location_to_xy_popwindow, container, false);
        findView();
        initView();
        return mView;
    }

    private void findView() {

    }

    private void initView() {

    }

    private void showDefaultView() {

    }

    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        //orgMgr.getCurrentOrganization(handler);
    }

    private void initPopupWindow() {

    }

}
