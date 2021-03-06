package keyboard.works;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringAppContext implements ApplicationContextAware {

	private static ApplicationContext CONTEXT;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CONTEXT = applicationContext;
	}
	
	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	} 
	
	public static <T> T getBean(String beanName, Class<T> type) {
		return CONTEXT.getBean(beanName, type);
	} 
	
	public static <T> T getBean(Class<T> type) {
		return CONTEXT.getBean(type);
	} 
	
}
