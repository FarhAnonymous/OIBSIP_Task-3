import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ATM_Interface {

	private static ArrayList<Integer> userid = new ArrayList<>(); // Array list for users
	private static ArrayList<String> passwords = new ArrayList<>(); // Array list for the passwords
	private static ArrayList<Integer> balance = new ArrayList<>(); // Array list for balance
	private static ArrayList<Integer> transaction_history = new ArrayList<>(); // Array list for transaction history

	static Scanner in = new Scanner(System.in);

	private static void registered_user() {

		System.out.println("Enter your ID:");
		int id = in.nextInt();
		System.out.println("Enter your password:");
		String password = in.next();

		String validation = passwords.get(id);

		if (id > userid.size()) {
			System.out.println("Invalid ID!");
			registered_user();
		}

		else if (password.equals(validation) == false) {
			System.out.println("Invalid password!");
			registered_user();
		}

		else if (id <= userid.size() && password.equals(validation) == true) {
			System.out.println("Welcome! Please select the options you want to perform:");
			int tempid = id;

			while (true) {

				System.out.println("1-Check Balance");
				System.out.println("2-Withdraw Amount");
				System.out.println("3-Deposit Amount");
				System.out.println("4-Transfer Amount");
				System.out.println("5-Transaction History");
				System.out.println("6-Exit");

				int userChoice = in.nextInt();

				switch (userChoice) {
				case 1:
					check_balance(tempid);
					break;

				case 2:
					withdraw_amount(tempid);
					break;

				case 3:
					deposit_amount(tempid);
					break;

				case 4:
					transfer_amount(tempid);
					break;

				case 5:
					transaction_history(tempid);
					break;

				case 6:
					exit();
				}

			}
		}

	}

	private static void check_balance(int id) {
		System.out.println("Amount left in your account is Rs. " + balance.get(id));
	}

	private static void withdraw_amount(int id) {
		System.out.println("Enter the amount you want to withdraw:");
		int withdrawAmount = in.nextInt();
		int currentAmount = balance.get(id);

		if (withdrawAmount > currentAmount) {
			System.out.println("Insufficient Balance!");
		}

		else {
			balance.set(id, currentAmount - withdrawAmount);
			transaction_history.add(0 - withdrawAmount);
			System.out.println("Please take your withdrawn cash.");
		}
	}

	private static void deposit_amount(int id) {
		System.out.println("Enter the amount you want to deposit:");
		int depositAmount = in.nextInt();
		int updateAmount = balance.get(id) + depositAmount;
		balance.set(id, updateAmount);
		System.out.println("Amount deposited into your account!");

	}

	private static void transfer_amount(int id) {
		System.out.println("Enter the ID in which you want to transfer the amount:");
		int receiverId = in.nextInt();

		if (receiverId < userid.size() && receiverId != id) {
			System.out.println("Enter the amount you want to transfer:");
			int transferAmount = in.nextInt();

			if (transferAmount > balance.get(id)) {
				System.out.println(
						"You have insufficient balance in your account! Please add balance into your account.");

				System.out.println("if you want to add amount to your account, enter '1' else '2'");
				int choice = in.nextInt();

				if (choice == 1) {
					deposit_amount(id);
				}

				else {
					registered_user();
				}
			}

			else {

				if (id == 0) {
					int currentAmount = balance.get(receiverId);
					int totalAmount_receiver = currentAmount + transferAmount;
					int deductAmount = balance.get(id) - transferAmount;

					balance.set(id, deductAmount);
					balance.set(receiverId, totalAmount_receiver);
					transaction_history.add(0 - deductAmount);

				}

				if (id != 0) {
					int currentAmount = balance.get(receiverId);
					int totalAmount_receiver = currentAmount + transferAmount;
					int deductAmount = balance.get(id) - transferAmount;

					balance.set(id, deductAmount);
					balance.set(receiverId, totalAmount_receiver);
					transaction_history.add(deductAmount);

				}
			}
		}

		else {
			System.out.println("The ID entered doesn't exist! Please enter valid ID.");
		}
	}

	private static void transaction_history(int id) {

		if (id == 0) {
			System.out.println("Welcome! This is your transaction History.");

			for (int i = 0; i < transaction_history.size(); i++) {

				if (transaction_history.get(i) < 0) {
					System.out.println("Rs. " + transaction_history.get(i) + "has been deducted from your account.");
				}

				else {
					System.out.println("Rs. " + transaction_history.get(i) + "has been credited to your account.");
				}
			}
		}

	}

	private static void exit() {
		System.out.println("If you want to login again, Enter '1' else enter '2'");
		int choice = in.nextInt();
		if (choice == 1) {
			registered_user();
		}

		else {
			System.out.println("Thank you for banking with us! :)");
		}
	}

	private static void new_user() {
		System.out.println("Hello User! Please enter " + userid.size() + " as your registration ID.");
		int enterId = in.nextInt();

		if (enterId != userid.size()) {
			System.out.println("Enter the recommended ID.");
			new_user();
		}

		else if (enterId == userid.size()) {
			userid.add(enterId);
			System.out.println("Set your password! (It should be more than 4 characters.)");
			String setPassword = in.next();

			if (setPassword.length() < 4) {
				new_password();
				if (new_password()) {
					passwords.add(enterId, setPassword);
				}

				else {
					new_password();
				}
			}

			else {
				passwords.add(enterId, setPassword);
			}

			System.out.println("You've registered as a new user successfully!");

			if (add_balance(enterId)) {
				System.out.println("Your account is activated! Please login as a registered user.");

			}

			else {
				add_balance(enterId);
			}

			registered_user();
		}
	}

	private static boolean add_balance(int enterId) {
		System.out.println("Deposit minimum Rs. 1000 to open your account.");
		int minAmount = in.nextInt();

		if (minAmount < 1000) {
			return false;
		}

		else {
			balance.add(enterId, minAmount);
			return true;
		}
	}

	private static boolean new_password() {
		System.out.println("Enter new password having more than 4 characters.");
		String setPassword = in.next();

		if (setPassword.length() >= 4) {
			return true;
		}

		else {
			return false;
		}

	}

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		System.out.println("Enter 1 if you're registered user \nEnter 2 if you're a new user.");
		int choice = in.nextInt();

		switch (choice) {
		case 1:
			registered_user();
			break;

		case 2:
			new_user();
			break;
		}

	}

}
