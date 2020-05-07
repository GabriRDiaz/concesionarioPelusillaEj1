package concesionarioPelusillaEj1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.cj.jdbc.DatabaseMetaData;
import com.mysql.cj.jdbc.Driver;

public class Main {
		private static final String URL = "jdbc:mysql://localhost/concesionariopelusilla?serverTimezone=Europe/Madrid";
		private static Connection connection = null;
		private static int id;
		private static int tabla;
		private static String dato1 = null;
		private static String dato2 = null;
		private static String dato3 = null;
		private static int optionSw;
		private static Statement stmt;
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
				optionSw = menuInsert();
				connection = null;
				try {
					connection = connect(URL);
				} catch (SQLException e) {
					System.out.println("No se ha podido conectar a la DB");
					e.printStackTrace();
				}
				if(optionSw == 1) {
					tabla = 1;
					menuInCocho();
					queryInsert();
					menu();
				}
				if (optionSw == 2) {
					tabla = 2;
					menuInCompradores();
					queryInsert();
					menu();
				}
				break;
			case 2:
				menuRead();
				connection = null;
				try {
					connection = connect(URL);
				} catch (SQLException e) {
					System.out.println("No se ha podido conectar a la DB");
					e.printStackTrace();
				}
					queryRead();
					menu();
				break;
			case 3:
				menuUp();
				connection = null;
				try {
					connection = connect(URL);
				} catch (SQLException e) {
					System.out.println("No se ha podido conectar a la DB");
					e.printStackTrace();
				}
					queryCochoUp(tabla);
				break;
			case 4:
				menuDel();
				connection = null;
				try {
					connection = connect(URL);
				} catch (SQLException e) {
					System.out.println("No se ha podido conectar a la DB");
					e.printStackTrace();
				}
					queryCochoDel(tabla);
				break;
			}
		}

			private static void queryCochoDel(int tabla2) {
				getId();
				PreparedStatement ps;
				if (tabla==1) {
					try {
						ps = connection.prepareStatement(
								 "DELETE FROM cochos "
								 + "WHERE id = ?");
						ps.setInt(1, id);
						ps.executeUpdate();
					} catch (SQLException e) {
						System.out.println("ERROR");
						e.printStackTrace();
					}
					System.out.println("Datos actualizados con éxito");
				}
				if (tabla==2) {
					try {
						ps = connection.prepareStatement(
								 "DELETE FROM compradores "
								 + "WHERE id = ?");
						ps.setInt(1, id);
						ps.executeUpdate();
					} catch (SQLException e) {
						System.out.println("ERROR");
						e.printStackTrace();
					}
					System.out.println("Datos actualizados con éxito");
				}
		}

			private static void menuDel() {
				System.out.print("¿En qué tabla desea borrar info?\n"
						+ "1. Tabla cochos\n"
						+ "2. Tabla compradores\n"
						+ "Elección: ");
				Scanner scan = new Scanner(System.in);
				tabla = scan.nextInt();
		}

			private static void queryCochoUp(int tabla) {
				getId();
				PreparedStatement ps;
				if (tabla==1) {
					menuInCocho();
					try {
						ps = connection.prepareStatement(
								 "UPDATE cochos "
								 + "SET pata = ?, alimentacion = ?, numMarcado = ? "
								 + "WHERE id = ?");
						ps.setString(1, dato1);
						ps.setString(2, dato2);
						ps.setString(3, dato3);
						ps.setInt(4, id);
						ps.executeUpdate();
					} catch (SQLException e) {
						System.out.println("ERROR");
						e.printStackTrace();
					}
					System.out.println("Datos actualizados con éxito");
				}
				if (tabla==2) {
					menuInCompradores();
					try {
						ps = connection.prepareStatement(
								 "UPDATE compradores "
								 + "SET nombre = ?, apellidos = ?"
								 + "WHERE id = ?");
						ps.setString(1, dato1);
						ps.setString(2, dato2);
						ps.setInt(3, id);
						ps.executeUpdate();
					} catch (SQLException e) {
						System.out.println("ERROR");
						e.printStackTrace();
					}
					System.out.println("Datos actualizados con éxito");
				}
		}

			private static void getId() {
				System.out.println("Introduzca la id");
				Scanner scan = new Scanner(System.in);
				id = scan.nextInt();
				if(id <= 0) {
					System.out.println("Introduzca una id válida");
					getId();
				}
			}

			private static int cochoUp() {
			System.out.println("Datos a modificar\n"
					+ "---------------");
			System.out.print("Id del registro: ");
			Scanner scan = new Scanner(System.in);
			return scan.nextInt();
		}

			private static void menuUp() {
			System.out.print("¿En qué tabla desea efectuar cambios?\n"
					+ "1. Tabla cochos\n"
					+ "2. Tabla compradores\n"
					+ "Elección: ");
			Scanner scan = new Scanner(System.in);
			tabla = scan.nextInt();
		}

			private static void queryRead() {
				if(tabla == 1) {
					try {
						stmt = connection.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT * FROM cochos");
						 while(rs.next()){
					        
					         int id  = rs.getInt("id");
					         String pata = rs.getString("pata");
					         String alim = rs.getString("alimentacion");
					         String num = rs.getString("numMarcado");

					         System.out.println("");
					         System.out.println("Id: " + id);
					         System.out.println("Pata: " + pata);
					         System.out.println("Alimentación: " + alim);
					         System.out.println("NúmMarcado: " + num);
					         System.out.println("-------------------");
						 }
					} catch (SQLException e) {
						System.out.println("ERROR");
						e.printStackTrace();
					}
				}
				if(tabla == 2) {
					try {
						stmt = connection.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT * FROM compradores");
						 while(rs.next()){
					        
					         int id  = rs.getInt("id");
					         String nombre = rs.getString("nombre");
					         String ap = rs.getString("apellidos");

					         System.out.println("");
					         System.out.println("Id: " + id);
					         System.out.println("Nombre: " + nombre);
					         System.out.println("Apellidos: " + ap);
					         System.out.println("-------------------");
						 }
					} catch (SQLException e) {
						System.out.println("ERROR");
						e.printStackTrace();
					}
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
				if(tabla == 1) {
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
				}
				if(tabla == 2) {
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
					System.out.println("Datos introducidos con éxito");
				}
				}

			private static void menuRead() {
				System.out.println("¿De qué tabla desea leer info?");
				System.out.println("1.Tabla cochos\n"
						+ "2.Tabla compradores\n"
						+ "0.Cancelar");
				Scanner scan = new Scanner(System.in);
				tabla = scan.nextInt();
		}

			private static void menuInCocho() {
				System.out.println("Introduzca tipo de pata del cocho");
				Scanner scan = new Scanner(System.in);
				dato1 = scan.nextLine();
				System.out.println("Introduzca tipo de alimentación del cocho");
				dato2 = scan.nextLine();
				System.out.println("Introduzca número de marcado del cocho");
				dato3 = scan.nextLine();
				if(dato1 == "" || dato2 == "" || dato3 == "") {
					System.out.println("ERROR: Introduzca los datos de nuevo");
					menuInCocho();
				}
		}

			private static int menuInsert() {
			System.out.println("¿En qué tabla desea insertar info?");
			System.out.print("1.Tabla cochos\n"
					+ "2.Tabla compradores\n"
					+ "0.Cancelar\n"
					+ "Elección: ");
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