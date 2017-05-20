package com.dang.crawler.core.serivce;

import com.dang.crawler.resources.mysql.dao.KeywordMapper;
import com.dang.crawler.resources.mysql.model.Keyword;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dang on 17-5-16.
 * 关键词服务  对关键词的增删改查
 */
@Service
public class KeywordService {
    @Resource
    private KeywordMapper keywordMapper;
    public synchronized void insertKeyword(int projectId, Map<String,String> map){
        int max = 0;
        try{
            max = keywordMapper.maxKid(projectId);
        }catch (Exception e){
        }
        List<Keyword> keywordList = new ArrayList<>();
        for(Map.Entry<String,String> entry :map.entrySet()){
            Keyword keyword = new Keyword(projectId,++max,entry.getKey(),entry.getValue());
            //keywordMapper.insert(keyword);
            keywordList.add(keyword);
        }
        keywordMapper.insertAll(keywordList);
    }
    public List<Keyword> select(int projectId, int page, int szie){
        Keyword keyword = new Keyword();
        keyword.setProjectId(projectId);
        keyword.setKid(page*szie);
        keyword.setLimit(szie);
//        keyword.setLimit(szie);
//        keyword.setStart(page);
        return keywordMapper.select(keyword);
    }

}
