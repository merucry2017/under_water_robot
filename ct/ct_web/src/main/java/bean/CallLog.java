package bean;

/**
 * 用于存放返回给用户的数据
 */
public class CallLog {
    private String id_date_dimension;
    private String temperature;
    private String oxygen_capacity;
    private String Ammonia;
    private String pH;
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String second;

    public String getId_date_dimension() {
        return id_date_dimension;
    }

    public void setId_date_dimension(String id_date_dimension) {
        this.id_date_dimension = id_date_dimension;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getOxygen_capacity() {
        return oxygen_capacity;
    }

    public void setOxygen_capacity(String oxygen_capacity) {
        this.oxygen_capacity = oxygen_capacity;
    }

    public String getAmmonia() {
        return Ammonia;
    }

    public void setAmmonia(String ammonia) {
        Ammonia = ammonia;
    }

    public String getpH() {
        return pH;
    }

    public void setpH(String pH) {
        this.pH = pH;
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
    public String toString() {
        return getClass().getName()+"{" +
                "id_date_dimension='" + id_date_dimension + '\'' +
                ", temperature='" + temperature + '\'' +
                ", oxygen_capacity='" + oxygen_capacity + '\'' +
                ", Ammonia='" + Ammonia + '\'' +
                ", pH='" + pH + '\'' +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                ", second='" + second + '\'' +
                '}';
    }
}
