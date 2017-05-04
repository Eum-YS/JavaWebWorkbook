package spms.context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.reflections.Reflections;

import spms.annotation.Component;

public class ApplicationContext {
	Hashtable<String, Object> objTable = new Hashtable<String, Object>();
	
	public Object getBeans(String key){
		return objTable.get(key);
	}
	
	public void addBean(String key, Object obj){
		objTable.put(key, obj);
	}
	
	public void prepareObjectByAnnotation(String basePackage) throws Exception{
		Reflections reflector = new Reflections(basePackage);
		
		Set<Class<?>> list = reflector.getTypesAnnotatedWith(Component.class);
		String key = null;
		for(Class<?> clazz : list){
			key = clazz.getAnnotation(Component.class).value();
			objTable.put(key,  clazz.newInstance());
		}
	}
	
	public void prepareObjectsByProperties(String propertiesPath) throws Exception{
		Properties props = new Properties();
		props.load(new FileReader(propertiesPath));
		
		Context ctx = new InitialContext();
		String key = null;
		String value = null;
		
		for(Object item : props.keySet()){
			key = (String)item;
			value = props.getProperty(key);
			if(key.startsWith("jndi.")){
				objTable.put(key, ctx.lookup(value));
			} else {
				objTable.put(key, Class.forName(value).newInstance());
			}
		}
	}
	
	public void injectDependency() throws Exception {
//		System.out.println("INJECTION DEPENDENCY");
		for (String key : objTable.keySet()){
			if(!key.startsWith("jndi.")){
//				System.out.println(" key:" + key + " value:" + objTable.get(key).toString());
				callSetter(objTable.get(key));
			}
		}
	}
	
	private void callSetter(Object obj) throws Exception{
		Object dependency = null;
		for (Method m : obj.getClass().getMethods()){
			if(m.getName().startsWith("set")){
				dependency = findObjectByType(m.getParameterTypes()[0]);
				if(dependency != null){
					m.invoke(obj, dependency);
//					System.out.println(obj.getClass().getName() + ":" + m.getName() + " callSetter complete !!!!");
				}
			}
		}
	}
	
	private Object findObjectByType(Class<?> type){
		for(Object obj : objTable.values()){
			if(type.isInstance(obj)){
				return obj;
			}
		}
		return null;
	}

}
