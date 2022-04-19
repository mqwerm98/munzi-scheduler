# munzi-scheduler
spring scheduler를 이용한 scheduler module

## 사용법

### 0. 의존성 추가

```groovy
implementation group:'com.munzi', name:'munzi-scheduler', version:"${version_munzi_scheduler}"
```

### 1. SchedulerInstance 생성

```java
import com.munzi.munzischeduler.config.ASchedulerInstance;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "scheduler")
public class SchedulerInstance extends ASchedulerInstance {
}
```

> scheduler관련 설정 데이터를 받기 위해  
> ASchedulerInstance를 상속받은 SchedulerInstance를 만들어서, ConfigurationProperties 설정을 추가해준다.

### 2. main 설정

```java
import com.munzi.munzischeduler.SchedulerWorkerManager;
...
	
@SpringBootApplication(scanBasePackages = "package")
public class MunziSchedulerApplication {

    @Autowired
    private SchedulerInstance schedulerInstance;

    /**
     * application start
     *
     * @param context Spring ApplicationContext
     */
    public void start(ApplicationContext context) {
        SchedulerWorkerManager schedulerWorkerManager = new SchedulerWorkerManager(schedulerInstance);
        schedulerWorkerManager.startWorker(context);
    }

    /**
     * application main
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MunziSchedulerApplication.class, args);
        ((MunziSchedulerApplication) context.getBean("munziSchedulerApplication")).start(context);
    }

}
```

> main에서 SchedulerWorkerManager를 생성해,  startWorker함수로 worker를 실행시켜준다.

### 3. worker 생성

```java
import com.munzi.munzischeduler.SchedulerWorkerBase;
...
	
@Component
public class MunziWorker extends SchedulerWorkerBase {

    @Override
    public void processJob(Map data) {
			// worker의 작업 내용
		}
}
```

> SchedulerWorkerBase를 상속받은 class를 생성하고, processJob을 overriding해준다.

### 4. 설정파일

```yaml
scheduler:
  schedulerProperties:
    - name: "munziWorker"
      className: "com.test.munzi.worker.MunziWorker"
      dynamicDelay: false
      delay: 5000 # scheduled delay, when dynamicDelay is true then it is max value, unit: milliseconds
    - name: "munziWorker2"
      className: "com.test.munzi.worker.MunziWorker2"
      dynamicDelay: false     
      cron: 0,10,20,30,40,50 * * * * ?
      zoneId: "Asia/Seoul"
```

|option|description|
|------|------|
|name|생성한 worker의 bean명.<br/>확실하게 하려면 class 생성시에 bean명을 설정해주면 된다.|
|className|worker의 full package명|
|dynamicDelay|dynamic delay 여부.<br/>default는 false.<br/>true로 설정할 경우 delay가 delay설정한 시간 이하의 랜덤값으로 스케줄링된다.<br/>cron 설정되었을 경우엔 작동하지 않는다.|
|delay|scheduling의 delay time으로, spring scheduler의 fixed delay와 같이 동작한다.<br/>unit : millisecond<br/>default는 false.<br/>cron과 동시에 사용할 수 없다.|
|cron|cron expression 으로 scheduing 주기를 설정하는 방식.<br/>delay와 동시에 사용할 수 없으며, dynamicDelay 설정이 적용되지 않는다.|
|zoneId|cron의 timeZone 설정을 위한 zoneId. delay 사용시에는 optional, cron 사용시에만 required.<br/>ex) Asia/Seoul<br/>default : UTC|
