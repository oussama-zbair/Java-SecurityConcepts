package com.security.concept;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * This Java code provides an implementation of two-factor authentication, which is a security process that requires users to provide two different authentication methods to verify their identity.
 * The first method is a password, which is entered by the user at the prompt.
 * The second method is a one-time code, which is also entered by the user at the prompt.
 * The code checks the validity of the password and one-time code using the isPasswordValid and isOneTimeCodeValid methods, respectively.
 * The isPasswordValid method checks if the password is at least 8 characters long, contains at least one uppercase letter, and contains at least one special character.
 * The isOneTimeCodeValid method sends the one-time code to a server and checks if the server returns a "VALID" response. If both the password and one-time code are valid, the user is granted access. Otherwise, the user is denied access.
 */
public class TwoFactorAuthentication {
    private final String password;
    private final String oneTimeCode;

    public TwoFactorAuthentication(String password, String oneTimeCode) {
        this.password = password;
        this.oneTimeCode = oneTimeCode;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for their password
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Prompt the user for their one-time code
        System.out.print("Enter your one-time code: ");
        String oneTimeCode = scanner.nextLine();

        // Create a TwoFactorAuthentication object with the user's password and one-time code
        TwoFactorAuthentication auth = new TwoFactorAuthentication(password, oneTimeCode);

        // Authenticate the user's password and one-time code
        if (auth.isPasswordValid() && auth.isOneTimeCodeValid()) {
            // If the password and one-time code are valid, grant the user access
            System.out.println("Access granted.");
        } else {
            // If the password or one-time code are invalid, deny the user access
            System.out.println("Access denied. Invalid password or one-time code.");
        }
    }

    public boolean isPasswordValid() {
        // Check if the password is at least 8 characters long
        if (password.length() < 8) {
            return false;
        }

        // Check if the password contains at least one uppercase letter
        boolean hasUppercase = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                hasUppercase = true;
                break;
            }
        }
        if (!hasUppercase) {
            return false;
        }

        // Check if the password contains at least one special character
        boolean hasSpecialCharacter = false;
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isLetterOrDigit(password.charAt(i))) {
                hasSpecialCharacter = true;
                break;
            }
        }
        return hasSpecialCharacter;

        // If the password has passed all checks, it is valid
    }

    public boolean isOneTimeCodeValid() {
        // Connect to the one-time code server and send the one-time code
        String response = sendOneTimeCodeToServer(oneTimeCode);

        // If the server returns a "VALID" response, return true
        // Otherwise, return false
        return response.equals("VALID");
    }

    private String sendOneTimeCodeToServer(String oneTimeCode) {
        // Implement code to send the one-time code to a server and receive a response
        String serverUrl = "http://otcserver.example.com";
        return sendHttpRequest(serverUrl, "otc=" + oneTimeCode);
    }

    private String sendHttpRequest(String url, String body) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set the request method and add the request body, if specified
            con.setRequestMethod("POST");
            if (body != null) {
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(body);
                wr.flush();
                wr.close();
            }

            // Send the request and receive the response
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
