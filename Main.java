package tinyurl;
import java.util.*;
import java.sql.*;

public class Main {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/tinyurl?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "password";
    
    private static final String baseUrl = "http://tinyurl.com/";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("TinyURL SERVICE\n1. Shorten URL\n2. Retrieve Original URL\n3. Exit");
            int ch = sc.nextInt();
            sc.nextLine(); // Consume newline character

            switch (ch) {
                case 1:
                    System.out.println("Enter the original URL:");
                    String original = sc.nextLine();
                    String shortUrl = encode(original);
                    if (shortUrl != null) {
                        System.out.println("Shortened URL: " + shortUrl);
                    } else {
                        System.out.println("Error creating short URL.");
                    }
                    break;

                case 2:
                    System.out.println("Enter the shortened URL code:");
                    String shortcode = sc.nextLine().replace(baseUrl, "");
                    String originalUrl = decode(shortcode);
                    if (originalUrl != null) {
                        System.out.println("Original URL: " + originalUrl);
                    } else {
                        System.out.println("Short URL not found.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting TinyURL service");
                    sc.close();
                    return;

                default:
                    System.out.println("Wrong option");
            }
        }
    }

    // Encode Method using Hash Code
    public static String encode(String originalUrl) {
        String encoded = Integer.toHexString(originalUrl.hashCode());
        String tinyUrl = "http://tinyurl.com/" + encoded;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "INSERT INTO urls (original_url, short_url) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, originalUrl);
                stmt.setString(2, encoded);
                stmt.executeUpdate();
                return tinyUrl;
            }
        } catch (SQLException e) {
            System.err.println("Error during URL encoding: " + e.getMessage());
        }
        return null;
    }

    // Decode Method using Hash Code
    public static String decode(String shortCode) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT original_url FROM urls WHERE short_url = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, shortCode);
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("original_url");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during URL decoding: " + e.getMessage());
        }
        return null;
    }
}
