package com.haohan.platform.service.sys.modules.xiaodian.service.merchant;

import com.google.common.collect.Lists;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.utils.excel.ImportExcel;
import com.haohan.platform.service.sys.modules.sys.entity.Dict;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.sys.utils.UserUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.merchant.MerchantVisitDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantDatabase;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantVisit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家拜访记录Service
 * @author haohan
 * @version 2018-04-07
 */
@Service
@Transactional(readOnly = true)
public class MerchantVisitService extends CrudService<MerchantVisitDao, MerchantVisit> {

	@Resource
	private MerchantDatabaseService merchantDatabaseService;
	public MerchantVisit get(String id)
	{
		MerchantVisit visit = super.get(id);
		if(null != visit){
			visit.setMerchantDatabase(merchantDatabaseService.get(visit.getMerchantDatabase()));
		}
		return visit;
	}

	
	public List<MerchantVisit> findList(MerchantVisit merchantVisit) {
		return super.findList(merchantVisit);
	}
	
	public Page<MerchantVisit> findPage(Page<MerchantVisit> page, MerchantVisit merchantVisit) {
		return super.findPage(page, merchantVisit);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantVisit merchantVisit) {
		super.save(merchantVisit);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantVisit merchantVisit) {
		super.delete(merchantVisit);
	}


    /**
     * 商家拜访记录导入
     *
     * @param file 导入的Excel文件
     */
    @Transactional(readOnly = false)
    public String merchantVisitImport(MultipartFile file) {
        int successNum = 0;
        int failureNum = 0;
        // 初始值：excel中表头行号 索引从1开始
        int rowNum = 2;
        // 导入失败 提示信息
        StringBuilder failureMsg = new StringBuilder();
        // 导入失败的单元格
        List<int[]> errorCells = Lists.newArrayList();
        try {
            // 参数headerNum 的 索引从0开始
            ImportExcel ei = new ImportExcel(file, rowNum - 1, 0);
            List<MerchantVisit> list = ei.getBeanList(MerchantVisit.class, fetchHeaderMapper(), errorCells);
            // 数据处理
            for (MerchantVisit merchantVisit : list) {
                rowNum++;
                // 公司名称判断 找不到时 不导入
                String name = merchantVisit.getMerchantDatabase().getRegName();
                boolean flag = true;
                if (StringUtils.isNotEmpty(name)) {
                    MerchantDatabase merchantDatabase = merchantDatabaseService.fetchByRegName(name);
                    if (merchantDatabase == null) {
                        flag = false;
                    } else {
                        // 拜访人员 判断
                        User user = UserUtils.fetchByName(merchantVisit.getUser().getName());
                        if (user == null) {
                            failureMsg.append("<br/>第 " + rowNum + " 行，" + name + "的拜访人员导入失败，请手动添加；");
                        } else {
                            merchantVisit.setUser(user);
                        }
                        successNum++;
                        merchantVisit.setMerchantDatabase(merchantDatabase);
                        super.save(merchantVisit);
                    }
                } else {
                    flag = false;
                }
                if (!flag) {
                    failureNum++;
                    failureMsg.append("<br/>第 " + rowNum + " 行导入失败：商家资料库没有该公司信息；");
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
        headerMapper.put("姓名", "contact");
        headerMapper.put("电话", "phoneNumber");
        headerMapper.put("公司", "merchantDatabase.regName"); // 必要
        headerMapper.put("地址", "visitAddress");
        headerMapper.put("拜访时间", "visitTime");    //  excel中需  年月日
        headerMapper.put("拜访内容", "visitContent");
        headerMapper.put("进展阶段", "visitStep");   // 字典
        headerMapper.put("拜访人", "user.name");
        headerMapper.put("备注", "remarks");
        // 从字典中查询替换  value:表头列名, label:实体类属性名, description:表头批注
        List<Dict> list = DictUtils.getDictList("merchant_visit_import");
        if (!Collections3.isEmpty(list)) {
            headerMapper.putAll(Collections3.extractToMap(list, "value", "label"));
        }
        return headerMapper;
    }

    // Excel表头和批注的映射
    public Map<String, String> fetchCommentMapper() {
        Map<String, String> commentMapper = new LinkedHashMap<>();
        commentMapper.put("公司", "必填项");
        commentMapper.put("拜访时间", "日期：年月日格式 XXXX/XX/XX");
        commentMapper.put("进展阶段", "填写数值：0 -初次拜访，1 -了解交流，2 -深入跟进，3 -达成合作，4 -商务往来");
        commentMapper.put("拜访人", "拜访人姓名，需和平台用户姓名相同");
        // 从字典中查询替换  value:表头列名, label:实体类属性名, description:表头批注
        List<Dict> list = DictUtils.getDictList("merchant_visit_import");
        if (!Collections3.isEmpty(list)) {
            commentMapper.putAll(Collections3.extractToMap(list, "value", "description"));
        }
        return commentMapper;
    }


}