import java.text.DecimalFormat;
import java.util.Scanner;

public class Principal {
	
	public final static int N = 100; //numero de muestras
	public final static int C = 2; //numero de clases
	public final static int dimension = 4; //dimension en la que trabajamos
	
	public int mostrarMenu (Scanner sc) {
		
		int opcion = 0;
		
		System.out.println();
		System.out.println();
		System.out.println("MENU");
		System.out.println();
		System.out.println("1.- Ejecutar Algoritmo por el metodo K-Medias.");
		System.out.println("2.- Ejecutar Algoritmo por el metodo Bayes.");
		System.out.println("3.- Ejecutar Algoritmo por el metodo Lloyd.");
		System.out.println("0.- Salir");
		System.out.println();
		
		opcion = Integer.parseInt(sc.nextLine());
		
		return opcion;
	}
	
	public void ejecutarAlgoritmoKMedias (String leidoArray[]) {
		
		KMedias kmedias = new KMedias();
		
		kmedias.inicializarMuestras(leidoArray);
		int i = 0;
		
		System.out.println("* Iteracion 0:");
		System.out.println("Centros iniciales:");
		kmedias.mostrarCentros();
		
		while (!kmedias.parar()) {
			kmedias.rellenarGradosPertenencia();
			kmedias.setV1Anterior(kmedias.getV1Actual());
			kmedias.setV2Anterior(kmedias.getV2Actual());
			kmedias.setV1Actual(kmedias.calcularV1());
			kmedias.setV2Actual(kmedias.calcularV2());
			i++;
			
			System.out.println("* Iteracion: " + i);
			System.out.println("Nuevos centros:");
			kmedias.mostrarCentros();
			
		} // while
		
		
		double[] punto1 = {5.1,3.5,1.4,0.2};
		double[] punto2 = {6.9,3.1,4.9,1.5};
		double[] punto3 = {5.0,3.4,1.5,0.2};
		kmedias.aQueClasePertenece(punto1);
		kmedias.aQueClasePertenece(punto2);
		kmedias.aQueClasePertenece(punto3);
	}
	
	public void ejecutarAlgoritmoBayes (String leidoArray[]) throws CloneNotSupportedException {
	
		DecimalFormat df = new DecimalFormat("0.000"); 
		Bayes bayes = new Bayes();

		//calculamos las medias
		double[] m1 = bayes.calcularM(leidoArray, 0, N/2);
		double[] m2 = bayes.calcularM(leidoArray, N/2, N);
		System.out.println("m1: " + df.format(m1[0])+ " , " + df.format(m1[1]) + " , " + df.format(m1[2]) + " , " + df.format(m1[3]));
		System.out.println("m2: " + df.format(m2[0])+ " , " + df.format(m2[1]) + " , " + df.format(m2[2]) + " , " + df.format(m2[3]));
		
		//calculamos los centros
		double[][] c1 = bayes.calcularC(m1, leidoArray, 0, N/2);
		double[][] c2 = bayes.calcularC(m2, leidoArray, N/2, N);

		//Mostramos el centro 1
		System.out.println("C1:");
		for (int a = 0; a< Principal.dimension; a++) {
			for (int b = 0; b< Principal.dimension; b++)
				System.out.print(df.format(c1[a][b]) + " , ");
			System.out.println();
		}
		//Mostramos el centro 2
		System.out.println("C2:");
		for (int a = 0; a < Principal.dimension; a++) {
			for (int b = 0; b < Principal.dimension; b++)
				System.out.print(df.format(c2[a][b]) + " , ");
			System.out.println();
		}
		
		// Operaciones para calcular la distancia.
		
		Matriz matriz1 = new Matriz(c1);
		Matriz matriz2 = new Matriz(c2);
		// Matrices a partir de las medias.
		double[][] m1Aux = {m1};
		Matriz ma1 = new Matriz(m1Aux);
		double[][] m2Aux = {m2};
		Matriz ma2 = new Matriz(m2Aux);
		// Calculo de las inversas de los centros.
		Matriz cInversa1 = Matriz.inversa(matriz1);
		Matriz cInversa2 = Matriz.inversa(matriz2);		
		
		// Punto a comprobar a que clase pertenece.
		double[][] x1= {{5.1,3.5,1.4,0.2}};		
		double[][] x2= {{6.9,3.1,4.9,1.5}};
		double[][] x3= {{5.0,3.4,1.5,0.2}};	
		
		bayes.aQueClasePertene(x1, cInversa1, cInversa2, ma1, ma2);
		bayes.aQueClasePertene(x2, cInversa1, cInversa2, ma1, ma2);
		bayes.aQueClasePertene(x3, cInversa1, cInversa2, ma1, ma2);
			
	}
	
	public void ejecutarAlgoritmoLloyd(String leidoArray[]){
		
		Lloyd lloyd = new Lloyd();
		lloyd.inicializarMuestras(leidoArray);
		do{
			for (int i = 0; i < N; i++){
				double x[] = new double[dimension];
				x[0] = lloyd.getMuestras()[i][0];
				x[1] = lloyd.getMuestras()[i][1];
				x[2] = lloyd.getMuestras()[i][2];
				x[3] = lloyd.getMuestras()[i][3];
				double distancia1 = lloyd.calcularDistanciaAlCentro(1,x);
				double distancia2 = lloyd.calcularDistanciaAlCentro(2,x);
				if (distancia1 < distancia2){
					lloyd.calcularC(1, x);
					
				}
				else{
					lloyd.calcularC(2, x);
				}
			}
			lloyd.mostrarcentroActual();
			lloyd.incrementarK();
		}
		while (!lloyd.comprobarFinalizado());
		lloyd.setIteracionesActuales(0);
		
		double[] x1= {5.1,3.5,1.4,0.2};		
		double[] x2= {6.9,3.1,4.9,1.5};
		double[] x3= {5.0,3.4,1.5,0.2};
		
		lloyd.mostrarAQueClasePertenece(x1);
		lloyd.mostrarAQueClasePertenece(x2);
		lloyd.mostrarAQueClasePertenece(x3);
		
	}

} // end Principal
