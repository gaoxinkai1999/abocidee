package com.abocidee.servlet.tools;

public class urls {
    public static String 用户信息url() {
        return "https://test.baidu.com/crowdtest/user/getUserInfo";
    }

    public static String 标注项目url() {
        return "https://test.baidu.com/mark/taskList/markProjects?_page=1&_limit=8&_order=-&authStatus=doing&name=";
    }

    public static String 审核项目url() {
        return "https://test.baidu.com/mark/taskList/verifyProjects?_page=1&_limit=8&_order=-&authStatus=doing&name=";
    }

    public static String 标注项目质检任务url(String 项目id) {
        return "https://test.baidu.com/mark/taskList/qualityTasks?_page=1&_limit=8&_order=id&projectId=" + 项目id + "&type=quality";
    }

    public static String 任务质检信息url() {
        return "https://test.baidu.com/mark/qualityTest/getCheckPageList";
    }

    public static final String 公会首页url() {
        return "https://test.baidu.com/crowdtest/n/union/index";
    }

    public static final String 申请退会url() {
        return "https://test.baidu.com/crowdtest/n/union/exitUnion";
    }

    public static final String 批准退会url() {
        return "https://test.baidu.com/crowdtest/n/union/opUnionUser";
    }

    public static final String 进入公会url(String token) {
        return "https://test.baidu.com/crowdtest/union/fastJoin?token=" + token;
    }

    public static final String 审核包查询url(String unionid, String name) {
        return "https://test.baidu.com/mark/unionProject/getTaskList?union_id=" + unionid + "&status=null&union_task_status=null&location=0&task_name=" +
                name + "&limit=10";
    }

    public static final String 审核包进度url(String unionid, String mark_id) {
        return "https://test.baidu.com/mark/unionProject/getUnionTaskProcess/union_id/" + unionid + "/mark_id/" + mark_id + "/location/0/limit/100/tag//type/verify";
    }
    //post
    public static final String 移除成员url(){
        return "https://test.baidu.com/crowdtest/n/union/opUnionUser";
    }

}
