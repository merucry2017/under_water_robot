package outputformat;

import converter.DimensionConverterImpl;
import kv.key.ComDimension;
import kv.value.CountDurationValue;
import utils.JDBCInstance;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import utils.JDBCUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MysqlOutputFormat extends OutputFormat<ComDimension, CountDurationValue>{
    private OutputCommitter committer = null;

    @Override
    public RecordWriter<ComDimension, CountDurationValue> getRecordWriter(TaskAttemptContext context)
            throws IOException, InterruptedException {
        //初始化JDBC连接器对象
        Connection conn = null;
        conn = JDBCInstance.getInstance();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return new MysqlRecordWriter(conn);
    }

    @Override
    public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {
        //输出校检
    }

    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        if(committer == null){
            String name = context.getConfiguration().get(FileOutputFormat.OUTDIR);
            Path outputPath = name == null ? null : new Path(name);
            committer = new FileOutputCommitter(outputPath, context);
        }
        return committer;
    }

    static class MysqlRecordWriter extends RecordWriter<ComDimension, CountDurationValue> {
        private DimensionConverterImpl dci = new DimensionConverterImpl();
        private Connection conn = null;
        private PreparedStatement preparedStatement = null;
        private String insertSQL = null;
        private int count = 0;
        private final int BATCH_SIZE = 500;
        public MysqlRecordWriter(Connection conn) {
            this.conn = conn;
        }

        @Override
        public void write(ComDimension key, CountDurationValue value) throws IOException, InterruptedException {
            try {
                //tb_call
                //id_date_contact, id_date_dimension, id_contact, call_sum, call_duration_sum

                //year month day
                int idDateDimension = dci.getDimensionID(key.getDateDimension());
                //telephone name
//                int idContactDimension = dci.getDimensionID(key.getContactDimension());

//                String idDateContact = idDateDimension + "_" + idContactDimension;
                String idDateContact = String.valueOf(idDateDimension);
                String temperature = key.getContactDimension().getTemperature();
                String oxygen = key.getContactDimension().getOxygen_capacity();
                String Ammonia = key.getContactDimension().getAmmonia_nitrogen_content();
                String pH = key.getContactDimension().getpH();

                int Sum = Integer.valueOf(value.getSum());

                if(insertSQL == null){
                    insertSQL = "INSERT INTO `under_water_date` (`id_date_dimension`,`temperature`,`oxygen_capacity`,`Ammonia`," +
                            "`pH`) VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE `id_date_dimension` = ?;";
                }

                if(preparedStatement == null){
                    preparedStatement = conn.prepareStatement(insertSQL);
                }
                //本次SQL
                int i = 0;
                preparedStatement.setString(++i, idDateContact);
                preparedStatement.setString(++i, temperature);
                preparedStatement.setString(++i, oxygen);
                preparedStatement.setString(++i, Ammonia);
                preparedStatement.setString(++i, pH);

                //无则插入，有则更新的判断依据
                preparedStatement.setString(++i, idDateContact);
                preparedStatement.addBatch();
                count++;
                if(count >= BATCH_SIZE){
                    preparedStatement.executeBatch();
                    conn.commit();
                    count = 0;
                    preparedStatement.clearBatch();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void close(TaskAttemptContext context) throws IOException, InterruptedException {
            try {
                if(preparedStatement != null){
                    preparedStatement.executeBatch();
                    this.conn.commit();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                JDBCUtil.close(conn, preparedStatement, null);
            }
        }
    }
}
