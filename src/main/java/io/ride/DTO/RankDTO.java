package io.ride.DTO;

public class RankDTO {
    private String item;    // 选项名称
    private String desc;    // 该选项得票情况
    private double per;     // 得票百分比

    public RankDTO() {
    }

    public RankDTO(String item, String desc, double per) {
        this.item = item;
        this.desc = desc;
        this.per = per;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPer() {
        return per;
    }

    public void setPer(double per) {
        this.per = per;
    }

    @Override
    public String toString() {
        return "RankDTO{" +
                "item='" + item + '\'' +
                ", desc='" + desc + '\'' +
                ", per=" + per +
                '}';
    }

}
