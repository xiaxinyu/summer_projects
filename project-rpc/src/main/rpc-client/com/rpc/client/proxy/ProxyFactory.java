package com.rpc.client.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.rpc.api.bean.RPCProtocol;
import com.rpc.api.util.SerializeUtils;
import com.rpc.client.RPCClient;

public class ProxyFactory {
	
	private static InvocationHandler handler = new InvocationHandler() {
		// 1.将本地的接口调用转换成JDK的动态代理，在动态代理中实现接口的远程调用
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			RPCProtocol rpcProtocol = new RPCProtocol();
			
			Class<?>[] classes = proxy.getClass().getInterfaces();
			String className = classes[0].getName();

			rpcProtocol.setClassName(className);
			rpcProtocol.setArgs(args);
			rpcProtocol.setMethod(method.getName());
			
			System.out.println("Class: "+className);
			System.out.println("Method: "+method.getName());
			String [] types = null; 
			if(args!=null) {
				types = new String [args.length];
				for (int i = 0; i < types.length; i++) {
					types[i] = args[i].getClass().getName();
				}
			}
			rpcProtocol.setTypes(types);
			
			byte[] byteArray = SerializeUtils.serialize(rpcProtocol);
			Object send = RPCClient.send(byteArray);
			return send;
		}
	};

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> clazz) {
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(),  new Class[]{clazz}, handler );
	}
}
