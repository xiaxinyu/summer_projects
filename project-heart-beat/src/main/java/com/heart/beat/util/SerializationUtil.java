package com.heart.beat.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.validation.Schema;

public class SerializationUtil {
	 private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();  
	  
	    private static Objenesis                objenesis    = new ObjenesisStd(true);  
	  
	    private static <T> Schema<T> getSchema(Class<T> clazz) {  
	        @SuppressWarnings("unchecked")  
	        Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);  
	        if (schema == null) {  
	            schema = RuntimeSchema.getSchema(clazz);  
	            if (schema != null) {  
	                cachedSchema.put(clazz, schema);  
	            }  
	        }  
	        return schema;  
	    }  
	  
	    /** 
	     * ���л� 
	     * 
	     * @param obj 
	     * @return 
	     */  
	    public static <T> byte[] serializer(T obj) {  
	        @SuppressWarnings("unchecked")  
	        Class<T> clazz = (Class<T>) obj.getClass();  
	        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);  
	        try {  
	            Schema<T> schema = getSchema(clazz);  
	            byte result[] = ProtostuffIOUtil.toByteArray(obj, schema, buffer);  
	            return result;  
	        } catch (Exception e) {  
	            throw new IllegalStateException(e.getMessage(), e);  
	        } finally {  
	            buffer.clear();  
	        }  
	    }  
	  
	    /** 
	     * �����л� 
	     * 
	     * @param data 
	     * @param clazz 
	     * @return 
	     */  
	    public static <T> T deserializer(byte[] data, Class<T> clazz) {  
	        try {  
	            T obj = objenesis.newInstance(clazz);  
	            Schema<T> schema = getSchema(clazz);  
	            ProtostuffIOUtil.mergeFrom(data, obj, schema);  
	            return obj;  
	        } catch (Exception e) {  
	            throw new IllegalStateException(e.getMessage(), e);  
	        }  
	    }  
}
