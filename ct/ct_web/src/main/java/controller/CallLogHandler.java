package controller;

import bean.CallLog;
import bean.QueryInfo;
import dao.CallLogDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
public class CallLogHandler {

    @RequestMapping("/queryCallLogList")
    public String queryCallLog(Model model, QueryInfo queryInfo){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CallLogDAO callLogDAO = applicationContext.getBean(CallLogDAO.class);

        HashMap<String, String> hashMap = new HashMap<>();

        if(queryInfo.getYear().length()<=0){
            queryInfo.setYear("-1");
        }
        if(queryInfo.getMonth().length()<=0){
            queryInfo.setMonth("-1");
        }
        if(queryInfo.getDay().length()<=0){
            queryInfo.setDay("-1");
        }
        if(queryInfo.getHour().length()<=0){
            queryInfo.setHour("-1");
        }
        hashMap.put("year", queryInfo.getYear());
        hashMap.put("month", queryInfo.getMonth());
        hashMap.put("day", queryInfo.getDay());
        hashMap.put("hour",queryInfo.getHour());

        List<CallLog> list = callLogDAO.getCallLogList(hashMap);
        StringBuilder date = new StringBuilder();
//        StringBuilder month = new StringBuilder();
//        StringBuilder day = new StringBuilder();
//        StringBuilder hour = new StringBuilder();
//        StringBuilder minute = new StringBuilder();
//        StringBuilder second = new StringBuilder();

        StringBuilder temperature = new StringBuilder();
        StringBuilder oxygen_capacity = new StringBuilder();
        StringBuilder Ammonia = new StringBuilder();
        StringBuilder pH = new StringBuilder();
        for(int i = 0; i < list.size(); i++){
            CallLog callLog = list.get(i);
            //1月, 2月, ....12月,
            date.append(callLog.getMonth()+"-"+callLog.getDay()+"-"+callLog.getHour()+":"+callLog.getMinute()+":"+callLog.getSecond()+",");
            temperature.append(callLog.getTemperature() + ",");
            oxygen_capacity.append(callLog.getOxygen_capacity() + ",");
            Ammonia.append(callLog.getAmmonia() + ",");
            pH.append(callLog.getpH() + ",");
        }
        System.out.println(date);
        if(date.length()>1){
            date.deleteCharAt(date.length() - 1);
        }
        if(temperature.length()>1){
            temperature.deleteCharAt(temperature.length() - 1);
        }
        if(oxygen_capacity.length()>1){
            oxygen_capacity.deleteCharAt(oxygen_capacity.length() - 1);

        }
        if(Ammonia.length()>1){
            Ammonia.deleteCharAt(Ammonia.length() - 1);
        }
        if(pH.length()>1){
            pH.deleteCharAt(pH.length() - 1);

        }
        //通过model返回数据
        model.addAttribute("date", date.toString());
        model.addAttribute("temperature", temperature.toString());
        model.addAttribute("oxygen_capacity", oxygen_capacity.toString());
        model.addAttribute("Ammonia", Ammonia.toString());
        model.addAttribute("pH", pH.toString());
        return "CallLogListEchart";
    }
}
