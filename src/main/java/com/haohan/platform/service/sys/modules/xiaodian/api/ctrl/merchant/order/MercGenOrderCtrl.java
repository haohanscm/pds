package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.merchant.order;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.IpUtils;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import com.haohan.platform.service.sys.modules.sys.service.AreaService;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.MerchantAuth;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IDeliveryConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IUserConstant;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.IOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.BaseOrderReq;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.TerminalGoodsOrderReq;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.WebGoodsOrderApiReq;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.BaseOrderDetailReq;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.service.OrderDetailApiService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.CommunityManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryAddress;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DistrictManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAppManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.CommunityManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DeliveryAddressService;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DistrictManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAppManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 代客下单controller
 * @author shenyu
 * @create 2018/9/26
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/merchant/v2.0/order/genOrder")
public class MercGenOrderCtrl extends BaseController {
    @Autowired
    private UPassportService uPassportService;
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Resource
    @Qualifier("webOrderService")
    private IOrderService webOrderService;
    @Resource
    @Qualifier("terminalAppOrderService")
    private IOrderService terminalAppOrderService;
    @Autowired
    private MerchantAppManageService merchantAppManageService;
    @Autowired
    private OrderDetailApiService orderDetailApiService;
    @Autowired
    private GoodsOrderDetailService goodsOrderDetailService;
    @Autowired
    private DistrictManageService districtManageService;
    @Autowired
    private CommunityManageService communityManageService;

    //获取应用列表
    @RequestMapping(value = "fetchAppList")
    @ResponseBody
    public BaseResp fetchAppList(String merchantId,HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        List<MerchantAppManage> appManageList = merchantAppManageService.findByMerchantIdEnable(merchantId);
        ArrayList<MerchantAppManage> respList = appManageList.stream().collect(Collectors.toCollection(ArrayList::new));

        if (CollectionUtils.isEmpty(respList)){
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.success();
        baseResp.setExt(respList);
        return baseResp;
    }

    //新增用户
    @RequestMapping(value = "addMember")
    @ResponseBody
    public BaseResp addMember(String merchantId,String mobile,String name,HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        if (StringUtils.isBlank(mobile)){
            baseResp.setMsg("请输入正确的手机号");
            return baseResp;
        }
        if (StringUtils.isBlank(name)){
            baseResp.setMsg("名称不能为空");
            return baseResp;
        }

        if (null != uPassportService.fetchByMobile(mobile)){
            baseResp.error();
            baseResp.setMsg("用户已存在");
        }
        UPassport uPassport = new UPassport();
        uPassport.setMerchantId(merchantId);
        uPassport.setTelephone(mobile);
        uPassport.setLoginName(name);
        uPassport.setRegIp(IpUtils.getRemoteIpAddr(request));
        uPassport.setRegTime(new Date());
        uPassport.setRegType(IUserConstant.RegType.mobile.getCode());
        uPassport.setRegFrom(IUserConstant.RegFrom.web.getCode());
        uPassport.setStatus(IMerchantConstant.available);
        uPassport.setIsTest(ICommonConstant.YesNoType.no.getCode());
        uPassportService.save(uPassport);

        baseResp.success();
        baseResp.setExt(uPassport.getId());
        return baseResp;
    }


    //根据手机号或姓名查询
    @RequestMapping(value = "queryByMobile")
    @ResponseBody
    public BaseResp queryByMobile(String merchantId,String mobile, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        if (StringUtils.isEmpty(mobile)){
            baseResp.setMsg("请输入有效的参数");
            return baseResp;
        }

        UPassport uPassport = uPassportService.fetchByMobile(mobile);
        if (null == uPassport){
            baseResp.setMsg("用户不存在");
            return baseResp;
        }
        baseResp.success();
        baseResp.setExt(uPassport);
        return baseResp;
    }



    //获取客户列表
    @RequestMapping(value = "fetchMemberList")
    @ResponseBody
    public BaseResp fetchMemberList(Integer pageNo,Integer pageSize,String merchantId,HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        Page<UPassport> reqPage = new Page();
        reqPage.setPageNo(pageNo == null ? 1 : pageNo);
        reqPage.setPageSize(pageSize == null ? 30 : pageSize);

        UPassport uPassport = new UPassport();
        uPassport.setMerchantId(merchantId);
        Page<UPassport> respPage = uPassportService.findPage(reqPage,uPassport);

        BaseList<UPassport> baseList = new BaseList<>();
        baseList.setTotalRows(new Long(respPage.getCount()).intValue());
        baseList.setCurPage(respPage.getPageNo());
        baseList.setTotalPage(respPage.getTotalPage());
        baseList.setList(respPage.getList());
        baseList.setPageSize(respPage.getPageSize());
        return baseList;
    }


    //生成订单
    @RequestMapping(value = "createOrder")
    @ResponseBody
    public BaseResp createWebOrder(@RequestBody WebGoodsOrderApiReq webGoodsOrderApiReq) throws Exception{
        BaseResp baseResp = BaseResp.newError();
        String merchantId = webGoodsOrderApiReq.getMerchantId();

        //先检查订单类型,为当面付时不校验地址
        String facePayEnumCode = IOrderServiceConstant.OrderType.face_pay.getCode();
        if (!StringUtils.equals(facePayEnumCode,webGoodsOrderApiReq.getOrderType())){
            DeliveryAddress deliveryAddress = deliveryAddressService.get(webGoodsOrderApiReq.getAddressId());
            if (null != deliveryAddress){
                webGoodsOrderApiReq.setAddressId(deliveryAddress.getId());
            } else {
                baseResp.setMsg("下单地址不正确");
                return baseResp;
            }
        }

        GoodsOrder goodsOrder = new GoodsOrder();
        webGoodsOrderApiReq.setMerchantId(merchantId);
        baseResp = webOrderService.createOrder(baseResp,webGoodsOrderApiReq,goodsOrder);
        if (!baseResp.isSuccess()){
            return baseResp;
        }

        List<BaseOrderDetailReq> detailList = webGoodsOrderApiReq.getOrderDetails();
        for (BaseOrderDetailReq detailReq:detailList){
            baseResp = orderDetailApiService.check(baseResp,detailReq,goodsOrder);
            if (!baseResp.isSuccess()){
                return baseResp;
            }
            GoodsOrderDetail orderDetail = new GoodsOrderDetail();
            BeanUtils.copyProperties(orderDetail,detailReq);
            orderDetail.fromOrder(goodsOrder);
            goodsOrderDetailService.save(orderDetail);
        }
//        goodsOrder.setDeliveryType(detailList.get(0).getDeliveryType());
        goodsOrderService.save(goodsOrder);
        baseResp.setExt(goodsOrder.getOrderId());
        return baseResp;

    }

    //根据用户ID获取地址
    @RequestMapping(value = "fetchAddress")
    @ResponseBody
    public BaseResp fetchAddress(String merchantId,String uid,String mobile,HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        UPassport uPassport = uPassportService.get(uid);

        if (null == uPassport){
            baseResp.setMsg("用户不存在");
            return baseResp;
        }

        List<DeliveryAddress> addressList = deliveryAddressService.findByMercIdAndUid(merchantId,uid);
        if (CollectionUtils.isEmpty(addressList)){
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.success();
        baseResp.setExt(addressList.stream().collect(Collectors.toCollection(ArrayList::new)));
        return baseResp;
    }

    //添加地址
    @RequestMapping(value = "addAddress")
    @ResponseBody
    public BaseResp addAddress(DeliveryAddress deliveryAddress, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        String merchantId = deliveryAddress.getMerchantId();

        UPassport uPassport = uPassportService.get(deliveryAddress.getUuid());
        if (null == uPassport){
            baseResp.setMsg("获取用户信息失败");
            return baseResp;
        }
        String provinceCode = deliveryAddress.getProvince();
        String cityCode = deliveryAddress.getCity();
        String regionCode = deliveryAddress.getRegion();

        Area proArea = areaService.fetchByCode(provinceCode);
        Area cityArea = areaService.fetchByCode(cityCode);
        Area regionArea = areaService.fetchByCode(regionCode);
        DistrictManage districtManage = districtManageService.get(deliveryAddress.getDistrictArea());
        CommunityManage communityManage = communityManageService.get(deliveryAddress.getCommunityId());
        if (null == proArea || null == cityArea || null == regionArea || null == districtManage || null == communityManage){
            baseResp.setMsg("请求参数错误");
            return baseResp;
        }

        String noEnumCode = ICommonConstant.YesNoType.no.getCode();
        //保存新增的地址
        deliveryAddress.setMerchantId(merchantId);
        deliveryAddress.setUuid(uPassport.getId());
        deliveryAddress.setIsDefault(noEnumCode);
        deliveryAddress.setProvinceName(proArea.getName());
        deliveryAddress.setCityName(cityArea.getName());
        deliveryAddress.setRegionName(regionArea.getName());
        deliveryAddress.setDistrictAreaName(districtManage.getName());
        deliveryAddress.setCommunityName(communityManage.getName());
        deliveryAddressService.save(deliveryAddress);

        HashMap<String,String> respMap = new HashMap<>();
        respMap.put("uid",uPassport.getId());
        respMap.put("addressId",deliveryAddress.getId());

        baseResp.success();
        baseResp.setExt(respMap);
        return baseResp;
    }

//    /**
//     * 终端下单
//     * @param orderReq
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "terminalAddOrder")
//    @ResponseBody
//    public BaseResp createTerminalOrder(@Validated TerminalGoodsOrderReq orderReq, BindingResult bindingResult, HttpServletRequest request){
//        BaseResp baseResp = BaseResp.newError();
//        if (!validBindingResult(bindingResult,baseResp)){
//            return baseResp;
//        }
//        try {
//            GoodsOrder goodsOrder = new GoodsOrder();
//            baseResp = terminalAppOrderService.createOrder(baseResp, orderReq,goodsOrder);
//            if (!baseResp.isSuccess()){
//                return baseResp;
//            }
//
//            for (BaseOrderDetailReq detailReq: orderReq.getOrderDetails()){
//                baseResp = orderDetailApiService.check(baseResp, detailReq, goodsOrder);
//                if (!baseResp.isSuccess()){
//                    return baseResp;
//                }
//                GoodsOrderDetail orderDetail = new GoodsOrderDetail();
//                BeanUtils.copyProperties(orderDetail,detailReq);
//                orderDetail.fromOrder(goodsOrder);
//                goodsOrderDetailService.save(orderDetail);
//            }
//            goodsOrder.setDeliveryType(IDeliveryConstant.DeliveryType.self_delivery.getCode());
//            goodsOrderService.save(goodsOrder);
//            baseResp.setExt(goodsOrder.getOrderId());
//        }catch (Exception e){
//            e.printStackTrace();
//            baseResp.error();
//            baseResp.setMsg("系统错误");
//        }finally {
//            return baseResp;
//        }
//    }

}
