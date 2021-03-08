package com.liuyan.zbauto.api;


import com.liuyan.zbauto.api.enumtype.ExchangeEnum;
import jodd.db.oom.meta.DbTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
/** 用户key */
public class UserApi implements Serializable {
	private static final long serialVersionUID = 2532495564964592602L;
	/**是否开启*/
	private Boolean mode;
	private String exchange;
	/** 自定义用户名 */
	private String userName;
	/** apiKey */
	private String apiKey;
	/** secretKey */
	private String secretKey;
	/** 兼容chbtc,可以不写 */
	private String payPass;
	/**Aex网站需要用到*/
	private String userId;
	/** Unix 时间戳-创建 */
	private Date createTime;

	public UserApi(ExchangeEnum exchange, String userName, String apiKey, String secretKey) {
		super();
		this.userName = userName;
		this.apiKey = apiKey;
		this.secretKey = secretKey;
		this.exchange = exchange.name();
		this.createTime = new Date();
		this.mode = false;
	}
	/**
	 * aex网站需要用到id
	 * @param exchange
	 * @param userName
	 * @param apiKey
	 * @param secretKey
	 * @param payPass
	 * @param userId
	 */
	public UserApi(ExchangeEnum exchange, String userName, String apiKey, String secretKey, String userId) {
		super();
		this.exchange = exchange.name();
		this.userName = userName;
		this.apiKey = apiKey;
		this.secretKey = secretKey;
		this.userId = userId;
		this.createTime = new Date();
		this.mode = false;
	}
}
