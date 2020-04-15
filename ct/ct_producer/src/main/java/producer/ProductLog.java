package producer;

import sun.nio.cs.ext.MacHebrew;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductLog {
    public String startTime = "2017-01-01";
    public String endTime = "2017-12-31";

    //生产数据
    /**
     * 形式：水温，氧含量，氨氮含量，pH值,时间
     */

    public String product() {
        String temperature = null;
        String oxygen_capacity = null;
        String Ammonia_nitrogen_content = null;
        String pH = null;


        DecimalFormat df = new DecimalFormat("0.0");
        temperature = df.format(9 * Math.random());
        oxygen_capacity = df.format(6 * Math.random());
        Ammonia_nitrogen_content = df.format(Math.random());
        pH = df.format(9 * Math.random());
        String buildTime = randomBuildTime(startTime, endTime);

        StringBuilder sb = new StringBuilder();
        sb.append(temperature + ",").append(oxygen_capacity + ",")
                .append(Ammonia_nitrogen_content + ",").append(pH + ",")
                .append(buildTime);

        return sb.toString();
    }

    /**
     * 随机产生时间
     * @param startTime
     * @param endTime
     * @return
     */
    public String randomBuildTime(String startTime, String endTime){
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf1.parse(startTime);
            Date endDate = sdf1.parse(endTime);

            if(endDate.getTime()<= startDate.getTime()){
                return null;
            }

            long randomTS = startDate.getTime() + (long) ((endDate.getTime() - startDate.getTime()) * Math.random());
            Date resultDate = new Date(randomTS);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String resultTimeString = sdf2.format(resultDate);
            return resultTimeString;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将数据写入文件
     * @param filePath
     */
    public void writeLog(String filePath){
        try{
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath));
            while(true){
                Thread.sleep(1000);
                String log = product();
                System.out.println(log);
                osw.write(log+"\n");
                osw.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if(args == null || args.length <=0){
            System.out.println("no arguments");
            return;
        }

        ProductLog productLog = new ProductLog();
        productLog.writeLog(args[0]);

    }

}
