package com.liuyan.zbauto.util;

import com.jfinal.kit.PathKit;
import jodd.io.FileUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.File;

/**
 *读取本地配置文件(开发用的)
 */
@RequiredArgsConstructor
public class LocalPathConfig {
	private @NonNull String fileName;
	
	public File getFile() {
		String path = "c:/config/"+fileName;
		if(this.isExist(path)) {
			return new File(path);
		}
		path = "/Users/chenwenxi/etc/"+fileName;//mac book的配置
		if(this.isExist(path)) {
			return new File(path);
		}
		//取class的配置
		path = PathKit.getRootClassPath(); //PathKit.getPath(AppKits.class);
		path=  path.substring(0,path.indexOf("classes")+7)+"/"+fileName;
		if(this.isExist(path)) {
			return new File(path);
		}
		return null;
	}
	private boolean isExist(String path) {
		return FileUtil.isExistingFile(new File(path));
	}
	public static void main(String[] args) {
		System.out.println(new LocalPathConfig("app.props").getFile());
	}
}
