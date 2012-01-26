package org.dbunit.ext.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.dbunit.IDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;

public class DatabaseTesterResolver {

	private Class<?> targetClass;
	private Class<?> databaseTesterClassProvider;
	private Method databaseTesterMethodProvider;

	public DatabaseTesterResolver(Class<?> targetClass) throws DbUnitAnnotationException {
		this.targetClass = targetClass ;
		resolveProvider() ;
	}
	
	public IDatabaseTester createDatabaseTester (Object target) throws Exception {
		IDatabaseTester databaseTester = null ;
		if (databaseTesterMethodProvider != null) {
			Object databaseTestInstanceProvider = resolveInstanceProvider(target);
			try {
				databaseTester = (IDatabaseTester) databaseTesterMethodProvider.invoke(databaseTestInstanceProvider) ;
			} catch (Exception e) {
				Throwable t = e ;
				if (e instanceof InvocationTargetException) {
					t = ((InvocationTargetException) e).getCause() ;
				}
				throw new DbUnitAnnotationException("Exception while trying to invoke the method " + databaseTesterMethodProvider.getDeclaringClass().getName() + "." + databaseTesterMethodProvider.getName() + ": " + t.getMessage() , t) ;
			}
			if (databaseTester == null) {
				throw new DbUnitAnnotationException("The method " + databaseTesterMethodProvider.getDeclaringClass().getName() + "." + databaseTesterMethodProvider.getName() + " annotated with @" + DatabaseTester.class.getSimpleName() + " cannot return null!");
			}
		}
		else {
			databaseTester = new PropertiesBasedJdbcDatabaseTester() ;
		}
		return databaseTester ;
	}

	private Object resolveInstanceProvider(Object target) throws DbUnitAnnotationException {
		Object databaseTestInstanceProvider = null ;
		if (target.getClass().equals(databaseTesterClassProvider)) {
			databaseTestInstanceProvider = target ;
		}
		else if ((databaseTesterMethodProvider.getModifiers() & Modifier.STATIC) == 0) {
			try {
				databaseTestInstanceProvider = databaseTesterClassProvider.newInstance() ;
			} catch (Exception e) {
				throw new DbUnitAnnotationException("Exception while instantiating class " + databaseTesterClassProvider.getName() + " annotated with @" + DatabaseTesterProvider.class.getSimpleName() + ": " + e.getMessage(), e) ;
			}
		}
		return databaseTestInstanceProvider;
	}

	private void resolveProvider() throws DbUnitAnnotationException {
		databaseTesterMethodProvider = findDatabaseTesterMethod(targetClass) ;
		if (databaseTesterMethodProvider != null) {
			this.databaseTesterClassProvider = targetClass ;
		} else {
			resolveExternalProvider() ;
		}
	}

	private void resolveExternalProvider() throws DbUnitAnnotationException {
		DatabaseTesterProvider providerAnnotation = targetClass.getAnnotation(DatabaseTesterProvider.class);
		if (providerAnnotation != null) {
			this.databaseTesterClassProvider = providerAnnotation.value() ;
			databaseTesterMethodProvider = findDatabaseTesterMethod(this.databaseTesterClassProvider);
			if (databaseTesterMethodProvider == null) {
				throw new DbUnitAnnotationException("The class " + targetClass.getName() + " annotated with @" + DatabaseTesterProvider.class.getSimpleName() + " references the class " + this.databaseTesterClassProvider.getName() + " which declares or inherits no method annotated with @" + DatabaseTester.class.getSimpleName() + "!") ;
			}
		}
	}

	private Method findDatabaseTesterMethod(Class<?> clazz) throws DbUnitAnnotationException {
		Method result = null;
		for (Method method : clazz.getMethods()) {
			DatabaseTester databaseTesterAnnotation = method.getAnnotation(DatabaseTester.class);
			if (databaseTesterAnnotation != null) {
				boolean signatureOk = true ;
				if (!IDatabaseTester.class.isAssignableFrom(method.getReturnType())) {
					signatureOk = false ;
				}
				if (method.getParameterTypes().length > 0) {
					signatureOk = false ;
				}
				if (! signatureOk) {
					throw new DbUnitAnnotationException("The method " + method.getDeclaringClass().getName() + "." + method.getName() + " annotated with @" + databaseTesterAnnotation.annotationType().getSimpleName()  + " must be declared with no argument and the return type must be compatible with " + IDatabaseTester.class.getName() + "!");
				}
				result = method;
				break;
			}
		}
		return result;
	}
	
	public Class<?> getTargetClass() {
		return targetClass;
	}
}
