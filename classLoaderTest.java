package com.letv.shop.jvm.classloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类加载器与instanceof关键字演示
 **
 * @author Bruce
 */
public class ClassLoaderTest {
	public static void main(String[] args) throws Exception {
		ClassLoader myLoader = new ClassLoader() {
			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException {
				try {
					String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
					InputStream is = getClass().getResourceAsStream(fileName);
					if (is == null) {
						return super.loadClass(name);
					}
					byte[] b = new byte[is.available()];
					is.read(b);
					return defineClass(name, b, 0, b.length);
				} catch (IOException e) {
					throw new ClassNotFoundException(name);
				}
			}
		};
		Object obj = myLoader.loadClass("com.letv.shop.jvm.classloader.ClassLoaderTest").newInstance();
		System.out.println(obj.getClass());
		
		/* 返回false 虚拟机中存在了两个ClassLoaderTest类，一个
		是由系统应用程序类加载器加载的，另外一个是由我们自定义的类加载器加载的，虽然都来
		自同一个Class文件，但依然是两个独立的类，做对象所属类型检查时结果自然为false
		*/
		System.out.println(obj instanceof com.letv.shop.jvm.classloader.ClassLoaderTest);
	}
}
