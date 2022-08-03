package com.abocidee.servlet;

import com.abocidee.Dao.UnionDao;
import com.abocidee.Dao.UserDao;
import com.abocidee.Dao.tongjiDao;
import com.abocidee.Domain.Union;
import com.abocidee.Domain.User;
import com.abocidee.servlet.tools.MyClient;
import com.abocidee.servlet.tools.MyError;
import com.abocidee.servlet.tools.MyJson;
import com.abocidee.servlet.tools.urls;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.exceptions.ForestNetworkException;
import com.dtflys.forest.exceptions.ForestRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class ActionServlet {
    @Autowired
    private MyClient myClient;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UnionDao unionDao;
    @Autowired
    private tongjiDao tongjiDao;

    public MyJson move(String username, String 目标公会name, Boolean onlyexit) {
        try {
//            用户执行阶段
            //获取当前用户实例
            User user = userDao.get(username);
            //获取用户当前公会
            JSONObject 用户信息 = myClient.get(urls.用户信息url(), user.getCookie());
            String unionName = 用户信息.getString("unionName");
            log.info("第一次验证当前公会:" + unionName);
            //如果当前没有公会
            if (unionName.isEmpty()) {
                //执行第二次验证
                String url = "https://test.baidu.com/crowdtest/n/union/index";
                String html = myClient.Stringget(url, user.getCookie());
                Document doc = Jsoup.parse(html);
                Elements elementsByClass = doc.getElementsByClass("union-sidebar-user-name");
                String unionName1 = elementsByClass.text();
                log.info("第二次验证当前公会:" + unionName1);
                if (unionName1.isEmpty()) {
                    if (onlyexit) {
                        return MyJson.success();
                    }
                    //执行进入公会
                    log.info("执行进入公会");
                    String token = unionDao.get(目标公会name).getToken();
                    JSONObject post = myClient.post(urls.进入公会url(token), user.getCookie(), "YII_CSRF_TOKEN=9e22c8bb046e2dd4fe7c44c729c7cf3a12444703");
                    tongjiDao.update("退会");
                    return MyJson.success();
                }
            }
            //如果当前有公会
            Union union = unionDao.get(unionName);
            if (union == null) {
                return MyJson.error(MyError.当前公会未接入);
            } else {
                //执行申请退会
                log.info("执行申请退会");
                String data = "union_id=" + union.getUnionid() + "&exitReason%5B%5D=3&YII_CSRF_TOKEN=9e22c8bb046e2dd4fe7c44c729c7cf3a12444703";
                JSONObject post = myClient.post(urls.申请退会url(), user.getCookie(), data);
                //执行批准退会
                log.info("执行批准退会");
                String data1 = "union_id=" + union.getUnionid() + "&user_id=" + user.getUserid() + "&op=allowExit&YII_CSRF_TOKEN=9e22c8bb046e2dd4fe7c44c729c7cf3a12444703";
                try {
                    JSONObject post1 = myClient.post(urls.批准退会url(), union.getCookie(), data1);
                } catch (ForestNetworkException ex) {
                    //接收公会cookie错误异常
                    log.error(ex.getMessage());
                    return MyJson.error(MyError.公会cookie失效);
                }
                if (onlyexit) {
                    return MyJson.success();
                }
                //执行进入公会
                log.info("执行进入公会");
                String token = unionDao.get(目标公会name).getToken();
                JSONObject post1 = myClient.post(urls.进入公会url(token), user.getCookie(), "YII_CSRF_TOKEN=9e22c8bb046e2dd4fe7c44c729c7cf3a12444703");
                return MyJson.success();


            }
        } catch (
                ForestNetworkException ex) {
            //用户cookie错误异常
            log.error(ex.getMessage());
            return MyJson.error(MyError.用户cookie失效);
        } catch (
                ForestRuntimeException ex) {
            //接收请求超时错误或其他Forest错误
            log.error(ex.getMessage());
            return MyJson.error(MyError.超时);
        } catch (
                Exception ex) {
            log.error(ex.getMessage());
            return MyJson.error(MyError.未知错误);
        }

    }

    public MyJson select(String unionname, String markid) {
        JSONObject json = new JSONObject();
        try {
            Union union = unionDao.get(unionname);
            markid = markid.trim();
            //查询到原始审核进度
            JSONObject jsonObject1 = myClient.get(urls.审核包进度url(union.getUnionid(), markid), union.getCookie());
            //取出用户进度数组
            JSONArray records1 = jsonObject1.getJSONArray("records");
            tongjiDao.update("查询");
            return MyJson.success(records1);

        } catch (ForestNetworkException exception) {
            //捕获cookie错误异常
            log.error(exception.getMessage());
            return MyJson.error(MyError.公会cookie失效);
        } catch (ForestRuntimeException ex) {
            //超时异常
            log.error(ex.getMessage());
            return MyJson.error(MyError.超时);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return MyJson.error(MyError.未知错误);
        }
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
