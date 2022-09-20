// 19021381 - Nguyen Van Tu
// 20021396 - Pham Trung Minh
// 20020156 - Pham Duc Thang

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

class Play {
    public String name;
    public String type;

    public Play(String name, String type) {
        this.name = name;
        this.type = type;
    }
}

class Performance {
    public String playID;
    public int audience;

    public Performance(String playID, int audience) {
        this.playID = playID;
        this.audience = audience;
    }
}

class Invoice {
    public String customer;
    public ArrayList<Performance> performances;

    public Invoice(String customer, ArrayList<Performance> performances) {
        this.customer = customer;
        this.performances = performances;
    }
}

public class Bill_Detail {

    // The following function is used to print bill details for one invoice
    public static String billDetailsString(Invoice invoice, HashMap<String, Play> plays) {
        int totalAmount = 0;
        int volumeCredits = 0;
        String results = "Statement for " + invoice.customer + "\n";

        for (int i = 0; i < invoice.performances.size(); i++) {
            Performance perf = invoice.performances.get(i);
            Play play = plays.get(perf.playID);
            int thisAmount = 0;

            switch (play.type) {
                case "tragedy":
                    thisAmount = 40000;
                    if (perf.audience > 30) {
                        thisAmount += 1000 * (perf.audience - 30);
                    }
                    break;

                case "comedy":
                    thisAmount = 30000;
                    if (perf.audience > 20) {
                        thisAmount += 10000 + 500 * (perf.audience - 20);
                    }
                    thisAmount += 300 * perf.audience;
                    break;
                default:
                    throw new InputMismatchException("unknow type: " + play.type);
            }
            // add volumn credits
            volumeCredits += Math.max(perf.audience, 0);
            // add extra credits for every ten comedy attendees
            if (play.type.equals("comedy")) {
                volumeCredits += Math.floor(perf.audience / 5.0);
            }
            // print line for this order
            results += play.name + ": " + String.format("$%.2f", (thisAmount / 100.0))
                    + " (" + perf.audience + " seats)\n";
            totalAmount += thisAmount;

        }
        results += "Amount owed is " + String.format("$%.2f", (totalAmount / 100.0) + "\n");
        results += "You earned " + volumeCredits + " credits\n";
        return results;
    }

    public static void main(String[] args) {
        // create dictionary plays
        HashMap<String, Play> plays = new HashMap<String, Play>();
        plays.put("hamlet", new Play("Hamlet", "tragedy"));
        plays.put("as-like", new Play("As You Like It", "comedy"));
        plays.put("othello", new Play("Othello", "tragedy"));

        // create array list performance
        ArrayList<Performance> performances = new ArrayList<Performance>();
        performances.add(new Performance("hamlet", 55));
        performances.add(new Performance("as-like", 35));
        performances.add(new Performance("othello", 40));

        // create new invoice = hoa don
        Invoice invoice = new Invoice("BigCo", performances);

        // print result of customer's invoice details
        System.out.println(billDetailsString(invoice, plays));
    }
}