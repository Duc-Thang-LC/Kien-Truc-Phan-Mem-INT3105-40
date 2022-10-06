// 19021381 - Nguyen Van Tu
// 20021396 - Pham Trung Minh
// 20020156 - Pham Duc Thang

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

//sửa name và type public thành private rồi thêm hàm get
class Play {
    private String name;
    private String type;

    public Play(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }
}

//tương tự class trên
class Performance {
    private String playID;
    private int audience;

    public Performance(String playID, int audience) {
        this.playID = playID;
        this.audience = audience;
    }

    public String getPlayID() {
        return this.playID;
    }

    public int getAudience() {
        return audience;
    }
}

//như class trên
class Invoice {
    private String customer;
    private ArrayList<Performance> performances;

    public Invoice(String customer, ArrayList<Performance> performances) {
        this.customer = customer;
        this.performances = performances;
    }

    public String getCustomer() {
        return customer;
    }

    public ArrayList<Performance> getPerformances() {
        return performances;
    }
}

public class Bill_Detail {

    // The following function is used to print bill details for one invoice
    public static String billDetailsString(Invoice invoice, HashMap<String, Play> plays) {
        int totalAmount = 0;
        int volumeCredits = 0;
        String results = "Statement for " + invoice.getCustomer() + "\n";

        for (int i = 0; i < invoice.getPerformances().size(); i++) {
            Performance perf = invoice.getPerformances().get(i);
            Play play = plays.get(perf.getPlayID());
            int thisAmount = 0;

            switch (play.getType()) {
                case "tragedy":
                    thisAmount = 40000;
                    if (perf.getAudience() > 30) {
                        thisAmount += 1000 * (perf.getAudience() - 30);
                    }
                    break;

                case "comedy":
                    thisAmount = 30000;
                    if (perf.getAudience() > 20) {
                        thisAmount += 10000 + 500 * (perf.getAudience() - 20);
                    }
                    thisAmount += 300 * perf.getAudience();
                    break;
                default:
                    throw new InputMismatchException("unknow type: " + play.getType());
            }
            // add volumn credits
            volumeCredits += Math.max(perf.getAudience(), 0);
            // add extra credits for every ten comedy attendees
            if (play.getType().equals("comedy")) {
                volumeCredits += Math.floor(perf.getAudience() / 5.0);
            }
            // print line for this order
            results += play.getName() + ": " + String.format("$%.2f", (thisAmount / 100.0))
                    + " (" + perf.getAudience() + " seats)\n";
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