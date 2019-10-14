package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Contains the methods used to serialize and deserialize an object
 */
public abstract class Serialize {

	/**
	 * Can be used to save any object on a given path
	 * @param object the object to serialize
	 * @param path the path where to save the object
	 */
	public static void save(Object object, String path) {
		ObjectOutputStream output;
		try {
			// We used a BufferedOutputStream to increase performance during writing
			output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
			output.writeObject(object);
			output.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Can be used to load an object depending on his path
	 * @param path the path where to load the object
	 * @return the object loaded, else null if the object isn't found
	 */
	public static Object load(String path) {
		ObjectInputStream input;
		File f = new File(path);
		// If the file doesn't exist or is a directory, return null
		if(!f.exists() || f.isDirectory()) {
			return null;
		}
		// Else :
		try {
			// We used a BufferedInputStream to increase performance during reading
			input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(path)));
			Object object = input.readObject();
			input.close();
			return object;
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
