package logging; //Requires to be in the package logging

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * 
 * @author lukeraeside This logger class must be used throughout your assignment
 *         each time Log every method entry by placing logMethodEntry(this) at
 *         the beginning of every method Log every method exit by placing
 *         logMethodExit(this) at the end of every method
 *         
 *         For static methods use logStaticMethodEntry() and logStaticMethodExit() 
 *         This is important as these methods cannot pass a reference 'this'
 *         
 *         For constructors use logConstructor(Object classId)
 * 
 */
public class AssignmentLogger {

	 static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH.mm.ss");   
	static LocalDateTime now = LocalDateTime.now(); 
	static Logger log = Logger.getLogger("External file log");
	static Logger advLog = Logger.getLogger("External advanced log");
	static Handler fileOut = createExternalLogFile();
	static Handler fileAdvLog = createAdvancedLogger();

	/**
	 * Register this computer and version of Java for the assignment
	 * Do not delete the log files
	 * 
	 */
	public synchronized static void register(Object classId) {
		log.log(Level.INFO,"\nRegister " + getDetails(classId) + "\n");
	}
	
	/**
	 * Makes a log entry at the start of the method
	 */
	public static synchronized void logMethodEntry(Object classId) {
		log.log(Level.INFO, "\n" + getDetails(classId));
		AssignmentLogger.logAdvancedDetails(classId);
	}

	/**
	 * Makes a log entry at the end of the method
	 */
	public static synchronized void logMethodExit(Object classId) {
		log.log(Level.INFO, "\n" + getDetails(classId));
		AssignmentLogger.logAdvancedDetails(classId);
	}
	
	/**
	 * Make a log for a static method entry
	 * No parameter as there is no object created
	 */
	public synchronized static void logStaticMethodEntry() {
		log.log(Level.WARNING,"\nStatic method entry " + "\n");
	}
	
	/**
	 * Make a log for a static method exit
	 * No parameter as there is no object created
	 */
	public synchronized static void logStaticMethodExit() {
		log.log(Level.WARNING,"\nStatic method exit " + "\n");
	}

	/**
	 * Makes a log entry at the beginning of a constructor
	 */
	public static synchronized void logConstructor(Object classId) {
		log.log(Level.INFO, "\n" + getDetails(classId));
		AssignmentLogger.logAdvancedDetails(classId);
	}

	/**
	 * Makes a log entry at the start of a main method
	 */
	public static synchronized void logMain() {
		log.log(Level.WARNING,"\nMain method entry " + "\n");
	}

	/**
	 * Makes a log entry at every catch error
	 */
	public static synchronized void logCatchException(Exception e) {
		log.log(Level.WARNING, "\nException " + e.toString() + " " + e.getMessage() + "\n");
	}

	/**
	 * This method sets up the external file handler
	 * 
	 * @return Handler the file handler for the external file log
	 */
	private static synchronized Handler createExternalLogFile() {
		
		try {
			if (fileOut==null) {
				fileOut = new FileHandler(System.getProperty("user.name") + "_" + dtf.format(now) + "_logDetails.txt");
				fileOut.setFormatter(new SimpleFormatter());
			}

			log.addHandler(fileOut);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileOut;
	}
	
	/**
	 * This method sets up the external file handler for the advanced logger
	 * 
	 * @return Handler the file handler for the advanced external file log
	 */
	private static Handler createAdvancedLogger() {

		try {
			if (fileAdvLog==null) {
				fileAdvLog = new FileHandler(System.getProperty("user.name") + "_" + dtf.format(now) + "_adv_logDetails.txt");
				fileAdvLog.setFormatter(new SimpleFormatter());
			}
			advLog.addHandler(fileAdvLog);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileAdvLog;
	}

	/**
	 * Returns most details for use in the logger
	 * 
	 * @param classId The class passed to the logger
	 * @return String of all of the details produced by the logger
	 */
	private static String getDetails(Object classId) {
		return classId.getClass().getCanonicalName() + "\nRan from: " + System.getProperty("user.dir") + "\nRan by: "
				+ System.getProperty("user.name") + "\nOS Details:" + System.getProperty("os.name") + " "
				+ System.getProperty("os.arch") + "\nJava details:" + System.getProperty("java.version") + " "
				+ System.getProperty("java.home") + "\n";
	}
	
	/**
	 * Make a call to create a log file with advanced details about the classes created
	 * @param classId The class that was passed
	 * @return Sends a log of advanced information about the code
	 */
	
	public static void logAdvancedDetails(Object classId) {
		logMethodDetails(classId);
		logFieldDetails(classId);	
		logConstructorDetails(classId);
		getDetails(classId);
	}
	/**
	 * This method will send to the advanced log file all of the details of the code created
	 * It includes the methods and parameters and return types etc.
	 * @param classId The class that was passed
	 */
	private static void logFieldDetails(Object classId) {
		Vector<String> fieldNames = new Vector<String>();
		Vector<String> fieldTypes = new Vector<String>();
		Vector<String> fieldAccess = new Vector<String>();
		
		Field[] fields = classId.getClass().getDeclaredFields();

		advLog.log(Level.OFF, "\n -------------------FIELDS------------------------------ \n");

		
		for (int i = 0; i < fields.length; i++) {
			fieldNames.add(fields[i].getName());
			fieldTypes.add(fields[i].getType().getCanonicalName());
			fieldAccess.add(decodeModifier(fields[i].getModifiers()));

			advLog.log(Level.OFF, "\n***FIELD " + (i+1) + "\n" + fieldAccess.elementAt(i) + " " + fieldTypes.elementAt(i) + " " + fieldNames.elementAt(i) );
		}		
	}

	/**
	 * This method will send to the advanced log file all of the details of the code created
	 * It includes the fields, field types etc.
	 * @param classId The class that was passed
	 */
	private static void logMethodDetails(Object classId) {

		Vector<String> methodNames = new Vector<String>();
		Vector<String> methodReturnTypes = new Vector<String>();
		Vector<String> methodAccess = new Vector<String>();
		Vector<String> methodParameters = new Vector<String>();
		
		Method[] methods = classId.getClass().getDeclaredMethods();
		advLog.log(Level.OFF, "\n -------------------METHODS------------------------------ \n");
		
		for (int i = 0; i < methods.length; i++) {
			methodNames.add(methods[i].getName());
			methodReturnTypes.add(methods[i].getReturnType().getCanonicalName());
			methodAccess.add(decodeModifier(methods[i].getModifiers()));
			methodParameters.add(decodeParameters(methods[i].getParameters()));
			
			advLog.log(Level.OFF, "\n***METHOD " + (i+1) + "\n" + methodAccess.elementAt(i) + " " + methodReturnTypes.elementAt(i) + " " + methodNames.elementAt(i) + " " + methodParameters.elementAt(i));

		}
		
		
	}
	
	/**
	 * This method will add to the logs any constructor details
	 * @param classId The class to be examined
	 */
	private static void logConstructorDetails(Object classId) {
		Vector<String> constructorNames = new Vector<String>();
		Vector<String> constructorParameters = new Vector<String>();
		
		advLog.log(Level.OFF, "\n -------------------CONSTRUCTORS------------------------------ \n");
		
		Constructor[] constructors = classId.getClass().getConstructors();
		
		for (int i = 0; i < constructors.length; i++) {
			constructorNames.add(constructors[i].getName());
			constructorParameters.add(decodeParameters(constructors[i].getParameters()));
			
			advLog.log(Level.OFF, "\n***CONSTRUCTOR " + (i+1) + "\n" + " " + constructorNames.elementAt(i) + " " + constructorParameters.elementAt(i));

		}
	}
	
	/**
	 * Decodes the modifier to readable code
	 * @param mod The modifier in integer form
	 * @return The modifier in string form public, private, protected, package (default)
	 */
	private static String decodeModifier(int mod) {
		
		if(Modifier.isPublic(mod)) {
			return "public";
		}
		else if(Modifier.isPrivate(mod)) {
			return "private";
		}
		else if(Modifier.isProtected(mod)) {
			return "protected";				
		}
		else {
			return "package";
		}
	}
	
	/**
	 * Decodes the modifier to readable code
	 * @param mod The modifier in integer form
	 * @return The modifier in string form public, private, protected, package (default)
	 */
	private static String decodeParameters(Parameter[] array) {
		
		String parameterDetails = "";
		
		if(array.length<1) {
			return "No-parameters";
		}
		else {
			for (int i = 0; i < array.length; i++) {
				parameterDetails += array[i].getType().getName() + " " + array[i].getName() + "\n";
			}
		}
		
		return parameterDetails;
	}

}
