import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserInfoApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные в формате: Фамилия Имя Отчество датарождения номертелефона пол");
        String input = scanner.nextLine();
        scanner.close();

        try {
            processInputData(input);
            System.out.println("Данные успешно записаны в файл.");
        } catch (UserDataFormatException e) {
            System.out.println("Ошибка в формате введенных данных: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void processInputData(String input) throws UserDataFormatException, IOException {
        String[] data = input.split("\\s+");
        if (data.length != 6) {
            throw new UserDataFormatException("Неверное количество данных. Требуется 6 параметров.");
        }

        String lastName = data[0];
        String firstName = data[1];
        String middleName = data[2];
        String birthDate = data[3];
        String phoneNumber = data[4];
        String gender = data[5];

        if (!isValidDate(birthDate)) {
            throw new UserDataFormatException("Неверный формат даты рождения. Ожидается dd.mm.yyyy.");
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            throw new UserDataFormatException("Неверный формат номера телефона. Ожидается целое беззнаковое число.");
        }

        if (!isValidGender(gender)) {
            throw new UserDataFormatException("Неверный формат пола. Ожидается символ 'f' или 'm'.");
        }

        String fileName = lastName + ".txt";
        String userInfo = lastName + " " + firstName + " " + middleName + " " + birthDate + " " + phoneNumber + " " + gender;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(userInfo);
            writer.newLine();
        }
    }

    private static boolean isValidDate(String date) {
        return date.matches("\\d{2}.\\d{2}.\\d{4}");
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d+");
    }

    private static boolean isValidGender(String gender) {
        return gender.matches("[fm]");
    }
}

class UserDataFormatException extends Exception {
    public UserDataFormatException(String message) {
        super(message);
    }
}
