package kv.value;

import kv.base.BaseValue;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CountDurationValue extends BaseValue{
    private String Sum;

    public CountDurationValue(){
        super();
    }

    public String getSum() {
        return Sum;
    }

    public void setSum(String sum) {
        this.Sum = sum;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(Sum);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.Sum = in.readUTF();
    }
}
