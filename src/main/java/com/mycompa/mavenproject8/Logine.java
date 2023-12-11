/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompa.mavenproject8;

/**
 *
 * @author 1
 */
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
public class Logine implements Serializable {
/*تم اختيار الماب  لتخزين بيانات المستخدمين في  البرنامج لأنه يوفر طريقة لتخزين الأزواج المفتاح-القيمة. في هذه الحالة، يتم استخدام الماب لتخزين عناوين البريد الإلكتروني للمستخدمين كمفتاح وكلمات المرور كقيمة. هذا يسمح بسهولة الوصول إلى بيانات المستخدم والتحقق من صحة بيانات  .*/
    private Map<String, String> userCredentials;//تعريف ماب لتخزين البيانات اعتمادا للمستخدم
    private Pattern passwordPattern;// تعريف نمط لفحص صحة كلمة المرور

    public Logine() {
        userCredentials = new HashMap<>();//تهيئة للماب عن طريق الكونستراكتر
        passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");// تعيين نمط لفحص صحة كلمة المرور
 
        //اختصار، هذا النمط يتطلب أن تحتوي كلمة المرور على الأقل على حرف كبير وحرف صغير ورقم وحرف خاص، وأن يكون طولها 8 أحرف على الأقل، وأن لا تحتوي على مسافات فارغة.
        
    }
 
    public void login() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to the system!");

            boolean loggedIn = false;//قيمة افتراضية
            while (!loggedIn) {// حلقة تستمر حتى يتم تسجيل الدخول بنجاح
                System.out.print("Enter Gmail address to login: ");
                String email = scanner.next();// قراءة البريد الإلكتروني من المستخدم
                System.out.print("Enter password: ");
                String password = scanner.next();// قراءة كلمة المرور من المستخدم
               
                if (isValidCredentials(email, password)) {// التحقق من صحة بيانات الاعتماد
                    System.out.println("User login successful!");
                    loggedIn = true;
                } else {
                    System.out.println("The email you entered doesn't exist . Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
        }
    }
 public void register() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("User Registration");
            System.out.print("Enter Gmail address: ");
            String email = scanner.next();
            System.out.print("Enter password: ");
            String password = scanner.next();
            if (isValidRegistration(email, password)) {
                if (!isExistingEmail(email)) {
                    userCredentials.put(email, password);
                    saveToFile("first.txt", userCredentials);
                    System.out.println("User registered successfully!");
                    System.out.println("Registered email: " + email);
                } else {
                    System.out.println("User with the same email already exists. Please try a different email.");
                }
            } else {
                System.out.println("Invalid registration details. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during registration: " + e.getMessage());
        }
    }


    private boolean isExistingEmail(String email) {
        Map<String, String> existingCredentials = readFromFile("first.txt");
        return existingCredentials.containsKey(email); }



   // التحقق من صحة بيانات الاعتماد

    private boolean isValidCredentials(String email, String password) {
        Map<String, String> existingCredentials = readFromFile("first.txt");

        return existingCredentials.containsKey(email) && existingCredentials.get(email).equals(password);
    }


    private boolean isValidRegistration(String email, String password) {
        if (!isValidGmailAddress(email)) {// التحقق من صحة عنوان Gmail
            System.out.println("Invalid Gmail address format.");
            return false;
        }

        if (!isValidPassword(password)) {// التحقق من صحة كلمة المرور
            System.out.println("Invalid password format. Password must contain at least 8 characters, including at least one uppercase letter, one lowercase letter, one digit, and one special character (@#$%^&+=).");
            return false;
        }

            
        return true;
    }
  
    private boolean isValidGmailAddress(String email) {
        String regex = "^[a-zA-Z0-9+_.-]+@gmail.com$";// تعريف نمط لفحص صحة عنوان Gmail
        return email.matches(regex);// التحقق من صحة عنوان Gmail باستخدام النمط
        //("gmail.com")باختصار، هذا النمط يتطلب أن يكون عنوان البريد الإلكتروني مكونًا من الأحرف الأبجدية الصغيرة والكبيرة والأرقام وبعض الرموز، مع وجود الرمز "@" وتتبعه .
    }

    private boolean isValidPassword(String password) {

        return passwordPattern.matcher(password).matches();// التحقق من صحة كلمة المرور باستخدام النمط
    }



// ...


    private void saveToFile(String fileName, Map<String, String> map)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true)))
        {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine(); }
            System.out.println("Data saved to file: " + fileName); }
        catch (IOException e) {
            System.out.println("An error occurred while saving data to file: " + e.getMessage()); }
    }

    private Map<String, String> readFromFile(String fileName)
    {
        Map<String, String> credentials = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
            {
                String line; while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2)
                { credentials.put(parts[0], parts[1]); } }
                System.out.println("Data read from file: " + fileName); }
    catch (IOException e) {
        System.out.println("An error occurred while reading data from file: " + e.getMessage());
    }
        return credentials;}}