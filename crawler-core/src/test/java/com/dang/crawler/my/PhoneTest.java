package com.dang.crawler.my;

import com.dang.crawler.core.serivce.ApplicationContext;
import com.dang.crawler.resources.mysql.dao.TableMapper;
import com.dang.crawler.resources.mysql.model.Table;
import javafx.scene.control.Tab;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by duang on 2017/12/3.
 */
public class PhoneTest {
    //@Resource
    private TableMapper tableMapper;
    @Before
    public void setUp() throws Exception {
        tableMapper= (TableMapper) ApplicationContext.getBean("tableMapper");

    }
    @Test
    public void test() {
        Set<String> set = new HashSet();
        boolean isFrist = true;
        for(int i = 0;i<10000;i++){
            int size = 1000;
            System.out.println(i);
            List<Map<String, Object>> list = tableMapper.select("crawler_phone_list2", i * size, size);
            Table table = new Table("crawler_phone_grade_list");
            for(Map<String,Object> map : list){
                String phone  = (String) map.get("phone");
                if(phone==null || phone.length() <10)continue;
                if(set.contains(phone)){
                    continue;
                }else {
                    set.add(phone);
                }
                int grade = grade(phone);
                Map<String,String> insertMap = new HashMap<>();
                insertMap.put("phone",phone);
                insertMap.put("grade",""+grade);
                table.add(insertMap);
            }
          try {
              tableMapper.insert(table);
          }catch (Exception e){
                e.printStackTrace();
          }

            if(list.size() != size) break;
        }

    }

    private static int grade(String phone) {
        int grade = 0;
        String[] numberStr = phone.split("");
        int [] number = new int[11];
        for(int i = 0 ;i<11;i++){
            number[i] = Integer.parseInt(numberStr[i]);
        }

        int numCount[] = new int[10];

        for(int i =1 ;i<number.length ; i ++){
            numCount[number[i]]++;
        }
        for(int i = 0;i<numCount.length;i++){
            grade += Math.pow(numCount[i],2)*100;  //100,400,900,1600,2500
        }
        grade += numCount[5]*200;
        grade += numCount[6]*200;
        grade += numCount[7]*200;
        grade += numCount[8]*200;


        // 连号
        for(int i= 2; i< number.length ;i++){
            if(number[i] == number[i-1]){
                grade +=500;
                if(number[i] == (number[i-2])){
                    grade+=2000;
                }
            }
        }

        //  顺子
        for(int i= 1; i< number.length ;i++){
            if(number[i] +1 == number[i-1]){
                grade += 500;
            }
            if(number[i] -1 == number[i-1]){
                grade += 2000;
            }
        }
        return grade;
    }
}
