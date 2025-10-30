package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/hospital";
    static final String USER = "root";
    static final String PASSWORD = "mohd@"; // your MySQL password

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            System.out.println("\n✅ Connected to Database Successfully!");
            while (true) {
                System.out.println("\n╔════════════════════════════════════════════════╗");
                System.out.println("║           🏥 HOSPITAL MANAGEMENT SYSTEM         ║");
                System.out.println("╠════════════════════════════════════════════════╣");
                System.out.println("║ 1. Manage Patients                             ║");
                System.out.println("║ 2. Manage Doctors                              ║");
                System.out.println("║ 3. Exit                                        ║");
                System.out.println("╚════════════════════════════════════════════════╝");
                System.out.print("👉 Choose an option: ");
                int mainChoice = sc.nextInt();

                switch (mainChoice) {
                    case 1 -> managePatients(conn, sc);
                    case 2 -> manageDoctors(conn, sc);
                    case 3 -> {
                        System.out.println("👋 Exiting System. Goodbye!");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice! Please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("\n❌ Database connection failed! Please check your credentials or MySQL server.");
        }
    }

    // ====================== PATIENT MANAGEMENT =========================
    private static void managePatients(Connection conn, Scanner sc) throws SQLException {
        while (true) {
            System.out.println("\n╔═══════════════════════════╗");
            System.out.println("║        PATIENT MENU       ║");
            System.out.println("╠═══════════════════════════╣");
            System.out.println("║ 1. Add Patient            ║");
            System.out.println("║ 2. View All Patients      ║");
            System.out.println("║ 3. Update Patient         ║");
            System.out.println("║ 4. Delete Patient         ║");
            System.out.println("║ 5. Search Patient by Name ║");
            System.out.println("║ 6. Count Patients         ║");
            System.out.println("║ 7. Back                   ║");
            System.out.println("╚═══════════════════════════╝");
            System.out.print("👉 Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addPatient(conn, sc);
                case 2 -> viewPatients(conn);
                case 3 -> updatePatient(conn, sc);
                case 4 -> deletePatient(conn, sc);
                case 5 -> searchPatient(conn, sc);
                case 6 -> countPatients(conn);
                case 7 -> { return; }
                default -> System.out.println("❌ Invalid choice!");
            }
        }
    }

    private static void addPatient(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter patient name: ");
        String name = sc.nextLine();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter gender: ");
        String gender = sc.nextLine();
        System.out.print("Enter disease: ");
        String disease = sc.nextLine();

        String query = "INSERT INTO patients (name, age, gender, disease) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, gender);
            pst.setString(4, disease);
            pst.executeUpdate();
            System.out.println("✅ Patient added successfully!");
        }
    }

    private static void viewPatients(Connection conn) throws SQLException {
        String query = "SELECT * FROM patients";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("\n╔══════════════════════════════════════════════════════════════════════════╗");
            System.out.printf("║ %-5s │ %-20s │ %-5s │ %-8s │ %-20s ║%n", "ID", "Name", "Age", "Gender", "Disease");
            System.out.println("╠═══════╪══════════════════════╪═══════╪══════════╪══════════════════════════╣");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("║ %-5d │ %-20s │ %-5d │ %-8s │ %-20s ║%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("disease"));
            }

            if (!found)
                System.out.println("║ No patients found in database.                                            ║");

            System.out.println("╚══════════════════════════════════════════════════════════════════════════╝");
        }
    }

    private static void updatePatient(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter patient ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new disease: ");
        String disease = sc.nextLine();

        String query = "UPDATE patients SET disease = ? WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, disease);
            pst.setInt(2, id);
            int rows = pst.executeUpdate();
            System.out.println(rows > 0 ? "✅ Patient updated!" : "❌ Patient not found!");
        }
    }

    private static void deletePatient(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter patient ID to delete: ");
        int id = sc.nextInt();

        String query = "DELETE FROM patients WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            int rows = pst.executeUpdate();
            System.out.println(rows > 0 ? "🗑️ Patient deleted!" : "❌ Patient not found!");
        }
    }

    private static void searchPatient(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter patient name to search: ");
        String name = sc.nextLine();

        String query = "SELECT * FROM patients WHERE name LIKE ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, "%" + name + "%");
            ResultSet rs = pst.executeQuery();

            boolean found = false;
            System.out.println("\n╔════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║ ID   │ Name                │ Age  │ Gender │ Disease                  ║");
            System.out.println("╠══════╪═════════════════════╪══════╪════════╪══════════════════════════╣");

            while (rs.next()) {
                found = true;
                System.out.printf("║ %-4d │ %-19s │ %-4d │ %-6s │ %-25s ║%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("disease"));
            }

            if (found) {
                System.out.println("╚════════════════════════════════════════════════════════════════════════╝");
            } else {
                System.out.println("║ ⚠️  No patients found matching that name.                             ║");
                System.out.println("╚════════════════════════════════════════════════════════════════════════╝");
            }
        }
    }


    private static void countPatients(Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM patients";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                System.out.println("👥 Total Patients: " + rs.getInt(1));
            }
        }
    }

    // ====================== DOCTOR MANAGEMENT =========================
    private static void manageDoctors(Connection conn, Scanner sc) throws SQLException {
        while (true) {
            System.out.println("\n╔═══════════════════════════╗");
            System.out.println("║         DOCTOR MENU       ║");
            System.out.println("╠═══════════════════════════╣");
            System.out.println("║ 1. Add Doctor             ║");
            System.out.println("║ 2. View All Doctors       ║");
            System.out.println("║ 3. Update Specialization  ║");
            System.out.println("║ 4. Delete Doctor          ║");
            System.out.println("║ 5. Search Doctor by Name  ║");
            System.out.println("║ 6. Count Doctors          ║");
            System.out.println("║ 7. Back                   ║");
            System.out.println("╚═══════════════════════════╝");
            System.out.print("👉 Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addDoctor(conn, sc);
                case 2 -> viewDoctors(conn);
                case 3 -> updateDoctor(conn, sc);
                case 4 -> deleteDoctor(conn, sc);
                case 5 -> searchDoctor(conn, sc);
                case 6 -> countDoctors(conn);
                case 7 -> { return; }
                default -> System.out.println("❌ Invalid choice!");
            }
        }
    }

    private static void addDoctor(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter doctor name: ");
        String name = sc.nextLine();
        System.out.print("Enter specialization: ");
        String specialization = sc.nextLine();
        System.out.print("Enter experience (in years): ");
        int experience = sc.nextInt();

        String query = "INSERT INTO doctors (name, specialization, experience) VALUES (?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, name);
            pst.setString(2, specialization);
            pst.setInt(3, experience);
            pst.executeUpdate();
            System.out.println("✅ Doctor added successfully!");
        }
    }

    private static void viewDoctors(Connection conn) throws SQLException {
        String query = "SELECT * FROM doctors";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("\n╔══════════════════════════════════════════════════════════════════════════╗");
            System.out.printf("║ %-5s │ %-20s │ %-25s │ %-10s ║%n", "ID", "Name", "Specialization", "Experience");
            System.out.println("╠═══════╪══════════════════════╪═══════════════════════════╪═══════════════╣");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("║ %-5d │ %-20s │ %-25s │ %-10d ║%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getInt("experience"));
            }

            if (!found)
                System.out.println("║ No doctors found in database.                                             ║");

            System.out.println("╚══════════════════════════════════════════════════════════════════════════╝");
        }
    }

    private static void updateDoctor(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter doctor ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new specialization: ");
        String spec = sc.nextLine();

        String query = "UPDATE doctors SET specialization = ? WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, spec);
            pst.setInt(2, id);
            int rows = pst.executeUpdate();
            System.out.println(rows > 0 ? "✅ Doctor updated!" : "❌ Doctor not found!");
        }
    }

    private static void deleteDoctor(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter doctor ID to delete: ");
        int id = sc.nextInt();

        String query = "DELETE FROM doctors WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            int rows = pst.executeUpdate();
            System.out.println(rows > 0 ? "🗑️ Doctor deleted!" : "❌ Doctor not found!");
        }
    }

    private static void searchDoctor(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter doctor name: ");
        String name = sc.nextLine();

        String query = "SELECT * FROM doctors WHERE name LIKE ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, "%" + name + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Specialization: %s | Experience: %d%n",
                        rs.getInt("id"), rs.getString("name"),
                        rs.getString("specialization"), rs.getInt("experience"));
            }
        }
    }

    private static void countDoctors(Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM doctors";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                System.out.println("👨‍⚕️ Total Doctors: " + rs.getInt(1));
            }
        }
    }
}
