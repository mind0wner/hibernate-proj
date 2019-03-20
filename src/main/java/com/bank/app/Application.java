package com.bank.app;

import com.bank.dao.AccountDaoImpl;
import com.bank.dao.TransactionDaoImpl;
import com.bank.dao.UserDaoImpl;
import com.bank.entities.Account;
import com.bank.entities.Transaction;
import com.bank.entities.User;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {


        AccountDaoImpl accountDao = new AccountDaoImpl();
        UserDaoImpl impl = new UserDaoImpl();
        TransactionDaoImpl transactionDao = new TransactionDaoImpl();
        EntityTransaction tx = JpaUtil.createTransaction();
        Scanner scanner = new Scanner(System.in);
        int choice;
        try {

//            User user = new User();
//            user.setBirthDate(new Date());
//            user.setCreatedBy("giannis34");
//            user.setCreatedDate(new Date());
//            user.setEmailAddress("Antetokounmpo@yahoo.com");
//            user.setFirstName("Giannis");
//            user.setLastName("Antetokounmpo");
//            user.setLastUpdatedBy("MVP");
//            user.setLastUpdatedDate(new Date());
//            Account a = new Account();
            //em.persist(user);
            //impl.persist(user);

            // List<User> users = impl.findByFirstName("LeBron");
//            for (User userr : users){
//                System.out.println(userr.toString());
//            }

            do {

                System.out.println("MENU");
                System.out.println("1. Top up account");
                System.out.println("2. Transfer money from one account to another");
                System.out.println("3. Convert money from one account to another");
                System.out.println("4. One user sum in uah");
                System.out.println("5. List All users");
                System.out.println("6. List All accounts");
                System.out.println("7. List All transactions");
                System.out.println("8. EXIT");

                System.out.println("enter ur choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        menuOne();
                        break;
                    case 2:
                        menuTwoAndThree(choice);
                        break;
                    case 3:
                        menuTwoAndThree(choice);
                        break;
                    case 4:
                        menuFour();
                        break;
                    case 5:
                        System.out.println("All users: ");
                        List<User> users = impl.findAll();
                        for (User user : users) {
                            System.out.println(user.toString());
                        }
                        break;
                    case 6:
                        System.out.println("All accounts: ");
                        List<Account> accs = accountDao.findAll();
                        for (Account acc : accs) {
                            System.out.println(acc.toString());
                        }
                        break;
                    case 7:
                        System.out.println("All transactions: ");
                        List<Transaction> trs = transactionDao.findAll();
                        for (Transaction tr : trs) {
                            System.out.println(tr.toString());
                        }
                        break;
                    case 8:
                        return;
                    default:
                        System.err.println("Wrong menu number");
                        break;
                }
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            JpaUtil.close();
            scanner.close();
        }

    }

    static void menuOne() {

        AccountDaoImpl accountDao = new AccountDaoImpl();
        EntityTransaction et = accountDao.getTransaction();
        et.begin();
        Scanner sc = new Scanner(System.in);
        Long firstId;
        String accCurrency;
        Double amount;
        System.out.print("Enter acc id: ");
        firstId = sc.nextLong();
        System.out.print("Enter amount: ");
        amount = sc.nextDouble();
        System.out.print("Enter acc currency: ");
        accCurrency = sc.next();
        accountDao.topUp(firstId, amount, accCurrency);
        et.commit();
    }

    static void menuTwoAndThree(int choice) {
        TransactionDaoImpl transactionDao = new TransactionDaoImpl();
        EntityTransaction et = transactionDao.getTransaction();
        et.begin();
        Scanner scanner = new Scanner(System.in);
        Long firstId, secondId;
        Double amount;
        System.out.print("Enter first acc id: ");
        firstId = scanner.nextLong();
        System.out.print("Enter second acc id: ");
        secondId = scanner.nextLong();
        System.out.print("Enter amount: ");
        amount = scanner.nextDouble();
        if (choice == 2) {
            transactionDao.transfer(firstId, secondId, amount);
        }
        if (choice == 3) {
            transactionDao.convert(firstId, secondId, amount);
        }
        et.commit();
    }

    private static void menuFour() {
        AccountDaoImpl accountDao = new AccountDaoImpl();
        EntityTransaction et = accountDao.getTransaction();
        Scanner scanner = new Scanner(System.in);
        et.begin();
        Long userId, accId;
        System.out.print("Enter user id: ");
        userId = scanner.nextLong();
        System.out.print("Enter acc id: ");
        accId = scanner.nextLong();
        accountDao.sumInUah(userId, accId);
        et.commit();
    }

    private static void menuFive() {

    }
}