package com.haohan.platform.service.sys.modules.xiaodian.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.order.CartDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.Cart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 购物车Service
 * @author haohan
 * @version 2017-12-07
 */
@Service
@Transactional(readOnly = true)
public class CartService extends CrudService<CartDao, Cart> {

	public Cart get(String id) {
		return super.get(id);
	}
	
	public List<Cart> findList(Cart cart) {
		return super.findList(cart);
	}
	
	public Page<Cart> findPage(Page<Cart> page, Cart cart) {
		return super.findPage(page, cart);
	}
	
	@Transactional(readOnly = false)
	public void save(Cart cart) {
		super.save(cart);
	}
	
	@Transactional(readOnly = false)
	public void delete(Cart cart) {
		super.delete(cart);
	}
	
}