package com.haohan.platform.service.sys.modules.xiaodian.filter;

import com.haohan.framework.utils.EnumUtils;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.service.BaseService;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.LogApiRecord;
import com.haohan.platform.service.sys.modules.xiaodian.entity.partner.PartnerApp;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.LogApiRecordService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 请求拦截处理类
 */
@Transactional(readOnly = false)
public class WebApiInterceptor extends BaseService implements HandlerInterceptor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LogApiRecordService logApiRecordService;

    private static final ThreadLocal<Long> startTimeThreadLocal =
            new NamedThreadLocal<Long>("ThreadLocal StartTime");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (logger.isDebugEnabled()){
            long beginTime = System.currentTimeMillis();//1、开始时间
            startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）
            logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS")
                    .format(beginTime), request.getRequestURI());
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type");
        response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
        
        Map<String, Object> reqParams =  CommonUtils.getRequestParameters(request);

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequestModel requestModel = new RequestModel();

            String productNo = request.getParameter("product");
            String platform = "";
            String productName = "";
            ICommonConstant.ProductSys productSys = (ICommonConstant.ProductSys)EnumUtils.getEnumByCode(ICommonConstant.ProductSys.class,productNo);
            if (null != productSys){
                platform = productSys.getProductType();
                productName = productSys.getDesc();
            }

            requestModel.setPlatform(platform);
            requestModel.setProduct(productName);
            requestModel.setVersion(request.getParameter("version"));
            requestModel.setSessionId(request.getParameter("sessionId"));
            requestModel.setStartMillis(System.currentTimeMillis());
            String reqUri = request.getRequestURI();
            requestModel.setReqUri(reqUri);

            if(reqUri.contains("xiaodian/api/pay")) {
                PartnerApp partnerApp = (PartnerApp) request.getAttribute("partnerApp");
                String reqBody = (String) request.getAttribute("reqBody");
                if(null != partnerApp){
                    requestModel.setPlatform(partnerApp.getPartnerName());
                    requestModel.setReqParams(reqBody);
                }
            }else{
                requestModel.setReqParams(JacksonUtils.toJson(reqParams));
            }

            requestModel.setHttpMethod(handlerMethod.getMethod().getName());
            requestModel.setReqTime(new Date());
            RequestModel.setRequestModel(requestModel);
        }

        // 官网的跨域设置
        String originHeader = request.getHeader("Origin");

       String isAccept = DictUtils.getDictValue(originHeader,"AccessOrigin","false");

       if(Boolean.valueOf(isAccept)){
           response.setHeader("Access-Control-Allow-Origin", originHeader);
       }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null){
            logger.info("ViewName: " + modelAndView.getViewName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {


      //记录日志
        RequestModel model = RequestModel.getRequestModel();
        if(null != model) {
            String reqUri = request.getRequestURI();
            if(reqUri.contains("xiaodian/api/pay")) {
                PartnerApp partnerApp = (PartnerApp) request.getAttribute("partnerApp");
                String reqBody = (String) request.getAttribute("reqBody");
                if(null != partnerApp){
                    model.setPlatform(partnerApp.getPartnerName());
                    model.setReqParams(reqBody);
                }
              model.setRespParams(request.getAttribute("respBody"));
            }
            if (reqUri.contains("pds/api/buyer/goods/queryList")){
                return;
            }

            LogApiRecord apiRecord = new LogApiRecord();
            BeanUtils.copyProperties(model, apiRecord);
            apiRecord.setHttpMethod(request.getMethod());
            apiRecord.setProduct(model.getProduct());
            apiRecord.setPlatform(model.getPlatform());
            apiRecord.setReqParams(model.getReqParams());
            String respParams;
            try {
                respParams = (String) model.getRespParams();
            } catch (ClassCastException e) {
                respParams = model.getRespParams().toString();
            }
            // respParams 存为 text
            if(StringUtils.isNotEmpty(respParams) && respParams.length()>60000){
                respParams = StringUtils.substring(respParams,0, 60000);
            }
            apiRecord.setRespParams(respParams);

            apiRecord.setReqTime(model.getReqTime());
            long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
            long endTime = System.currentTimeMillis(); 	//2、结束时间
            apiRecord.setCostTime((endTime-beginTime)+"ms");
            apiRecord.setRespTime(new Date());
            apiRecord.setSessionId(request.getRequestedSessionId());
            apiRecord.setReqId(IdGen.genByT(LogApiRecord.class));
            logApiRecordService.save(apiRecord);
            logger.debug("--req---uri:{}\n{}\n", model.getReqUri(), model.getReqParams());
            logger.debug("--resp---\n{}\n", apiRecord.getRespParams());


        }
        //移除线程局部变量,释放内存
        RequestModel.removeRequestModel();

        // 打印接口信息。
        if (logger.isDebugEnabled()){
            long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
            long endTime = System.currentTimeMillis(); 	//2、结束时间
            logger.debug("计时结束：{}  耗时：{}  URI: {}  ",
                    new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), (endTime - beginTime)+"ms", request.getRequestURI());
            //删除线程变量中的数据，防止内存泄漏
            startTimeThreadLocal.remove();
        }

    }

}
