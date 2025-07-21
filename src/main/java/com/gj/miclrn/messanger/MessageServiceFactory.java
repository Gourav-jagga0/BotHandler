//package com.gj.miclrn.messanger;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MessageServiceFactory {
//
//	private final List<String> serviceClassNames;
//
//	public MessageServiceFactory(@Value("${message.services}") List<String> serviceClassNames) {
//		this.serviceClassNames = serviceClassNames;
//	}
//
//	public MessageService createService(String serviceName) {
//		String className = serviceClassNames.stream()
//				.filter(name -> name.endsWith("." + serviceName) || name.equals(serviceName)).findFirst()
//				.orElseThrow(() -> new IllegalArgumentException("No service found with name: " + serviceName));
//		if (className.equals(serviceName)) {
//			className = "com.gj.miclrn.messangers." + className;c
//		}
//		try {
//			Class<?> clazz = Class.forName(className);
//
//			if (MessageService.class.isAssignableFrom(clazz)) {
//				return (MessageService) clazz.getDeclaredConstructor().newInstance();
//			} else {
//				throw new IllegalArgumentException("unsupported Service " + className );
//			}
//		} catch (ClassNotFoundException e) {
//			throw new IllegalArgumentException("Class not found: " + className, e);
//		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException
//				| InvocationTargetException e) {
//			throw new RuntimeException("Failed to instantiate service: " + className, e);
//		}
//	}
//
//	public Set<MessageService> createAllServices() {
//		return serviceClassNames.stream().map(this::createService).collect(Collectors.toSet());
//	}
//}