package com.haohan.platform.service.sys.modules.pds.api.common;

import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.IpUtils;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.dto.ShopDTO;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.common.FeiePrinterReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.common.PdsCommonAreaListReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.common.PdsPrinterQueryApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.common.PdsTextPrintApiReq;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.common.IPdsCommonService;
import com.haohan.platform.service.sys.modules.pds.core.common.IPdsCommonShopService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import com.haohan.platform.service.sys.modules.sys.security.SystemAuthorizingRealm;
import com.haohan.platform.service.sys.modules.sys.utils.UserUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.inf.IYiCloudPrintService;
import com.haohan.platform.service.sys.modules.weixin.open.api.service.WxOpenApiService;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IOpenPlatformUserService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IShopConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.CloudPrintTerminal;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.FeiePrinter;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
import com.haohan.platform.service.sys.modules.xiaodian.service.UserOpenPlatformService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.printer.CloudPrintTerminalService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ????????????
 *
 * @author shenyu
 * @create 2018/10/19
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/common")
public class PdsCommonCtrl extends BaseController implements IPdsConstant {

    @Autowired
    private IPdsCommonService pdsCommonService;
    @Autowired
    @Lazy(true)
    private UPassportService uPassportService;
    @Autowired
    @Lazy(true)
    private PdsBuyerService buyerService;
    @Autowired
    @Lazy(true)
    private PdsSupplierService supplierService;
    @Autowired
    @Lazy(true)
    private AuthAppService authAppService;
    @Autowired
    @Lazy(true)
    private WxOpenApiService wxOpenApiService;
    @Autowired
    @Lazy(true)
    private UserOpenPlatformService userOpenPlatformService;
    @Autowired
    @Lazy(true)
    private ShopService shopService;
    @Autowired
    private IYiCloudPrintService yiCloudPrintService;
    @Autowired
    private CloudPrintTerminalService cloudPrintTerminalService;

    @Autowired
    private IOpenPlatformUserService openPlatformUserService;
    @Autowired
    @Lazy(true)
    private IPdsCommonShopService commonShopService;

    /**
     * ????????????????????????????????????
     *
     * @return
     */
    @RequestMapping(value = "addAndCheck")
    @ResponseBody
    public BaseResp addAndCheck(HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        Map<String, Object> reqParams = getRequestParameters(request);
        String appId = (String) reqParams.get("appId");
        String code = (String) reqParams.get("code");
        if (StringUtils.isAnyBlank(appId, code)) {
            baseResp.setMsg("??????????????????appId/code");
            return baseResp;
        }
        //??????????????????
        String userInfo = (String) reqParams.get("rawData");
        String encryptedData = (String) reqParams.get("encryptedData");
        String signature = (String) reqParams.get("signature");
        String iv = (String) reqParams.get("iv");
        String regIp = IpUtils.getRemoteIpAddr(request);
        BaseResp resp = openPlatformUserService.openUserAdd(appId, code, userInfo, encryptedData, signature, iv, regIp);
        if (!resp.isSuccess()) {
            return resp;
        }
        // ?????????  uid  buyer flag shopId
        HashMap<String, Object> result = new HashMap<>(8);
        String uid = (String) resp.getExt();
        result.put("uid", uid);

        boolean flag = false;
        String pmId = "";
        PdsBuyer buyer = buyerService.fetchByPassPortId(uid, pmId, "");
        if (null != buyer) {
            flag = true;
            pmId = buyer.getPmId();
            result.put("buyer", buyer);
        }
        result.put("flag", flag);
        // ???????????????????????????????????????
        // ??????????????????????????????????????????????????????
        ShopDTO shop = commonShopService.fetchPmShop(pmId);
        result.put("shop", shop);
        baseResp.success();
        baseResp.setExt(result);
        return baseResp;
    }

    /**
     * ????????? ?????????????????????   ?????????????????????
     *
     * @return
     */
    @RequestMapping(value = "uidCheck")
    @ResponseBody
    public BaseResp uidCheck(String uid, HttpServletResponse response) {

        BaseResp baseResp = BaseResp.newError();
        //req-uid
        if (StringUtils.isEmpty(uid)) {
            baseResp.setMsg("??????????????????uid");
            return baseResp;
        }
        HashMap<String, Object> result = new HashMap<>(8);
        boolean flag = false;
        String name = "";
        String pmId = "";

        PdsBuyer buyer = buyerService.fetchByPassPortId(uid, pmId, "");
        if (null != buyer) {
            flag = true;
            result.put("buyer", buyer);
            name = buyer.getBuyerName();
            pmId = buyer.getPmId();
        } else {
            PdsSupplier supplier = supplierService.fetchByPassPortId(uid, pmId);
            if (null != supplier) {
                flag = true;
                result.put("supplier", supplier);
                name = supplier.getSupplierName();
                pmId = supplier.getPmId();
            }
        }
        // ????????????
        if (flag) {
            // ????????????????????????
//            User user = new User();
//            user.setName(name);
//
//            String id = takeSession(user);
//            // ?????????????????????session
//            result.put(TOKEN_KEY, id);
//            result.put("token", id);
        }
        result.put("result", flag);
        // ???????????????????????????????????????
        Shop shop = new Shop();
        shop.setMerchantId(pmId);
        shop.setShopLevel(IShopConstant.ShopLevelType.pds.getCode());
        List<Shop> shopList = shopService.fetchByMerchantIdEnable(shop);
        if (!Collections3.isEmpty(shopList)) {
            result.put("shopId", shopList.get(0).getId());
        }
        baseResp.success();
        baseResp.setExt(result);
        return baseResp;
    }

    /**
     * ??????session
     *
     * @param user
     * @return
     */
    private String takeSession(User user) {
        // ??????shiro???session  cookie????????? haohan.session.id
        Session session = UserUtils.getSession();
        String id = (String) session.getId();
        Object obj = JedisUtils.getObject(id);
        if (null == obj) {
            SystemAuthorizingRealm.Principal principal = new SystemAuthorizingRealm.Principal(user, true);
            obj = principal;
        }
        // ????????????
        Long time = JedisUtils.ttl(id);
        if (null != time && time < EXPIRE_TIME / 2) {
            JedisUtils.setObject(id, obj, EXPIRE_TIME);
            session.setTimeout(EXPIRE_TIME * 1000);
        }
        return id;
    }

    /**
     * ?????? ?????????/????????? ??????
     *
     * @return
     */
    @RequestMapping(value = "bindTel")
    @ResponseBody
    public BaseResp bindTel(String uid, String telephone, String validCode) {

        BaseResp baseResp = BaseResp.newError();
        //??????????????????
        if (StringUtils.isAnyEmpty(uid, telephone, validCode)) {
            baseResp.setMsg("??????????????????uid/telephone/validCode");
            return baseResp;
        }
        UPassport uPassport = uPassportService.get(uid);
        if (null == uPassport) {
            baseResp.setMsg("???????????????uid??????");
            return baseResp;
        }
        //???????????????
        String code = JedisUtils.get(telephone);
        if (!StringUtils.equals(validCode, code)) {
            baseResp.setMsg("???????????????");
            return baseResp;
        }
        return findTelAndBind(uid, telephone, uPassport, baseResp);
    }

    /**
     * ?????? ?????????/????????? ??????
     * ??????2: ??????????????????????????????????????????
     *
     * @return
     */
    @RequestMapping(value = "bindTel2")
    @ResponseBody
    public BaseResp bindTel2(String appId, String uid, String encryptedData, String iv) {
        BaseResp baseResp = BaseResp.newError();
        //??????????????????
        if (StringUtils.isAnyEmpty(appId, uid, encryptedData, iv)) {
            baseResp.setMsg("??????????????????appId/uid/encryptedData/iv");
            return baseResp;
        }
        UPassport uPassport = uPassportService.get(uid);
        if (null == uPassport) {
            baseResp.setMsg("???????????????uid??????");
            return baseResp;
        }
        // ??????
        AuthApp authApp = authAppService.fetchByAppId(appId);
        if (null == authApp) {
            baseResp.setMsg("appId??????");
            return baseResp;
        }
        UserOpenPlatform userOpenPlatform = userOpenPlatformService.fetchByAppIdAndUid(appId, uid);
        if (null == userOpenPlatform) {
            baseResp.setMsg("appId/uid??????");
            return baseResp;
        }
        // ?????????????????????
        String telephone;
        try {
            WxOpenApiService apiService = wxOpenApiService.fetchByAppId(authApp.getAuthAppId());
            WxMaUserService wxMaUserService = apiService.getWxOpenComponentService().getWxMaServiceByAppid(appId).getUserService();
            WxMaPhoneNumberInfo wxMaPhoneNumberInfo = wxMaUserService.getPhoneNoInfo(userOpenPlatform.getAccessToken(), encryptedData, iv);
            telephone = wxMaPhoneNumberInfo.getPurePhoneNumber();
        } catch (Exception e) {
            // ????????????
            baseResp.setMsg("???????????????");
            return baseResp;
        }

        // ??????????????????/?????????
        return findTelAndBind(uid, telephone, uPassport, baseResp);
    }

    /**
     * ???????????? ???????????????/???????????? ?????????uid
     *
     * @param uid
     * @param telephone
     * @param uPassport
     * @param baseResp
     * @return
     */
    private BaseResp findTelAndBind(String uid, String telephone, UPassport uPassport, BaseResp baseResp) {
        HashMap<String, Object> result = new HashMap<>(8);
        //???????????????????????????  ???????????????
        PdsBuyer buyer = buyerService.fetchByMobile(telephone);
        // ??????????????????
        boolean flag = false;

        if (null != buyer) {
            buyer.setPassportId(uid);
            buyerService.save(buyer);
            flag = true;
            result.put("buyer", buyer);
        }
        // ?????????????????? ???????????????
        if (!flag) {
            PdsSupplier supplier = supplierService.fetchByMobile(telephone);
            if (null != supplier) {
                supplier.setPassportId(uid);
                supplierService.save(supplier);
                flag = true;
                result.put("supplier", supplier);
            }
        }
        if (flag) {
            // ???????????????
            uPassport.setTelephone(telephone);
            uPassportService.save(uPassport);
            baseResp.success();
            baseResp.setExt(result);
        }
        return baseResp;
    }


    /**
     * ?????????????????????
     *
     * @return
     */
    @RequestMapping(value = "printer/fetchPrinterList")
    @ResponseBody
    public BaseResp fetchPrinterList(FeiePrinterReq feiePrinterReq) {
        BaseResp baseResp = BaseResp.newError();
        // ????????????
        if (StringUtils.isAnyEmpty(feiePrinterReq.getShopId(), feiePrinterReq.getPmId())) {
            baseResp.setMsg("????????????shopId/pmId");
            return baseResp;
        }

        // ????????????
        int pageNo = StringUtils.toInteger(feiePrinterReq.getPageNo());
        int pageSize = StringUtils.toInteger(feiePrinterReq.getPageSize());
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;
        FeiePrinter feiePrinter = new FeiePrinter();

        // ????????????
        feiePrinter.setMerchantId(feiePrinterReq.getPmId());
        feiePrinter.setShopId(feiePrinterReq.getShopId());
        feiePrinter.setPrinterSn(feiePrinterReq.getPrinterSn());
        feiePrinter.setPrinterName(feiePrinterReq.getPrinterName());
        feiePrinter.setUseable(feiePrinterReq.getUseable());

        // ??????????????????
        Page<FeiePrinter> page = new Page<>(pageNo, pageSize);
        feiePrinter.setPage(page);

        baseResp = pdsCommonService.fetchPrinterList(feiePrinter);
        return baseResp;
    }

    @RequestMapping(value = "printer/yiPrinterList")
    @ResponseBody
    public BaseResp cloudPrinterList(@Validated PdsPrinterQueryApiReq printerQueryReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();

        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        String shopId = printerQueryReq.getShopId();
        String pmId = printerQueryReq.getPmId();

        baseResp = pdsCommonService.findYiPrinterList(baseResp, shopId, pmId);
        return baseResp;
    }


    @RequestMapping(value = "printer/textPrint")
    @ResponseBody
    public BaseResp textPrint(@Validated PdsTextPrintApiReq apiTextPrintReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        String machineCode = apiTextPrintReq.getMachineCode();
        String orderId = apiTextPrintReq.getOrderId();
        String content = apiTextPrintReq.getContent();
        CloudPrintTerminal cloudPrintTerminal = cloudPrintTerminalService.fetchByMachineCode(machineCode);
        if (null == cloudPrintTerminal || StringUtils.isEmpty(cloudPrintTerminal.getClientId())) {
            baseResp.setMsg("????????????????????????");
            return baseResp;
        }

        baseResp = yiCloudPrintService.textPrint(cloudPrintTerminal.getClientId(), content, orderId, machineCode);
        return baseResp;
    }

    /**
     * ????????????
     *
     * @return
     */
    @RequestMapping(value = "area/findList")
    @ResponseBody
    public BaseResp fetchAreaList(@Validated PdsCommonAreaListReq listReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        Area area = new Area();
        area.setName(listReq.getName());
        area.setCode(listReq.getCode());
        area.setType(listReq.getType());
        String parentId = listReq.getParentId();
        if (StringUtils.isNotEmpty(parentId)) {
            area.setParent(new Area(parentId));
        }
        area.setParentIds(listReq.getParentIds());
        baseResp = pdsCommonService.fetchAreaList(area);
        return baseResp;
    }

}
