package com.beerbuddy;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

public class SetterGetterTest {

	private static ImmutableSet<ClassInfo> classes;
	
	@BeforeClass
	public static void setupClassPath() throws Exception {
		//this can throw an exception, if it does, something is wrong when running the
		//test and I would not trust this test if it continued. 
		ClassPath classPath = ClassPath.from(SetterGetterTest.class.getClassLoader());
		
		classes = classPath.getTopLevelClassesRecursive(SetterGetterTest.class.getPackage().getName());
	}

	@Test
	public void testGetters() throws Exception {
		
		classes.stream().forEach(info -> {
			
			Class<?> clazz = info.load();
			
			final Object instance = getInstance(clazz);
			
			if( instance != null ) {
				Arrays.asList(clazz.getMethods())
				.stream()
				.filter(method -> method.getName().startsWith("get"))
				.filter(method -> method.getParameterCount() == 0)
				.forEach(method -> {
					System.out.println("invoking " + method.getName());
					try {
						method.invoke(instance, new Object[0]);
					} catch (Exception e) {
						//e.printStackTrace();
					}
				});
				
			}
			
		});
		
	}
	
	@Test
	public void testIs() throws Exception {
		
		classes.stream().forEach(info -> {
			Class<?> clazz = info.load();
			final Object instance = getInstance(clazz);
			if( instance != null ) {
				Arrays.asList(clazz.getMethods())
				.stream()
				.filter(method -> method.getName().startsWith("is"))
				.filter(method -> method.getParameterCount() == 0)
				.forEach(method -> {
					System.out.println("invoking " + method.getName());
					try {
						method.invoke(instance, new Object[0]);
					} catch (Exception e) {
						//e.printStackTrace();
					}
				});
			}
		});
	}

	@Test
	public void testSetters() throws Exception {
		
		classes.stream().forEach(info -> {
			
			Class<?> clazz = info.load();
			
			final Object instance = getInstance(clazz);
			
			if( instance != null ) {
				Arrays.asList(clazz.getMethods())
				.stream()
				.filter(method -> method.getName().startsWith("set"))
				.filter(method -> method.getParameterCount() == 1)
				.forEach(method -> {
					System.out.println("invoking " + method.getName());
					
					try {
						//handle primitives a little differently since they do not understand null
						if( method.getParameters()[0].getType() == Integer.TYPE ) {
							method.invoke(instance, -1);
						} else if( method.getParameters()[0].getType() == Boolean.TYPE ) {
							method.invoke(instance, true);
						} if( method.getParameters()[0].getType() == Long.TYPE ) {
							method.invoke(instance, -1L);
						} else {
							method.invoke(instance, new Object[]{ null });
						}
					} catch (Exception e) {
						//e.printStackTrace();
					}
				});
				
			}
			
		});
		
	}
	
	protected Object getInstance(Class<?> clazz) {
		Object instance = null;
		Constructor<?>[] constructors = clazz.getConstructors();
		Optional<Constructor<?>> optional = findSimplestConstructor(constructors);
		try {
			Constructor<?> constructor = null;
			if( optional.isPresent() ) {
				constructor = optional.get();
			} else {
				constructor = constructors[0];
			}
			if( constructor.getParameterCount() == 0 ) {
				System.out.println("creating new " + clazz + " from default constructor");
				instance = constructor.newInstance();
			} else {
				Object[] params = Arrays.asList(constructor.getParameters())
					.stream()
					.map(p -> {
						if( p.getType() == Integer.TYPE ) {
							return -1;
						} else if( p.getType() == Boolean.TYPE ) {
							return true;
						} else if( p.getType() == Long.TYPE ) {
							return -1L;
						} else if( p.getType() == String.class ) {
							return "test";
						} else {
							return null;
						}
					})
					.collect(Collectors.toList())
					.toArray();
				System.out.println("creating new " + clazz + " with dummy parameters");
				instance = constructor.newInstance(params);
			}
		} catch (Exception e){}
		return instance;
	}

	private Optional<Constructor<?>> findSimplestConstructor(Constructor<?>[] constructors) {
		return Arrays.asList(constructors)
			.stream()
			.filter(SetterGetterTest::isSimplestConstructor)
			.findFirst();
	}
	
	private static boolean isSimplestConstructor(Constructor<?> constructor) {
		//is simple default no-arg constructor
		if ( constructor.getParameterCount() == 0 ) {
			return true;
		}
		//TODO: add logic to recursively span the constuctor's parameters and
		// see if we can easily create the object with this more complex constructor
		
		return false;
	}
	
}
