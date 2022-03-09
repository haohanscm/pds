package com.haohan.platform.service.sys.modules.xiaodian.service.merchant;

import com.google.common.collect.Lists;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.utils.excel.ImportExcel;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import com.haohan.platform.service.sys.modules.sys.entity.Dict;
import com.haohan.platform.service.sys.modules.sys.service.AreaService;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.merchant.MerchantDatabaseDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.Industry;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantDatabase;
import com.haohan.platform.service.sys.modules.xiaodian.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家资料库Service
 * @author haohan
 * @version 2018-04-07
 */
@Service
@Transactional(readOnly = true)
public class MerchantDatabaseService extends CrudService<MerchantDatabaseDao, MerchantDatabase> {

    @Autowired
    private AreaService areaService;
    @Autowired
    private IndustryService industryService;

	public MerchantDatabase get(String id) {
		return super.get(id);
	}
	
	public List<MerchantDatabase> findList(MerchantDatabase merchantDatabase) {
		return super.findList(merchantDatabase);
	}
	
	public Page<MerchantDatabase> findPage(Page<MerchantDatabase> page, MerchantDatabase merchantDatabase) {
		return super.findPage(page, merchantDatabase);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantDatabase merchantDatabase) {
		super.save(merchantDatabase);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantDatabase merchantDatabase) {
		super.delete(merchantDatabase);
	}

	public MerchantDatabase fetchByRegName(String regName) {
        MerchantDatabase merchantDatabase = new MerchantDatabase();
        merchantDatabase.setRegName(regName);
        List<MerchantDatabase> list = dao.fetchByRegName(merchantDatabase);
        // 有重复的情况下返回空
        return list.size() == 1 ? list.get(0) : null;
	}

    /**
     * 商家资料导入
     *
     * @param file 导入的Excel文件
     */
    @Transactional(readOnly = false)
    public String merchantDatabaseImport(MultipartFile file) {
        int successNum = 0;
        int failureNum = 0;
        // 初始值：excel中表头行号  索引从1开始
        int rowNum = 2;
        // 导入失败 提示信息
        StringBuilder failureMsg = new StringBuilder();
        // 导入失败的单元格
        List<int[]> errorCells = Lists.newArrayList();
        try {
            // 参数headerNum 的 索引从0开始
            ImportExcel excel = new ImportExcel(file, rowNum - 1, 0);
            List<MerchantDatabase> list = excel.getBeanList(MerchantDatabase.class, fetchHeaderMapper(), errorCells);
            // 数据处理
            for (MerchantDatabase merchantDatabase : list) {
                rowNum++;
                // 缺少必传字段时 不导入
                String name = merchantDatabase.getRegName();
                String contact = merchantDatabase.getContact();
                String telephone = merchantDatabase.getTelephone();
                String shopAddress = merchantDatabase.getShopAddress();
                String bizfromType = merchantDatabase.getBizfromType();
                boolean flag = true;
                if (StringUtils.isNoneEmpty(name, contact, telephone, shopAddress, bizfromType)) {
                    // 公司名称判断 重复时 不导入
                    List<MerchantDatabase> merchantList = dao.fetchByRegName(merchantDatabase);
                    if (merchantList.size() > 0) {
                        failureNum++;
                        failureMsg.append("<br/>第 " + rowNum + " 行导入失败：该商家资料已存在；");
                    } else {
                        // 所属区域 判断
                        Area area = areaService.fetchByName(merchantDatabase.getArea().getName());
                        if (area == null) {
                            failureMsg.append("<br/>第 " + rowNum + " 行，" + name + "的所属区域导入失败，请手动添加；");
                        } else {
                            merchantDatabase.setArea(area);
                        }
                        // 行业类别 判断
                        Industry industry = industryService.fetchByName(merchantDatabase.getIndustry());
                        if (industry == null) {
                            failureMsg.append("<br/>第 " + rowNum + " 行，" + name + "的行业类别导入失败，请手动添加；");
                        } else {
                            merchantDatabase.setIndustry(industry.getId());
                        }
                        // 商机来源 默认为1 系统录入
                        if(!StringUtils.equals(bizfromType, "0")){
                            merchantDatabase.setBizfromType("1");
                        }
                        successNum++;
                        super.save(merchantDatabase);
                    }
                } else {
                    flag = false;
                }
                if (!flag) {
                    failureNum++;
                    failureMsg.append("<br/>第 " + rowNum + " 行导入失败：该商家资料缺少必要信息；");
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条信息，导入信息如下：");
            }
            if (errorCells.size() > 0) {
                failureMsg.append("<br/>共有 " + errorCells.size() + " 个单元格信息导入失败：<br/>" + JacksonUtils.toJson(errorCells));
            }
            failureMsg.insert(0, "已成功导入 " + successNum + " 条信息");
        } catch (Exception e) {
            failureMsg.append("导入信息失败！");
            failureMsg.append("<br/>第 " + rowNum + " 行导入信息导致失败，导入中断！");
        }
        return failureMsg.toString();
    }


    // Excel表头和实体类属性的映射
    public Map<String, String> fetchHeaderMapper() {
        Map<String, String> headerMapper = new LinkedHashMap<>();
        headerMapper.put("商户注册全称", "regName");  // 必要
        headerMapper.put("经营法人", "regUser");
        headerMapper.put("所属区域", "area.name");    // 对应id
        headerMapper.put("经营地址", "opAddress");
        headerMapper.put("商家联系人", "contact");  // 必要
        headerMapper.put("联系手机", "telephone");  // 必要
        headerMapper.put("座机电话", "phoneNumber");
        headerMapper.put("商户类别", "merchantType");  // 对应字典
        headerMapper.put("行业类别", "industry");    // 对应id
        headerMapper.put("网站名称", "website"); //
        headerMapper.put("淘宝店名称", "taobaoShop");
        headerMapper.put("现有推广平台", "marketPlatform");  // 对应字典 多选逗号分隔
        headerMapper.put("现有支付工具", "payTools");   // 对应字典 多选逗号分隔
        headerMapper.put("店铺名称", "shopName");
        headerMapper.put("经营面积", "operateArea");
        headerMapper.put("员工人数", "workerNum");
        headerMapper.put("店铺介绍", "shopDesc");
        headerMapper.put("营业时间", "serviceTime");
        headerMapper.put("业务介绍", "bizDesc");
        headerMapper.put("店铺地址", "shopAddress");  // 必要
//        headerMapper.put("营业执照", "bizLicense"); // 图片暂不处理
        headerMapper.put("周围环境", "environment");
        headerMapper.put("店铺服务", "shopService");
        headerMapper.put("年经营流水", "shopSale");
//        headerMapper.put("照片资料", "pictureFile");  // 图片暂不处理
        headerMapper.put("收录时间", "initTime");     // 日期
        headerMapper.put("商机来源", "bizfromType");  // 对应字典
        headerMapper.put("是否审核", "status");  // 对应字典yes_no
        headerMapper.put("备注", "remarks");
        // 从字典中查询替换  value:表头列名, label:实体类属性名, description:表头批注
        List<Dict> list = DictUtils.getDictList("merchant_database_import");
        if (!Collections3.isEmpty(list)) {
            headerMapper.putAll(Collections3.extractToMap(list, "value", "label"));
        }
        return headerMapper;
    }

    // Excel表头和批注的映射
    public Map<String, String> fetchCommentMapper() {
        Map<String, String> commentMapper = new LinkedHashMap<>();
        commentMapper.put("商户注册全称", "必填项");
        commentMapper.put("商家联系人", "必填项");
        commentMapper.put("所属区域", "对应区域管理中的区域名称，如：江津区");
        commentMapper.put("联系手机", "必填项");
        commentMapper.put("商户类别", "填写值：0 -个体工商户，C -企业");
        commentMapper.put("行业类别", "对应行业分类的小程序行业分类名称，如：服装");
        commentMapper.put("现有推广平台", "多选(英文逗号分隔)，填写数值：0 -微信，1 -网络，2 -LED、展架、墙体广告");
        commentMapper.put("现有支付工具", "多选(英文逗号分隔)，填写数值：0 -微信，1 -支付宝，2 -POS机，3 -其他");
        commentMapper.put("店铺地址", "必填项");
        commentMapper.put("收录时间", "日期：年月日格式 XXXX/XX/XX");
        commentMapper.put("商机来源", "必填项，填写数值：0 -商家申请，1 -系统录入");
        commentMapper.put("是否审核", "填写数值：0 -否，1 -是");
        // 从字典中查询替换  value:表头列名, label:实体类属性名, description:表头批注
        List<Dict> list = DictUtils.getDictList("merchant_database_import");
        if (!Collections3.isEmpty(list)) {
            commentMapper.putAll(Collections3.extractToMap(list, "value", "description"));
        }
        return commentMapper;
    }

}