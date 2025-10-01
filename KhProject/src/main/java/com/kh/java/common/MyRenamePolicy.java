package com.kh.java.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyRenamePolicy implements FileRenamePolicy {
	
	@Override
	public File rename(File originFile) {
		
		String originName = originFile.getName();
		String ext = originName.substring(originName.lastIndexOf("."));
		
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss")
								.format(new Date());
		
		int randomNo = (int)(Math.random() * 900) + 100;
		
		String changeName = "KHacacemy_" + currentTime + "_" + randomNo + ext;
		
		/*
		String str = "abc";
		StringBuilder sb = new StringBuilder();
		sb.append(str);
		sb.reverse();
		
		for(int i = str.length(); i < i--) {
			
		}
		*/
		
		return new File(originFile.getParent(), changeName);
		
	}

}
