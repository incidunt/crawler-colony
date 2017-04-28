package com.dang.crawler.core.script;

import com.dang.crawler.core.bean.Base;
import com.dang.crawler.core.fetcher.PageService;
import com.dang.crawler.core.fetcher.WebService;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.fetcher.bean.Request;
import com.dang.crawler.core.fetcher.service.Fetcher;

import java.io.IOException;

/**
 * Created by duang on 2017/4/27.
 */
public class SinaWeibao {
    public static void main(String []args) throws Exception {
        Request request = new Request("http://www.baidu.com");
        request.getHeader().put("Accept","*/*");
        request.getHeader().put("Accept-Encoding","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        request.getHeader().put("Accept-Connection","keep-alive");
        request.getHeader().put("Content-Type","application/x-www-form-urlencoded");
        //request.getHeader().put("Host","weibo.com");
        //request.getHeader().put("Referer","http://weibo.com/u/5616413326?topnav=1&wvr=6&topsug=1&is_hot=1");
        request.getHeader().put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
        //request.getHeader().put("X-Requested-With","XMLHttpRequest");
//        request.getHeader().put("Cookie","\t\n" +
//                "SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5RghEHUxPnrZ.sW1CVRf5j5JpX5KMhUgL.Foz7Soe71KME1Kz2dJLoIpjLxK-L1h-L1-2LxK-LB-qL12eLxKnLBoBLBo-t\n" +
//                "; SINAGLOBAL=4498515172177.254.1493224497978; ULV=1493307080395:3:3:3:9396276144286.68.1493307079809\n" +
//                ":1493303379891; SCF=AkXJ9Bqytrc-KJ6CSCt1hXwYgo1l4m_ICth824G-nMMFlZFunTGOK_cyS5vyQbGrOyYHCQ6VDgVrXV2UCUD6YhA\n" +
//                ".; SUHB=0M2UObSgaCRDiN; ALF=1524843066; un=dangfugui@163.com; UOR=,,login.sina.com.cn; YF-Page-G0=0dccd34751f5184c59dfe559c12ac40a\n" +
//                "; SUB=_2A250Bn7vDeThGeRO7VER-SnOwj6IHXVXctcnrDV8PUNbmtBeLU2lkW9bD-GpJY1Fnha6lOAkZZGMdh9puA..; SSOLoginState\n" +
//                "=1493307071; _s_tentry=login.sina.com.cn; Apache=9396276144286.68.1493307079809");
        Base base = WebService.phantomjs(request, null);
        System.out.println(base.getStringList().get(0));
    }
}
