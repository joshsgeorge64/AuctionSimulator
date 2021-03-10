import java.util.Scanner;

/**
 * @author joshua.s.george@stonybrook.edu
 * ID: 112839378
 * Rec: 02
 * <p>
 *     This class contains the main method for managing the auctions through the AuctionTable.
 * </p>
 */
public class AuctionSystem {
    /**
     * This is the main method of the Auction System class, it provides a menu for the user and manages all auctions
     * @param args Command-line args
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String selection = "";
        System.out.print("Please enter a username: ");
        String username = scan.next();
        AuctionTable<String, Auction> auctionTable = new AuctionTable<>();
        while (!selection.equalsIgnoreCase("Q")) {
            System.out.println();
            try {
                System.out.println("Menu:\n" +
                        "    (D) - Import Data from URL\n" +
                        "    (A) - Create a New Auction\n" +
                        "    (B) - Bid on an Item\n" +
                        "    (I) - Get Info on Auction\n" +
                        "    (P) - Print All Auctions\n" +
                        "    (R) - Remove Expired Auctions\n" +
                        "    (T) - Let Time Pass\n" +
                        "    (Q) - Quit\n");
                System.out.print("Please select and option: ");
                selection = scan.next();
                if (selection.equalsIgnoreCase("D")) {
                    System.out.print("Please enter a URL: ");
                    String url = scan.next();
                    auctionTable = AuctionTable.buildFromURL(url);
                    System.out.println("Loading...");
                    System.out.println("Auction data loaded successfully!");

                } else if (selection.equalsIgnoreCase("A")) {
                    System.out.println("Creating new Auction as " + username);
                    System.out.print("Please enter an Auction ID: ");
                    String id = scan.next();
                    System.out.print("Please enter an auction time (hours): ");
                    int hours = scan.nextInt();
                    System.out.print("Please enter some item info: ");
                    scan.nextLine();
                    String info = scan.nextLine();
                    System.out.println();
                    auctionTable.putAuction(id, new Auction(hours, 0, id, username, "N/A", info));
                    System.out.println("Auction " + id + " inserted into table");

                } else if (selection.equalsIgnoreCase("B")) {
                    System.out.print("PLease enter an Auction ID: ");
                    String id = scan.next();
                    Auction temp = auctionTable.getAuction(id);
                    if (auctionTable.getAuction(id).getTimeRemaining() <= 0) {
                        System.out.println("Auction " + id + " is CLOSED");
                        System.out.println("Current Bid: $ " + auctionTable.getAuction(id).getCurrentBid());
                    } else {
                        System.out.println("Auction " + id + " is OPEN");
                        System.out.println("Current bid: " + auctionTable.getAuction(id).getCurrentBid());
                        System.out.print("What would you like to bid?:  ");
                        int bid = scan.nextInt();
                        if(bid > auctionTable.getAuction(id).getCurrentBid()) {
                            System.out.println("Bid accepted");
                            auctionTable.put(id, new Auction(temp.getTimeRemaining(), bid, temp.getAuctionID(),
                                    temp.getSellerName(), temp.getBuyerName(),temp.getItemInfo()));
                        } else {
                            System.out.println("Bid denied - not enough value");
                        }
                    }

                } else if (selection.equalsIgnoreCase("I")) {
                    System.out.print("Please enter an Auction ID: ");
                    String id = scan.next();
                    Auction temp = auctionTable.getAuction(id);
                    System.out.println("    Seller: " + temp.getSellerName());
                    System.out.println("    Buyer: " + temp.getBuyerName());
                    System.out.println("    Time: " + temp.getTimeRemaining() + " hours");
                    System.out.println("    Info: " + temp.getItemInfo());


                } else if (selection.equalsIgnoreCase("P")) {
                    auctionTable.printTable();

                } else if (selection.equalsIgnoreCase("R")) {
                    System.out.println("Removing expired auctions...");
                    //auctionTable.removeExpiredAuctions();
                    System.out.println("All expired auctions removed");

                } else if (selection.equalsIgnoreCase("T")) {
                    System.out.print("How many hours should pass: ");
                    int hours = scan.nextInt();
                    auctionTable.letTimePass(hours);

                } else if (selection.equalsIgnoreCase("Q")) {
                    System.out.println("Writing Auction Table to file...");
                    System.out.println("done");
                } else {
                    System.out.println("Please enter a valid option");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e);
                selection = "";
            } catch (Exception e) {
                System.out.println("Please enter a valid input");
            }

        }


    }
}
