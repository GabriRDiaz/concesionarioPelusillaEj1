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
		private static String dato1 = null;
		private static String dato2 = null;
		private static String dato3 = null;
		public static void main(String[] args) {
			testConnect();
			int option = menu();
			selection(option, URL);
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

		private static void selection(int option, String URL) {
			switch(option) {
			case 1:
				int optionIn = menuInsert();
				if(optionIn == 0) {
					menu();
				}else {
				connection = null;
				try {
					connection = connect(URL);
				} catch (SQLException e) {
					System.out.println("No se ha podido conectar a la DB");
					e.printStackTrace();
				}
					}
				if(option == 1) {
					menuInCocho();
					PreparedStatement ps;
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
					System.out.println("Datos introducidos con éxito");
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

			private static void menuInCocho() {
				System.out.println("Introduzca tipo de pata del cocho");
				Scanner scan = new Scanner(System.in);
				dato1 = scan.nextLine();
				System.out.println("Introduzca tipo de alimentación del cocho");
				dato2 = scan.nextLine();
				System.out.println("Introduzca número de marcado del cocho");
				dato3 = scan.nextLine();
				if(dato1 == "" || dato2 == "" || dato3 != "") {
					System.out.println("ERROR: Introduzca los datos de nuevo");
					menuInCocho();
				}
		}

			private static int menuInsert() {
			System.out.println("¿En qué tabla desea insertar info?");
			System.out.println("1.Tabla cochos\n"
					+ "2.Tabla compradores\n"
					+ "0.Cancelar");
			Scanner scan = new Scanner(System.in);
			return scan.nextInt();
		}

			private static Connection connect(String URL) throws SQLException {
				return DriverManager.getConnection(URL, "root", "");
			}

		private static int menu() {
			System.out.println("Escoja un opción:");
			System.out.print("1.Insertar datos\n"
					+ "2.Leer datos\n"
					+ "3.Cambiar datos\n"
					+ "4.Borrar datos\n"
					+ "Elección: ");
			Scanner scan = new Scanner(System.in);
			return scan.nextInt();
		}
}
