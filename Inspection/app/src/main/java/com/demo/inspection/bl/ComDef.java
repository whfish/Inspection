package com.demo.inspection.bl;

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

    public static final String URL_PRE = "http://10.52.200.138:8082/";//服务器地址
    public static final String INTF_QUERYSTATIC = "queryStatic";//查询统计数据

    public static final String INTF_QUERYDEVICE = "queryDeviceInfo";//查询设备列表
    public static final String INTF_QUERYDEVICEDETAIL = "queryDeviceDetail";//查询设备详情

    public static final String INTF_QUERYSYS = "querySysInfoState";//查询系统列表
    public static final String INTF_QUERYSYSDETAIL  = "querySysInfoDetail";//查询系统详情

    public static final String INTF_QUERYADMIN  = "queryUserName";//查询账号
    public static final String INTF_UPDATEADMIN  = "modifyPasswd";//修改密码

    public static final String QUERY_ACCOUNT = "account";//查询日期

    public static final String QUERY_DATE = "datetime";//查询日期
    public static final String QUERY_IP = "ip";//查询服务器地址
    public static final String QUERY_INDEX = "id";//查询索引值
    public static final String QUERY_DEVINDEX = "devid";//查询系统索引值
    public static final String QUERY_SYSINDEX = "sysid";//查询设备索引值
    public static final String QUERY_STATE = "score";//状查询态
}
