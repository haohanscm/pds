package com.haohan.platform.service.sys.modules.pds.api.admin;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.beanvalidator.DefaultGroup;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBaseApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBuyerApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsSupplierListApiReq;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoods;
import com.haohan.platform.service.sys.modules.pds.entity.cost.GoodsLoss;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.cost.GoodsLossService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.MerchantEmployeeService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsModelService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2018/11/1
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/admin/common")
public class PdsAdminCommonCtrl extends BaseController {
    @Autowired
    private PdsSupplierService pdsSupplierService;
    @Autowired
    private PdsBuyerService pdsBuyerService;
    @Autowired
    private GoodsLossService goodsLossService;
    @Autowired
    private MerchantEmployeeService merchantEmployeeService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private GoodsModelService goodsModelService;

    /**
     * ?????????????????????  ???goodsId??????????????????
     *
     * @param supplierListReq
     * @param bindingResult
     * @param request
     * @return
     */
    @RequestMapping(value = "supplier/list")
    @ResponseBody
    public BaseResp fetchSupplierList(@Validated(DefaultGroup.class) PdsSupplierListApiReq supplierListReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        String goodsId = supplierListReq.getGoodsId();
        String pmId = supplierListReq.getPmId();
        List<PdsSupplier> pdsSupplierList;
        PdsSupplier pdsSupplier;
        String status = ICommonConstant.YesNoType.yes.getCode();
        if (StringUtils.isEmpty(goodsId)) {
            // ????????????????????????
            pdsSupplier = new PdsSupplier();
            pdsSupplier.setPmId(pmId);
            pdsSupplier.setStatus(status);
            pdsSupplierList = pdsSupplierService.findList(pdsSupplier);
            if (CollectionUtils.isEmpty(pdsSupplierList)) {
                baseResp.setMsg("????????????????????????");
                return baseResp;
            }
        } else {
            // goodsId(sku) ??????
            // ?????????????????????????????????????????????????????????(supplyPrice);
            // ???????????????????????????,???????????????????????????,??????????????????;
            SupplierGoods supplierGoods = new SupplierGoods();
            supplierGoods.setPmId(pmId);
            supplierGoods.setGoodsModelId(goodsId);
            pdsSupplierList = pdsSupplierService.findListWithSupplyPrice(supplierGoods);
            if (Collections3.isEmpty(pdsSupplierList)) {
                pdsSupplier = new PdsSupplier();
                pdsSupplier.setPmId(pmId);
                pdsSupplier.setStatus(status);
                pdsSupplier.setSupplierType(IPdsConstant.SupplierType.stock.getCode());
                // ????????????????????? 1???
                pdsSupplierList = pdsSupplierService.findList(pdsSupplier);
                if (CollectionUtils.isEmpty(pdsSupplierList)) {
                    baseResp.setMsg("??????????????????????????????");
                    return baseResp;
                }
                GoodsModel goodsModel = goodsModelService.get(goodsId);
                if (null != goodsModel) {
                    pdsSupplierList.get(0).setSupplyPrice(goodsModel.getModelPrice());
                }
            }
        }
        baseResp.success();
        baseResp.setExt(new ArrayList<>(pdsSupplierList));
        return baseResp;
    }

    //?????????????????????
    @RequestMapping(value = "buyer/list")
    @ResponseBody
    public BaseResp fetchBuyerList(@Validated(DefaultGroup.class) PdsBuyerApiReq apiBuyerReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }

        PdsBuyer pdsBuyer = new PdsBuyer();
        pdsBuyer.setPmId(apiBuyerReq.getPmId());
        pdsBuyer.setMerchantId(apiBuyerReq.getBuyerMerchantId());
        List<PdsBuyer> pdsBuyerList = pdsBuyerService.findUsableList(pdsBuyer);
        if (CollectionUtils.isEmpty(pdsBuyerList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }

        baseResp.success();
        baseResp.setExt(new ArrayList<>(pdsBuyerList));
        return baseResp;
    }

    //??????????????? ?????????
    @RequestMapping(value = "goods/fetchLoss")
    @ResponseBody
    public BaseResp fetchLoss(HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        String goodsId = request.getParameter("goodsId");
        if (StringUtils.isEmpty(goodsId)) {
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }

        GoodsLoss goodsLoss = goodsLossService.fetchByGoodsId(goodsId);
        BigDecimal lossPercent = BigDecimal.ZERO;
        if (null != goodsLoss && null != goodsLoss.getLossPercent()) {
            lossPercent = goodsLoss.getLossPercent();
        }

        baseResp.success();
        baseResp.setExt(lossPercent);
        return baseResp;
    }

    //??????????????????
    @RequestMapping(value = "driver/list")
    @ResponseBody
    public BaseResp driverList(@Validated(DefaultGroup.class) PdsBaseApiReq apiBaseReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }

        MerchantEmployee merchantEmployee = new MerchantEmployee();
        merchantEmployee.setPmId(apiBaseReq.getPmId());
        merchantEmployee.setRole(IPdsConstant.EmployeeRole.driver.getCode());
        merchantEmployee.setStatus(ICommonConstant.IsEnable.enable.getCode());
        List<MerchantEmployee> list = merchantEmployeeService.findList(merchantEmployee);
        if (CollectionUtils.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }

        baseResp.setExt(list.stream().collect(Collectors.toCollection(ArrayList::new)));
        baseResp.success();
        return baseResp;
    }

    @RequestMapping(value = "pmList")
    @ResponseBody
    public BaseResp pmList(HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        Merchant merchant = new Merchant();
        merchant.setPdsType(IMerchantConstant.PdsType.platform.getType());
        merchant.setStatus(Integer.valueOf(IMerchantConstant.available));
        List<Merchant> merchantList = merchantService.findList(merchant);
        if (CollectionUtils.isEmpty(merchantList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.setExt(merchantList.stream().collect(Collectors.toCollection(ArrayList::new)));
        baseResp.success();
        return baseResp;
    }

}
