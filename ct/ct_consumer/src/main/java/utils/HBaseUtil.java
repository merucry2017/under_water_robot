package utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

public class HBaseUtil {
    /**
     * 判断表是否存在
     * @param conf HBaseConfiguration
     * @param tableName
     * @return
     */
    public static boolean isExistTable(Configuration conf, String tableName) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();
        boolean result = admin.tableExists(TableName.valueOf(tableName));

        admin.close();
        connection.close();

        return result;
    }

    /**
     * 初始化命名空间
     * @param conf
     * @param namespace
     */
    public static void initNamespace(Configuration conf, String namespace) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        NamespaceDescriptor nd = NamespaceDescriptor
                .create(namespace)
                .addConfiguration("CREATE_TIME", String.valueOf(System.currentTimeMillis()))
                .addConfiguration("AUTHOR", "JinJi")
                .build();

        admin.createNamespace(nd);
        admin.close();
        connection.close();
    }

    /**
     * 创建表：协处理器
     * @param conf
     * @param tableName
     * @param columnFamily
     * @throws IOException
     */
    public static void createTable(Configuration conf, String tableName, int regions, String... columnFamily) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        if(isExistTable(conf, tableName)) return;

        HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(tableName));
        for(String cf: columnFamily){
            htd.addFamily(new HColumnDescriptor(cf));
        }
//        htd.addCoprocessor("hbase.CalleeWriteObserver");
        admin.createTable(htd, genSplitKeys(regions));
        admin.close();
        connection.close();
    }

    private static byte[][] genSplitKeys(int regions){
        //定义一个存放分区键的数组
        String[] keys = new String[regions];
        //目前推算，region个数不会超过2位数，所以region分区键格式化为两位数字所代表的字符串
        DecimalFormat df = new DecimalFormat("00");
        for(int i = 0; i < regions; i ++){
            keys[i] = df.format(i) + "|";
        }

        byte[][] splitKeys = new byte[regions][];
        //生成byte[][]类型的分区键的时候，一定要保证分区键是有序的
        TreeSet<byte[]> treeSet = new TreeSet<>(Bytes.BYTES_COMPARATOR);
        for(int i = 0; i < regions; i++){
            treeSet.add(Bytes.toBytes(keys[i]));
        }

        Iterator<byte[]> splitKeysIterator = treeSet.iterator();
        int index = 0;
        while(splitKeysIterator.hasNext()){
            byte[] b = splitKeysIterator.next();
            splitKeys[index ++] = b;
        }
        return splitKeys;
    }

    /**
     *
     * @param regionCode
     * @param temperature
     * @param oxygen_capacity
     * @param Ammonia_nitrogen_content
     * @param buildTime
     * @param pH
     * @return
     */

    public static String genRowKey(String regionCode, String temperature, String oxygen_capacity, String Ammonia_nitrogen_content, String buildTime, String pH){
        StringBuilder sb = new StringBuilder();
        sb.append(regionCode + "_")
                .append(temperature + "_")
                .append(oxygen_capacity + "_")
                .append(Ammonia_nitrogen_content + "_")
                .append(buildTime + "_")
                .append(pH);
        return sb.toString();
    }

    /**
     *
     * @param buildTime
     * @param regions
     * @return
     */
    public static String genRegionCode(String buildTime, int regions){

        //随机取4位号码
//        String lastPhone = String.valueOf((int)((Math.random()*9+1)*1000));
        String lastPhone = "1234";
        String ym = buildTime.substring(0,6);

        Integer x = Integer.valueOf(lastPhone) ^ Integer.valueOf(ym);
        int y = x.hashCode();
        int regionCode = y % regions;

        //格式化分区号
        DecimalFormat df = new DecimalFormat("00");
        return  df.format(regionCode);
    }

}
