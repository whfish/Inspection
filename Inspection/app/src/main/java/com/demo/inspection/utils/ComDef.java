package com.demo.inspection.utils;

import android.graphics.Color;

/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection.bl
 * @ClassName: ComDef
 * @Description: java类作用描述
 * @Author: 王欢
 * @CreateDate: 2019/10/30 10:12
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/30 10:12
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface ComDef {

    public static final String TAG = "demolog";//日志标识
    public static final String DATEFORMAT="YYYY-MM-dd";//日期格式
    public static final int QRY_NUM = 10;//分页使用，最大查询条数

    public static final String URL_PRE = "http://10.52.200.138:8082/";//服务器地址
    public static final String INTF_QUERYSTATIC = "queryStatic";//查询统计数据

    public static final String INTF_QUERYDEVICE = "queryDeviceInfo";//查询设备列表
    public static final String INTF_QUERYDEVICEPAGE = "queryDeviceInfoPage";//查询设备列表(分页)
    public static final String INTF_QUERYDEVICEDETAIL = "queryDeviceDetail";//查询设备详情

    public static final String INTF_QUERYSYS = "querySysInfoState";//查询系统列表
    public static final String INTF_QUERYSYSDETAIL = "querySysInfoDetail";//查询系统详情

    public static final String INTF_QUERYADMIN = "queryUserName";//查询账号
    public static final String INTF_UPDATEADMIN = "modifyPasswd";//修改密码

    public static final String INTF_QUERYQRDD = "queryQRDeviceDetail";//查询设备近7日详情

    public static final String QUERY_ACCOUNT = "account";//查询日期

    public static final String QUERY_DATE = "datetime";//查询日期
    public static final String QUERY_IP = "ip";//查询服务器地址
    public static final String QUERY_INDEX = "id";//查询索引值
    public static final String QUERY_DEVINDEX = "devid";//查询系统索引值
    public static final String QUERY_SYSINDEX = "sysid";//查询设备索引值
    public static final String QUERY_STATE = "score";//状查询态
    public static final String MODIFY_PW = "password";//密码

    //系统信息增删改
    public static final String INTF_SYSADD = "addSysInfo";//新增系统
    public static final String INTF_SYSMOD = "modifySysInfo";//修改系统
    public static final String INTF_SYSDEL = "deleteSysInfo";//删除系统

    public static final String SYS_NAME ="sysname";
    public static final String SYS_DETIAL ="detial";//非必填
    public static final String SYS_LINKMAN ="linkman";//非必填
    public static final String SYS_PHONE ="phone";//非必填

    public static final String PAGENUM="pageNum";//分页页码，从1开始
    public static final String PAGESIZE="pageSize";//每次最多查询条数


    public static final String[] TITLE_NAME = {"状态信息", "设备列表", "系统列表", "我的"};
    public static final int[] MY_COLORS = {Color.GREEN, Color.YELLOW, Color.BLUE, Color.CYAN, Color.GRAY, Color.LTGRAY};
    public static final String[] XSTRS = new String[]{"1", "2", "3", "4", "5", "6", "7"};
    public static final int PRE_INDEX = 10010;
    public static final int REQUEST_CODE_CREATE = 0;
    public static final int REQUEST_CODE_MOD = 1;


    public int[] STATE_COLORS = {
            Color.WHITE,
            Color.rgb (76, 175, 80),
            Color.rgb (247, 201, 77),
            Color.rgb (228, 95, 95),
            Color.rgb (154, 183, 224)};


}





