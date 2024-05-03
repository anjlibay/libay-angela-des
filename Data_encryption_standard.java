/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */      
//        #This implementation follows the steps outlined:
//
//#1. Generate DES Key: The generate_des_key function creates a DES key securely using get_random_bytes.
//
//#2. Create a Cipher Instance: The DES.new function is used to create a DES cipher instance with the specified key and mode (ECB in this case).
//
//#3. Convert String to Byte[] Array: The _pad_message function converts the plaintext message from string format to a byte array format and pads it to make its length a multiple of 8 bytes.
//
//#4.  Encryption: The encrypt_message function initializes the cipher in encryption mode and encrypts the plaintext message.
//
//#5. Decryption: The decrypt_message function initializes the cipher in decryption mode and decrypts the ciphertext back to the original plaintext.
//#pip install pycryptodome
package data_encryption_standard;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Base64;

public class Data_encryption_standard {

    public static void main(String[] args) throws Exception {
        // Get DES key
        byte[] keyBytes = "mydeskey".getBytes();
        DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        while (true) {
            // Choose operation
            String[] options = {"Encrypt", "Decrypt"};
            int choice = JOptionPane.showOptionDialog(null, "Choose operation:", "DES Encryption/Decryption", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (choice == 0) { // Encrypt
                String plaintext = JOptionPane.showInputDialog("Enter the message to encrypt:");
                if (plaintext != null && !plaintext.isEmpty()) {
                    byte[] plaintextBytes = plaintext.getBytes();

                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    byte[] encryptedBytes = cipher.doFinal(plaintextBytes);
                    String encryptedMessage = Base64.getEncoder().encodeToString(encryptedBytes);

                    JPanel panel = new JPanel(new GridLayout(0, 1));
                    panel.add(new JLabel("Encrypted message:"));
                    JTextField textField = new JTextField(encryptedMessage);
                    panel.add(textField);
                    JButton copyButton = new JButton("Copy to Clipboard");
                    copyButton.addActionListener(e -> {
                        try {
                            StringSelection stringSelection = new StringSelection(encryptedMessage);
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            clipboard.setContents(stringSelection, null);
                            JOptionPane.showMessageDialog(null, "Encrypted message copied to clipboard.");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error copying to clipboard.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                    panel.add(copyButton);
                    JOptionPane.showMessageDialog(null, panel, "Encrypted Message", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (choice == 1) { // Decrypt
                String encryptedMessage = JOptionPane.showInputDialog("Enter the message to decrypt:");
                if (encryptedMessage != null && !encryptedMessage.isEmpty()) {
                    cipher.init(Cipher.DECRYPT_MODE, secretKey);
                    byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
                    String decryptedMessage = new String(decryptedBytes);

                    JOptionPane.showMessageDialog(null, "Decrypted message:\n" + decryptedMessage);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid choice!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Ask user if they want to decrypt again
            int option = JOptionPane.showConfirmDialog(null, "Do you want to perform another decryption?", "Decrypt Again?", JOptionPane.YES_NO_OPTION);
            if (option != JOptionPane.YES_OPTION) {
                break;
            }
        }
    }
}