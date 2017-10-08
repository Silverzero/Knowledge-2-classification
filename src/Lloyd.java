import java.text.DecimalFormat;


public class Lloyd {
	
	private static final double razonDeAprendizaje = 0.1; //razon de aprendizaje
	private static double tolerancia = 0.0000000001;
	private static double iteracionesMaximas = 10;
	private static int iteracionesActuales = 0;
	private static int dimension = 4;
	private double[][] muestras = new double[Principal.N][Principal.dimension];
	

	private double[] c1Actual = {4.6, 3.0, 4.0, 0.0}; //centro inicial 1
	private double[] c2Actual = {6.8, 3.4, 4.6, 0.7}; //centro inicial 2
	
	private double[] c1Anterior = {0.0, 0.0, 0.0, 0.0}; //centro inicial 1
	private double[] c2Anterior = {0.0, 0.0, 0.0, 0.0}; //centro inicial 2
	
	
	
	public double[][] getMuestras() {
		return muestras;
	}


	public void setMuestras(double[][] muestras) {
		this.muestras = muestras;
	}
	
	public double[] getC1Actual() {
		return c1Actual;
	}


	public void setC1Actual(double[] c1Actual) {
		this.c1Actual = c1Actual;
	}


	public double[] getC2Actual() {
		return c2Actual;
	}


	public void setC2Actual(double[] c2Actual) {
		this.c2Actual = c2Actual;
	}


	public double[] getC1Anterior() {
		return c1Anterior;
	}


	public void setc1Anterior(double[] c1Anterior) {
		this.c1Anterior = c1Anterior;
	}


	public double[] getc2Anterior() {
		return c2Anterior;
	}


	public void setc2Anterior(double[] c2Anterior) {
		this.c2Anterior = c2Anterior;
	}
	
	public static int getIteracionesActuales() {
		return iteracionesActuales;
	}


	public void setIteracionesActuales(int iteracionesActuales) {
		Lloyd.iteracionesActuales = iteracionesActuales;
	}

	
	
	// Inicializar muestras para quedarnos con las 100 muestras y obviar la clase a que pertenecen. 
	public void inicializarMuestras (String[] leido) {
		
		for (int i = 0;i < Principal.N; i++) {
			String[] x = leido[i].split(",");
			for (int j = 0; j < Principal.dimension ;j++) {
				muestras[i][j] = Double.parseDouble(x[j]);
			}
		}
	}
	
	
	public double calcularDistanciaAlCentro(int j, double[] x){

		double distancia = 0.0;
		if(j == 1){
			for (int i=0;i<Principal.dimension;i++)
				distancia += Math.pow(x[i]-c1Actual[i],2);
		}
		else{
			for (int i=0;i<Principal.dimension;i++)
				distancia += Math.pow(x[i]-c2Actual[i],2);
		}
			
		return Math.sqrt(distancia);
	}
	
	
	public void calcularC(int i, double[] x){
	
		//actualizamos c1
		if(i == 1){
			c1Anterior = c1Actual.clone();
			//setc1Anterior(getC1Actual());
			for(int j = 0; j< Principal.dimension; j++)
				c1Actual[j] = ((x[j] - c1Anterior[j])*razonDeAprendizaje) + c1Anterior[j] ;
		}
		//actualizamos c2
		else{
			c2Anterior = c2Actual.clone();
			//setc2Anterior(getC2Actual());
			for(int j = 0; j< Principal.dimension; j++)
				c2Actual[j] = ((x[j] - c2Anterior[j])*razonDeAprendizaje) + c2Anterior[j] ;
		}
	}

	public void incrementarK(){
		iteracionesActuales++;
	}
	

	public boolean comprobarFinalizado(){
		
		boolean finalizado = false;
		//Si lo que se han movido los centros en menos que la tolerancia, debemos acabar
		if ((calcularDistanciaAlCentro(1, getC1Anterior())< tolerancia)&&(calcularDistanciaAlCentro(2, getc2Anterior())< tolerancia)){
			finalizado = true;
		}
		//Si ya hemos llegado al numero maximo de iteraciones, debemos acabar
		if (iteracionesActuales == iteracionesMaximas)
			finalizado = true;
		return finalizado;
	}
	
	public void mostrarcentroActual(){
		
		DecimalFormat df = new DecimalFormat("0.0000000000000000000");
		
		System.out.println("Iteracion: " + iteracionesActuales);
		System.out.println();
		System.out.println("C1: " + df.format(c1Actual[0]) + " , " +  df.format(c1Actual[1]) + " , " + df.format(c1Actual[2]) + " , " + df.format(c1Actual[3]));
		System.out.println("C2: " + df.format(c2Actual[0]) + " , " +  df.format(c2Actual[1]) + " , " + df.format(c2Actual[2]) + " , " + df.format(c2Actual[3]));
		System.out.println();
	}
	
		
	
	public void mostrarAQueClasePertenece(double[] x){
		DecimalFormat df = new DecimalFormat("0.0");
		
		if(calcularDistanciaAlCentro(1,x)<calcularDistanciaAlCentro(2,x)){
			System.out.println("El punto " + df.format(x[0]) + " , " +  df.format(x[1]) + " , " + df.format(x[2]) + " , " + df.format(x[3]) + " pertenece a la clase Iris-setosa");
			System.out.println();
		}
		else{
			System.out.println("El punto " + df.format(x[0]) + " , " +  df.format(x[1]) + " , " + df.format(x[2]) + " , " + df.format(x[3]) + " pertenece a la clase Iris-versicolor");
			System.out.println();
			
		}
			
	}

}



