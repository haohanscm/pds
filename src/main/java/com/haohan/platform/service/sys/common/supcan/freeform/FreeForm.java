/**
 * Copyright &copy; 2012-2017 <a href="http://www.haohanwork.com">haohan</a> All rights reserved
 */
package com.haohan.platform.service.sys.common.supcan.freeform;

import com.haohan.platform.service.sys.common.supcan.common.Common;
import com.haohan.platform.service.sys.common.supcan.common.properties.Properties;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 硕正FreeForm
 *
 * @author WangZhen
 * @version 2013-11-04
 */
@XStreamAlias("FreeForm")
public class FreeForm extends Common {

    public FreeForm() {
        super();
    }

    public FreeForm(Properties properties) {
        this();
        this.properties = properties;
    }

}
