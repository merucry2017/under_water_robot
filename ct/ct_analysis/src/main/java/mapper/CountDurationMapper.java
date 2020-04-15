package mapper;

import kv.key.ComDimension;
import kv.key.ContactDimension;
import kv.key.DateDimension;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.log4j.Logger;

import java.io.IOException;


public class CountDurationMapper extends TableMapper<ComDimension, Text>{
    private ComDimension comDimension = new ComDimension();
    private Text durationText = new Text();
//    public Logger logger = Logger.getLogger(CountDurationMapper.class);

    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        // 03_8.7_1.5_0.9_2017010204_2.9
        String rowKey = Bytes.toString(key.get());
//        logger.info("这就是获取到的数据 " + rowKey);
        if(rowKey.length()==0||rowKey.equals("")||rowKey==null){
            return;
        }
        String[] splits = rowKey.split("_");
//        //以下数据全部是主叫数据，但是也包含了被叫电话的数据
        String temperature = splits[1];
        String oxygen_capacity = splits[2];
        String Ammonia_nitrogen_content = splits[3];
        String buildTime = splits[4];
        String pH = splits[5];
        String num = "1";
        durationText.set(num);
        String year = buildTime.substring(0, 4);
        String month = buildTime.substring(4, 6);
        String day = buildTime.substring(6, 8);
        String hour = buildTime.substring(8,10);
        String minute = buildTime.substring(10,12);
        String second = buildTime.substring(12,14);


        //组装DateDimension
        DateDimension yearDimension = new DateDimension(year, "-1", "-1", "-1", "-1", "-1");
        DateDimension monthDimension = new DateDimension(year, month, "-1", "-1", "-1", "-1");
        DateDimension dayDimension = new DateDimension(year, month, day, "-1", "-1", "-1");
        DateDimension hourDimension = new DateDimension(year, month, day, hour, "-1", "-1");
        DateDimension minuteDimension = new DateDimension(year, month, day, hour, minute, "-1");
        DateDimension secondDimension = new DateDimension(year, month, day, hour, minute, second);

        //组装ContactDimension
        ContactDimension contactDimension = new ContactDimension(temperature,oxygen_capacity
                                                        ,Ammonia_nitrogen_content,pH);

        //开始聚合主叫数据
        comDimension.setContactDimension(contactDimension);
        //年
        comDimension.setDateDimension(yearDimension);
        context.write(comDimension, durationText);
        //月
        comDimension.setDateDimension(monthDimension);
        context.write(comDimension, durationText);
        //日
        comDimension.setDateDimension(dayDimension);
        context.write(comDimension, durationText);
        //小时
        comDimension.setDateDimension(hourDimension);
        context.write(comDimension, durationText);
        //分钟
        comDimension.setDateDimension(minuteDimension);
        context.write(comDimension, durationText);
        //秒钟
        comDimension.setDateDimension(secondDimension);
        context.write(comDimension, durationText);
    }
}