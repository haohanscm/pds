package com.haohan.platform.service.sys.modules.pds.api.entity.resp.buyer;

/**
 * @author shenyu
 * @create 2018/12/13
 */
public class PdsApiCollectGoodsResp extends PdsTopNGoodsResp {
    private String collectId;   //收藏ID

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }
}
