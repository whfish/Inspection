package com.demo.inspection.bl;

import java.util.HashMap;

/**
 * @Path: com.demo.inspection.bl.ReqParam
 * @Description: 后端服务器请求封装
 * @Author: 王欢
 * @CreateDate: 2019/10/30 9:55
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/30 9:55
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ReqParam {

    private String url="";//请求地址
    private HashMap<String,String> map =null;

    public String getUrl() {
        return url;
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }
}
