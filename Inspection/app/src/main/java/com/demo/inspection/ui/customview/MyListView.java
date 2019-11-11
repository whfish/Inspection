package com.demo.inspection.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.inspection.R;
import com.demo.inspection.utils.ComDef;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection.ui
 * @ClassName: MyListView
 * @Description: java类作用描述
 * @Author: 王欢
 * @CreateDate: 2019/11/7 15:44
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/7 15:44
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MyListView extends ListView implements AbsListView.OnScrollListener {
    List<Map<String, String>> equList = new ArrayList<>();
    View footer;
    boolean flag = false;//是否滑动到底部
    boolean isLoading = false; //是否已经发送查询
    boolean isqueryfinished = false;//是否已查询到底页
    ILoadListener loadListener;
    SimpleAdapter myAdapter;
    SimpleAdapter.ViewBinder viewBinder = null;

    public MyListView(Context context) {
        super(context);
        initView(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public List<Map<String, String>> getEquList() {
        return equList;
    }

    public SimpleAdapter getMyAdapter() {
        return myAdapter;
    }


    private void initView(Context context) {
        String[] from = {"sysname", "ip", "score", "id"};  //决定提取哪些值来生成列表项
        int[] to = {R.id.textName, R.id.textIP, R.id.textState, R.id.tv_disabled}; //对应到xml里的名字
        myAdapter = new SimpleAdapter(context, equList, R.layout.activity_equipmentlist, from, to);
        viewBinder = new MyViewBinder();
        myAdapter.setViewBinder(viewBinder);
        setAdapter(myAdapter);
        footer = LayoutInflater.from(context).inflate(R.layout.footer, null);
        footer.findViewById(R.id.loading_layout).setVisibility(GONE);//先关闭加载提示
        footer.findViewById(R.id.tv_loaded).setVisibility(GONE);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (flag && (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) && (!isLoading) && (!isqueryfinished)) {
            Log.i(ComDef.TAG, "已滑到底部");
            showLoading();
            //新加载数量与承诺不一致则表示已经查询完记录，不继续加载
                loadListener.onLoad();

        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        flag = (firstVisibleItem + visibleItemCount == totalItemCount);
    }

    public void showLoading() {
        isLoading = true;
        footer.findViewById(R.id.loading_layout).setVisibility(VISIBLE);
    }

    public void loadComplete(int result) {
        /**
         * @method loadComplete
         * @description 加载结束
         * @date: 2019/11/03 15:15
         * @author: 王欢
         * @param [result] //本次加载数据条数
         * @return void
         */
        isLoading = false;
        footer.findViewById(R.id.loading_layout).setVisibility(GONE);
        //加载数据不等于最大值证明已没有数据可供加载
        if (result != ComDef.QRY_NUM) {
            isqueryfinished = true;
            footer.findViewById(R.id.tv_loaded).setVisibility(VISIBLE);
        } else {
            isqueryfinished = false;
            footer.findViewById(R.id.tv_loaded).setVisibility(GONE);
        }
    }

    public void setInterface(ILoadListener iListener) {
        this.loadListener = iListener;
    }

    //获取数据并展示
    public interface ILoadListener {
        public void onLoad();
    }

    public void clearflag() {
        /**
         * @method clearflag
         * @description 清理各项标志
         * @date: 2019/10/18 16:13
         * @author: 王欢
         * @param []
         * @return void
         */
        isLoading = false;
        isqueryfinished = false;
        footer.findViewById(R.id.tv_loaded).setVisibility(GONE);
        footer.findViewById(R.id.loading_layout).setVisibility(GONE);
    }
}
