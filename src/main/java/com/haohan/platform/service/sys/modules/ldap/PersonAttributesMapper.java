package com.haohan.platform.service.sys.modules.ldap;

import com.haohan.framework.utils.TransBeanMap;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class PersonAttributesMapper implements AttributesMapper<Person> {
	public Person mapFromAttributes(Attributes attrs) throws NamingException {

		 Person person = new Person();
		// person.setCn((String)attrs.get("cn").get());
		// person.setSn((String)attrs.get("sn").get());
		// person.setDisplayName((String)attrs.get("displayName").get());
		NamingEnumeration<? extends Attribute> ne = attrs.getAll();
		HashMap<String, Object> map = new HashMap<>();
		while (ne.hasMore()) {
			Attribute a = ne.next();

			if ("userPassword".equals(a.getID())) {
				map.put(a.getID(), new String((byte[]) a.get(), StandardCharsets.UTF_8));
			} else {
				map.put(a.getID(), a.get());
			}
			// System.out.println(a.getID()+" "+a.get());
		}
		try {
			TransBeanMap.transMap2Bean(person, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return person;
	}
}