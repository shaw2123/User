package Controller;

import Model.Validate;
import View.Menu;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Manager extends Menu {

    Validate vali = new Validate();

    @Override
    public void realMenu() {
    //loop until user wants to exit
    while (true) {
        int choice = menu();
        switch (choice) {
            case 1:
                createNewAccount();
                break;
            case 2:
                loginSystem();
                break;
            case 3:
                return;
        }
    }
}

//display menu
public int menu() {
    System.out.println("1. Create a new account.");
    System.out.println("2. Login system.");
    System.out.println("3. Exit.");
    System.out.print("Enter your choice: ");
    int choice = vali.checkInputIntLimit(1, 3);
    return choice;
}

//create a new account
public void createNewAccount() {
    //check if the data file exists or not
    if (!vali.checkFileExist()) {
        return;
    }
    String username = vali.checkInputUsername();
    String password = vali.checkInputPassword();
    //check if the username already exists or not
    if (!vali.checkUsernameExist(username)) {
        System.err.println("Username already exists.");
        return;
    }
    addAccountData(username, password);
}

//login system
public void loginSystem() {
    //check if the data file exists or not
    if (!vali.checkFileExist()) {
        return;
    }
    String username = vali.checkInputUsername();
    String password = vali.checkInputPassword();
    //check if the username exists or not
    if (!vali.checkUsernameExist(username)) {
        System.err.println("Account doesn't exist. Please create a new account.");
        return;
    }
    if (!password.equalsIgnoreCase(passwordByUsername(username))) {
        System.err.println("Password incorrect.");
        return;
    }
    System.err.println("Login success.");
}

//write new account to data
public void addAccountData(String username, String password) {
    File file = new File("C:\\Users\\admin\\Documents\\NetBeansProjects\\J1.S.P0057\\src\\Model\\User.dat");
    try {
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(username + ";" + password + "\n");
        fileWriter.close();
        System.err.println("Create successly.");
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

//get password by username
public String passwordByUsername(String username) {
    File file = new File("C:\\Users\\admin\\Documents\\NetBeansProjects\\J1.S.P0057\\src\\Model\\User.dat");
    try {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] account = line.split(";");
            if (username.equalsIgnoreCase(account[0])) {
                return account[1];
            }
        }
        bufferedReader.close();
        fileReader.close();
    } catch (FileNotFoundException ex) {
        ex.printStackTrace();
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    return null;
    }
}