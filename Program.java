import java.sql.*;
import java.util.Scanner;

public class Program {
//stale do polaczenia z baza
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/rklewek";
    static final String USER = "rafal";
    static final String PASS = "klewek";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); //tworzenie obiektow do komunikacji z baza
	     Statement statement = connection.createStatement(); 		     
	     PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Baza VALUES (NULL, ?, ?);")
	     ) {
//tworzenie tabeli i wpisanie poczatkowych wartosci
            statement.execute("CREATE TABLE Baza (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(100), surname VARCHAR(100), PRIMARY KEY(ID));");
            statement.execute("INSERT INTO Baza VALUES (NULL, 'Adam', 'Adamiak'), (NULL, 'Beata', 'Baran'), (NULL, 'Cezary', 'Czajka');");

            boolean isFinished = false;
            Scanner scanner = new Scanner(System.in);
	    int id;
            String name, surname;
            ResultSet rs;
            while (!isFinished) {
                int choice;
                System.out.println("Co chcesz zrobic?"); //wyswietlanie informacji o mozliwych opcjach (menu)
                System.out.println("'1' - Wyswietl dane z bazy");
                System.out.println("'2' - Dodaj wpis do bazy");
                System.out.println("'3' - Zakoncz");
		System.out.println("------------------------------------------------\n");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1: //wyswietlenie zawartosci bazy
                        rs = statement.executeQuery("SELECT id, name, surname FROM Baza");
                        while (rs.next()) {
                            id = rs.getInt("id");
                            name = rs.getString("name");
                            surname = rs.getString("surname");
                            System.out.println("ID: " + id + ", name: " + name + ", surname: " + surname);
                        }
                        System.out.println("------------------------------------------------");
                        rs.close();
                        break;
                    case 2: //dodanie nowego wpisu do bazy
                        System.out.println("Podaj imie:");
                        name = scanner.next();
                        System.out.println("Podaj nazwisko:");
                        surname = scanner.next();
                        insertStatement.setString(1, name);
                        insertStatement.setString(2, surname);
                        insertStatement.executeUpdate();
                        insertStatement.clearParameters();
                        System.out.println("Dodano rekord!");
			System.out.println("------------------------------------------------\n");
                        break;
                    case 3: //wyjscie
                        isFinished = true;
                        break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
