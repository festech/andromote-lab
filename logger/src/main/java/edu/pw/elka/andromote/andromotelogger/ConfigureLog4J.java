package edu.pw.elka.andromote.andromotelogger;

import android.os.Environment;
import android.util.Log;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.net.SyslogAppender;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Call {@link #configure()} from your application's activity.
 */
public class ConfigureLog4J {
	private static final String TAG = ConfigureLog4J.class.getName();

	public static void configure(String logFileName, String sysLogServerAddress) {
		final LogConfigurator logConfigurator = new LogConfigurator();
		if (!logFileName.endsWith(".log")) {
			logFileName = logFileName + ".log";
		}
		String directory = "/data/mobi.andromote.andro/log/";
		logConfigurator.setFileName(Environment.getExternalStorageDirectory() + File.separator + directory + File.separator + logFileName);
		Log.d(TAG, "logging file name: " + logConfigurator.getFileName());
		
		logConfigurator.setMaxFileSize(10000);
		logConfigurator.setMaxBackupSize(10);
		logConfigurator.setRootLevel(Level.DEBUG);
		logConfigurator.setLevel("org.apache", Level.DEBUG);
		logConfigurator.setUseLogCatAppender(true);
		logConfigurator.setUseFileAppender(true);
		logConfigurator.configure();
		
		if(sysLogServerAddress != null) {
			addSysLogAppender(sysLogServerAddress);
		}
	}

	private static void addSysLogAppender(String sysLogServerAddress) {
		Layout layout = new PatternLayout("%d - [%p::%c::%C] - %m%n");
		Appender syslogAppender = new SyslogAppender(layout, sysLogServerAddress, SyslogAppender.LOG_LOCAL0);
		Logger.getRootLogger().addAppender(syslogAppender);
	}

}