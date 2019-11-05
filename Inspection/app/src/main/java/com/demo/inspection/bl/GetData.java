package com.demo.inspection.bl;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection.bl
 * @ClassName: GetData
 * @Description: 异步调用封装
 * @Author: 王欢
 * @CreateDate: 2019/11/2 15:58
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/2 15:58
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public abstract  class GetData {
    MyHttp myHttp = new MyHttp();
    List<Map<String, String>> list = null;
    ReqParam req;

    public GetData(ReqParam req) {
        this.req = req;
        getList();
    }

    public void getList(){
        myHttp.requestOkHttpAsync(req, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(ComDef.TAG, "请求okHttp失败:" + e);
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody rb = response.body();
                //直接获得应答字符串
                String result = rb.string();
                Log.i(ComDef.TAG, "原始返回:" + result);
                try {
                    dealResult(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    rb.close();
                }
            }
        });
    }
    public abstract  void  dealResult(String result) throws JSONException ;
}
