package utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSON;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.ExcelData.*;
import utils.SQLConnection;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;

@Ignore
@Slf4j
public class ExcelReader {
    private static List<BasicInfoStruct> BISresultList = null;
    private static List<DetailInfoStruct> DISresultList = null;
    private static List<WorkInfoStruct> WISresultList = null;

    public static String ImportBasicInfo(String path){
        String ResultString = "";
        EasyExcel.read(path, BasicInfoStruct.class , new PageReadListener<BasicInfoStruct>(dataList->{
            BISresultList = dataList;
        })).sheet().doRead();
        for(BasicInfoStruct data : BISresultList){
            try {
                SQLConnection.InsertBasicInfo(data.getStudentId(),data.getName(),data.getGender(),data.getNationality(),
                        data.getBirthDate(),data.getPClass(),data.getNote());
            }catch(Exception e){
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String exceptionText = sw.toString();
                ResultString = ResultString.concat(exceptionText);
            }
        }
        BISresultList = null;
        if(ResultString.equals("")){
            return "全部导入成功";
        }
        return ResultString;
    }

    public static String ImportDetailInfo(String path){
        String ResultString = "";
        EasyExcel.read(path, DetailInfoStruct.class , new PageReadListener<DetailInfoStruct>(dataList->{
            DISresultList = dataList;
        })).sheet().doRead();
        for(DetailInfoStruct data : DISresultList){
            try {
                SQLConnection.InsertDetailInfo(data.getStudentId(), data.getPName(), data.getDorm(), data.getId(), data.getBirthPlace(),
                        data.getPhoneNum(), data.getPolStatus(), data.getHouseholdTransfer(), data.getSeniorSchool(), data.getFatherName(),
                        data.getFatherPhone(), data.getFatherWorkPlace(), data.getFatherWorkPosition(), data.getMotherName(), data.getMotherPhone(),
                        data.getMotherWorkPlace(), data.getMotherWorkPosition(), data.getHomePlace(),data.getPostCode(), data.getPoorGrade(), data.getDetailNote());
            }catch(Exception e){
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String exceptionText = sw.toString();
                ResultString = ResultString.concat(exceptionText);
            }
        }
        DISresultList = null;
        if(ResultString.equals("")){
            return "全部导入成功";
        }
        return ResultString;
    }

    public static String ImportWorkInfo(String path){
        String ResultString = "";
        EasyExcel.read(path, BasicInfoStruct.class , new PageReadListener<WorkInfoStruct>(dataList->{
            WISresultList = dataList;
        })).sheet().doRead();
        for(WorkInfoStruct data : WISresultList){
            try {
                SQLConnection.InsertClassWorkInfo(data.getStudentId(),data.getName(), data.getPClass(), data.getWork(), data.getDorm());
            }catch(Exception e){
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String exceptionText = sw.toString();
                ResultString = ResultString.concat(exceptionText);
            }
        }
        WISresultList = null;
        if(ResultString.equals("")){
            return "全部导入成功";
        }
        return ResultString;
    }
}
