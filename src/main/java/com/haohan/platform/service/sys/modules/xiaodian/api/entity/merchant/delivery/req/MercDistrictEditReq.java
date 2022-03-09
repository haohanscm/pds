package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.req;

/**
 * @author shenyu
 * @create 2018/9/29
 */
public class MercDistrictEditReq {
    private String id;
    private String name;		// 片区名称
    private String province;		// 省
//    private String provinceName;	//省名称
    private String city;		// 市
//    private String cityName;	//市名称
    private String region;		// 区县
//    private String regionName;	//区县名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
