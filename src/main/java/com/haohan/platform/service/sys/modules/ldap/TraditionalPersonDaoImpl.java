package com.haohan.platform.service.sys.modules.ldap;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class TraditionalPersonDaoImpl {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getAllPersonNames() {
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://123.56.41.117:389/dc=gh178,dc=com");
		env.put(Context.SECURITY_CREDENTIALS, "gh178");  
		DirContext ctx;
		try {
			ctx = new InitialDirContext(env);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
		LinkedList list = new LinkedList();
		NamingEnumeration results = null;
		try {
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			results = ctx.search("", "(objectclass=person)", controls);
			while (results.hasMore()) {
				SearchResult searchResult = (SearchResult) results.next();
				Attributes attributes = searchResult.getAttributes();
				Attribute attr = attributes.get("cn");
				String cn = (String) attr.get();
				list.add(cn);
			}
		} catch (NameNotFoundException e) {
			// The base context was not found.
			// Just clean up and exit.
			e.printStackTrace();
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (Exception e) {
					// Never mind this.
				}
			}
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
					// Never mind this.
				}
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
		System.out.println(TraditionalPersonDaoImpl.getAllPersonNames());
	}
	
}