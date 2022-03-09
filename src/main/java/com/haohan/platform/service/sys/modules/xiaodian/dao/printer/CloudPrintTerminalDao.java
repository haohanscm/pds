package com.haohan.platform.service.sys.modules.xiaodian.dao.printer;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.CloudPrintTerminal;

import java.util.List;

/**
 * 易联云打印机管理DAO接口
 * @author haohan
 * @version 2018-11-26
 */
@MyBatisDao
public interface CloudPrintTerminalDao extends CrudDao<CloudPrintTerminal> {

    List<CloudPrintTerminal> findJoinList(CloudPrintTerminal cloudPrintTerminal);
}