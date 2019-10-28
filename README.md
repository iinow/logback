# logback 작성법 :star_struck:

위 프로젝트는 logback.groovy 로 작성되어 있습니다. 

- groovy 로 작성하기 위해서는 위 groovy 의존 모듈을 받아야 합니다.

```groovy
implementation 'org.codehaus.groovy:groovy-all:3.0.0-rc-1'
```

- src/main/resources/logback.groovy 생성
- 아래 코드 작성

```groovy
import ch.qos.logback.classic.encoder.PatternLayoutEncoder 
import ch.qos.logback.core.FileAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.classic.turbo.MDCFilter
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import ch.qos.logback.core.util.FileSize

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.WARN
import ch.qos.logback.classic.gaffer.ComponentDelegate

def HOSTNAME = hostname 
def PID = ProcessHandle.current().pid()

appender('console', ConsoleAppender) {
   encoder(PatternLayoutEncoder) {
	   pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} %highlight(%level) %magenta(${PID}) --- [%10thread] %cyan(%-40logger{36}) : %msg%n"
   }
}

appender('file', RollingFileAppender) {
	file = "./log/logback.log"
	append = true
	
	encoder(PatternLayoutEncoder) {
		pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} %level ${PID} --- [%thread] %logger{36} : %msg%n"
	}
	
	rollingPolicy(ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy) {
		fileNamePatternStr = "./log/logback-%d{yyyy-MM-dd}.%i.log.gz"
		maxHistory = 60
		totalSizeCap = "100MB"
		maxFileSize = FileSize.valueOf("50MB")
	}
	
	triggeringPolicy(ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy) {
		maxFileSize = '100MB'
	}
}

root(INFO, ['console', 'file'])
```
- xml 과는 다르게 자동 완성 나온다 대신 gradle 에 groovy 의존 모듈 추가를 해야한다.


:smiley_cat: :smiley_cat: :smiley_cat:

