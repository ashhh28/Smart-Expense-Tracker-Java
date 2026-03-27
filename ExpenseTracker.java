import java.io.*;
import java.util.*;

class Expense {
    String category;
    double amount;

    Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }
}

public class ExpenseTracker {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Show Analytics");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Enter category: ");
                String category = sc.nextLine();

                System.out.print("Enter amount: ");
                double amount = sc.nextDouble();
                sc.nextLine();

                FileWriter fw = new FileWriter("expenses.txt", true);
                fw.write(category + "," + amount + "\n");
                fw.close();

                System.out.println("✅ Expense added!");
            }

            else if (choice == 2) {
                BufferedReader br = new BufferedReader(new FileReader("expenses.txt"));
                String line;

                System.out.println("\n--- All Expenses ---");
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                br.close();
            }

            else if (choice == 3) {
                BufferedReader br = new BufferedReader(new FileReader("expenses.txt"));
                String line;

                double total = 0;
                double max = 0;
                String maxCategory = "";

                HashMap<String, Double> map = new HashMap<>();
                ArrayList<Expense> list = new ArrayList<>();

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    String category = parts[0];
                    double amount = Double.parseDouble(parts[1]);

                    total += amount;

                    if (amount > max) {
                        max = amount;
                        maxCategory = category;
                    }

                    map.put(category, map.getOrDefault(category, 0.0) + amount);
                    list.add(new Expense(category, amount));
                }
                br.close();

                System.out.println("\n💰 Total Spending: " + total);

                System.out.println("\n📊 Category-wise Spending:");
                for (String key : map.keySet()) {
                    System.out.println("• " + key + " : " + map.get(key));
                }

                System.out.println("\n🏆 Highest Single Expense:");
                System.out.println(maxCategory + " - " + max);

                // Sort expenses (descending)
                list.sort((a, b) -> Double.compare(b.amount, a.amount));

                System.out.println("\n🔥 Top 3 Expenses:");
                for (int i = 0; i < Math.min(3, list.size()); i++) {
                    System.out.println((i + 1) + ". " + list.get(i).category + " - " + list.get(i).amount);
                }
            }

        } while (choice != 4);

        System.out.println("Exiting...");
    }
}
