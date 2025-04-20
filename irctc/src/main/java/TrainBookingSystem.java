import enteties.Train;
import enteties.User;
import phonepe.service.UserBookingService;
import phonepe.util.UserServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TrainBookingSystem {
    public static void main(String[] args) throws IOException {
        System.out.println("Train Booking System");
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        userBookingService = new UserBookingService();

        while(option != 8){
            System.out.println("Please enter your choice");
            System.out.println("1.SingUp");
            System.out.println("2.Login");
            System.out.println("3.FetchBooking");
            System.out.println("4.CancelBooking");
            System.out.println("5.SearchTrains");
            System.out.println("6:FetchSeats");
            System.out.println("7:BookingSeats");
            System.out.println("8:Exit");
            option = scanner.nextInt();
            scanner.nextLine();
            switch(option){
                case 1:
                    System.out.println("Please enter your username");
                    String username = scanner.next();
                    System.out.println("Please enter your password");
                    String password = scanner.next();
                    User userToSignup = new User(
                            UUID.randomUUID().toString(),
                            username,
                            password,
                            UserServiceUtil.hashPassword(password),
                            new ArrayList<>()
                    );
                    userBookingService.singUp(userToSignup);
                    break;
                    case 2:
                        System.out.println("Please enter your username");
                        String name = scanner.nextLine();

                        System.out.println("Please enter your password");
                        String userPassword = scanner.nextLine();
                        User userToLogin = new User(UUID.randomUUID().toString(),name, userPassword, UserServiceUtil.hashPassword(userPassword), new ArrayList<>());
                        userBookingService = new UserBookingService(userToLogin);
                        if(userBookingService.loginUser()){
                            System.out.println("Login Successful");
                        }else{
                            System.out.println("Login Failed");
                        }
                        break;
                        case 3:
                            userBookingService.fetchBooking();
                            break;
                            case 4:
                                System.out.println("For cancel booking, Please enter your ticket id!");
                                String ticketId = scanner.nextLine();
                               try{
                                   userBookingService.cancelBooking(ticketId);
                               } catch (IOException e) {
                                   throw new RuntimeException(e);
                               }
                               break;
                               case 5:
                                   System.out.println("Please enter source and destination");
                                   String source = scanner.nextLine();
                                   String destination = scanner.nextLine();
                                   List<Train> trains = userBookingService.getTrains(source, destination);

                                   if (trains.isEmpty()) {
                                       System.out.println("No trains available for the given route.");
                                   } else {
                                       trains.forEach(train -> System.out.println(train.getTrainInfo()));
                                   }
                                   break;
                                   case 6:
                                       System.out.println("Please enter train id");
                                       String trainId = scanner.nextLine();
                                       List<List<Integer>> seats =userBookingService.trainSeats(trainId);
                                       for (List<Integer> row: seats){
                                           for (Integer val: row){
                                               System.out.print(val+" ");
                                           }
                                           System.out.println();
                                       }
                                       break;
                                       case 7:
                                           System.out.println("Select the seat by typing the row and column");
                                           System.out.println("Please enter train id");
                                           String trainIdForBook = scanner.nextLine();
                                           System.out.println("Enter the row");
                                           int row = scanner.nextInt();
                                           System.out.println("Enter the column");
                                           int col = scanner.nextInt();
                                           System.out.println("Booking your seat...");
                                           Boolean booked = userBookingService.bookTickets(trainIdForBook, row, col);
                                           if(booked.equals(Boolean.TRUE)){
                                               System.out.println("Ticket Booked Successfully");
                                           }else{
                                               System.out.println("Can't book this seat.");
                                           }
                                           break;
                                   case 8:
                                       System.out.println("Bye!");
                                       break;
            }
        }
    }
}
