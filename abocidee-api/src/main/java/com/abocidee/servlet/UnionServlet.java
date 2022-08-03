package com.abocidee.servlet;

import com.abocidee.Dao.UnionDao;
import com.abocidee.Domain.Union;
import com.abocidee.servlet.tools.MyClient;
import com.abocidee.servlet.tools.MyError;
import com.abocidee.servlet.tools.MyJson;
import com.abocidee.servlet.tools.urls;
import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.exceptions.ForestNetworkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UnionServlet {
    @Autowired
    private UnionDao unionDao;
    @Autowired
    private MyClient myClient;

    public MyJson add(String unionId, String cookie) {
        try {
            //查询公会信息，不需要cookie
            String url = "https://test.baidu.com/crowdtest/union/detail/id/" + unionId;
            JSONObject jsonObject = myClient.get(url);
            String name = jsonObject.getString("name");
            String adm_user_name = jsonObject.getString("adm_user_name");
            if (unionDao.get(name) != null) {
                return MyJson.error(MyError.公会已存在);
            } else {
                //查询cookie用户信息
                JSONObject userjson = myClient.get(urls.用户信息url(), cookie);
                String userName = userjson.getString("userName");
                //如果该用户是公会会长,则获取公会token
                if (userName.equals(adm_user_name)) {
                    String url1 = "https://test.baidu.com/crowdtest/union/getFastJoinUrl?union_id=" + unionId;
                    JSONObject jsonObject1 = myClient.get(url1, cookie);
                    JSONObject data = jsonObject1.getJSONObject("data");
                    String FastJoinUrl = data.getString("url");
                    String token = FastJoinUrl.split("fjToken=")[1];
                    Union union = new Union();
                    union.setCookie(cookie);
                    union.setUnionid(unionId);
                    union.setUnionname(name);
                    union.setToken(token);
                    unionDao.add(union);
                    return MyJson.success();

                } else {
                    return MyJson.error(MyError.cookie与公会不匹配);
                }
            }
        } catch (ForestNetworkException ex) {
            return MyJson.error(MyError.cookie无效);
        }


    }

    public MyJson set(String unionname, String cookie) {
        try {
            Union union = unionDao.get(unionname);
            String unionid = union.getUnionid();
            //查询公会信息，不需要cookie
            String url = "https://test.baidu.com/crowdtest/union/detail/id/" + unionid;
            JSONObject jsonObject = myClient.get(url);
            String adm_user_name = jsonObject.getString("adm_user_name");
            //查询cookie用户信息
            JSONObject userjson = myClient.get(urls.用户信息url(), cookie);
            String userName = userjson.getString("userName");
            //如果该用户是公会会长,则更新公会cookie
            if (userName.equals(adm_user_name)) {
                unionDao.setCookie(unionname,cookie);
                return MyJson.success();
            } else {
                return MyJson.error(MyError.cookie与公会不匹配);
            }
        } catch (ForestNetworkException ex) {
            return MyJson.error(MyError.cookie无效);
        }
    }

    public MyJson getAll() {
        ArrayList<Union> all = unionDao.getAll();
        ArrayList<String> unionnames = new ArrayList<>();
        for (Union union : all) {
            unionnames.add(union.getUnionname());
        }
        return MyJson.success(unionnames);

    }
}
