package com.ruitukeji.novate.adapter;

import android.content.Context;

import com.ruitukeji.novate.R;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 我的  我的钱包 账户明细  适配器
 * Created by Admin on 2017/9/8.
 */

public class AccountDetailsAdapter extends BGAAdapterViewAdapter<String> {


    public AccountDetailsAdapter(Context context) {
        super(context, R.layout.item_accountdetails);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper) {
        super.setItemChildListener(helper);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String model) {

        /**
         * 明细类型
         */
        helper.setText(R.id.tv_test, model);

    }
}
