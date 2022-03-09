/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: IPayQueryConstant
 * Author:   Lenovo
 * Date:     2018/6/14 21:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.haohan.platform.service.sys.modules.xiaodian.api.constant;

/**
 * @author shenyu
 * @create 2018/6/14
 */
public interface IPayQueryConstant {
    enum QueryPeriod {

        YESTERDAY("-1","昨日"),
        TODAY("0","今日"),
        THIS_WEEK("1","本周"),
        ;

        QueryPeriod(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }


}
