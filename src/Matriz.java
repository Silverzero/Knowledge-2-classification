/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marco
 */
public class Matriz implements Cloneable {

	public int n; // dimensin
	private double[][] x;

	public Matriz(int n) {
		this.n = n;
		x = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				x[i][j] = 0.0;
			}
		}
	}

	public Matriz(double[][] x) {
		this.x = x;
		n = x.length;
	}

	// Matriz inversa
	public static Matriz inversa(Matriz d) throws CloneNotSupportedException {
		int n = d.n; // dimensin de la matriz
		Matriz a = (Matriz) d.clone();
		Matriz b = new Matriz(n); // matriz de los trminos independientes
		Matriz c = new Matriz(n); // matriz de las incgnitas
		// matriz unidad
		for (int i = 0; i < n; i++) {
			b.x[i][i] = 1.0;
		}
		// transformacin de la matriz y de los trminos independientes
		for (int k = 0; k < n - 1; k++) {
			for (int i = k + 1; i < n; i++) {
				// trminos independientes
				for (int s = 0; s < n; s++) {
					b.x[i][s] -= a.x[i][k] * b.x[k][s] / a.x[k][k];
				}
				// elementos de la matriz
				for (int j = k + 1; j < n; j++) {
					a.x[i][j] -= a.x[i][k] * a.x[k][j] / a.x[k][k];
				}
			}
		}
		// clculo de las incgnitas, elementos de la matriz inversa
		for (int s = 0; s < n; s++) {
			c.x[n - 1][s] = b.x[n - 1][s] / a.x[n - 1][n - 1];
			for (int i = n - 2; i >= 0; i--) {
				c.x[i][s] = b.x[i][s] / a.x[i][i];
				for (int k = n - 1; k > i; k--) {
					c.x[i][s] -= a.x[i][k] * c.x[k][s] / a.x[i][i];
				}
			}
		}
		return c;
	}
	
	// Resta dos matrices
	static Matriz resta(Matriz a, Matriz b) {
		// Matriz resultado=new Matriz(a.n);
		Matriz resultado = new Matriz(a.x);
		for (int i = 0; i < a.n; i++) {
			for (int j = 0; j < 4; j++) {
				resultado.x[i][j] = a.x[i][j] - b.x[i][j];
			}
		}
		return resultado;
	}

	// Producto de dos matrices que da como resultado una matrices de 1x4
	static Matriz productoPara1x4(Matriz auxA, Matriz auxB) {
		double[][] resultado = new double[1][4];

		double[][] a = auxA.x;
		double[][] b = auxB.x;

		resultado[0][0] = a[0][0] * b[0][0] + a[0][1] * b[1][0] + a[0][2]
				* b[2][0] + a[0][3] * b[3][0];
		resultado[0][1] = a[0][0] * b[0][1] + a[0][1] * b[1][1] + a[0][2]
				* b[2][1] + a[0][3] * b[3][1];
		resultado[0][2] = a[0][0] * b[0][2] + a[0][1] * b[1][2] + a[0][2]
				* b[2][2] + a[0][3] * b[3][2];
		resultado[0][3] = a[0][0] * b[0][3] + a[0][1] * b[1][3] + a[0][2]
				* b[2][3] + a[0][3] * b[3][3];

		Matriz Mresultado = new Matriz(resultado);
		return Mresultado;
	}

	// Producto de dos matrices que da como resultado una matrices de 1x1
	static double productoPara1x1(Matriz auxA, Matriz auxB) {
		double resultado;

		double[][] a = auxA.x;
		double[][] b = auxB.x;

		resultado = a[0][0] * b[0][0] + a[0][1] * b[1][0] + a[0][2] * b[2][0]
				+ a[0][3] * b[3][0];

		return resultado;

	}

	// matriz traspuesta
	public Matriz traspuesta(Matriz resta) {

		double[][] matriz = new double[4][1];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 1; j++) {
				matriz[i][j] = resta.x[j][i];
			}
		}
		Matriz d = new Matriz(matriz);
		return d;
	}

	public void mostrar() {
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < n; b++) {
				System.out.print(x[a][b] + ",");
			}
			System.out.println();
		}
	}

	public double[][] getX() {
		return x;
	}

	public void setX(double[][] x) {
		this.x = x;
	}

}

