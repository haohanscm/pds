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
 * 公共接口
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
     * 判断小程序用户是否采购商
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
            baseResp.setMsg("缺少请求参数appId/code");
            return baseResp;
        }
        //设置用户信息
        String userInfo = (String) reqParams.get("rawData");
        String encryptedData = (String) reqParams.get("encryptedData");
        String signature = (String) reqParams.get("signature");
        String iv = (String) reqParams.get("iv");
        String regIp = IpUtils.getRemoteIpAddr(request);
        BaseResp resp = openPlatformUserService.openUserAdd(appId, code, userInfo, encryptedData, signature, iv, regIp);
        if (!resp.isSuccess()) {
            return resp;
        }
        // 返回值  uid  buyer flag shopId
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
        // 返回平台商家的采购配送店铺
        // 未校验成功时返回默认平台商家店铺信息
        ShopDTO shop = commonShopService.fetchPmShop(pmId);
        result.put("shop", shop);
        baseResp.success();
        baseResp.setExt(result);
        return baseResp;
    }

    /**
     * 判断是 采购商或供应商   该接口取消使用
     *
     * @return
     */
    @RequestMapping(value = "uidCheck")
    @ResponseBody
    public BaseResp uidCheck(String uid, HttpServletResponse response) {

        BaseResp baseResp = BaseResp.newError();
        //req-uid
        if (StringUtils.isEmpty(uid)) {
            baseResp.setMsg("缺少请求参数uid");
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
        // 验证成功
        if (flag) {
            // 目前小程序无校验
//            User user = new User();
//            user.setName(name);
//
//            String id = takeSession(user);
//            // 目前改为只使用session
//            result.put(TOKEN_KEY, id);
//            result.put("token", id);
        }
        result.put("result", flag);
        // 返回平台商家的采购配送店铺
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
     * 处理session
     *
     * @param user
     * @return
     */
    private String takeSession(User user) {
        // 利用shiro的session  cookie中存为 haohan.session.id
        Session session = UserUtils.getSession();
        String id = (String) session.getId();
        Object obj = JedisUtils.getObject(id);
        if (null == obj) {
            SystemAuthorizingRealm.Principal principal = new SystemAuthorizingRealm.Principal(user, true);
            obj = principal;
        }
        // 更新时间
        Long time = JedisUtils.ttl(id);
        if (null != time && time < EXPIRE_TIME / 2) {
            JedisUtils.setObject(id, obj, EXPIRE_TIME);
            session.setTimeout(EXPIRE_TIME * 1000);
        }
        return id;
    }

    /**
     * 绑定 采购商/供应商 手机
     *
     * @return
     */
    @RequestMapping(value = "bindTel")
    @ResponseBody
    public BaseResp bindTel(String uid, String telephone, String validCode) {

        BaseResp baseResp = BaseResp.newError();
        //请求参数验证
        if (StringUtils.isAnyEmpty(uid, telephone, validCode)) {
            baseResp.setMsg("缺少请求参数uid/telephone/validCode");
            return baseResp;
        }
        UPassport uPassport = uPassportService.get(uid);
        if (null == uPassport) {
            baseResp.setMsg("手机号绑定uid错误");
            return baseResp;
        }
        //验证码验证
        String code = JedisUtils.get(telephone);
        if (!StringUtils.equals(validCode, code)) {
            baseResp.setMsg("验证码错误");
            return baseResp;
        }
        return findTelAndBind(uid, telephone, uPassport, baseResp);
    }

    /**
     * 绑定 采购商/供应商 手机
     * 方法2: 通过调用微信的获取手机号功能
     *
     * @return
     */
    @RequestMapping(value = "bindTel2")
    @ResponseBody
    public BaseResp bindTel2(String appId, String uid, String encryptedData, String iv) {
        BaseResp baseResp = BaseResp.newError();
        //请求参数验证
        if (StringUtils.isAnyEmpty(appId, uid, encryptedData, iv)) {
            baseResp.setMsg("缺少请求参数appId/uid/encryptedData/iv");
            return baseResp;
        }
        UPassport uPassport = uPassportService.get(uid);
        if (null == uPassport) {
            baseResp.setMsg("手机号绑定uid错误");
            return baseResp;
        }
        // 验证
        AuthApp authApp = authAppService.fetchByAppId(appId);
        if (null == authApp) {
            baseResp.setMsg("appId有误");
            return baseResp;
        }
        UserOpenPlatform userOpenPlatform = userOpenPlatformService.fetchByAppIdAndUid(appId, uid);
        if (null == userOpenPlatform) {
            baseResp.setMsg("appId/uid有误");
            return baseResp;
        }
        // 解密获取手机号
        String telephone;
        try {
            WxOpenApiService apiService = wxOpenApiService.fetchByAppId(authApp.getAuthAppId());
            WxMaUserService wxMaUserService = apiService.getWxOpenComponentService().getWxMaServiceByAppid(appId).getUserService();
            WxMaPhoneNumberInfo wxMaPhoneNumberInfo = wxMaUserService.getPhoneNoInfo(userOpenPlatform.getAccessToken(), encryptedData, iv);
            telephone = wxMaPhoneNumberInfo.getPurePhoneNumber();
        } catch (Exception e) {
            // 解密失败
            baseResp.setMsg("手机号有误");
            return baseResp;
        }

        // 验证是采购商/供应商
        return findTelAndBind(uid, telephone, uPassport, baseResp);
    }

    /**
     * 按手机号 查找采购商/供应商表 并设置uid
     *
     * @param uid
     * @param telephone
     * @param uPassport
     * @param baseResp
     * @return
     */
    private BaseResp findTelAndBind(String uid, String telephone, UPassport uPassport, BaseResp baseResp) {
        HashMap<String, Object> result = new HashMap<>(8);
        //验证手机号是否合法  查采购商表
        PdsBuyer buyer = buyerService.fetchByMobile(telephone);
        // 绑定成功标志
        boolean flag = false;

        if (null != buyer) {
            buyer.setPassportId(uid);
            buyerService.save(buyer);
            flag = true;
            result.put("buyer", buyer);
        }
        // 不是采购商时 查供应商表
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
            // 绑定手机号
            uPassport.setTelephone(telephone);
            uPassportService.save(uPassport);
            baseResp.success();
            baseResp.setExt(result);
        }
        return baseResp;
    }


    /**
     * 飞鹅打印机列表
     *
     * @return
     */
    @RequestMapping(value = "printer/fetchPrinterList")
    @ResponseBody
    public BaseResp fetchPrinterList(FeiePrinterReq feiePrinterReq) {
        BaseResp baseResp = BaseResp.newError();
        // 参数验证
        if (StringUtils.isAnyEmpty(feiePrinterReq.getShopId(), feiePrinterReq.getPmId())) {
            baseResp.setMsg("缺少参数shopId/pmId");
            return baseResp;
        }

        // 分页查询
        int pageNo = StringUtils.toInteger(feiePrinterReq.getPageNo());
        int pageSize = StringUtils.toInteger(feiePrinterReq.getPageSize());
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;
        FeiePrinter feiePrinter = new FeiePrinter();

        // 查询条件
        feiePrinter.setMerchantId(feiePrinterReq.getPmId());
        feiePrinter.setShopId(feiePrinterReq.getShopId());
        feiePrinter.setPrinterSn(feiePrinterReq.getPrinterSn());
        feiePrinter.setPrinterName(feiePrinterReq.getPrinterName());
        feiePrinter.setUseable(feiePrinterReq.getUseable());

        // 传入参数处理
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
            baseResp.setMsg("打印机配置不正确");
            return baseResp;
        }

        baseResp = yiCloudPrintService.textPrint(cloudPrintTerminal.getClientId(), content, orderId, machineCode);
        return baseResp;
    }

    /**
     * 区域列表
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
