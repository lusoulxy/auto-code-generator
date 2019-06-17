package com.silas.generator.helper.interface_;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CreateFileHelperV2 implements CreateHelper{
	protected List<Module> modules = new ArrayList<Module>();;//所有模块
	protected Map<String,Integer> toCreateModule = new HashMap<String,Integer>();//要生成的模块
	
	/**
	 * 模块内容
	 * @return
	 */
	@Override
	public List<Module> getModules(Object object,List<Module> modules){
		Class clazz = object.getClass();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.getName() == null || (toCreateModule.get(method.getName()) == null))
				continue;
			if (toCreateModule.get(method.getName()) != 0) {
				try {
					Module module =  (Module) method.invoke(object, null);
					module.setOrder(toCreateModule.get(method.getName()));
					modules.add(module);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return modules;
	};
	
}
