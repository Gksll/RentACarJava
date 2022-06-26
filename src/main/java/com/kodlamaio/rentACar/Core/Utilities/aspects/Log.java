//package com.kodlamaio.rentACar.Core.Utilities.aspects;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.time.LocalDate;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Component
//@Aspect
//public class Log {
//	Logger log = LoggerFactory.getLogger(Log.class);
//
//	@Pointcut("execution(* com.kodlamaio.rentACar.Business.Concretes.*.*(..))")
//	public void myPointcut() {
//
//	}
//
//	@Before("myPointcut()")
//	public void applicationLogger(JoinPoint joinPoint) throws Throwable {
//		ObjectMapper mapper = new ObjectMapper();
//		String methodName = joinPoint.getSignature().getName();
//		String className = joinPoint.getTarget().getClass().getSimpleName().toString();
//		Object[] array = joinPoint.getArgs();
//		log.info("date:" + LocalDate.now().getYear() + "\n" + "className " + className + "\n" + " : " + methodName
//				+ "()" + "arguments : " + mapper.writeValueAsString(array));
////		log.info(className + " : " + methodName + "()" + "Response : " + mapper.writeValueAsString(object));
//		File file = new File("C:\\logs\\operations.json");
//		try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(file, true))) {
//			bufferWriter.write("\"date\":" + LocalDate.now().getYear());
//			bufferWriter.newLine();
//			bufferWriter.write("\"className\":" + className);
//			bufferWriter.newLine();
//			bufferWriter.write("\"methodName\":" + methodName);
//			bufferWriter.newLine();
//			bufferWriter.write("\"parameters\":" + mapper.writeValueAsString(array));
//			bufferWriter.newLine();
//		} catch (IOException e) {
//			System.out.println("Unable to read file " + file.toString());
//		}
//	}
//
//}
