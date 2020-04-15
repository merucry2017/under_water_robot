package kv.key;

import kv.base.BaseDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class ContactDimension extends BaseDimension{
    private String temperature;
    private String oxygen_capacity;
    private String Ammonia_nitrogen_content;
    private String pH;

    public ContactDimension(){
        super();
    }

    public ContactDimension(String temperature, String oxygen_capacity, String ammonia_nitrogen_content, String pH) {
        super();
        this.temperature = temperature;
        this.oxygen_capacity = oxygen_capacity;
        Ammonia_nitrogen_content = ammonia_nitrogen_content;
        this.pH = pH;
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

    public String getAmmonia_nitrogen_content() {
        return Ammonia_nitrogen_content;
    }

    public void setAmmonia_nitrogen_content(String ammonia_nitrogen_content) {
        Ammonia_nitrogen_content = ammonia_nitrogen_content;
    }

    public String getpH() {
        return pH;
    }

    public void setpH(String pH) {
        this.pH = pH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDimension that = (ContactDimension) o;
        return Objects.equals(temperature, that.temperature) &&
                Objects.equals(oxygen_capacity, that.oxygen_capacity) &&
                Objects.equals(Ammonia_nitrogen_content, that.Ammonia_nitrogen_content) &&
                Objects.equals(pH, that.pH);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, oxygen_capacity, Ammonia_nitrogen_content, pH);
    }

    @Override
    public int compareTo(BaseDimension o) {
        ContactDimension anotherContactDimension = (ContactDimension) o;

        int result = this.temperature.compareTo(anotherContactDimension.temperature);
        if(result != 0) return result;

        result = this.oxygen_capacity.compareTo(anotherContactDimension.oxygen_capacity);
        if(result != 0) return result;

        result = this.Ammonia_nitrogen_content.compareTo(anotherContactDimension.Ammonia_nitrogen_content);
        if(result != 0) return result;

        result = this.pH.compareTo(anotherContactDimension.pH);
        if(result != 0) return result;
        return result;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.temperature);
        out.writeUTF(this.oxygen_capacity);
        out.writeUTF(this.Ammonia_nitrogen_content);
        out.writeUTF(this.pH);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.temperature = in.readUTF();
        this.oxygen_capacity = in.readUTF();
        this.Ammonia_nitrogen_content = in.readUTF();
        this.pH = in.readUTF();
    }
}
