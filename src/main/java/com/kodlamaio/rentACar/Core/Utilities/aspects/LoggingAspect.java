//package com.kodlamaio.rentACar.Core.Utilities.aspects;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.time.LocalDateTime;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import com.kodlamaio.rentACar.Entities.Concretes.LogType;
//
//@Aspect
//@Component
//public class LoggingAspect {
//	
//	
//	
//	@Before("execution(* com.kodlamaio.rentACar.Business.Concretes.*.*(..))")
//	public LogType Log(JoinPoint joinPoint) {
//		LogType log = new LogType();
////		Logger logger = LoggerFactory.getLogger(LoggingController.class);
//		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//		log.setDate(LocalDateTime.now());
//		log.setClassName( signature.getDeclaringType().getName());
//		log.setMethodName(signature.getMethod().getName());
//		log.setParameters(joinPoint.getArgs()[0].toString());
//
//		File file = new File("C:\\logs\\operations.json");
////		File file = new File("text.txt");
//		  
////		  try {
////			  FileWriter fileWriter = new FileWriter(file, true);
////			  fileWriter.write(log.getDate().toString());
////			  fileWriter.write(log.getClassName());
////			  fileWriter.write(log.getMethodName());
////	d		  fileWriter.write(log.getParameters());
////			  fileWriter.close();
////		} catch (Exception e) {
////			// TODO: handle exception
////		}
//
//        try(BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(file,true))){
//        	bufferWriter.write(log.getDate().toString());
//        	bufferWriter.newLine();
//            bufferWriter.write(log.getClassName());
//            bufferWriter.newLine();
//            bufferWriter.write(log.getMethodName());
//            bufferWriter.newLine();
//            bufferWriter.write(log.getParameters());
//
//        } catch (IOException e) {
//            System.out.println("Unable to read file " +file.toString());
//        }
////		Writer output = new StringWriter();
////		try {
////			
////		} catch (Exception e) {
////			// TODO: handle exception
////		}
////		System.out.println("before brand manager deleteById");
////		System.out.println(pointJoinPoint.FIELD_GET);
////		System.out.println(signature.getParameterNames()[0]);
//		return log;
//	}
//	
//	
//	
//	
//	// advice
//	// @Before("execution(* com.kodlamaio.rentACar.Business.Concretes.*.*(..))")
////	@Around("execution(* com.kodlamaio.rentACar.Business.Concretes.*.*(..))")
////	public void BeforeLog(ProceedingJoinPoint pointJoinPoint) throws Throwable {
////		System.out.println("before logging");
////		pointJoinPoint.proceed();
////		System.out.println("after logging");
////		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
////		System.out.println("before brand manager deleteById");
////		System.out.println(joinPoint.FIELD_GET);
////		System.out.println(signature.getParameterNames()[0]);
////	}
//}
