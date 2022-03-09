package com.haohan.platform.service.sys.modules.xiaodian.dao.printer;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.FeiePrinter;

/**
 * 飞鹅打印机管理DAO接口
 * @author haohan
 * @version 2018-08-02
 */
@MyBatisDao
public interface FeiePrinterDao extends CrudDao<FeiePrinter> {
	
}