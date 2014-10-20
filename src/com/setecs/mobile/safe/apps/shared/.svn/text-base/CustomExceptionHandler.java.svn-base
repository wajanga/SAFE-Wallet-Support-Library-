package com.setecs.mobile.safe.apps.shared;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.widget.Toast;


public class CustomExceptionHandler implements UncaughtExceptionHandler {

	private final SharedMethods cv = new SharedMethods();
	private final Activity activity;
	private String localPath = "/sdcard/";
	private UncaughtExceptionHandler defaultUEH;

	public CustomExceptionHandler(Activity activity) {
		this.activity = activity;

		this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		//		String timestamp = TimestampFormatter.getInstance().getTimestamp();
		//        final Writer result = new StringWriter();
		//        final PrintWriter printWriter = new PrintWriter(result);
		//        ex.printStackTrace(printWriter);
		//        String stacktrace = result.toString();
		//        printWriter.close();
		////        String filename = timestamp + ".stacktrace";
		//		String filename = "foo.stacktrace";
		//
		//        if (localPath != null) {
		//            writeToFile(stacktrace, filename);
		//        }
		////        if (url != null) {
		////            sendToServer(stacktrace, filename);
		////        }
		//
		//        defaultUEH.uncaughtException(thread, ex);
		if (SharedMethods.socket != null) {
			if (!SharedMethods.socket.isClosed())

				Toast.makeText(activity, "Connection Error", Toast.LENGTH_LONG).show();

			cv.closeSession();
			cv.progressStop();
			android.os.Process.killProcess(android.os.Process.myPid());

		}

	}

	//	  private void writeToFile(String stacktrace, String filename) {
	//	        try {
	//	            BufferedWriter bos = new BufferedWriter(new FileWriter(
	//	                    localPath + "/" + filename));
	//	            bos.write(stacktrace);
	//	            bos.flush();
	//	            bos.close();
	//	        } catch (Exception e) {
	//	            e.printStackTrace();
	//	        }
	//	    }

}
