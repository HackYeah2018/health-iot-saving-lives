package au.gov.hack.health.iot.stub;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

import org.apache.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan(basePackages = { "au.gov.hack.health.iot.stub" })
@EnableAsync
@EnableScheduling
public class StubConfig implements AsyncConfigurer {

	private static final Logger logger = Logger.getLogger(StubConfig.class);
	
	@Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(7);
        executor.setMaxPoolSize(42);
        executor.setQueueCapacity(11);
        executor.setThreadNamePrefix("StubExecutor-");
        executor.initialize();
        return executor;
    }

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncUncaughtExceptionHandler() {
			
			@Override
			public void handleUncaughtException(Throwable ex, Method method, Object... params) {
				logger.error("Uncaught async exception in method [" + method.getName() + "] on class [" + method.getDeclaringClass().getCanonicalName() + "]: " + ex.getMessage(), ex);
				
			}
		};
	}

	

	
}
