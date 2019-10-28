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

def HOSTNAME = hostname // scope 룰 때문에 nested block에서는 hostname 변수 사용불가
def PID = ProcessHandle.current().pid()
/**
 * %level = debug, info, warn, error, trace == %p
 * %thread = main.. == %t
 * %logger = 로거 잡힌 곳의 패키지 경로 최대 자릿수 36 {36} == %C
 * %msg = 로거 내용
 * pid =
 * %L = 로그 기록된 파일 소스 위치 줄
 * %relative = 밀리세컨드 단위 타임스탬프...
 * */
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
		maxFileSize = '100MB' // 파일 크기 100MB될때마다 롤링
	}
//	timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
//		maxFileSize = "100MB"
//	}
}

//다른 라이브러리 로그 경로
//logger("com.ha", INFO, ["console"])

root(INFO, ['console', 'file'])