package org.creation.singlejob.example.config;


import java.util.concurrent.TimeUnit;

import org.creation.singlejob.EnableSingleJob;
import org.creation.singlejob.key.SpELSingleJobKeyGenerator;
import org.creation.singlejob.persistence.LocalMemoryDataPersistenceProvider;
import org.creation.singlejob.persistence.RedisSingleJobDataPersistenceProvider;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Import(value = {})
@ComponentScan(basePackages = {"org.creation.singlejob.example"})
//@ImportResource({"classpath*:**/applicationContext.xml"})
//@PropertySource(value = {"classpath:application.properties"})
// cgLib 可以不用接口，它底层调用asm 动态生成一个代理类去覆盖父类中非 final 的方法，然后实现 MethodInterceptor 接口的
// intercept 方法，这样以后直接调用重写的方法，比 JDK 要快。但是加载 cglib 消耗时间比直接 JDK
// 反射时间长，开发的过程中，如果是反复动态生成新的代理类推荐用 JDK 自身的反射，反之用 cglib。
@EnableAspectJAutoProxy(proxyTargetClass = true)
//@EnableAsync
//@EnableScheduling
//@EnableCaching
@EnableSingleJob
public class AppConfig {

    //private final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    @Bean
    LocalMemoryDataPersistenceProvider localMemoryDataPersistenceProvider()
    {
        return new LocalMemoryDataPersistenceProvider();
    }
    @Bean
    public PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
//    @Bean
//    public ExampleController exampleController()
//    {
//        return new ExampleController();
//    }
    
    @Bean
    public RedisSingleJobDataPersistenceProvider redisSingleJobDataPersistenceProvider() {
        Config config = new Config();
        config.useMasterSlaveServers().setMasterAddress("redis://cl-dev-all-redis26-task4service-kvx54vrf.docker.sdp:7019").setConnectTimeout(5000);
        RedissonClient redisson = Redisson.create(config);
        RedisSingleJobDataPersistenceProvider redisSingleJobDataPersistenceProvider = new RedisSingleJobDataPersistenceProvider();
        redisSingleJobDataPersistenceProvider.setRedissonClient(redisson);
        redisSingleJobDataPersistenceProvider.setLeaseTime(3600, TimeUnit.SECONDS);
        return redisSingleJobDataPersistenceProvider;
    }
    @Bean
    public SpELSingleJobKeyGenerator spELSingleJobKeyGenerator() {
        return SpELSingleJobKeyGenerator.getInstance();
    }

//    @Bean
//    public TaskScheduler getTaskScheduler() {
//        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
//        scheduler.setPoolSize(5);
//        scheduler.setThreadNamePrefix("-schedule-task-");
//        scheduler.setAwaitTerminationSeconds(60);
//        scheduler.setWaitForTasksToCompleteOnShutdown(true);
//        scheduler.setErrorHandler(new ScheduleErrorHandler());
//        scheduler.initialize();
//
//        return scheduler;
//    }
//
//    @Bean
//    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
//        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
//        pool.setCorePoolSize(96);
//        pool.setMaxPoolSize(Integer.MAX_VALUE);
//        pool.setQueueCapacity(96);
//        pool.setThreadNamePrefix("-spring-task-");
//        pool.setWaitForTasksToCompleteOnShutdown(true);
//        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        return pool;
//    }

//    @Bean
//    @Primary
//    public CacheManager cacheManager() {
//        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
//        GuavaCache appCache = new GuavaCache(Constants.APP_CACHE, CacheBuilder.newBuilder().build());
//        //GuavaCache calculateKeyCache = new GuavaCache(CalculateKeyManager.CALCULATE_KEY_CACHE, CacheBuilder.newBuilder().build());
//        GuavaCache groovyRuleCache = new GuavaCache(GroovyRule.RULECACHE, CacheBuilder.newBuilder().build());
//        GuavaCache ruleManagerCache = new GuavaCache(RuleManager.RULETEMPCACHE, CacheBuilder.newBuilder().build());
//        simpleCacheManager.setCaches(Arrays.asList(appCache,groovyRuleCache,ruleManagerCache));
//        return simpleCacheManager;
//    }
//
//    @Bean
//    public MapperScannerConfigurer taskScannerConfigurer() {
//        MapperScannerConfigurer config = new MapperScannerConfigurer();
//        config.setSqlSessionFactoryBeanName("basicSqlSessionFactory");
//        config.setBasePackage("com.nd.task.repository.mysql.mapper.task");
//        return config;
//    }
//
//    @Bean
//    public MapperScannerConfigurer logScannerConfigurer() {
//        MapperScannerConfigurer config = new MapperScannerConfigurer();
//        config.setSqlSessionFactoryBeanName("logSqlSessionFactory");
//        config.setBasePackage("com.nd.task.repository.mysql.mapper.log");
//        return config;
//    }


}
