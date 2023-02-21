import java.sql.*;

public class JdbcMenuDriver {
    private static final String DB_URL = "jdbc:mysql://localhost/mydatabase";
    private static final String USER = "myuser";
    private static final String PASS = "mypassword";
    
    public static void main(String args[]) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            
            boolean exit = false;
            while (!exit) {
                System.out.println("Menu:");
                System.out.println("1. Create record");
                System.out.println("2. Read record");
                System.out.println("3. Update record");
                System.out.println("4. Delete record");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(System.console().readLine());
                switch (choice) {
                    case 1:
                        System.out.print("Enter the name: ");
                        String name = System.console().readLine();
                        System.out.print("Enter the age: ");
                        int age = Integer.parseInt(System.console().readLine());
                        String sql = "INSERT INTO mytable (name, age) VALUES ('" + name + "', " + age + ")";
                        int rows = stmt.executeUpdate(sql);
                        System.out.println(rows + " row(s) inserted");
                        break;
                    case 2:
                        System.out.print("Enter the id: ");
                        int id = Integer.parseInt(System.console().readLine());
                        sql = "SELECT * FROM mytable WHERE id = " + id;
                        ResultSet rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            System.out.println("Name: " + rs.getString("name"));
                            System.out.println("Age: " + rs.getInt("age"));
                        } else {
                            System.out.println("Record not found");
                        }
                        break;
                    case 3:
                        System.out.print("Enter the id: ");
                        id = Integer.parseInt(System.console().readLine());
                        System.out.print("Enter the new name: ");
                        name = System.console().readLine();
                        System.out.print("Enter the new age: ");
                        age = Integer.parseInt(System.console().readLine());
                        sql = "UPDATE mytable SET name = '" + name + "', age = " + age + " WHERE id = " + id;
                        rows = stmt.executeUpdate(sql);
                        System.out.println(rows + " row(s) updated");
                        break;
                    case 4:
                        System.out.print("Enter the id: ");
                        id = Integer.parseInt(System.console().readLine());
                        sql = "DELETE FROM mytable WHERE id = " + id;
                        rows = stmt.executeUpdate(sql);
                        System.out.println(rows + " row(s) deleted");
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
