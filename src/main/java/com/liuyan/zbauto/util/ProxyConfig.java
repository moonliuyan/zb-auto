package com.liuyan.zbauto.util;

import jodd.props.Props;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class ProxyConfig {

	public static Proxy proxy;
	
	public static void main(String[] args) {
		System.out.println(ProxyConfig.proxy);
	}

	static {
		File file = new LocalPathConfig("proxy.pros").getFile();
		Props prop = new Props();
		try {
			prop.load(file);
			log.error("加载代理:"+file.getAbsolutePath());
		} catch (IOException e) {
			log.error("找不到路径:"+file.getAbsolutePath());
		}
		proxy = new Proxy(prop.getBooleanValue("proxy.mode"), prop.getValue("proxy.ip"), prop.getIntegerValue("proxy.port"), prop.getValue("proxy.userName"), prop.getValue("proxy.userPassword"));
	}

	@AllArgsConstructor
	@Data
	public static class Proxy {
		/** 开关,如果正式环境下不需要代理则false */
		private boolean mode;
		private String ip;
		private int port;
		private String userName;
		private String userPassword;
	}
}
