package com.centres.area.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centres.area.entity.AreaInfoEntity;
import com.sun.deploy.ref.AppModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * @program: init_area
 * @description:
 * @author: CentreS
 * @create: 2020-03-02 14:29:10
 **/
@Controller
public class AreaController extends BaseController {

    /**
     * 功能说明：更新高德的省市区到我们的省市区表里
     *
     * @return model
     * @author MrDoubi 2019-07-08 10:12:30
     */
    @RequestMapping(value = "init", method = RequestMethod.POST)
    @ResponseBody
    public String updateInitGaoDe() {
        //慎用！
        //删除省市区表原来的记录
        areaInfoMapper.deleteAll();
        String gaodeKey = "a0650856e88f97d602fb8765da385ee2";
        //一个http请求  这个方法放在羡慕
        String res = AreaController.sendPost("http://restapi.amap.com/v3/config/district", "key=" + gaodeKey + "&subdistrict=3");
        JSONObject json = JSONObject.parseObject(res);
        int count = 0;
        if (json.get("status").equals("1")) {
            //国家 暂时只有中国
            JSONArray districts = json.getJSONArray("districts");
            for (int i = 0; i < districts.size(); i++) {
                JSONObject district = districts.getJSONObject(i);
                //省份
                JSONArray provinces = district.getJSONArray("districts");
                for (int j = 0; j < provinces.size(); j++) {
                    JSONObject province = provinces.getJSONObject(j);
                    System.out.println(
                            "province ::::::  ====  name:" + province.getString("name") +
                                    "   center:" + province.getString("center") +
                                    "   adcode:" + province.getString("adcode")
                    );
                    AreaInfoEntity areaInfoEntity = new AreaInfoEntity();
                    areaInfoEntity.setName(province.getString("name"));
                    areaInfoEntity.setCode(province.getString("adcode"));
                    areaInfoEntity.setParentId(0);
                    areaInfoEntity.setType(0);
                    areaInfoMapper.insertSelective(areaInfoEntity);
                    Integer provinceId = areaInfoEntity.getId();
                    JSONArray citys = province.getJSONArray("districts");
                    for (int k = 0; k < citys.size(); k++) {
                        JSONObject city = citys.getJSONObject(k);
                        System.out.println(
                                "city ::::::  ====  name:" + city.getString("name") +
                                        "   center:" + city.getString("center") +
                                        "   adcode:" + city.getString("adcode")
                        );
                        AreaInfoEntity areaInfoEntityCity = new AreaInfoEntity();
                        areaInfoEntityCity.setName(city.getString("name"));
                        areaInfoEntityCity.setCode(city.getString("adcode"));
                        areaInfoEntityCity.setParentId(provinceId);
                        areaInfoEntityCity.setType(1);
                        areaInfoMapper.insertSelective(areaInfoEntityCity);
                        Integer cityId = areaInfoEntityCity.getId();
                        JSONArray areas = city.getJSONArray("districts");
                        for (int l = 0; l < areas.size(); l++) {
                            JSONObject area = areas.getJSONObject(l);
                            count++;
                            System.out.println(
                                    "area ::::::  ====  name:" + area.getString("name") +
                                            "   center:" + area.getString("center") +
                                            "   adcode:" + area.getString("adcode")
                            );
                            AreaInfoEntity areaInfoEntityArea = new AreaInfoEntity();
                            areaInfoEntityArea.setName(area.getString("name"));
                            areaInfoEntityArea.setCode(area.getString("adcode"));
                            areaInfoEntityArea.setParentId(cityId);
                            areaInfoEntityArea.setType(2);
                            areaInfoMapper.insertSelective(areaInfoEntityArea);
                        }
                    }
                }
            }
            System.out.println("获取成功 共" + count + "条数据");
            return "success";
        } else {
            System.err.println(json.get("info"));
            return "false";
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            String urlNameString = url + "?" + param;
            logger.info("sendPost - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            logger.info("recv - {}", result);
        } catch (SocketTimeoutException e) {
            logger.error("调用HttpUtils.sendPost SocketTimeoutException, url=" + url + ",param=" + param, e.getMessage());
        } catch (IOException e) {
            logger.error("调用HttpUtils.sendPost IOException, url=" + url + ",param=" + param, e.getMessage());
        } catch (Exception e) {
            logger.error("调用HttpsUtil.sendPost Exception, url=" + url + ",param=" + param, e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                logger.error("调用in.close Exception, url=" + url + ",param=" + param, ex.getMessage());
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        AreaController c = new AreaController();
        c.updateInitGaoDe();
    }
}
