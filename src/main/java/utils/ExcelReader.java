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
    private static String ResultString = "";

    public static String ImportBasicInfo(String path){
        ResultString = "";
        EasyExcel.read(path, BasicInfoStruct.class , new PageReadListener<BasicInfoStruct>(ExcelReader::ImportBasicInfoHelp)).sheet().doRead();
        if(ResultString.equals("")){
            return "全部导入成功";
        }
        return ResultString;
    }

    public static void ImportBasicInfoHelp(List<BasicInfoStruct> dataList){
        for(BasicInfoStruct data : dataList){
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
    }

    public static String ImportDetailInfo(String path){
        ResultString = "";
        EasyExcel.read(path, DetailInfoStruct.class , new PageReadListener<DetailInfoStruct>(ExcelReader::ImportDetailInfoHelp)).sheet().doRead();
        if(ResultString.equals("")){
            return "全部导入成功";
        }
        return ResultString;
    }

    public static void ImportDetailInfoHelp(List<DetailInfoStruct> dataList){
        for(DetailInfoStruct data : dataList){
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
    }

    public static String ImportWorkInfo(String path){
        ResultString = "";
        EasyExcel.read(path, BasicInfoStruct.class , new PageReadListener<WorkInfoStruct>(ExcelReader::ImportWorkInfoHelp)).sheet().doRead();
        if(ResultString.equals("")){
            return "全部导入成功";
        }
        return ResultString;
    }

    public static void ImportWorkInfoHelp(List<WorkInfoStruct> dataList){
        for(WorkInfoStruct data : dataList){
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
    }
}
