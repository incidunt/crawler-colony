package script.my.dawang;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.parser.Parser;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.DB;
import com.dang.crawler.core.script.tools.Fetch;

import java.util.*;

/**
 * Created by mi on 2017/5/17.
 */
public class Detail implements Script {
    private static Set<String> set = new HashSet<>();
    @Override
    public List<Task> work(Crawler crawler, Job job) throws Exception {
        Page page = Fetch.fetch(crawler);
        Map<String,String> map = new HashMap<>();
        for(String phone :Parser.html(page.getContent()).regex("\\d{11,11}")){
            phone = phone.trim();
            if(set.contains(phone)){
                continue;
            }else {
                set.add(phone);
            }
            map.put("phone",phone);
            map.put("grade",""+grade(phone));
            DB.insert("phone_list2","phone",map);
        }
        return null;
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
