import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

public class URLShortener {
    private HashMap<String, String> shortToLongMap;
    private HashMap<String, String> longToShortMap;
    private static final int SHORT_URL_LENGTH = 7; // Length of short URLs

    public URLShortener() {
        this.shortToLongMap = new HashMap<>();
        this.longToShortMap = new HashMap<>();
    }

    public String shortenURL(String longURL) {
        // Generate short URL using hash function and ensure uniqueness
        String shortURL = generateShortURL(longURL);
        shortToLongMap.put(shortURL, longURL);
        longToShortMap.put(longURL, shortURL);
        return shortURL;
    }

    public String expandURL(String shortURL) {
        // Retrieve long URL from short URL
        return shortToLongMap.get(shortURL);
    }

    private String generateShortURL(String longURL) {
        // Basic hash function to generate short URL
        // Example: Using a simple substring of the MD5 hash
        String hash = MD5Hash(longURL);
        String shortURL = hash.substring(0, SHORT_URL_LENGTH);
        return shortURL;
    }

    private String MD5Hash(String input) {
        // Generate MD5 hash of the input string
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5 hashing failed.");
        }
    }

    public static void main(String[] args) {
        URLShortener urlShortener = new URLShortener();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nURL Shortener Menu:");
            System.out.println("1. Shorten a URL");
            System.out.println("2. Expand a Shortened URL");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("Enter the long URL to shorten: ");
                    String longURL = scanner.nextLine();
                    String shortURL = urlShortener.shortenURL(longURL);
                    System.out.println("Shortened URL: " + shortURL);
                    break;
                case 2:
                    System.out.print("Enter the short URL to expand: ");
                    String shortURLToExpand = scanner.nextLine();
                    String expandedURL = urlShortener.expandURL(shortURLToExpand);
                    if (expandedURL != null) {
                        System.out.println("Expanded URL: " + expandedURL);
                    } else {
                        System.out.println("Short URL not found.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting URL Shortener. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
