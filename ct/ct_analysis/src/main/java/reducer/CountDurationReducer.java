package reducer;

import kv.key.ComDimension;
import kv.value.CountDurationValue;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class CountDurationReducer extends Reducer<ComDimension, Text, ComDimension, CountDurationValue>{
    CountDurationValue countDurationValue = new CountDurationValue();
    @Override
    protected void reduce(ComDimension key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int Sum = 0;

        for(Text t : values){
            Sum++;
        }
        countDurationValue.setSum(String.valueOf(Sum));

        context.write(key, countDurationValue);
    }
}
