package hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;
import utils.HBaseUtil;
import utils.PropertiesUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CalleeWriteObserver extends BaseRegionObserver{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        super.postPut(e, put, edit, durability);
        //1、获取你想要操作的目标表的名称
        String targetTableName = PropertiesUtil.getProperty("hbase.calllog.tablename");
        //2、获取当前成功Put了数据的表（不一定是我们当前业务想要操作的表）
        String currentTableName = e.getEnvironment().getRegionInfo().getTable().getNameAsString();

        if(!targetTableName.equals(currentTableName)) return;

        //01_158373123456_20170110154530_13737312345_1_0360
        String oriRowKey = Bytes.toString(put.getRow());
        String[] splitOri = new String[6];
        try{
            splitOri = oriRowKey.split("_");
        }catch (Exception e1){
            System.err.println(e1);
        }

        int regions = Integer.valueOf(PropertiesUtil.getProperty("hbase.calllog.regions"));

        String temperature = splitOri[1];
        String oxygen_capacity = splitOri[2];
        String Ammonia_nitrogen_content = splitOri[3];
        String buildTime = splitOri[4];
        String pH = splitOri[5];
        String regionCode = HBaseUtil.genRegionCode(buildTime, regions);

        //生成rowkey
        String rowkey = HBaseUtil.genRowKey(regionCode, temperature, oxygen_capacity
                ,Ammonia_nitrogen_content, buildTime, pH);

        try{
            Put calleePut = new Put(Bytes.toBytes(rowkey));
            calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("temperature"), Bytes.toBytes(temperature));
            calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("oxygen"), Bytes.toBytes(oxygen_capacity));
            calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("Ammonia"), Bytes.toBytes(Ammonia_nitrogen_content));
            calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("buildTime"), Bytes.toBytes(buildTime));
            calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("pH"), Bytes.toBytes(pH));
            Bytes.toBytes(100L);
            Table table = e.getEnvironment().getTable(TableName.valueOf(targetTableName));
            table.put(calleePut);
            table.close();
        }catch (Exception e2){
            System.err.println(e2);
        }

    }
}
