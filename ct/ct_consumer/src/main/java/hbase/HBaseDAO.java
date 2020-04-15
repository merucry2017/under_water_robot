package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import utils.ConnectionInstance;
import utils.HBaseUtil;
import utils.PropertiesUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HBaseDAO {
    private int regions;
    private String namespace;
    private String tableName;
    public static final Configuration conf;
    private HTable table;
    private Connection connection;
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");

    private List<Put> cacheList = new ArrayList<>();
    static {
        conf = HBaseConfiguration.create();
    }

    public HBaseDAO() {
        try {
            regions = Integer.valueOf(PropertiesUtil.getProperty("hbase.calllog.regions"));
            namespace = PropertiesUtil.getProperty("hbase.calllog.namespace");
            tableName = PropertiesUtil.getProperty("hbase.calllog.tablename");

            if (!HBaseUtil.isExistTable(conf, tableName)) {
                HBaseUtil.initNamespace(conf, namespace);
                HBaseUtil.createTable(conf, tableName, regions, "f1");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ori数据样式： 18576581848,17269452013,2017-08-14 13:38:31,1761
     *              22.7,0.7,1.7,0.5,5.8,2017-01-16 20:32:57
     * rowkey样式：01_18576581848_20170814133831_17269452013_1_1761
     *              01_22.7_0.7_1.7_0.5_5.8_2017-01-16 20:32:57
     * HBase表的列：call1  call2   build_time   build_time_ts   flag   duration
     * @param ori
     */
    public void put(String ori) {
        try {
            if(cacheList.size() == 0){
                connection = ConnectionInstance.getConnection(conf);
                table = (HTable) connection.getTable(TableName.valueOf(tableName));
                table.setAutoFlushTo(false);
                table.setWriteBufferSize(2 * 1024 * 1024);
            }

            String[] splitOri = ori.split(",");
            String temperature = splitOri[0];
            String oxygen_capacity = splitOri[1];
            String Ammonia_nitrogen_content = splitOri[2];
            String pH = splitOri[3];
            String buildTime = splitOri[4];
            String buildTimeReplace = sdf2.format(sdf1.parse(buildTime));
            String regionCode = HBaseUtil.genRegionCode(buildTimeReplace, regions);

            //生成rowkey
            String rowkey = HBaseUtil.genRowKey(regionCode, temperature, oxygen_capacity
                    ,Ammonia_nitrogen_content, buildTimeReplace,pH);
            //向表中插入该条数据
            Put put = new Put(Bytes.toBytes(rowkey));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("temperature"), Bytes.toBytes(temperature));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("oxygen"), Bytes.toBytes(oxygen_capacity));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("Ammonia"), Bytes.toBytes(Ammonia_nitrogen_content));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("buildTime"), Bytes.toBytes(buildTimeReplace.substring(0,10)));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("pH"), Bytes.toBytes(pH));
            cacheList.add(put);

            if(cacheList.size() >= 50){
                table.put(cacheList);
                table.flushCommits();

                table.close();
                cacheList.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
