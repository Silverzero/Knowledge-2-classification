
	import java.io.BufferedReader;
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;

	public class CargarFichero {
		
		/**
		 * Metodo que carga un fichero especificandole la ruta del mismo
		 */
		public String cargarDeFichero(String ruta_fichero) { 
			String leido = "";
			if(ruta_fichero != null) { 
				BufferedReader reader;
				try {
					reader = new BufferedReader(new FileReader(ruta_fichero));
					while(reader.ready()) {//mientras no se llegue al final del fichero
						leido += reader.readLine() + "\n";//guardamos la linea leida
					}
				} catch (FileNotFoundException e) { 
					System.err.println(e + "\nFichero no encontrado." + " Indique una ruta valida."); //si la ruta no es correcta
				} catch (IOException e) { 
					System.err.println (e + "\nError de hardware. " + " Consulte la documentacion."); // otro tipo de problemas
				}
			}
			return leido;
		}
}