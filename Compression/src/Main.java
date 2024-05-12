import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueMenu = true;

        // Frase 1: cuantos cuentos cuentas
        // Frase 2: tres tristes tigres
        // Frase 3: 0 99 0 111 0 109 17 32 42 99 44 109 42 112 25 99 42 99 46 109 0 112 0 114 33 0

        while (continueMenu) {
            System.out.println("Enter your choice: \n[1] Pack \n[2] Unpack \n[3] Exit");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        packOperation(scanner);
                        break;
                    case 2:
                        unpackOperation(scanner);
                        break;
                    case 3:
                        continueMenu = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void packOperation(Scanner scanner) {
        System.out.println("Pack");
        System.out.print("Enter the phrase: ");
        String phrase = scanner.nextLine();
        Packing packing = new Packing(phrase);
        while (packing.findUniqueChar()) {
        }
        packing.toBytes();
        for (Byte i : packing.getByteList()) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private static void unpackOperation(Scanner scanner) {
        System.out.println("Unpack");
        System.out.print("Enter the package: ");
        String pack = scanner.nextLine();
        Packing unpacking = new Packing();
        unpacking.stringToBytes(pack);
        unpacking.setBlockList(unpacking.toBlock(unpacking.getByteList()));
        unpacking.toStringFormat();
        System.out.println(unpacking.getFullSentence());
    }
}
