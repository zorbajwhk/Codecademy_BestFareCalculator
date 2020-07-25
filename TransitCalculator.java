import java.io.*;
import java.util.Arrays;

public class TransitCalculator{
   
    int daysOfRide;
    int rideQuantity;
    int location;
    boolean discount;

static String[] city = {"New York City", "(NYC)" , "Mexico City", "(MC)"}; 
    static String[] transitFareType = {"Pay-per-ride (Single Ride)", "7-day Unlimited Rides", "30-day Unlimited Rides"};
   
    static double[][] transitFare = {
        {2.75, 33.00, 127.00}, //NYC Fare
        {1.35, 16.50, 63.50},  //NYC Discount Fare
        {5.00, 60.00, 230.00}, //Mexico Fare
        {2.50, 30.00, 130.00}, //Mexico Discount Fare
    };

    double[] userPrice = new double[3];

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public TransitCalculator() throws IOException {
        this.daysOfRide = daysSelector();
        this.rideQuantity = ridesSelector();
        this.location = citySelector();
        this.discount = discountSelector(this.location);
        this.userPrice = getRidePrices(this.rideQuantity, this.daysOfRide, this.location, this.discount);
        

        unlimited7Price(this.daysOfRide,this.location ,this.discount);
    }

    //input days of travel.
    public int daysSelector() throws IOException {
        System.out.print("Please insert how many days are you planning to transit( 1 - 30 Days ): ");
        String strDays = input.readLine();
        int intDays = Integer.parseInt(strDays);

        while (intDays < 0 || intDays > 30) {
            System.out.println("Error... Please input correct selection. \n");
            System.out.print("Please insert how many days are you planning to transit( 1 - 30 Days ): ");
            strDays = input.readLine();
            intDays = Integer.parseInt(strDays);
        }

        return intDays;
    }

    // input intention rides.
    public int ridesSelector() throws IOException {
        System.out.print("Please insert how many rides are you planning to use( 1 or above ): ");
        String strRides = input.readLine();
        int intRides = Integer.parseInt(strRides);

        while (intRides < 0) {
            System.out.println("Error... Please input correct selection. \n");
            System.out.print("Please insert how many rides are you planning to use( 1 or above ): ");
            strRides = input.readLine();
            intRides = Integer.parseInt(strRides);
        }

        return intRides;
    }

    // Select city for fare cal. Returns int.
    public int citySelector() throws IOException {
        int cityNo = 0;
        boolean citySelected = false;

        while (citySelected == false) {
            System.out.println("Please input the city you plan to use trainst at: " + Arrays.toString(city));
            String userCity = input.readLine();

            if (userCity.equalsIgnoreCase("New York City") || userCity.equalsIgnoreCase("NewYorkCity") || userCity.equalsIgnoreCase("NYC")){
                cityNo = 0;
                citySelected = true;
            } else if (userCity.equalsIgnoreCase("Mexico City") || userCity.equalsIgnoreCase("MC")) {
                cityNo = 2;
                citySelected = true;
            } else {
                System.out.println("Error... Please input correct selections. \n");
            }
        }
        return cityNo;
    }

    // Select if user are eligible for discount price
    public boolean discountSelector(int cityNo) throws IOException {
        boolean discount = false;
        boolean userInput = false;

        while (userInput == false) {
            System.out.println(city[cityNo]
                    + " Metro offers discount fare price for disabilities and people who are at least 65 years old."
                    + "\n" + "Are you eligible? (Yes / No)");
            String userChoice = input.readLine();

            if (userChoice.equalsIgnoreCase("Yes")) {
                discount = true;
                userInput = true;
            } else if (userChoice.equalsIgnoreCase("No")) {
                discount = false;
                userInput = true;
            } else {
                System.out.println("Error... Please input correct selection. \n");
            }
        }

        return discount;
    }

    // Get price of using week pass. Pass +1 per every 7 days.
    public double unlimited7Price(int days, int city, boolean discount) {
        int DiscountSwtich = city;
        if(discount == true){DiscountSwtich++;};

        double price = transitFare[DiscountSwtich][1];
        for (int pass = days / 7; pass != 0; pass--) {
            price += price;
        }
        return price;
    }

    // Get avg price per ride for each choices.
    public double[] getRidePrices(int rides, int days, int city, boolean discount) {
        int DiscountSwtich = city;
        if(discount == true){DiscountSwtich++;};

        double[] getRidePrices = new double[3];
        getRidePrices[0] = rides * transitFare[DiscountSwtich][0];
        getRidePrices[1] = (double) unlimited7Price(days, city, discount) / rides;
        getRidePrices[2] = transitFare[city][2] / rides;

        return getRidePrices;
    }

    // Sorting prices return.
    public void getBestFare(double[] prices) {
        String[] fareType = transitFareType;
        double[] farePrice = prices;

        // Sorting for cheaper
        for (int i = 0; i < farePrice.length - 1; i++) {
            for (int j = i + 1; j < farePrice.length; j++) {
                if (farePrice[i] > farePrice[j]) {
                    String strTemp = fareType[i];
                    double doubleTemp = farePrice[i];

                    fareType[i] = fareType[j];
                    farePrice[i] = farePrice[j];

                    fareType[j] = strTemp;
                    farePrice[j] = doubleTemp;
                }
            }
        }

        // Sorting for cheapest
        if (farePrice[0] > farePrice[1]) {
            String strTemp = fareType[0];
            double doubleTemp = farePrice[0];

            fareType[0] = fareType[1];
            farePrice[0] = farePrice[1];

            fareType[1] = strTemp;
            farePrice[1] = doubleTemp;
        }

        String bestFare = "You should use the " + fareType[0] + " for " + prices[0] + " per ride.";
        System.out.println(bestFare);
    }

    public static void main(String[] args) throws IOException {

        TransitCalculator userCalculator01 = new TransitCalculator();
        userCalculator01.getBestFare(userCalculator01.userPrice);

    }

}