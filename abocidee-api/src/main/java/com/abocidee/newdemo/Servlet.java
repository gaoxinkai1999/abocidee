package com.abocidee.newdemo;

import com.abocidee.Dao.UnionDao;
import com.abocidee.Dao.UserDao;
import com.abocidee.Dao.tongjiDao;
import com.abocidee.Domain.Union;
import com.abocidee.Domain.User;
import com.abocidee.newdemo.tools.MyClient;
import com.abocidee.newdemo.tools.urls;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.exceptions.ForestHandlerException;
import com.dtflys.forest.exceptions.ForestRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class Servlet {
    @Autowired
    private MyClient myClient;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UnionDao unionDao;
    @Autowired
    private tongjiDao tongjiDao;

    public JSONObject move(String username, String 目标公会name) {
        //准备响应json
        JSONObject jsonObject = new JSONObject();
        try {
//            用户执行阶段

            //获取当前用户实例
            User user = userDao.get(username);
//        System.out.println(user.getCookie());

            //获取用户当前公会
            JSONObject 用户信息 = myClient.get(urls.用户信息url(), user.getCookie());


//        System.out.println("用户信息" + 用户信息.toString());
            String unionName = 用户信息.getString("unionName");


            //如果当前没有公会
            if (unionName.equals("")) {
                //执行进入公会
                String token = unionDao.get(目标公会name).getToken();
                JSONObject post = myClient.post(urls.进入公会url(token), user.getCookie(), "YII_CSRF_TOKEN=9e22c8bb046e2dd4fe7c44c729c7cf3a12444703");
                jsonObject.put("result", "success");
                jsonObject.put("msg","转会成功");
                tongjiDao.update("退会");
                return jsonObject;
            } else {
                //如果当前有公会

                Union union = unionDao.get(unionName);
                if (union == null) {
                    jsonObject.put("result", "fail");
                    return jsonObject;
                } else {
                    //执行申请退会
                    String data = "union_id=" + union.getUnionid() + "&exitReason%5B%5D=3&YII_CSRF_TOKEN=9e22c8bb046e2dd4fe7c44c729c7cf3a12444703";
                    JSONObject post = myClient.post(urls.申请退会url(), user.getCookie(), data);
                    //执行批准退会
                    String data1 = "union_id=" + union.getUnionid() + "&user_id=" + user.getUserid() + "&op=allowExit&YII_CSRF_TOKEN=9e22c8bb046e2dd4fe7c44c729c7cf3a12444703";
                    try {
                        JSONObject post1 = myClient.post(urls.批准退会url(), union.getCookie(), data1);
                    } catch (ForestHandlerException exception) {
                        //接收公会cookie错误异常
                        jsonObject.put("result", "fail");
                        jsonObject.put("msg", "公会cookie失效,请联系作者");
                        return jsonObject;
                    }

                    //执行进入公会
                    String token = unionDao.get(目标公会name).getToken();
                    JSONObject post1 = myClient.post(urls.进入公会url(token), user.getCookie(), "YII_CSRF_TOKEN=9e22c8bb046e2dd4fe7c44c729c7cf3a12444703");
                    jsonObject.put("result", "success");
                    tongjiDao.update("退会");
                    return jsonObject;

                }

            }

        } catch (ForestHandlerException ex) {
            //接收cookie错误异常
            log.error(ex.toString());
            String msg="用户cookie失效";
            log.error(msg);
            jsonObject.put("result", "fail");
            jsonObject.put("msg", msg);
            return jsonObject;
        } catch (ForestRuntimeException ex) {
            //接收请求超时错误或其他Forest错误
            log.error(ex.toString());
            String msg="请求超时";
            log.error(msg);
            jsonObject.put("result", "fail");
            jsonObject.put("msg", msg);
            return jsonObject;
        } catch (Exception ex) {
            log.error(ex.toString());
            String msg="未知异常,联系作者";
            log.error(msg);
            jsonObject.put("result", "fail");
            jsonObject.put("msg", msg);
            return jsonObject;
        }
    }

    public JSONObject select(String unionname, String markid) {
        JSONObject response = new JSONObject();
        try {
            Union union = unionDao.get(unionname);
            markid = markid.trim();
            //查询到原始审核进度
            JSONObject jsonObject1 = myClient.get(urls.审核包进度url(union.getUnionid(), markid), union.getCookie());
            //取出用户进度数组
            JSONArray records1 = jsonObject1.getJSONArray("records");

            response.put("result", "success");
            response.put("records", records1);
            tongjiDao.update("查询");
            return response;

        } catch (ForestHandlerException exception) {
            //捕获cookie错误异常
            log.error(exception.toString());
            String msg="公会cookie失效";
            response.put("result", "fail");
            response.put("msg", msg);
            return response;
        }catch (ForestRuntimeException ex) {
            //接收请求超时错误或其他Forest错误
            log.error(ex.toString());
            String msg="请求超时";
            log.error(msg);
            response.put("result", "fail");
            response.put("msg", msg);
            return response;
        } catch (Exception ex) {
            log.error(ex.toString());
            String msg="未知异常,联系作者";
            log.error(msg);
            response.put("result", "fail");
            response.put("msg", msg);
            return response;
        }
    }

    public JSONObject useradd(String cookie) {
        try {
            //获取用户当前公会
            JSONObject jsonObject = myClient.get(urls.用户信息url(), cookie);
            String userName = jsonObject.getString("userName");
            String userId = jsonObject.getString("userId");
            User user = userDao.get(userName);
            if (user != null) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("result", "fail");
                jsonObject1.put("msg", "用户已存在");
                return jsonObject1;
            } else {
                User user1 = new User();
                user1.setUserid(userId);
                user1.setUsername(userName);
                user1.setCookie(cookie);
                userDao.add(user1);
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("result", "success");
                jsonObject1.put("username", userName);
                return jsonObject1;
            }

        } catch (Exception a) {

        }
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("result", "fail");
        jsonObject1.put("msg", "cookie输入有误,无法获得用户信息，请重新输入");
        return jsonObject1;
    }

    @Async("doSomethingExecutor")
    public void 查询质检包(Union union) {
        System.out.println("查询质检包开始");
        System.out.println(union.getUnionname());
        //获取所有包项目id
        JSONObject jsonObject = myClient.get(urls.标注项目url(), union.getCookie());
        JSONObject data = jsonObject.getJSONObject("data");
        //项目数量
        String recordNum = data.getString("recordNum");
        if (recordNum.equals("0")) {
            System.out.println(union.getUnionname() + "" + "没有标注项目");
            return;
        } else {
            //定义一个hashmap key是项目名，value是项目id
            HashMap<String, String> 所有项目 = new HashMap<>();

            JSONArray records = data.getJSONArray("records");
            for (Object record : records) {
                JSONObject jsonObject1 = JSONObject.parseObject(record.toString());
                所有项目.put(jsonObject1.getString("name"), jsonObject1.getString("id"));
            }
            //查询每个项目的质检任务
            //定义一个Hashmap key是任务名 value是任务id
            HashMap<String, String> 质检任务 = new HashMap<>();

            for (String value : 所有项目.values()) {
                JSONObject jsonObject1 = myClient.get(urls.标注项目质检任务url(value), union.getCookie());
                //解析json获取质检任务任务id
                JSONObject data1 = jsonObject1.getJSONObject("data");
                //判断题量
                if (data1.getString("recordNum").equals("0")) {
                    continue;
                } else {
                    //取出所有任务
                    JSONArray records1 = data1.getJSONArray("records");
                    for (Object o : records1) {
                        JSONObject jsonObject2 = JSONObject.parseObject(o.toString());
                        //判断是否是答题
                        if (jsonObject2.getString("responseStatus").equals("doing")) {
                            System.out.println(union.getUnionname() + "的答题中题包:" + jsonObject2.toString());
                        }
                        //判断是否是打回包
                        if (jsonObject2.getString("responseStatus").equals("pushed_back")) {
                            System.out.println(jsonObject2.toString());
                        }
                    }
                }

            }
        }


    }

}
