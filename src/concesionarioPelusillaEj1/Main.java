package concesionarioPelusillaEj1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.jdbc.DatabaseMetaData;
import com.mysql.cj.jdbc.Driver;

public class Main {
		private static final String URL = "jdbc:mysql://localhost/concesionariopelusilla?serverTimezone=Europe/Madrid";
		private static Connection connection = null;
		private static int id;
		private static String tabla = null;
		private static String dato1 = null;
		private static String dato2 = null;
		private static String dato3 = null;
		public static void main(String[] args) {
			testConnect();
			int option = menu();
			selection(option);
			System.out.println(option);
		}

		private static void testConnect() {
			try {
				DriverManager.registerDriver(new Driver());
			} catch (SQLException e) {
				System.out.println("Error al registrar el driver");
				System.exit(0);
			}
		}

		private static void selection(int option) {
			switch(option) {
			case 1:
				int optionIn = menuInsert();
				connection = null;
				try {
					connection = connect(URL);
				} catch (SQLException e) {
					System.out.println("No se ha podido conectar a la DB");
					e.printStackTrace();
				}
				if(optionIn == 1) {
					tabla = "cochos";
					menuInCocho();
					queryInsert();
					menu();
				}
				if (optionIn == 2) {
					tabla = "compradores";
					menuInCompradores();
					queryInsert();
					menu();
				}
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				break;
			}
		}

			private static void menuInCompradores() {
				System.out.println("Introduzca el nombre del comprador");
				Scanner scan = new Scanner(System.in);
				dato1 = scan.nextLine();
				System.out.println("Introduzca los apellidos del comprador");
				dato2 = scan.nextLine();
				if(dato1 == "" || dato2 == "") {
					System.out.println("ERROR: Introduzca los datos de nuevo");
					menuInCompradores();
				}
		}

			private static void queryInsert() {
				PreparedStatement ps;
				if(tabla == "cochos") {
					try {
						ps = connection.prepareStatement(
								"INSERT INTO cochos (pata, alimentacion, numMarcado)"
								+ "VALUES (?, ?, ?)");
						ps.setString(1, dato1);
						ps.setString(2, dato2);
						ps.setString(3, dato3);
						ps.executeUpdate();
					} catch (SQLException e) {
						System.out.println("ERROR");
						e.printStackTrace();
					}
					System.out.println("Datos introducidos con �xito");
				}
				if(tabla == "compradores") {
					try {
						ps = connection.prepareStatement(
								"INSERT INTO compradores (nombre, apellidos)"
								+ "VALUES (?, ?)");
						ps.setString(1, dato1);
						ps.setString(2, dato2);
						ps.executeUpdate();
					} catch (SQLException e) {
						System.out.println("ERROR");
						e.printStackTrace();
					}
					System.out.println("Datos introducidos con �xito");
				}
				}

			private static int menuRead() {
				System.out.println("�De qu� tabla desea leer info?");
				System.out.println("1.Tabla cochos\n"
						+ "2.Tabla compradores\n"
						+ "0.Cancelar");
				Scanner scan = new Scanner(System.in);
				return scan.nextInt();
		}

			private static void menuInCocho() {
				System.out.println("Introduzca tipo de pata del cocho");
				Scanner scan = new Scanner(System.in);
				dato1 = scan.nextLine();
				System.out.println("Introduzca tipo de alimentaci�n del cocho");
				dato2 = scan.nextLine();
				System.out.println("Introduzca n�mero de marcado del cocho");
				dato3 = scan.nextLine();
				if(dato1 == "" || dato2 == "" || dato3 == "") {
					System.out.println("ERROR: Introduzca los datos de nuevo");
					menuInCocho();
				}
		}

			private static int menuInsert() {
			System.out.println("�En qu� tabla desea insertar info?");
			System.out.print("1.Tabla cochos\n"
					+ "2.Tabla compradores\n"
					+ "0.Cancelar\n"
					+ "Elecci�n: ");
			Scanner scan = new Scanner(System.in);
			return scan.nextInt();
		}

			private static Connection connect(String URL) throws SQLException {
				return DriverManager.getConnection(URL, "root", "");
			}

		private static int menu() {
			System.out.println("Escoja un opci�n:");
			System.out.print("1.Insertar datos\n"
					+ "2.Leer datos\n"
					+ "3.Cambiar datos\n"
					+ "4.Borrar datos\n"
					+ "Elecci�n: ");
			Scanner scan = new Scanner(System.in);
			return scan.nextInt();
		}
}
