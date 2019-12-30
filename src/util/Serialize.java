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
 * Contient les méthodes pour serialiser et déserialiser un objet
 */
public abstract class Serialize {

	/**
	 * Sauvegarde un objet au chemin choisi
	 * @param object l'objet à serialiser
	 * @param path le chemin où sauvegarder l'objet
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
	 * Charge un objet situé au chemin passé en paramètres
	 * @param path le chemin depuis lequel on charge l'objet
	 * @return l'objet en question, on null si il n'est pas trouvé
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
