import java.text.DecimalFormat;

public class KMedias {

	private static final int B = 2; //peso exponencial
	private static double tolerancia = 0.01;
	
	private double[][] muestras = new double[Principal.N][Principal.dimension];
	
	private double[] v1Actual = {4.6, 3.0, 4.0, 0.0}; //centro inicial 1
	private double[] v2Actual = {6.8, 3.4, 4.6, 0.7}; //centro inicial 2
	
	private double[] v1Anterior = {0.0, 0.0, 0.0, 0.0}; //centro inicial 1
	private double[] v2Anterior = {0.0, 0.0, 0.0, 0.0}; //centro inicial 2
	
	
	private double[][] U = new double[Principal.N][Principal.C];
	
	// Inicializar muestras para quedarnos con las 100 muestras y obviar la clase a que pertenecen. 
	public void inicializarMuestras (String[] leido) {
		
		for (int i = 0;i < Principal.N; i++) {
			String[] x = leido[i].split(",");
			for (int j = 0; j < Principal.dimension ;j++) {
				muestras[i][j] = Double.parseDouble(x[j]);
			}
		}
	}
	
	// Calcula P para un Vi y un Xi dados.	
	private double calcularP (int numMuestra, int numCLase) {
		
		double p = 0.0;
		double distanciaAClase1 = 0.0;
		double distanciaAClase2 = 0.0;
		double[] muestra = {muestras[numMuestra][0],muestras[numMuestra][1],muestras[numMuestra][2],muestras[numMuestra][3]};
		distanciaAClase1 = calcularDistancia(v1Actual, muestra);
		distanciaAClase2 = calcularDistancia(v2Actual, muestra);
		
		double numerador = 0.0;
		double denominador = 0.0;
		
		if (numCLase == 0){
			numerador = Math.pow((1/distanciaAClase1),1/(B-1));
			denominador = Math.pow((1/distanciaAClase1),1/(B-1)) + Math.pow((1/distanciaAClase2),1/(B-1));
		} else {
			numerador = Math.pow((1/distanciaAClase2),1/(B-1));
			denominador = Math.pow((1/distanciaAClase1),1/(B-1)) + Math.pow((1/distanciaAClase2),1/(B-1));
		}
		p = numerador/denominador;
		
		return p;
	}

	// Calcula la distancia entre dos puntos
	private double calcularDistancia (double[] v,double[] x) {
		
		double distancia = 0.0;
		
		for (int i=0;i<Principal.dimension;i++)
			distancia += Math.pow(x[i]-v[i],2);

		//return Math.sqrt(distancia);
		return distancia;
	}
	
	// Rellenamos la matriz U con las diferentes probabilidades.
	public void rellenarGradosPertenencia() {

		for(int i = 0; i < Principal.N; i++) {
			for(int j = 0; j < Principal.C; j++)
				U[i][j]= calcularP(i,j);
		}
	}
	
	// Calcula V1
	public double[] calcularV1() {
		
		double denominador = 0.0;
		for (int i = 0; i < Principal.N; i++)
			denominador += Math.pow(calcularP(i,0),B);

		double[] numerador =new double [Principal.dimension];
		double[] xj = new double [Principal.dimension];
		
		for (int j = 0; j <Principal.N; j++) {
			for (int k = 0; k < Principal.dimension; k++) {
				xj[k] = muestras[j][k];
				numerador[k] += Math.pow(U[j][0],B)*xj[k];
			}
		}
		
		double[] nuevoCentro = new double[Principal.dimension];
		
		for (int p = 0; p < Principal.dimension;p++)
			nuevoCentro[p] = (numerador[p] / denominador);
		 
		return nuevoCentro;
		
	}
	
	// Calcula V2
	public double[] calcularV2() {
		
		double denominador = 0.0;
		for(int i = 0; i < Principal.N; i++){
			denominador += Math.pow(calcularP(i,1),B);
		}
		double[] numerador =new double [Principal.dimension];
		double[] xj = new double [Principal.dimension];
		
		for(int j = 0; j < Principal.N; j++){
			xj[0] = muestras[j][0];
			xj[1] = muestras[j][1];
			xj[2] = muestras[j][2];
			xj[3] = muestras[j][3];
			for(int k = 0; k < Principal.dimension; k++){
				numerador[k] += Math.pow(U[j][1],B)*xj[k]; 
			}
		}
		double[] nuevoCentro = new double[Principal.dimension];
		for (int p = 0; p<Principal.dimension;p++)
			nuevoCentro[p] = numerador[p]/denominador;
		 
		return nuevoCentro;
	}
	
	// Mtodo para indicar si hay que hacer ms iteraciones o no dependiendo 
	//de si los centros Vi son practicamente iguales.
	public boolean parar() {
		
		DecimalFormat df = new DecimalFormat("0.000"); 
		double distancia1 = calcularDistancia(v1Actual,getV1Anterior());
		double distancia2 = calcularDistancia(v2Actual,getV2Anterior());
		
		System.out.println("Distancias");
		System.out.println("dist 1: " + df.format(distancia1));
		System.out.println("dist 2: " + df.format(distancia2));
		System.out.println();
		
		return (distancia1<tolerancia) && (distancia2<tolerancia);
		
	}
	
	// Mtodo que nos indica a qu clase pertenece un punto dado
	public void aQueClasePertenece (double[] punto) {
		
		System.out.print("El punto: ");
		System.out.print(punto[0]);
		for(int i=1; i<punto.length; i++)
			System.out.print(  " , " + punto[i]);
				
		if(calcularDistancia(v1Actual,punto)<calcularDistancia(v2Actual,punto))
			System.out.println(" pertenece a la clase uno: Iris-setosa");
		else System.out.println(" pertenece a la clase dos: Iris-versicolor");
		
	}
	
	// Muestra V1 y V2
	public void mostrarCentros() {
		
		DecimalFormat df = new DecimalFormat("0.000");
		
		System.out.println("V1: " + df.format(getV1Actual(0)) + " , " +  df.format(getV1Actual(1)) + " , " + df.format(getV1Actual(2)) + " , " + df.format(getV1Actual(3)));
		System.out.println("V2: " + df.format(getV2Actual(0)) + " , " +  df.format(getV2Actual(1)) + " , " + df.format(getV2Actual(2)) + " , " + df.format(getV2Actual(3)));
		System.out.println();
	}


	public double[] getV1Anterior() {
		return v1Anterior;
	}


	public void setV1Anterior(double[] v1Anterior) {
		this.v1Anterior = v1Anterior;
	}


	public double[] getV2Anterior() {
		return v2Anterior;
	}


	public void setV2Anterior(double[] v2Anterior) {
		this.v2Anterior = v2Anterior;
	}

	public double getV1Actual(int pos) {
		return v1Actual[pos];
	}
	
	public double[] getV1Actual() {
		return v1Actual;
	}


	public void setV1Actual(double[] v1Actual) {
		this.v1Actual = v1Actual;
	}


	public double getV2Actual(int pos) {
		return v2Actual[pos];
	}
	
	public double[] getV2Actual() {
		return v2Actual;
	}


	public void setV2Actual(double[] v2Actual) {
		this.v2Actual = v2Actual;
	}
}
