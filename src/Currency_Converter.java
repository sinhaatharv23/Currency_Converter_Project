import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Currency_Converter {
    public static void main(String[] args) throws IOException {
        HashMap<Integer,String>currencyCodes=new HashMap<Integer,String>();
        //here key is integer and value from which we will mapped to will be string which will be our currency codes

        //Add currency codes:-
        currencyCodes.put(1,"USD");//US dollars
        currencyCodes.put(2,"CAD");//Canadian dollars
        currencyCodes.put(3,"EUR");//Euros
        currencyCodes.put(4,"INR");//Indian rupee
        currencyCodes.put(5,"HKD");//Hong-Kong Dollars
        //Now if we pass a key of 1 we'll get a string back which is USD and so on
        String fromCode,toCode;//to convert from (fromCode) and to convert into(toCode)
        double amount;
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Currency Converter!");
        System.out.println("Currency converting from??");
        System.out.println("1:USD(US Dollar)\t 2:CAD(Canadian Dollar)\t 3:EUR(Euro)\t 4:INR(Indian Rupee)\t 5:HKD(Hong-Kong Dollar)");
        fromCode = currencyCodes.get(sc.nextInt());
        System.out.println("Currency converting to??");
        System.out.println("1:USD(US Dollar)\t 2:CAD(Canadian Dollar)\t 3:EUR(Euro)\t 4:INR(Indian Rupee)\t 5:HKD(Hong-Kong Dollar)");
        toCode=currencyCodes.get(sc.nextInt());
        System.out.println("Amount you wish to convert?");
        amount= sc.nextFloat();
        sendHttpGETRequest(fromCode,toCode,amount);//API that will give us current exchange rate in real time and we'll do that via HTTP, here we will be writing a function
        System.out.println("Thank you for using the currency converter!");
    }
    private static void sendHttpGETRequest(String fromCode,String toCode,double amount) throws IOException {
        String GET_URL = "https://open.er-api.com/v6/latest/" + fromCode;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode==HttpURLConnection.HTTP_OK){//successfull request
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine=in.readLine())!=null){
                response.append(inputLine);
            }in.close();
            //JSON :- Javascript object notation is a human and machine format to represent data that is structured
            JSONObject obj = new JSONObject(response.toString());
            Double exchangeRate = obj.getJSONObject("rates").getDouble(toCode);
            System.out.println(obj.getJSONObject("rates"));
            System.out.println(exchangeRate);//keep for debugging
            System.out.println();
            System.out.printf("%.2f %s = %.2f %s\n",amount,fromCode,(amount*exchangeRate),toCode);
        }else{
            System.out.println("GET request failed!");
        }
    }
}
