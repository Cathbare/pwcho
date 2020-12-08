import java.sql.*;
import java.util.Scanner;

public class App {
    
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/chmura";

    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://10.0.10.3:3306/chmura?serverTimezone=UTC";
        String DB_USER = "ADz";
        String DB_PASS = "Chmura";

        Statement stmt = null;
        Connection conn = null;
        
        try{
           // Class.forName(JDBC_DRIVER);
            while (conn == null)
            {
                try{
                    System.out.println("Proba polaczenia");
                    conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                }
                catch (SQLException e){
                    System.out.println("Blad polaczenia\n");
                    System.out.println(e);
                }

                try{
                    Thread.sleep(10000);
                }
                catch(Exception e){}
            }
            if(conn != null)
            {
                System.out.println("Polaczono!");     
                try{
                    createDB(conn);
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                while(true){
                    System.out.println("Wybierz opcję: ");
                    System.out.println("1. Dodaj chmure");
                    System.out.println("2. Wyswietl baze");
                    System.out.println("3. Wyjdź");

                    int Menu = new Scanner(System.in).nextInt();

                    switch(Menu){
                        case 1:
                            System.out.println("Podaj: ID");
                            int id = new Scanner(System.in).nextInt();
                            System.out.println("Podaj polska nazwe chmury");
                            String NazwaPol = new Scanner(System.in).next();
                            System.out.println("Podaj lacinska nazwe chmury");
                            String NazwaLac = new Scanner(System.in).next();
                            insertDB(conn, id, NazwaPol, NazwaLac);
                            break;
                        case 2:
                            System.out.println("Chmury: ");
                            showResult(conn);
                        case 3:
                            try{
                                if(stmt!=null)
                                    stmt.close();
                            }catch(SQLException se2){
                            }
                            try{
                                if(conn!=null)
                                conn.close();
                            }catch(SQLException se){
                                se.printStackTrace();
                            }
                            System.out.println("Goodbye!");
                    break;
                        }
                    }
                }else{
                    System.out.println("NIe udalo sie polaczyc z baza");
                }
            }catch(SQLException ex)
            {
                System.out.println(ex);
            }
    }
    public static void createDB(Connection conn) throws SQLException
    {
        String sql = "CREATE TABLE Chmura(ID INT NOT NULL, NazwaPol varchar(30) NOT NULL, NazwaLac varchar(30) NOT NULL, PRIMARY KEY(ID));";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute(sql);
        System.out.println("Utworzono tabele Chmura");
    }
    public static void insertDB(Connection conn, int id, String NazwaPol, String NazwaLac) throws SQLException
    {
        String sql = "INSERT INTO Chmura(ID, NazwaPol, NazwaLac) VALUES(?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        statement.setString(2, NazwaPol);
        statement.setString(3, NazwaLac);

        int rowsInserted = statement.executeUpdate();
        if(rowsInserted > 0)
        {
           System.out.println("Nowa chmura dodana pomyślnie"); 
        }
    }
    public static void showResult(Connection conn)
    {
        String sql = "SELECT * FROM Chmura;";
        try{
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet result = statement.executeQuery("SELECT * FROM Chmura");
        while(result.next())
        {
            int id = result.getInt("ID");
            String NazwaPol = result.getString("NazwaPol");
            String NazwaLac = result.getString("NazwaLac");

            System.out.print("ID: " + id + ", Nazwa Polska: " + NazwaPol);
            System.out.print(", Nazwa Lacinska: "+ NazwaLac + "\n");
        }
    } catch(SQLException ex)
    {
        ex.printStackTrace();
    }
}
}
