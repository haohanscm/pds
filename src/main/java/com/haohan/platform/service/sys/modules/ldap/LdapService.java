package com.haohan.platform.service.sys.modules.ldap;

import com.haohan.framework.utils.TransBeanMap;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.ldap.util.PasswordUtil;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

/**
 * Created by zgw on 2018/2/28.
 */
@Service
public class LdapService {

    private static final String ou = "haohanshop";


    @Resource
    private LdapTemplate ldapTemplate;

    public boolean addUser(User user) {

        try {
            String dn = "cn=" + user.getLoginName() + ",ou=" + ou;
            Person p1 = ldapTemplate.lookup(dn, new PersonAttributesMapper());
            if(null != p1){
                return modifyUser(user);
            }
        }catch (NameNotFoundException e){
            Person p = new Person();
            p.setUidNumber("1" + user.getNo());
            p.setCn(user.getLoginName());
            p.setDisplayName(user.getName());
            p.setMail(user.getEmail());
            p.setTelephoneNumber(user.getMobile());
            p.setOu(ou);
            p.setSn(p.getCn());
            if(StringUtils.isNotEmpty(user.getNewPassword())) {
                p.setUserPassword(PasswordUtil.SHA(user.getNewPassword()));
            }else {
                p.setUserPassword(PasswordUtil.SHA("haohanshop.com"));
            }
            p.setUid(p.getCn());
            p.setGidNumber("9999");
            p.setDescription(p.getDisplayName());
            p.setHomeDirectory("/home/" + p.getCn());
            p.setDepartmentNumber("0");
            try {
                ldapTemplate.bind(buildDn(p), null, buildAttributes(p));
                return true;
            } catch (Exception e1) {
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
        return false;
    }

    public boolean deleteUser(User user){
        try {
            String dn = "cn=" + user.getLoginName() + ",ou=" + ou;
            ldapTemplate.unbind(dn);
            return  true;

        }catch (Exception e) {

            return false;
        }
    }


    public boolean modifyPassword(String loginName, String newPassword) {

        try {
            String dn = "cn=" + loginName + ",ou=" + ou;
            Person p = ldapTemplate.lookup(dn, new PersonAttributesMapper());
            p.setUserPassword(PasswordUtil.SHA(newPassword));
            ldapTemplate.rebind(dn, null, buildAttributes(p));
            return true;

        }catch (Exception e) {

            return false;
        }
    }

    public boolean modifyUser(User user){
        try {
            String dn = "cn=" + user.getLoginName() + ",ou=" + ou;
            Person p = ldapTemplate.lookup(dn, new PersonAttributesMapper());
            p.setUidNumber("1"+user.getNo());
            p.setCn(user.getLoginName());
            p.setDisplayName(user.getName());
            p.setMail(user.getEmail());
            p.setTelephoneNumber(user.getMobile());
            p.setOu(ou);
            p.setSn(p.getCn());
           if(StringUtils.isNotEmpty(user.getNewPassword())) {
               p.setUserPassword(PasswordUtil.SHA(user.getNewPassword()));
           }
            p.setUid(p.getCn());
            p.setGidNumber("9999");
            p.setDescription(p.getDisplayName());
            p.setHomeDirectory("/home/" + p.getCn());
            p.setDepartmentNumber("0");
            ldapTemplate.rebind(dn, null, buildAttributes(p));
            return  true;

        }catch (Exception e) {

            return false;
        }
    }




    protected Name buildDn(Person p) {
        try {
            LdapName ln;
            ln = new LdapName("");
            ln.add(new Rdn("ou", p.getOu()));
            ln.add(new Rdn("cn", p.getCn()));
            return ln;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Attributes buildAttributes(Person p) throws Exception {

        final Attributes attrs = new BasicAttributes();
        BasicAttribute ocattr = new BasicAttribute("objectclass");
        ocattr.add("top");
        ocattr.add("person");
        ocattr.add("organizationalPerson");
        ocattr.add("inetOrgPerson");
        ocattr.add("posixAccount");
        attrs.put(ocattr);

        TransBeanMap.foreachFeild(p, new TransBeanMap.DealFeild<Person>() {
            @Override
            public void deal(String name, Object value, Person p) {
                if (null != value) {
                    attrs.put(name, value);
                }
            }

        });
        return attrs;
    }



}
