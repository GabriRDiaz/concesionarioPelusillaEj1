package concesionarioPelusillaEj1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.cj.jdbc.Driver;

public class Main {
	private static PreparedStatement ps;
	private static int tabla;
	private static String dato1;
	private static String dato2;
	private static String dato3;
	private static int id;
	private static Connection connection = null;
	private static final String URL = "jdbc:mysql://localhost/concesionariopelusilla?serverTimezone=Europe/Madrid";
	public static void main(String[] args) {
		while(true) {
			checkDriver();
			menuPrincipal();	
		}
	}

	private static void menuPrincipal() {
		int menuSelQuery = menuSelQuery();
		switch(menuSelQuery) {
			case 1:
				menuInsert();		
				break;
			case 2:
				menuRead();
				break;
			case 3:
				menuUpdate();
				break;
			case 4:
				menuDelete();
				break;
			case 5:
				menuSearch();
				break;
			case 0:
				System.out.println("Cerrando...");
				System.exit(0);
				break;
			default:
				System.out.println("Seleccione una opción válida");
				menuPrincipal();
		}
	}

	private static void menuSearch() {
		System.out.print("Escriba el número de marcado: ");
		Scanner scan = new Scanner(System.in);
		dato1 = scan.nextLine();
		testConnect();
		try {
			ps = connection.prepareStatement(
					"SELECT * "
					+ "FROM cochos "
					+ "WHERE numMarcado = ?");
			ps.setString(1, dato1);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
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

	private static void menuDelete() {
		System.out.println("\n¿De qué tabla desea borrar info");
		seleccionTabla();
		switch(tabla) {
		case 1:
				delete();
			break;
		case 2:
				delete();
			break;
		case 0:
			System.out.println("\n");
			menuPrincipal();
			break;
		default:
			System.out.println("Elija una opción válida");
			menuUpdate();
		}
	}

	private static void delete() {
		getId();
		if(id<=0) {
			System.out.println("Introduzca un id válido");
			delete();
		}
		testConnect();
		try {
			if(tabla==1) {
				ps = connection.prepareStatement(
						"DELETE FROM cochos " 
						+ "WHERE id = ?");
						ps.setInt(1, id);
						ps.executeUpdate();	
			}
			else if(tabla==2) {
				ps = connection.prepareStatement(
						"DELETE FROM compradores " 
						+ "WHERE id = ?");
						ps.setInt(1, id);
						ps.executeUpdate();
			}
			else {
				System.out.println("Error en la selección de tabla");
				menuPrincipal();
			}
		} catch (SQLException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
		System.out.println("Datos borrados con éxito");
	}

	private static void menuUpdate() {
		System.out.println("\n¿De qué tabla desea actualizar info?");
		seleccionTabla();
		switch(tabla) {
		case 1:
				updateCocho();
			break;
		case 2:
				updateComprador();
			break;
		case 0:
			System.out.println("\n");
			menuPrincipal();
			break;
		default:
			System.out.println("Elija una opción válida");
			menuUpdate();
		}
	}

	private static void updateComprador() {
		getId();
		if(id<=0) {
			System.out.println("Introduzca un id válido");
			updateComprador();
		}
		testConnect();
		datosComprador();
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

	private static void updateCocho() {
		getId();
		if(id<=0) {
			System.out.println("Introduzca un id válido");
			updateCocho();
		}
		testConnect();
		datosCocho();
		try {
			ps = connection.prepareStatement(
					"UPDATE cochos " 
					+ "SET pata = ?, "
					+ "alimentacion = ?, "
					+ "numMarcado = ? " 
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

	private static void menuRead() {
		System.out.println("\n¿De qué tabla desea leer info?");
		seleccionTabla();
		switch(tabla) {
		case 1:
				readCocho();
			break;
		case 2:
				readComprador();
			break;
		case 0:
			System.out.println("\n");
			menuPrincipal();
			break;
		default:
			System.out.println("Elija una opción válida");
			menuRead();
		}
	}

	private static void readComprador() {
		testConnect();
		try {
			ps = connection.prepareStatement(
					"SELECT * "
					+ "FROM compradores");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
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

	private static void readCocho() {
		testConnect();
		try {
			ps = connection.prepareStatement(
					"SELECT * "
					+ "FROM compradores");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				int id = rs.getInt("id");
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

	private static void menuInsert() {
		System.out.println("\n¿En qué tabla desea insertar info?");
		seleccionTabla();
		switch(tabla) {
		case 1:
				insertCocho();
			break;
		case 2:
				insertComprador();
			break;
		case 0:
			System.out.println("\n");
			menuPrincipal();
			break;
		default:
			System.out.println("Elija una opción válida");
			menuInsert();
		}
	}

	private static void insertComprador() {
		testConnect();
		datosComprador();
		try {
			ps = connection.prepareStatement("INSERT INTO compradores "
					+ "(nombre, apellidos)" 
					+ "VALUES (?, ?)");
			ps.setString(1, dato1);
			ps.setString(2, dato2);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
		System.out.println("Datos actualizados con éxito \n");
	}

	private static void insertCocho() {
		testConnect();
		datosCocho();
		try {
			ps = connection.prepareStatement("INSERT INTO "
					+ "cochos (pata, alimentacion, numMarcado)" 
					+ "VALUES (?, ?, ?)");
			ps.setString(1, dato1);
			ps.setString(2, dato2);
			ps.setString(3, dato3);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
		System.out.println("Datos actualizados con éxito \n");
	}

	private static int menuSelQuery() {
		System.out.println("Escoja una opción:\n");
		System.out.print(
				"1.Insertar datos\n"
				+ "2.Leer datos\n"
				+ "3.Cambiar datos\n"
				+ "4.Borrar datos\n"
				+ "5.Buscar por numMarcado\n"
				+ "0.Cerrar programa\n"
				+ "-------------------\n"
				+ "Elección: ");
		Scanner scan = new Scanner(System.in);
		return scan.nextInt();
	}

	private static void checkDriver() {
		try {
			DriverManager.registerDriver(new Driver());
		} catch (SQLException e) {
			System.out.println("Error al registrar el driver");
			System.exit(0);
		}
	}
	
	private static Connection connect(String URL) throws SQLException {
		return DriverManager.getConnection(URL, "root", "");
	}
	private static void testConnect() {
		connection = null;
		try {
			connection = connect(URL);
		} catch (SQLException e) {
			System.out.println("No se ha podido conectar a la DB");
			e.printStackTrace();
		}
	}
	private static void datosCocho() {
		System.out.println("Introduzca tipo de pata del cocho");
		Scanner scan = new Scanner(System.in);
		dato1 = scan.nextLine();
		System.out.println("Introduzca tipo de alimentación del cocho");
		dato2 = scan.nextLine();
		System.out.println("Introduzca número de marcado del cocho");
		dato3 = scan.nextLine();
		if (dato1 == "" || dato2 == "" || dato3 == "") {
			System.out.println("ERROR: Introduzca los datos de nuevo");
			datosCocho();
		}
	}
	private static void datosComprador() {
		System.out.println("Introduzca el nombre del comprador");
		Scanner scan = new Scanner(System.in);
		dato1 = scan.nextLine();
		System.out.println("Introduzca los apellidos del comprador");
		dato2 = scan.nextLine();
		if (dato1 == "" || dato2 == "") {
			System.out.println("ERROR: Introduzca los datos de nuevo");
			datosComprador();
		}
	}
	private static void getId() {
		System.out.println("Datos a modificar\n" + "---------------");
		System.out.print("Id del registro: ");
		Scanner scan = new Scanner(System.in);
		id = scan.nextInt();
	}
	private static void seleccionTabla() {
		System.out.print(
				"1.Tabla cochos\n"
				+ "2.Tabla compradores\n"
				+ "0.Cancelar\n"
				+ "Elección: ");
		Scanner scan = new Scanner(System.in);
		tabla = scan.nextInt();
	}
}
