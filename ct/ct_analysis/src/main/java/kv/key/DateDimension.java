package kv.key;

import kv.base.BaseDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class DateDimension extends BaseDimension {
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String second;

    public DateDimension(){
        super();
    }

    public DateDimension(String year, String month, String day, String hour, String minute, String second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateDimension that = (DateDimension) o;
        return Objects.equals(year, that.year) &&
                Objects.equals(month, that.month) &&
                Objects.equals(day, that.day) &&
                Objects.equals(hour, that.hour) &&
                Objects.equals(minute, that.minute) &&
                Objects.equals(second, that.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day, hour, minute, second);
    }

    @Override
    public int compareTo(BaseDimension o) {
        DateDimension anotherDateDimension = (DateDimension)o;
        int result = this.year.compareTo(anotherDateDimension.year);
        if(result != 0) return result;

        result = this.month.compareTo(anotherDateDimension.month);
        if(result != 0) return result;

        result = this.day.compareTo(anotherDateDimension.day);
        if(result != 0) return result;

        result = this.hour.compareTo(anotherDateDimension.hour);
        if(result != 0) return result;

        result = this.minute.compareTo(anotherDateDimension.minute);
        if(result != 0) return result;

        result = this.second.compareTo(anotherDateDimension.second);
        if(result != 0) return result;
        return result;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.year);
        out.writeUTF(this.month);
        out.writeUTF(this.day);
        out.writeUTF(this.hour);
        out.writeUTF(this.minute);
        out.writeUTF(this.second);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.year = in.readUTF();
        this.month = in.readUTF();
        this.day = in.readUTF();
        this.hour = in.readUTF();
        this.minute = in.readUTF();
        this.second = in.readUTF();
    }
}
