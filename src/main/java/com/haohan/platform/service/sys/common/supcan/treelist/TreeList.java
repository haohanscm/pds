/**
 * Copyright &copy; 2012-2017 <a href="http://www.haohanwork.com">haohan</a> All rights reserved
 */
package com.haohan.platform.service.sys.common.supcan.treelist;

import com.google.common.collect.Lists;
import com.haohan.platform.service.sys.common.supcan.annotation.common.fonts.SupFont;
import com.haohan.platform.service.sys.common.supcan.annotation.treelist.SupTreeList;
import com.haohan.platform.service.sys.common.supcan.common.Common;
import com.haohan.platform.service.sys.common.supcan.common.fonts.Font;
import com.haohan.platform.service.sys.common.supcan.common.properties.Properties;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * 硕正TreeList
 *
 * @author WangZhen
 * @version 2013-11-04
 */
@XStreamAlias("TreeList")
public class TreeList extends Common {

    /**
     * 列集合
     */
    @XStreamAlias("Cols")
    private List<Object> cols;

    public TreeList() {
        super();
    }

    public TreeList(Properties properties) {
        this();
        this.properties = properties;
    }

    public TreeList(SupTreeList supTreeList) {
        this();
        if (supTreeList != null) {
            if (supTreeList.properties() != null) {
                this.properties = new Properties(supTreeList.properties());
            }
            if (supTreeList.fonts() != null) {
                for (SupFont supFont : supTreeList.fonts()) {
                    if (this.fonts == null) {
                        this.fonts = Lists.newArrayList();
                    }
                    this.fonts.add(new Font(supFont));
                }
            }
        }
    }

    public List<Object> getCols() {
        if (cols == null) {
            cols = Lists.newArrayList();
        }
        return cols;
    }

    public void setCols(List<Object> cols) {
        this.cols = cols;
    }

}
