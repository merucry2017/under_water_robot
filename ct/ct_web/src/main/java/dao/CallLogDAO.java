package dao;

import bean.CallLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CallLogDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<CallLog> getCallLogList(HashMap<String, String> paramsMap) {
        //按照年统计：统计某个用户，1年12个月的所有的数据（不精确到day）
        String month = paramsMap.get("month");
        String day = paramsMap.get("day");
        String hour = paramsMap.get("hour");
        String sql = "";
        if(month.equals("-1")&&day.equals("-1")&&hour.equals("-1")){
            sql = "SELECT `year` , `month`, `day`, `hour`, `minute`, `second`, `temperature`, `oxygen_capacity`, `Ammonia`, `pH` FROM " +
                    "tb_dimension_date t4 ,under_water_date t3 WHERE `year` = :year AND `month` != :month AND `day` !=" +
                    ":day AND `hour` != :hour AND `minute` != -1 AND `second` != -1 AND t4.id = t3.id_date_dimension " +
                    "ORDER BY `year`, `month`, `day`, `hour`, `minute`, `second`; ";
        }else if(month.equals("-1")&&day.equals("-1")){
            sql = "SELECT `year` , `month`, `day`, `hour`, `minute`, `second`, `temperature`, `oxygen_capacity`, `Ammonia`, `pH` FROM " +
                    "tb_dimension_date t4 ,under_water_date t3 WHERE `year` = :year AND `month` != :month AND `day` !=" +
                    ":day AND `hour` = :hour AND `minute` != -1 AND `second` != -1AND t4.id = t3.id_date_dimension " +
                    "ORDER BY `year`, `month`, `day`, `hour`, `minute`, `second`; ";
        }else if(month.equals("-1")&&hour.equals("-1")){
            sql = "SELECT `year` , `month`, `day`, `hour`, `minute`, `second`, `temperature`, `oxygen_capacity`, `Ammonia`, `pH` FROM " +
                    "tb_dimension_date t4 ,under_water_date t3 WHERE `year` = :year AND `month` != :month AND `day` =" +
                    ":day AND `hour` != :hour AND `minute` != -1 AND `second` != -1 AND t4.id = t3.id_date_dimension " +
                    "ORDER BY `year`, `month`, `day`, `hour`, `minute`, `second`; ";
        }else if(day.equals("-1")&&hour.equals("-1")){
            sql = "SELECT `year` , `month`, `day`, `hour`, `minute`, `second`, `temperature`, `oxygen_capacity`, `Ammonia`, `pH` FROM " +
                    "tb_dimension_date t4 ,under_water_date t3 WHERE `year` = :year AND `month` = :month AND `day` !=" +
                    ":day AND `hour` != :hour AND `minute` != -1 AND `second` != -1 AND t4.id = t3.id_date_dimension " +
                    "ORDER BY `year`, `month`, `day`, `hour`, `minute`, `second`; ";
        }else if(month.equals("-1")){
            sql = "SELECT `year` , `month`, `day`, `hour`, `minute`, `second`, `temperature`, `oxygen_capacity`, `Ammonia`, `pH` FROM " +
                    "tb_dimension_date t4 ,under_water_date t3 WHERE `year` = :year AND `month` != :month AND `day` =" +
                    ":day AND `hour` = :hour AND `minute` != -1 AND `second` != -1 AND t4.id = t3.id_date_dimension " +
                    "ORDER BY `year`, `month`, `day`, `hour`, `minute`, `second`; ";
        }else if(hour.equals("-1")){
            sql = "SELECT `year` , `month`, `day`, `hour`, `minute`, `second`, `temperature`, `oxygen_capacity`, `Ammonia`, `pH` FROM " +
                    "tb_dimension_date t4 ,under_water_date t3 WHERE `year` = :year AND `month` = :month AND `day` =" +
                    ":day AND `hour` != :hour AND `minute` != -1 AND `second` != -1 AND t4.id = t3.id_date_dimension " +
                    "ORDER BY `year`, `month`, `day`, `hour`, `minute`, `second`; ";
        }else if(day.equals("-1")){
            sql = "SELECT `year` , `month`, `day`, `hour`, `minute`, `second`, `temperature`, `oxygen_capacity`, `Ammonia`, `pH` FROM " +
                    "tb_dimension_date t4 ,under_water_date t3 WHERE `year` = :year AND `month` = :month AND `day` !=" +
                    ":day AND `hour` = :hour AND `minute` != -1 AND `second` != -1 AND t4.id = t3.id_date_dimension " +
                    "ORDER BY `year`, `month`, `day`, `hour`, `minute`, `second`; ";
        }else {
            sql = "SELECT `year` , `month`, `day`, `hour`, `minute`, `second`, `temperature`, `oxygen_capacity`, `Ammonia`, `pH` FROM " +
                    "tb_dimension_date t4 ,under_water_date t3 WHERE `year` = :year AND `month` = :month AND `day` =" +
                    ":day AND `hour` = :hour AND `minute` != -1 AND `second` != -1 AND t4.id = t3.id_date_dimension " +
                    "ORDER BY `year`, `month`, `day`, `hour`, `minute`, `second`; ";
        }

        BeanPropertyRowMapper<CallLog> beanPropertyRowMapper = new BeanPropertyRowMapper<>(CallLog.class);
        List<CallLog> list = namedParameterJdbcTemplate.query(sql, paramsMap, beanPropertyRowMapper);
        return list;
    }
}
