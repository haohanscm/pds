package com.haohan.platform.service.sys.modules.xiaodian.service.merchant;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.TShopDto;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IShopConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.merchant.ShopDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 店铺管理Service
 * @author haohan
 * @version 2017-12-15
 */
@Service
@Transactional(readOnly = true)
public class ShopService extends CrudService<ShopDao, Shop> {

	public Shop get(String id) {
		return super.get(id);
	}

	public List<Shop> findList(Shop shop) {
		return super.findList(shop);
	}

	/**
	 * 返回带 merchantName
	 * @param shop
	 * @return
	 */
	public List<Shop> findJoinList(Shop shop) {
		return dao.findJoinList(shop);
	}

	public Page<Shop> findPage(Page<Shop> page, Shop shop) {
		return super.findPage(page, shop);
	}

	@Transactional(readOnly = false)
	public void save(Shop shop) {
		super.save(shop);
	}

	@Transactional(readOnly = false)
	public void delete(Shop shop) {
		super.delete(shop);
	}

	@Override
	public Shop get(Shop entity) {
		return super.get(entity);
	}

	public  Shop fetchByShopCode(String code){
		Shop shop = new Shop();
		shop.setCode(code);
		List<Shop> list = findList(shop);
		return (CollectionUtils.isEmpty(list))?null:list.get(0);
	}

	/**
	 * 根据店铺名称查找 值相等
	 * @param name
	 * @return
	 */
	public Shop fetchByShopName(String name) {
		Shop shop = new Shop();
		shop.setName(name);
		List<Shop> list = dao.fetchByName(shop);
		return (CollectionUtils.isEmpty(list)) ? null : list.get(0);
	}

	public List<Shop> fetchByMerchantId(String merchantId) {
		Shop shop = new Shop();
		shop.setMerchantId(merchantId);
		List<Shop> list = findList(shop);
		return CollectionUtils.isEmpty(list)?null:list;
	}

	public List<TShopDto> selectByMerchant(String merchantId){
		Shop shop = new Shop();
		shop.setMerchantId(merchantId);
		shop.setStatus(IShopConstant.ShopStatus.enable.getCode());
		return dao.selectSimpleShop(shop);
	}

	public List<TShopDto> selectById(String shopId){
		Shop shop = new Shop();
		shop.setId(shopId);
		return dao.selectSimpleShop(shop);
	}


	public List<Shop> fetchByMerchantIdEnable(Shop shop){
		if(StringUtils.isEmpty(shop.getMerchantId())){
			return null;
		}
		shop.setStatus(IMerchantConstant.available);
		List<Shop> list = findList(shop);
		return CollectionUtils.isEmpty(list)?null:list;
	}

//	public void createStaticPayQrCode(String shopId,String token){
//		String url = baseServerName+"xiaodian/api/pay/valid?sid="+shopId+"&t="+token;
//		String staticQrCodeDir = "D:"+CommonConstant.staticPayQrCodePath;
//		File path = new File(staticQrCodeDir);
//		if (!path.exists()){
//			path.mkdirs();
//		}
//		String fileStr = path.getPath()+"/"+shopId+".png";
//		QRCodeUtil.encodeQrcode(url,fileStr);
//	}

	/**
	 *  获取开通即速应用的店铺
	 * @param shop
	 * @return
	 */
	public List<Shop> fetchJisuApp(Shop shop){
		shop.setStatus(IMerchantConstant.available);
		return dao.fetchJisuApp(shop);
	}

	/**
	 * 总子店 根据子店id返回总店id 找不到时返回当前id
	 * @param shopId
	 * @return
	 */
	public String fetchHeadShopId(String shopId){
		String headShopId = shopId;
		Shop shop = super.get(shopId);
		// 不是云小店模式
		if (null != shop && !StringUtils.equals(shop.getServiceType(), IShopConstant.ShopServiceType.single.getCode())){
			// 不为总店时
			if(!StringUtils.equals(shop.getShopLevel(), IShopConstant.ShopLevelType.head.getCode())){
				Shop temp = new Shop();
				temp.setMerchantId(shop.getMerchantId());
				temp.setShopLevel(IShopConstant.ShopLevelType.head.getCode());
				List<Shop> list = super.findList(temp);
				// 返回总店Id
				if(CollectionUtils.isNotEmpty(list)){
					headShopId = list.get(0).getId();
				}
			}
		}
		return headShopId;
	}

	/**
	 * 店铺信息 部分修改
	 * @param shop
	 * @return
	 */
	@Transactional(readOnly = false)
	public int modifyShopInfo(Shop shop) {
		shop.preUpdate();
		return dao.modifyShopInfo(shop);
	}

	/**
	 * 根据店铺服务模式 设置店铺级别
	 * @param shop
	 */
	public void settingShopLevel(Shop shop){
		// 当为 云连锁总店/分店时
		if(StringUtils.equals(shop.getServiceType(), IShopConstant.ShopServiceType.headChain.getCode())) {
			shop.setShopLevel(IShopConstant.ShopLevelType.head.getCode());
		}else if(StringUtils.equals(shop.getServiceType(), IShopConstant.ShopServiceType.subChain.getCode())){
			shop.setShopLevel(IShopConstant.ShopLevelType.sub.getCode());
		}
	}

	/**
	 * 返回 平台商家及子商家下的店铺
	 * @param shop 必须pmId 可传shopId
	 * @return
	 */
	public List<Shop> findListWithPmId(Shop shop) {
		return dao.findListWithPmId(shop);
	}

}