import java.text.DecimalFormat;

public class Bayes {	
	
	public double[] calcularM (String[] leido, int ini, int fin) {
		
		double[] vectorM = new double[Principal.dimension];
		double r = 0.0;
		
		for (int j = 0; j < Principal.dimension; j++) {	
			for (int i = ini; i < fin; i++) {
				String[] x = leido[i].split(",");
				r += Double.parseDouble(x[j]);				
			}
			vectorM[j] = r;
			r = 0.0;
		}
		
		for (int p = 0; p < Principal.dimension;p++)
			vectorM[p] = (vectorM[p] / (fin-ini));
				
		return vectorM;
	}

	
	public double[][] calcularC (double[] m, String[] leido, int ini, int fin ) {
		double[][] vectorCAux = new double[Principal.dimension][Principal.dimension];
		double[][] vectorC = new double[Principal.dimension][Principal.dimension];
		double vectorResta[] = new double[Principal.dimension];
		
		//para todas las muestras
		for (int l = ini; l< fin; l++){
			String[] x = leido[l].split(",");
			
			//calculamos la resta de la muestra actual con la media
			for(int i = 0; i< Principal.dimension; i++)
				vectorResta[i] = Double.parseDouble(x[i]) -m[i];	
			
			//multiplicamos la resta por su traspuesta
			for (int j = 0; j < Principal.dimension; j++) {
				for (int k = 0; k < Principal.dimension; k++)
					//el resultado de la multiplicacion es una matriz de tamao dimension x dimension
					vectorCAux[j][k] = vectorResta[j] * vectorResta[k];
			}
			
			//sumamos a la matriz solucion la matriz actual
			for (int p = 0; p < Principal.dimension; p++) {
				for (int q = 0; q < Principal.dimension; q++)
					vectorC[p][q] += vectorCAux[p][q];
			}
		}
		
		//dividimos la matriz solucion por el numero de muestras de la clase
		for (int a = 0; a< Principal.dimension; a++) {
			for (int b = 0; b< Principal.dimension; b++)
				vectorC[a][b] = (vectorC[a][b] / (fin-ini));
		}
		
		return vectorC;
	}
	
	public void aQueClasePertene(double[][] x, Matriz cInversa1, Matriz cInversa2, Matriz ma1, Matriz ma2 )
	{

		// Mostramos la distancia del punto a los dos centros.
		System.out.println();
		System.out.print("-Punto : ");
		System.out.print(x[0][0]);
		for (int i = 1; i < 4; i++)
			System.out.print(" , " + x[0][i]);
		
		
		Matriz mPunto = new Matriz(x);
		DecimalFormat df = new DecimalFormat("0.000");
		// Calculamos la distancia del punto a los dos centros.
		double distancia1 = this.calcularDistancia(mPunto, ma1, cInversa1);
		double distancia2 = this.calcularDistancia(mPunto, ma2, cInversa2);

		System.out.println();
		System.out.println("Distancia del centro 1 al punto: "
				+ df.format(distancia1));
		System.out.println("Distancia del centro 2 al punto: "
				+ df.format(distancia2));

		// Comprobamos y mostramos cual es la distancia menor.
		if (distancia1 < distancia2)
			System.out.print("Este punto pertenece a la clase uno: Iris-setosa");
		else System.out.print("Este punto pertenece a la clase dos: Iris-versicolor");
		
		System.out.println();
					
	}
	
	public double calcularDistancia (Matriz punto, Matriz m , Matriz cInversa) {
		
		double distancia;
		Matriz resta = Matriz.resta(punto, m);
		Matriz restaTraspuesta = resta.traspuesta(resta);
		Matriz distanciaAux = Matriz.productoPara1x4(resta, cInversa);
		
		distancia = Matriz.productoPara1x1(distanciaAux, restaTraspuesta);
		
		return distancia;
	}
	
	
}
