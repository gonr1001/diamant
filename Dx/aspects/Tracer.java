///*
// * Created on Mar 30, 2005
// * 
// */
//package aspects;
//
//import java.awt.event.ActionEvent;
//import java.io.File;
//
//import org.apache.log4j.*;
//import org.aspectj.lang.*;
//
//import dInterface.DApplication;
//
///**
// * @author Pascal
// * 
// */
//public aspect Tracer {
//    private static int stackDepth = 0;
//    private static final String SPACE = " ";
//    private static final int INDENT = 4;
//
//    private static final String LOGGER_NAME = "AspectTracer";
//    private static final String LOGGER_LAYOUT = "%m%n";
//    private static final boolean LOGGER_ADDITIVITY = false;
//    private static final String FILE_APPENDER_NAME = "trace" + File.separator + LOGGER_NAME + ".log";
//	
//    static {
//    	Layout layout = new PatternLayout(LOGGER_LAYOUT);
//    	Appender appender = null;
//    	//		appender = new ConsoleAppender(layout);
//    	try {
////	    	appender = new FileAppender(layout, FILE_APPENDER_NAME);
//    		appender = new RollingFileAppender(layout, FILE_APPENDER_NAME);
//    		((RollingFileAppender)appender).setMaxFileSize("1MB");
//    	} catch(java.io.IOException e) {
//    		System.err.println("Aspect \"Tracer\": Incapable d'ouvrir le fichier " +
//	    		FILE_APPENDER_NAME);
//    	}
//    	Logger.getLogger(LOGGER_NAME).addAppender(appender);
//    	Logger.getLogger(LOGGER_NAME).setAdditivity(LOGGER_ADDITIVITY);
//    }
//	
//    pointcut methodCall() : 
//    	(
//    		call(* dConstants..*(..)) || 
//			call(* dDeveloper..*(..)) ||
//			call(* dInterface..*(..)) || 
//			call(* dInternal..*(..))  ||
//			call(* dmains..*(..))     ||
//			call(* dResources..*(..)) ||
//			call(* dTest..*(..))      ||
//			call(* eLib..*(..))       ||
//			call(* eTest..*(..))      ||
//			call(* lineInterface..*(..))
//    	);
//	
//    pointcut methodExecution() : 
//    	(
//    		execution(* dConstants..*(..)) || 
//			execution(* dDeveloper..*(..)) ||
//			execution(* dInterface..*(..)) || 
//			execution(* dInternal..*(..))  ||
//			execution(* dmains..*(..))     ||
//			execution(* dResources..*(..)) ||
//			execution(* dTest..*(..))      ||
//			execution(* eLib..*(..))       ||
//			execution(* eTest..*(..))      ||
//			execution(* lineInterface..*(..))
//    	);
//    
//    pointcut publicMethodExecution() : 
//    	(
//    		execution(public * dConstants..*(..)) || 
//			execution(public * dDeveloper..*(..)) ||
//			execution(public * dInterface..*(..)) || 
//			execution(public * dInternal..*(..))  ||
//			execution(public * dmains..*(..))     ||
//			execution(public * dResources..*(..)) ||
//			execution(public * dTest..*(..))      ||
//			execution(public * eLib..*(..))       ||
//			execution(public * eTest..*(..))      ||
//			execution(public * lineInterface..*(..))
//    	);
//    
//    pointcut allExecution() :
//    	!within(Tracer) && execution(* *(..));
//	
//    pointcut actionPerformed() : !within(Tracer) && 
//		cflow(execution(public void dInterface.DApplication.actionPerformed(ActionEvent))) &&
//		execution(public void execute(DApplication));
//    
//    pointcut commandExecution() : execution(public void dInterface.Command+.execute(DApplication));
//
//    before() : methodExecution() {
//    	Logger.getLogger(LOGGER_NAME).info(createSimpleTracerOutput(thisJoinPointStaticPart));
//
//    	++stackDepth;
//    }
//	
//    after() : methodExecution() {
//    	--stackDepth;
//    }
//	
//    private String createTracerOutput(JoinPoint jp) {
//    	StringBuffer str = new StringBuffer();
//		
//    	// ajout d'espaces afin de determiner visuellement la profondeur du
//    	// stack lors de
//    	// l'appelle de la methode
//    	for (int i = 0; i < stackDepth*INDENT; ++i)
//    		str.append(SPACE);
//		
//    	// ajout a la String a retourner du nom de la methode
//    	str.append(jp.getStaticPart().getSignature().getName() + "()");
//		
//    	// extraction des arguments afin de les integrer a la String a retourner
//    	Object[] args = jp.getArgs();
//    	for (int i = 0; i < args.length-1; ++i) {
//		   str.insert(str.length()-1, args[i] + ",");
//    	}
//		
//    	if (args.length != 0)
//    		str.insert(str.length()-1, args[args.length-1]);
//
//    	// informations contextuelles supplementaires
//    	str.append(" - " + 
//    		jp.getStaticPart().getSignature().getDeclaringTypeName() + 
//			"@" + 
//			jp.getStaticPart().getSourceLocation().getFileName() + 
//			":" + 
//			jp.getStaticPart().getSourceLocation().getLine());
//		
//    	return str.toString();
//    }
//    
//    private String createSimpleTracerOutput(JoinPoint.StaticPart jps) {
//    	StringBuffer str = new StringBuffer();
//    		
//    	// ajout d'espaces afin de determiner visuellement la profondeur du
//    	// stack lors de
//    	// l'appelle de la methode
//    	for (int i = 0; i < stackDepth*INDENT; ++i)
//    	    str.append(SPACE);
//    		
//    	// ajout a la String a retourner du nom de la methode
//    	str.append(jps.getSignature().getName() + "()");
//
//    	// informations contextuelles supplementaires
//    	str.append(" - " + 
//    		   jps.getSignature().getDeclaringTypeName() + 
//    		   "@" + 
//    		   jps.getSourceLocation().getFileName() + 
//    		   ":" + 
//    		   jps.getSourceLocation().getLine());
//    		
//    	return str.toString();
//    }
//}
