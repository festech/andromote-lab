package edu.pw.elka.andromote.functionalityframework.utils;

import android.os.Environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FileUtils {
	private final static Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	/**
	 * private constructor - this class is not supposed to be instantiated
	 */
	private FileUtils() {
		
	}

	public static File createFileName(MediaType type) {
		File fileDir = getDir(type);
		if (!fileDir.exists() && !fileDir.mkdirs()) {
			logger.warn("Can't create directory to save image.");
			return new File("");
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault());
		String date = dateFormat.format(new Date());
		String photoFile = date + getExtension(type);

		String filename = fileDir.getPath() + File.separator + photoFile;
		File pictureFile = new File(filename);
		return pictureFile;
	}
	
	private static String getExtension(MediaType type) {
		String extension = "";
		switch(type) {
		case VIDEO:
			extension = ".mp4";
			break;
		case AUDIO:
			extension = ".3gpp";
			break;
		case PICTURE:
			extension = ".jpg";
			break;
		}
		return extension;
	}

	public  static File getDir(MediaType type) {
		File sdDir = new File("");
		switch(type) {
		case VIDEO:
			sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
			break;
		case AUDIO:
			sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS);
			break;
		case PICTURE:
			sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			break;
		}
		return new File(sdDir, "Andromote");
	}

	/**
	 * Enumeration that specifies the media type to create file name
	 * @author Sebastian Łuczak
	 *
	 */
	public enum MediaType {
		VIDEO,
		AUDIO,
		PICTURE;
	}
}
