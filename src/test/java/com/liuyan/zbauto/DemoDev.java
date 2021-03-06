package com.liuyan.zbauto;


import com.liuyan.zbauto.api.RestApiGet;
import com.liuyan.zbauto.api.RestApiPostApi;
import com.liuyan.zbauto.api.UserApi;
import com.liuyan.zbauto.api.entity.Account;
import com.liuyan.zbauto.api.entity.TradeResult;
import com.liuyan.zbauto.api.enumtype.ExchangeEnum;
import jodd.props.Props;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 测试环境
 * @author Administrator
 *
 */
public class DemoDev {
	private final String url_get = "http://api.zb.com/data/v1/";
	private final String url_post = "https://trade.zb.center/api/";
//	private final String url_post = "https://trade.zb.live/api/";
	@Autowired
	private RestApiPostApi apiPost;

	
	private final String symbol = "zb_qc";
	
	
	@Test
	public void buy() {
		// 遍历用户信息
//		Account account = apiPost.getAccount();
//		account.getResult().getCoins().forEach(coin -> {
//			System.out.println("可用:" + coin.getAvailable() + ",冻结:" + coin.getFreez());
//		});
//		apiPost.buy(1, 1);
		
//		double price = apiGet.getDept().getBids().get(2).getPrice();// 获取买4价格挂单
		TradeResult buyResult = apiPost.buy(31.7, 1,symbol);
		long id = buyResult.getId();// 返回挂单id
		System.out.println("下单返回结果:" + buyResult + ",挂单Id:" + id);
	}
	
	@Test
	public void account() {
		Account account = apiPost.getAccount();
		Account.Result result = account.getResult();
		List<Account.Result.Coin> coins = result.getCoins();
		coins.stream().filter(x->x.getAvailable()>0).forEach(x->{
			System.out.println(x);
		});

		System.out.println(account);
	}


	@Test
	public void query() {
		apiPost.getTicker(symbol);
	}

	@Test
	public void auto(){
		//模拟买入
		apiPost.auto(symbol);

	}
	
	@Test
	public void withdraw() {
		String withdraw = apiPost.withdraw(2, 1, "dizhi1232132dfd");
		System.out.println(withdraw);
	}
	
	

}
