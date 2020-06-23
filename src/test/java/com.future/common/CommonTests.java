package com.future.common;

import com.future.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Ignore
public class CommonTests {


    @Test
    public void testGetHistoryOrder(){
        String date="2020-01-04 02:00:35";
        long time =1582058292;
        Date dataTime= DateUtil.toDataFormTimeStamp(time);
        System.out.println(dataTime);
        System.out.println(DateUtil.toDatetimeString(dataTime));
        /*Date getDate=DateUtil.toDate(date);
        System.out.println(getDate);
        System.out.println(getDate.getTime());
        System.out.println(DateUtil.toTimestamp(getDate));

        System.out.println("```````````````1111111111111111111111```````````````");
        Date thisDate= new Date();
        System.out.println(thisDate);
        System.out.println(thisDate.getTime());
        System.out.println(DateUtil.toTimestamp(thisDate));*/
    }


    @Test
    public void testDouble(){
        String a="SFSDFS.E";
        System.out.println(a.substring(0,a.indexOf(".")));
        System.out.println("AAAA"+a.substring(a.indexOf(".")));
    }

    @Test
    public void testBetMonth(){
        String begin="2020-05-01 00:00:00";
        String end="2020-05-01 00:00:00";
        String mid="";
        List<String> months= DateUtil.getMonthBetween(begin.substring(0,7),end.substring(0,7));
        for(String month:months) {
            mid=month+"-00";
            System.out.println(mid);
        }
    }


    @Test
    public void testStringDate() {
        String date = "2020-01-04";
        Date dataTime = DateUtil.toDate(date);
        System.out.println(dataTime);
        date=DateUtil.toDateString(dataTime);
        System.out.println(date);
    }
}