<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--<%@ page import="com.hisun.iposm.*"%>--%>
<%@ page import="com.hyt.cap.sdk.HytConfig"%>
<%@ page import="com.hyt.cap.sdk.HytUtil"%>
<%@ page import="com.hyt.cap.sdk.RSASignUtil,java.io.IOException"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="java.util.Map"%>
<html>
<head>
<title>OEM商户入驻:9007</title>
<link href="sdk.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body>
	<%
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                        //商户会计日期（自定义）
                        String partner_ac_date = sdf.format(new Date()).substring(0, 8);
                      //获取表单提交的各参数
                      request.setCharacterEncoding("gb2312");
                    String merc_typ = request.getParameter("merc_typ");
                    String merc_nm = request.getParameter("merc_nm");
                    String merc_trd_cls = request.getParameter("merc_trd_cls");
                    String merc_sub_cls = request.getParameter("merc_sub_cls");
                    String merc_third_cls = request.getParameter("merc_third_cls");
                    String merc_trird_cls_ali = request.getParameter("merc_trird_cls_ali");
                    String crp_nm = request.getParameter("crp_nm");
                    String crp_id_no = request.getParameter("crp_id_no");
                    String reg_id = request.getParameter("reg_id");
                    String organization_code = request.getParameter("organization_code");
                    String bus_addr = request.getParameter("bus_addr");
                    String merc_prov = request.getParameter("merc_prov");
                    String merc_city = request.getParameter("merc_city");
                    String agr_no = request.getParameter("agr_no");
                    String usr_opr_nm = request.getParameter("usr_opr_nm");
                    String usr_opr_mbl = request.getParameter("usr_opr_mbl");
                    String stl_perd = request.getParameter("stl_perd");
                    String stl_day_flg = request.getParameter("stl_day_flg");
                    String wc_bnk = request.getParameter("wc_bnk");
                    String opn_bnk_prov = request.getParameter("opn_bnk_prov");
                    String opn_bnk_city = request.getParameter("opn_bnk_city");
                    String lbnk_nm = request.getParameter("lbnk_nm");
                    String wc_lbnk_no = request.getParameter("wc_lbnk_no");
                    String bnk_acnm = request.getParameter("bnk_acnm");
                    String stl_oac = request.getParameter("stl_oac");
                    String bus_psn_flg = request.getParameter("bus_psn_flg");
                    
        			//加载配置文件
                       HytConfig.getConfig().loadPropertiesFromSrc();
                       String partner_id = HytConfig.getConfig().getMerchantId();
                       
                       String charset = "GBK";
                       String service = "9007";
                        Map req = new HashMap();
                        req .put("char_set","00");
             			req .put("version_no","1.0");
             			req .put("sign_type","RSA");
             			req .put("biz_type",service);
             			req .put("partner_id",partner_id);
             			req .put("merc_typ",merc_typ);
             			req .put("merc_nm",merc_nm);
             			req .put("merc_trd_cls",merc_trd_cls);
             			req .put("merc_sub_cls",merc_sub_cls);
             			req .put("merc_third_cls",merc_third_cls);
             			req .put("merc_trird_cls_ali",merc_trird_cls_ali);
             			req .put("crp_nm",crp_nm);
             			req .put("crp_id_no",crp_id_no);
             			req .put("reg_id",reg_id);
             			req .put("organization_code",organization_code);
             			req .put("bus_addr",bus_addr);
             			req .put("merc_prov",merc_prov);
             			req .put("merc_city",merc_city);
             			req .put("agr_no",agr_no);
             			req .put("usr_opr_nm",usr_opr_nm);
             			req .put("usr_opr_mbl",usr_opr_mbl);
             			req .put("stl_perd",stl_perd);
             			req .put("stl_day_flg",stl_day_flg);
             			req .put("wc_bnk",wc_bnk);
             			req .put("opn_bnk_prov",opn_bnk_prov);
             			req .put("opn_bnk_city",opn_bnk_city);
             			req .put("lbnk_nm",lbnk_nm);
             			req .put("wc_lbnk_no",wc_lbnk_no);
             			req .put("bnk_acnm",bnk_acnm);
             			req .put("stl_oac",stl_oac);
             			req .put("bus_psn_flg",bus_psn_flg);
				req .put("usr_opr_log_id","m91900000003");             			
                    	//初始化加密工具类
            			RSASignUtil util = new RSASignUtil();
            			//加载商户私钥
            			boolean loadFlg = util.loadPrivateKey();
            			if (!loadFlg) {
            				out.println("加载商户私钥文件失败");
            				return;
            			}
                       
                    String reqData = util.coverMap2String(req);
					out.println("待签名原文数据:[" + reqData + "]");
					out.println("<br/>");
					
					//调用方法获取请求数据
					String buf = util.genRequestData(reqData, charset);
					out.println("BackRequest组装请求报文:[" + buf + "]");
					out.println("<br/>");
            		
            		//调用通讯基础类发送数据到支付平台
            		String tradUrl = HytConfig.getConfig().getRequestUrl();
                    String res = "";
					try {
						res = HytUtil.sendAndRecv(tradUrl, buf, charset);
					} catch (IOException e) {
						out.println("发送数据失败，原因:[" + e.getMessage() + "]");
					}
			
					out.println("报文发送后的响应信息：  " + res);
					out.println("<br/>");
					
					Map<String, String> retMap = new LinkedHashMap<String, String>();
					retMap.put("data",(String)util.getValue(res,"data"));
					retMap.put("signature",(String)util.getValue(res,"signature"));
					retMap.put("partner_id",(String)util.getValue(res,"partner_id"));
					retMap.put("charset",(String)util.getValue(res,"charset"));
			
			
					//对支付平台返回数据使用平台公钥解密
					RSASignUtil rsautil = new RSASignUtil( HytConfig.getConfig().gethytServerCertPath());
					boolean loadFlg2 = rsautil.loadPublicKey();
					if (!loadFlg2) {
						out.println("加载服务器公钥文件失败");
						return;
					}
					String rspPlainText = new String(rsautil.decryptByPublickey(retMap.get("data").getBytes()));
					out.println("支付平台返回明文数据=[" + rspPlainText + "]");
					out.println("<br/>");
					String serverSign = util.getValue(res,"signature");
					out.println("支付平台返回signature=[" + serverSign + "]");
					out.println("<br/>");
			
					// -- 验证签名
					boolean flag = false;
					//对于支付平台返回明文数据,可能由于系统查询导致后面带空格的需要trim处理
					flag = rsautil.verifyBySoft(serverSign, rspPlainText.trim(), charset);
					if (!flag) {
						out.println("返回验签报文：" + res);
						out.println("错误信息：验签错误");
						return;
					} else {
						// 成功的业务逻辑处理
						out.println("验签成功！");
						out.println("<br/>");
						Map rspMap= rsautil.coverString2Map(rspPlainText);
						out.println("返回码=" + rspMap.get("returnCode") + ",返回信息=" + rspMap.get("returnMessage")
								+ ",商户号=" + rspMap.get("partner_id")
								+ ",借口类型=" + rspMap.get("biz_type")
								+ ",签名类型=" + rspMap.get("sign_type")
								+ ",版本信息=" + rspMap.get("version_no")
								+ ",子商户="+ rspMap.get("sub_partner_id"));
					}
                %>
</body>
</html>
