package com.dang.crawler.resources.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dangqihe on 2017/3/23.
 */
public class DateUtils {
    public static String convertDate(String crawlTime, String cleanBefore){
        String dateAfterStr = null;
        try {
            if( "".equals(cleanBefore) || cleanBefore == null ){
                return "";
            } else {
                cleanBefore = removeEnterSpace(cleanBefore.trim());
            }
            String regNormal = "(\\d+)年(\\d+)月(\\d+)日\\s*[0-2]?[0-9]:[0-6][0-9]";
            String regNoYear = "(\\d+)[月-](\\d+)日?\\s*[0-2]?[0-9]:[0-6][0-9]";
            String regNoYearMonthDay = "^(前天|昨天|今天)*\\s*[0-2]?[0-9]:[0-6]?[0-9]";
            String regNoTime = "(\\d+)年(\\d+)月(\\d+)日";
            String regNoYearTime = "(\\d+)月(\\d+)日";
            String regOnlyMinus = "(\\d+)分钟(前)?";
            String regOnlyHour = "(\\d+)\\s*小时";
            String regOnlyDay = "(\\d+)\\s*天";
            String regOnlyMonth = "(\\d+)\\s*月前";
            String regOnlyYear = "(\\d+)\\s*年前";
            String regOnlyYear2 = "大约\\s*(\\d+)\\s*年";
            String regNormalFull = "([0-9]{4})-([0-1][0-9])-([0-3][0-9])\\s*[0-2][0-9]:[0-6][0-9]:[0-6][0-9]";
            String regNormalNoS = "([0-9]{4})-([0-1]?[0-9])-([0-3]?[0-9])\\s*[0-2]?[0-9]:[0-6][0-9]";
            String regNormalNoTime = "([0-9]{4})-([0-1]?[0-9])-([0-3]?[0-9])";
            String regNormalFormat = "([0-9]{4})\\.([0-1]?[0-9])\\.([0-3]?[0-9])";
            String regNormalFormat3 = "([0-9]{4})\\/([0-1]?[0-9])\\/([0-3]?[0-9])\\s*[0-2]?[0-9]:[0-6][0-9]";
            String regNormalFormat2 = "([0-9]{4})\\/([0-1]?[0-9])\\/([0-3]?[0-9])";
            String regNoYearTime2 = "([0-1]?[0-9])-([0-3]?[0-9])";
            String dateAfter = "";
            Date crawled = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            crawled = df.parse(crawlTime);
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(crawled);

            Matcher matcherNormalFull = Pattern.compile(regNormalFull).matcher(cleanBefore);
            Matcher matcherNormalNoS = Pattern.compile(regNormalNoS).matcher(cleanBefore);
            Matcher matcherNormalNoTime = Pattern.compile(regNormalNoTime).matcher(cleanBefore);
            Matcher matcherNormalFormat = Pattern.compile(regNormalFormat).matcher(cleanBefore);
            Matcher matcherNormal = Pattern.compile(regNormal).matcher(cleanBefore);
            Matcher matcherNoYear = Pattern.compile(regNoYear).matcher(cleanBefore);
            Matcher matcherNoYearMonthDay = Pattern.compile(regNoYearMonthDay).matcher(cleanBefore);
            Matcher matcherNoTimeFormat3 = Pattern.compile(regNormalFormat3).matcher(cleanBefore);
            Matcher matcherNoTimeFormat = Pattern.compile(regNormalFormat2).matcher(cleanBefore);
            Matcher matcherNoYearTime = Pattern.compile(regNoYearTime).matcher(cleanBefore);
            Matcher matcherNoTime = Pattern.compile(regNoTime).matcher(cleanBefore);
            Matcher matcherOnlyMinus = Pattern.compile(regOnlyMinus).matcher(cleanBefore);
            Matcher matcherOnlyHour = Pattern.compile(regOnlyHour).matcher(cleanBefore);
            Matcher matcherOnlyDay = Pattern.compile(regOnlyDay).matcher(cleanBefore);
            Matcher matcherOnlyMonth = Pattern.compile(regOnlyMonth).matcher(cleanBefore);
            Matcher matcherOnlyYear = Pattern.compile(regOnlyYear).matcher(cleanBefore);
            Matcher matcherOnlyYear2 = Pattern.compile(regOnlyYear2).matcher(cleanBefore);
            Matcher matcherNoYearTime2 = Pattern.compile(regNoYearTime2).matcher(cleanBefore);
            if(matcherNormalFull.find()){
                return matcherNormalFull.group();
            } else if(matcherNormalNoS.find()){
                dateAfter = matcherNormalNoS.group() + ":00" ;
            } else if(matcherNormalNoTime.find()){
                dateAfter = matcherNormalNoTime.group() + " 00:00:00" ;
            } else if(matcherNormalFormat.find()){
                dateAfter = matcherNormalFormat.group().replaceAll("\\.", "-") + " 00:00:00";
            } else if(matcherNormal.find()){
                dateAfter = matcherNormal.group().replace("年", "-").replace("月", "-").replace("日"," ");
            } else if(matcherNoYear.find()){
                dateAfter = crawlTime.substring(0, 4) + "-" + matcherNoYear.group().replace("月", "-").replace("日"," ");
                String[] dateStr = dateAfter.split("-");
                int month = Integer.parseInt(dateStr[1].trim());
                String dayStr = dateStr[2].trim().split(" ")[0].trim();
                int year = cal.get(Calendar.YEAR);
                int day = 0;
                if(dayStr.matches("\\d+")){
                    day = Integer.parseInt(dayStr);
                } else {
                    return "";
                }
                int crawlMonth = cal.get(Calendar.MONTH) + 1;
                int crawlDay = cal.get(Calendar.DATE);
                if((month > crawlMonth || (month == crawlMonth && day > crawlDay))){
                    dateAfter = year - 1 + "-" + matcherNoYear.group().replace("月","-").replace("日","");
                }

            } else if(matcherNoYearMonthDay.find()){
                Matcher matcher = Pattern.compile("[0-2]?[0-9]:[0-6]?[0-9]").matcher(matcherNoYearMonthDay.group());
                if(matcher.find()){
                    String[] timeStr = matcher.group(0).split(":");
                    int hour = Integer.parseInt(timeStr[0]);
                    int minus = Integer.parseInt(timeStr[1]);
                    Integer date = 0;
                    if(cleanBefore.contains("前天")){
                        date = -2;
                    } else if(cleanBefore.contains("昨天")) {
                        date = -1;
                    } else {
                        date = 0;
                    }
                    cal.set(GregorianCalendar.HOUR_OF_DAY, hour);
                    cal.set(GregorianCalendar.MINUTE, minus);
                    cal.add(Calendar.DATE, date);
                    dateAfter = dateConvertToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
                }
            } else if(matcherNoTime.find()){
                dateAfter = matcherNoTime.group().replace("年", "-").replace("月", "-").replace("日","") + " 00:00:00";
            } else if(matcherNoYearTime.find()){
                dateAfter = crawlTime.substring(0, 4) + "-" + matcherNoYearTime.group().replace("月", "-").replace("日","");
                int year = cal.get(Calendar.YEAR);
                String[] dateStr = dateAfter.split("-");
                int month = Integer.parseInt(dateStr[1].trim());
                int day = Integer.parseInt(dateStr[2].trim());
                int crawlMonth = cal.get(Calendar.MONTH) + 1;
                int crawlDay = cal.get(Calendar.DATE);
                if((month > crawlMonth || (month == crawlMonth && day > crawlDay))){
                    dateAfter = year - 1 + "-" +  matcherNoYearTime.group().replace("月","-").replace("日","");
                }
                dateAfter = dateAfter + " 00:00:00";
            } else if(matcherOnlyMinus.find()){
                dateAfter = matcherOnlyMinus.group().replace("分钟", "").replace("前", "");
                cal.add(Calendar.MINUTE, (0 - Integer.parseInt(dateAfter)));
                dateAfter = dateConvertToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
            } else if(matcherOnlyHour.find()){
                dateAfter = matcherOnlyHour.group().replace("小时", "");
                cal.add(Calendar.HOUR_OF_DAY, (0 - Integer.parseInt(dateAfter)));
                dateAfter = dateConvertToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
            } else if ("刚刚".equals(cleanBefore.trim())){
                dateAfter = dateConvertToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
            }  else if(matcherNoTimeFormat3.find()){
                dateAfter = matcherNoTimeFormat3.group().replaceAll("\\/", "-") + ":00";
            } else if(matcherNoTimeFormat.find()){
                dateAfter = matcherNoTimeFormat.group().replaceAll("\\/","-") + " 00:00:00";
            } else if(matcherOnlyDay.find()){
                dateAfter = matcherOnlyDay.group().replaceAll("[^\\d]+", "");
                cal.add(Calendar.DATE, (0 - Integer.parseInt(dateAfter)));
                dateAfter = dateConvertToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
            } else if(matcherOnlyMonth.find()){
                dateAfter = matcherOnlyMonth.group().replaceAll("[^\\d]+", "");
                cal.add(Calendar.MONTH, (0 - Integer.parseInt(dateAfter)));
                dateAfter = dateConvertToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
            } else if(matcherOnlyYear.find()){
                dateAfter = matcherOnlyYear.group().replaceAll("[^\\d]+", "");
                cal.add(Calendar.YEAR, (0 - Integer.parseInt(dateAfter)));
                dateAfter = dateConvertToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
            } else if(matcherOnlyYear2.find()){
                dateAfter = matcherOnlyYear2.group().replaceAll("[^\\d]+", "");
                cal.add(Calendar.YEAR, (0 - Integer.parseInt(dateAfter)));
                dateAfter = dateConvertToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
            } else if(matcherNoYearTime2.find()) {
                dateAfter = crawlTime.substring(0, 4) + "-" + matcherNoYearTime2.group();
                int year = cal.get(Calendar.YEAR);
                String[] dateStr = dateAfter.split("-");
                int month = Integer.parseInt(dateStr[1].trim());
                int day = Integer.parseInt(dateStr[2].trim());
                int crawlMonth = cal.get(Calendar.MONTH) + 1;
                int crawlDay = cal.get(Calendar.DATE);
                if((month > crawlMonth || (month == crawlMonth && day > crawlDay))){
                    dateAfter = year - 1 + "-" +  matcherNoYearTime2.group().replace("月","-").replace("日","");
                }
                dateAfter = dateAfter + " 00:00:00";
            } else {
                return "";
            }
            String[] dateStr = dateAfter.split("-");
            dateAfterStr = dateStr[0].trim();
            String monthStr = dateStr[1].trim();
            if(dateAfterStr.length() == 2){
                dateAfterStr = "20" + dateAfterStr;
            }
            if(monthStr.length() == 1){
                dateAfterStr += "-0" + monthStr;
            }else{
                dateAfterStr += "-" + monthStr;
            }
            String dayStr = dateStr[2].trim().split(" ")[0].trim();
            if(dayStr.length() == 1){
                dateAfterStr += "-0" + dayStr;
            }else{
                dateAfterStr += "-" + dayStr;
            }
            Matcher matcher = Pattern.compile("[0-2]?[0-9]:[0-6][0-9]:[0-6][0-9]").matcher(dateAfter);
            if(matcher.find()){
                String dateAfterTemp = matcher.group(0);
                if(dateAfterTemp.length() == 8){
                    dateAfterStr += " " + dateAfterTemp;
                } else {
                    dateAfterStr += " 0" + dateAfterTemp;
                }

            } else {
                Matcher matcherNoS = Pattern.compile("[0-2]?[0-9]:[0-6][0-9]").matcher(dateAfter);
                if(matcherNoS.find()){
                    String dateAfterTemp = matcherNoS.group(0);
                    if(dateAfterTemp.length() == 5){
                        dateAfterStr += " " + dateAfterTemp + ":00";
                    } else {
                        dateAfterStr += " 0" + dateAfterTemp + ":00";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            dateAfterStr = "";
        }
        return dateAfterStr;
    }

    public static String dateConvertToString(Date date, String format){
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        return sdf.format(date);
    }
    public static Date stringConvertDate(String date, String format) throws ParseException {
        Date resDate = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        resDate = df.parse(date);
        return resDate;
    }

    //将多个空格变为一个
    public static String removeEnterSpace(String content){
        if(content == null){
            return "";
        }
        Pattern pattern = Pattern.compile("\\t|\\r|\\n", Pattern.CASE_INSENSITIVE);
        String contentReturn = pattern.matcher(content).replaceAll("");

        pattern = Pattern.compile("\\t|\\r|\\n" + "+", Pattern.CASE_INSENSITIVE);
        contentReturn =  pattern.matcher(contentReturn).replaceAll(" ").trim();
        return contentReturn;
    }
    public static void main(String []args){
        String a = convertDate(dateConvertToString(new Date(),"yyyy-MM-dd HH:mm:ss"), "2017年03月27日 15:32 21世纪经济报道 21Plus ");
        System.out.println("date"+a);
    }
}
