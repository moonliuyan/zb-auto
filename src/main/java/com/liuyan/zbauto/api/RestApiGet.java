package com.liuyan.zbauto.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.google.gson.Gson;

import com.liuyan.zbauto.api.entity.DeptResult;
import com.liuyan.zbauto.api.entity.KlineResult;
import com.liuyan.zbauto.api.entity.TickerResult;
import com.liuyan.zbauto.api.enumtype.KTimeEnum;
import com.liuyan.zbauto.api.enumtype.SymbolEnum;
import com.liuyan.zbauto.util.HttpUtilManager;
import jodd.json.JsonParser;
import lombok.Data;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.http.HttpException;


import java.io.IOException;
import java.util.*;

/**
 * 封装客户端
 */
@Data
@Slf4j
public class RestApiGet {
	private SymbolEnum symbol;
	private String symbolStr;
	private String url_base="http://api.zb.center/data/v1/";

	Gson gson = new Gson();

	public RestApiGet(String symbol) {
		super();
		// TODO Auto-generated constructor stub
		this.symbolStr = symbol;
	}

	private final JsonParser jsonParser = new JsonParser();

//	public static String url_base = "http://api.zb.com/data/v1";

	private final String url_kline = "/kline";
	private final String url_ticker = "/ticker";
	private final String url_dept = "/depth";
	private final String url_trades = "/trades";
	private final String url_markets = "/markets";
	private final String url_allTicker = "/allTicker";

	public List<KlineResult> getKline(KTimeEnum ktime) {
		return this.getKline(ktime, 1000);
	}

	public String getMarkets() {
		String json = getJsonGet(url_base + url_markets, new LinkedHashMap<String, String>());
		return json;
	}

	/** 获取行情数据 **/
	public String getTrades() {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("currency", symbolStr);
		// HttpRequest request = HttpRequest.get(urlTicker).query(params);
		// log.debug("执行url:"+request.url());
		String json = "";
		try {
			json = HttpUtilManager.getInstance().requestHttpGet(url_base + url_trades, params);
		} catch (HttpException | IOException e) {
			try {
				json = HttpUtilManager.getInstance().requestHttpGet(url_base + url_trades, params);
			} catch (HttpException | IOException e1) {
			}
		}
		return json;
	}
	
	

	public String getAllTicker() {
		String json = getJsonGet(url_base + url_allTicker, new LinkedHashMap<String, String>());
		return json;
	}

	/**
	 * 获取k线 @Title: getKline @Description: TODO @param @param ktime @param @param
	 * minuteFront多少分钟前的数据,负数 @param @return @author: npc QQ:568081714 @return
	 * List<KlineResult> @throws
	 */
	@Synchronized
	public List<KlineResult> getKline(KTimeEnum ktime, int size) {
		log.info(symbolStr + "获取k线-zb");
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("market", symbolStr);// 交易类型（目前仅支持btc_cny/ltc_cny/eth_cny/eth_btc）
		params.put("type", ktime.getTime());
		// params.put("since", new JDateTime().addMinute(minute)
		// .getTimeInMillis() + "");// 从某个时间数据
		params.put("size", Convert.toStr(size));
		String json = getJsonGet(url_base + url_kline, params);

		// log.debug(json);
		JSONObject klineObj = JSONObject.fromObject(json);
		List<KlineResult> list = new ArrayList<KlineResult>();
		try {
			json = klineObj.getString("data");
			JSONArray arr = JSONArray.fromObject(json);
			KlineResult kl = null;
			for (Object o : arr) {
				JSONArray k = (JSONArray) o;
				kl = new KlineResult();
				int i = 0;
				kl.setZhouqi(ktime);
				kl.setDate(new Date(Long.parseLong(k.get(i++).toString())));
				kl.setOpen(Float.parseFloat(k.get(i++).toString()));
				kl.setHigh(Float.parseFloat(k.get(i++).toString()));
				kl.setLow(Float.parseFloat(k.get(i++).toString()));
				kl.setClose(Float.parseFloat(k.get(i++).toString()));
				kl.setVol(Float.parseFloat(k.get(i++).toString()));
				list.add(kl);
			}
		} catch (JSONException e) {
			log.debug("解析json异常:" + json);
			// 递归直到有数据为止
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
//			list = this.getKline(ktime, size);
		}

		return list;
	}

	/** 获取默认深度 */
	public DeptResult getDept() {
		return this.getDept(3, 0.1f);
	}

	/**
	 * 获取深度
	 * 
	 * @param size  档位1-50，如果有合并深度，只能返回5档深度
	 * @param merge eth_cny : 可选0.5,0.3,0.1
	 * @return
	 */
	private DeptResult getDept(int size, float merge) {
		val timer = DateUtil.timer();
		val params = new LinkedHashMap<String, String>();
		params.put("market", symbolStr);
		params.put("size", Convert.toStr(size));
		// params.put("merge", Convert.toString(merge));
		// HttpRequest request = HttpRequest.get(urlDept).query(params);
		// log.debug("执行url:"+request.url());
		String json = getJsonGet(url_base + url_dept, params);
		log.debug("执行时间:" + timer.intervalMs() + json);
		DeptResult dept = gson.fromJson(json, DeptResult.class);
		return dept;
	}

	/** 获取行情数据 **/
	public TickerResult getTicker() {
		// String cacheName = cName+"getTicker";
		// TickerResult ticker = CacheKit.get(cache1s, cacheName);
		// if(ticker != null){
		// return ticker;
		// }
		val params = new LinkedHashMap<String, String>();
		params.put("market", symbolStr);
		// HttpRequest request = HttpRequest.get(urlTicker).query(params);
		// log.debug("执行url:"+request.url());
		String json = "";
		try {
			log.debug(url_base + url_ticker + " 参数:" + params);
			json = HttpUtilManager.getInstance().requestHttpGet(url_base + url_ticker, params);
			log.debug(json);
		} catch (HttpException | IOException e) {
			try {
				json = HttpUtilManager.getInstance().requestHttpGet(url_base + url_ticker, params);
			} catch (HttpException | IOException e1) {
			}
		}
		log.debug("ticker:" + json);
		TickerResult ticker = gson.fromJson(json, TickerResult.class);
		// CacheKit.put(cache1s, cacheName,ticker);
		return ticker;
	}

	private String getJsonGet(String url, Map<String, String> params) {
		String requestHttpGet = "";
		try {
			requestHttpGet = HttpUtilManager.getInstance().requestHttpGet(url, params);
		} catch (HttpException | IOException e) {
			log.error("获取json异常:url" + url);
		}
		return requestHttpGet;
	}

	// public DepthJson getDepth() {
	// DepthJson depthJsonEntity = new DepthJson();
	// String json = getJsonGet(urlDepth, null);
	// // log.debug(json);
	// JSONObject fromObject = JSONObject.fromObject(json);
	// // 获取asks
	// json = fromObject.getString("asks");
	// List<AsksBidsEntity> asksList = AsksBidsEntity.getEntity(json);
	// Collections.reverse(asksList);// 需要将卖方反转
	// // 获取bids
	// json = fromObject.getString("bids");
	// List<AsksBidsEntity> bidsList = AsksBidsEntity.getEntity(json);
	// // 封装实体
	// depthJsonEntity.setBids(bidsList);
	// depthJsonEntity.setAsks(asksList);
	// return depthJsonEntity;
	// }

}
