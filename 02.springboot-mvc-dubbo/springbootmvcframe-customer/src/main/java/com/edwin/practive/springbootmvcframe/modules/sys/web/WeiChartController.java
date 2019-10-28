package com.edwin.practive.springbootmvcframe.modules.sys.web;

import com.edwin.practive.springbootmvcframe.core.properties.Config;
import com.edwin.practive.springbootmvcframe.core.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import weixin.popular.api.MenuAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLImageMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.TokenManager;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;


import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("wx")
public class WeiChartController extends BaseController {

    // 重复通知过滤
    private static ExpireKey expireKey = new DefaultExpireKey();

    @Autowired
    Config conf;

    @RequestMapping("sig")
    @ResponseBody
    public void sig(@RequestParam Map<String, String> param , HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("-------" + ToStringBuilder.reflectionToString(param));

        ServletInputStream inputStream = request.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();

        // 算出来的签名
        String signature = param.get("signature");
        String echostr = param.get("echostr");
        String timestamp = param.get("timestamp");
        String nonce = param.get("nonce");

        // 对称加密  本地
        String token = conf.getTokenString();

        if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp)) {
            outputStreamWrite(outputStream, "faild request");
            return;
        }

        if (echostr != null) {
            outputStreamWrite(outputStream, echostr);
            return;
        }

        // 验证请求签名
        if (!signature.equals(SignatureUtil.generateEventMessageSignature(token, timestamp, nonce))) {
            System.out.println("The request signature is invalid");
            return;
        }

        if (inputStream != null) {
            // 转换XML
            EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);

            logger.info("eventMessage:" + ToStringBuilder.reflectionToString(eventMessage));
            String key = eventMessage.getFromUserName() + "__" + eventMessage.getToUserName() + "__" + eventMessage.getMsgId() + "__" + eventMessage.getCreateTime();


            if (expireKey.exists(key)) {
                // 重复通知不作处理
                System.err.println("重复通知不作处理");
                return;
            } else {
                expireKey.add(key);
            }
/**
 * erviceController     : eventMessage:weixin.popular.bean.message.EventMessage@2d2a1bd[toUserName=gh_d147868bd782,fromUserName=oTfG5uBhe66GO2jHtQ77OnDuenoY,createTime=1565618365,msgType=event,event=pic_weixin,eventKey=rselfmenu_1_2,msgId=<null>,content=<null>,picUrl=<null>,mediaId=<null>,format=<null>,recognition=<null>,thumbMediaId=<null>,location_X=<null>,location_Y=<null>,scale=<null>,label=<null>,title=<null>,description=<null>,url=<null>,ticket=<null>,latitude=<null>,longitude=<null>,precision=<null>,status=<null>,totalCount=<null>,filterCount=<null>,sentCount=<null>,errorCount=<null>,copyrightCheckResult=<null>,expiredTime=<null>,failTime=<null>,failReason=<null>,uniqId=<null>,poiId=<null>,result=<null>,msg=<null>,chosenBeacon=<null>,aroundBeacons=<null>,lotteryId=<null>,money=<null>,bindTime=<null>,connectTime=<null>,expireTime=<null>,vendorId=<null>,shopId=<null>,deviceNo=<null>,keyStandard=<null>,keyStr=<null>,country=<null>,province=<null>,city=<null>,sex=<null>,scene=<null>,regionCode=<null>,reasonMsg=<null>,otherElements=[[SendPicsInfo: null]]]
 2019-08-12 21:59:28.026  INFO 17076 --- [-nio-700-exec-3] c.m.s.controller.WxServiceController     : -------java.util.LinkedHashMap@401988b[accessOrder=false,threshold=6,loadFactor=0.75]
 *
 *
 2019-08-12 21:59:28.027  INFO 17076 --- [-nio-700-exec-3] c.m.s.controller.WxServiceController     : eventMessage:weixin.popular.bean.message.EventMessage@29111c23[toUserName=gh_d147868bd782,fromUserName=oTfG5uBhe66GO2jHtQ77OnDuenoY,createTime=1565618365,msgType=image,event=<null>,eventKey=<null>,msgId=22414264928317727,content=<null>,picUrl=http://mmbiz.qpic.cn/mmbiz_jpg/06ib7Uood3MG6tlVHVWuXgGvia91dVGT802vGk6TaT72picWUdN5LEFCa3V0iaQla8VfAQ5icbCrZvjvxKjOiaE3FLBg/0,mediaId=zYNNuHMhJ0dstBmlXEiBHmo69BZnjFKp6J6MNP8i9ZtLrJFnZKBYE9qEn3Bu2Xp8,format=<null>,recognition=<null>,thumbMediaId=<null>,location_X=<null>,location_Y=<null>,scale=<null>,label=<null>,title=<null>,description=<null>,url=<null>,ticket=<null>,latitude=<null>,longitude=<null>,precision=<null>,status=<null>,totalCount=<null>,filterCount=<null>,sentCount=<null>,errorCount=<null>,copyrightCheckResult=<null>,expiredTime=<null>,failTime=<null>,failReason=<null>,uniqId=<null>,poiId=<null>,result=<null>,msg=<null>,chosenBeacon=<null>,aroundBeacons=<null>,lotteryId=<null>,money=<null>,bindTime=<null>,connectTime=<null>,expireTime=<null>,vendorId=<null>,shopId=<null>,deviceNo=<null>,keyStandard=<null>,keyStr=<null>,country=<null>,province=<null>,city=<null>,sex=<null>,scene=<null>,regionCode=<null>,reasonMsg=<null>,otherElements=<null>]
 2019-08-12 21:59:37.481  INFO 17076 --- [-nio-700-exec-5] c.m.s.controller.WxServiceController     : -------java.util.LinkedHashMap@5e541c85[accessOrder=false,threshold=6,loadFactor=0.75]

 2019-10-21 22:46:35.521  INFO 18296 --- [nio-8080-exec-5] c.e.p.s.m.sys.web.WeiChartController     : eventMessage:weixin.popular.bean.message.EventMessage@4cb85e45[toUserName=gh_cafbe88b7b18,fromUserName=odg5gwbXbR1ms1AWHut_B19E804c,createTime=1571669191,msgType=image,event=<null>,eventKey=<null>,msgId=22500893582933289,content=<null>,
 picUrl=http://mmbiz.qpic.cn/mmbiz_jpg/wDOugIYNlJRx8Xabz5UUX43azicUvzxh0t5u2wCbCMdA1uXjB4z1738HeIwh2c0KVvt2nIpQU86zgTtJRvdbtEw/0
 mediaId=YijiX6uO5KF4qD1YjvuEieaFqVS2ggB7LDkL7C4KOx9YF76suTLkbi1o5Qp4u_R6,format=<null>,recognition=<null>,thumbMediaId=<null>,location_X=<null>,location_Y=<null>,scale=<null>,label=<null>,title=<null>,description=<null>,url=<null>,ticket=<null>,latitude=<null>,longitude=<null>,precision=<null>,status=<null>,totalCount=<null>,filterCount=<null>,sentCount=<null>,errorCount=<null>,copyrightCheckResult=<null>,expiredTime=<null>,failTime=<null>,failReason=<null>,uniqId=<null>,poiId=<null>,result=<null>,msg=<null>,chosenBeacon=<null>,aroundBeacons=<null>,lotteryId=<null>,money=<null>,bindTime=<null>,connectTime=<null>,expireTime=<null>,vendorId=<null>,shopId=<null>,deviceNo=<null>,keyStandard=<null>,keyStr=<null>,country=<null>,province=<null>,city=<null>,sex=<null>,scene=<null>,regionCode=<null>,reasonMsg=<null>,otherElements=<null>]


 */

            String msg = eventMessage.getContent()
                    .replace("吗","")
                    .replace("你","")
                    .replace("我","")
                    .replace("?","!")
                    .replace("？","！");

            XMLTextMessage xmlTextMessage2 = new XMLTextMessage(eventMessage.getFromUserName(), eventMessage.getToUserName(), msg);
            xmlTextMessage2.outputStreamWrite(outputStream);


            /*XMLImageMessage xmlImageMessage = new XMLImageMessage(eventMessage.getFromUserName(),eventMessage.getToUserName() , "YijiX6uO5KF4qD1YjvuEieaFqVS2ggB7LDkL7C4KOx9YF76suTLkbi1o5Qp4u_R6");

            xmlImageMessage.outputStreamWrite(outputStream);*/
            return;
        }
    }


    @RequestMapping("createMenu")
    @ResponseBody
    public BaseResult createMenu() {
        String MenuString = "{\r\n" +
                "     \"button\":[\r\n" +
                "     {    \r\n" +
                "          \"type\":\"click\",\r\n" +
                "          \"name\":\"今日歌曲\",\r\n" +
                "          \"key\":\"V1001_TODAY_MUSIC\"\r\n" +
                "      },\r\n" +
                "      {\r\n" +
                "           \"name\":\"菜单\",\r\n" +
                "           \"sub_button\":[\r\n" +
                "           {    \r\n" +
                "               \"type\":\"view\",\r\n" +
                "               \"name\":\"搜索\",\r\n" +
                "               \"url\":\"http://www.soso.com/\"\r\n" +
                "            },\r\n" +
                "            {\r\n" +
                "                 \"type\":\"miniprogram\",\r\n" +
                "                 \"name\":\"wxa\",\r\n" +
                "                 \"url\":\"http://mp.weixin.qq.com\",\r\n" +
                "                 \"appid\":\"wx286b93c14bbf93aa\",\r\n" +
                "                 \"pagepath\":\"pages/lunar/index\"\r\n" +
                "             },\r\n" +
                "            {\r\n" +
                "               \"type\":\"click\",\r\n" +
                "               \"name\":\"赞一下我们\",\r\n" +
                "               \"key\":\"V1001_GOOD\"\r\n" +
                "            }]\r\n" +
                "       }]\r\n" +
                " }";

        String menuString2 = "{\r\n" +
                "    \"button\": [\r\n" +
                "        {\r\n" +
                "            \"name\": \"扫码\",\r\n" +
                "            \"sub_button\": [\r\n" +
                "                {\r\n" +
                "                    \"type\": \"scancode_waitmsg\",\r\n" +
                "                    \"name\": \"扫码带提示\",\r\n" +
                "                    \"key\": \"rselfmenu_0_0\",\r\n" +
                "                    \"sub_button\": []\r\n" +
                "                },\r\n" +
                "                {\r\n" +
                "                    \"type\": \"scancode_push\",\r\n" +
                "                    \"name\": \"扫码推事件\",\r\n" +
                "                    \"key\": \"rselfmenu_0_1\",\r\n" +
                "                    \"sub_button\": []\r\n" +
                "                }\r\n" +
                "            ]\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"name\": \"发图\",\r\n" +
                "            \"sub_button\": [\r\n" +
                "                {\r\n" +
                "                    \"type\": \"pic_sysphoto\",\r\n" +
                "                    \"name\": \"系统拍照发图\",\r\n" +
                "                    \"key\": \"rselfmenu_1_0\",\r\n" +
                "                    \"sub_button\": []\r\n" +
                "                },\r\n" +
                "                {\r\n" +
                "                    \"type\": \"pic_photo_or_album\",\r\n" +
                "                    \"name\": \"拍照或者相册发图\",\r\n" +
                "                    \"key\": \"rselfmenu_1_1\",\r\n" +
                "                    \"sub_button\": []\r\n" +
                "                },\r\n" +
                "                {\r\n" +
                "                    \"type\": \"pic_weixin\",\r\n" +
                "                    \"name\": \"微信相册发图\",\r\n" +
                "                    \"key\": \"rselfmenu_1_2\",\r\n" +
                "                    \"sub_button\": []\r\n" +
                "                }\r\n" +
                "            ]\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"name\": \"发送位置\",\r\n" +
                "            \"type\": \"location_select\",\r\n" +
                "            \"key\": \"rselfmenu_2_0\"\r\n" +
                "        }\r\n" +
                "    ]\r\n" +
                "}";
        BaseResult result = MenuAPI.menuCreate(TokenManager.getDefaultToken(), menuString2);
        return result;
    }


    private boolean outputStreamWrite(OutputStream outputStream, String text) {
        try {
            outputStream.write(text.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
