package com.haohan.platform.service.sys.modules.ldap;

import com.haohan.framework.entity.BiStringEntry;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.framework.utils.TransBeanMap;
import com.haohan.platform.service.sys.modules.ldap.util.PasswordUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ldap/*.xml" })
public class CreateUser {
	@Resource
	private LdapTemplate ldapTemplate;

	public String getBase() throws Exception {
		return ldapTemplate.getContextSource().getReadOnlyContext().getNameInNamespace();
	}

	public String ouSevenKids = "Sevenkids";
	public String ouPeople = "People";

	public String ou = "haohanshop";

	// 批量创建用户
	@Test
	public void createArray() throws Exception {
		ArrayList<BiStringEntry> names = new ArrayList<BiStringEntry>();
		names.add(new BiStringEntry("马浩元", "haoyuan.ma"));
//		names.add(new BiStringEntry("张至尧", "zhiyao.zhang"));
//		names.add(new BiStringEntry("张来杰", "laijie.zhang"));
		// 这个不能有重复的 这个就是用户ID号
		int i = 1;
		for (BiStringEntry be : names) {
			i++;
			Person p = createPerson(be.msg.trim(), be.code.trim(), i);
			Name dn = buildDn(p);
			Attributes attrs = buildAttributes(p);
			ldapTemplate.bind(dn, null, attrs);
		}
	}

	public static void main(String[] args){

		System.out.print("hello");
	}

	public Person createPerson(String login, String name, Integer uidNumber) {
		Person p = new Person();
		p.setUidNumber(uidNumber.toString());
		p.setCn(login);
		p.setDisplayName(name);
		p.setMail(p.getCn() + "@haohanshop.com");
		p.setTelephoneNumber("18686123492");
		p.setOu(ou);
		p.setSn(p.getCn());
		p.setUserPassword(PasswordUtil.SHA("haohanshop.com"));
		p.setUid(p.getCn());
		p.setGidNumber("99999");
		p.setDescription(p.getDisplayName());
		p.setHomeDirectory("/home/" + p.getCn());
		p.setDepartmentNumber("0");
		return p;
	}

	@Test
	public void create() throws Exception {
		Person p = createPerson();
		Name dn = buildDn(p);
		Attributes attrs = buildAttributes(p);
		System.out.println(attrs);
		ldapTemplate.bind(dn, null, attrs);
	}


	@Test
	public Person createPerson() {
		Person p = new Person();
		p.setUidNumber("100");
		p.setCn("haoyuan.ma");
		p.setDisplayName("马浩元");
		p.setMail(p.getCn() + "@haohanshop.com");
		p.setTelephoneNumber("18686123492");
		p.setOu(ou);
		p.setSn(p.getCn());
		p.setUserPassword(PasswordUtil.SHA("haohanshop.com"));
		p.setUid(p.getCn());
		p.setGidNumber("99999");
		p.setDescription(p.getDisplayName());
		p.setHomeDirectory("/home/" + p.getCn());
		p.setDepartmentNumber("0");
		return p;
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

	@Test
	public void findPerson() throws InvalidNameException {
		String name = "laijie.li";
		String dn = "cn=" + name + ",ou=" + ou;
		Person p = ldapTemplate.lookup(dn, new PersonAttributesMapper());
		System.out.println(p);
	}

	@Test
	public void remove() {
		String name = "";
		String dn = "cn=" + name + ",ou=" + ou;
		ldapTemplate.unbind(dn);
	}

	@Test
	public void update() throws Exception {
		String name = "xikun.zhao";
		String dn = "cn=" + name + ",ou=" + ou;
		Person p = ldapTemplate.lookup(dn, new PersonAttributesMapper());
		p.setDescription("赵坤儿");
		ldapTemplate.rebind(dn, null, buildAttributes(p));
	}

	// 获取所有用户
	@Test
	public void getAllPersons() throws InvalidNameException {
		EqualsFilter filter = new EqualsFilter("objectclass", "person");
		LdapName ln = new LdapName("");
		ln.add(new Rdn("ou", ou));
		List<Person> list = ldapTemplate.search(ln, filter.encode(), new PersonAttributesMapper());
		System.out.println(JacksonUtils.toJson(list));
		list = ldapTemplate.search("", "(objectclass=person)", new PersonAttributesMapper());
		System.out.println(list.size());
	}
}
