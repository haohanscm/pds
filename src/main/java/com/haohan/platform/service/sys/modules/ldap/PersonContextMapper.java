package com.haohan.platform.service.sys.modules.ldap;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;

public class PersonContextMapper implements ContextMapper<Person> {
    public Person mapFromContext(Object ctx) {
        DirContextAdapter context = (DirContextAdapter)ctx;
        Person p = new Person();
        p.setCn(context.getStringAttribute("cn"));
        p.setSn(context.getStringAttribute("sn"));
        p.setDisplayName(context.getStringAttribute("displayName"));
        return p;
}

}
