import big.data.DataSource;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/**
 * @author joshua.s.george@stonybrook.edu
 * ID: 112839378
 * Rec: 02
 * <p>
 * This class acts a hashtable for managing of the added auctions based on their unique ID's
 * </p>
 */
public class AuctionTable<K, V> extends Hashtable {

    /**
     * This method prints a neatly formatted output of the AuctionTable
     */
    public void printTable() {
        Set<String> auctionID = this.keySet();
        System.out.println("Auction ID |      Bid    |        Seller            |          Buyer           |    Time   |  Item Info \n" +
                "===================================================================================================================================");
        for (String ids : auctionID) {
            System.out.println(this.get(ids));
        }
    }

    /**
     * This method puts an auction with the given ID into the Auction table
     *
     * @param auctionID ID of the auction to be added
     * @param auction   Auction to be added
     * @throws IllegalArgumentException Thrown if given ID already exists within the Auction Table
     */
    public void putAuction(String auctionID, Auction auction) throws IllegalArgumentException {
        if (this.containsKey(auctionID))
            throw new IllegalArgumentException("This ID is already in use on the auction table");
        this.put(auctionID, auction);
    }

    /**
     * This method returns the auction with the given ID
     *
     * @param auctionID ID of the auction to return
     * @return Auction with the given ID
     */
    public Auction getAuction(String auctionID) {
        if (!this.containsKey(auctionID))
            throw new IllegalArgumentException("This ID is not found within the auction table");
        return (Auction) this.get(auctionID);
    }

    /**
     * This method lets the time of all the auction pass by a given amount
     *
     * @param numHours Number of hours to decrease each auction by
     */
    public void letTimePass(int numHours) {
        if (numHours < 0)
            throw new IllegalArgumentException("Please enter a positive number of hours");
        Set<String> auctionID = this.keySet();
        for (String ids : auctionID) {
            this.put(ids, ((Auction) this.get(ids)).decrementTimeRemaining(numHours));
        }
    }

    public void removeExpiredAuctions() {
        Set<String> auctionID = this.keySet();
        for( Iterator<String> itr = auctionID.iterator(); itr.hasNext();) {
            String temp = itr.next();
            System.out.println(temp);
        }
    }



    /**
     * This method builds an Auction Table from the given URL link
     *
     * @param URL URL of the webpage with auction data in XML form
     * @return Auction Table with auctions from the data found at the URL
     */
    public static AuctionTable buildFromURL(String URL) {
        DataSource ds = DataSource.connect(URL).load();
        String[] time = ds.fetchStringArray("listing/auction_info/time_left");
        String[] current = ds.fetchStringArray("listing/auction_info/current_bid");
        String[] id = ds.fetchStringArray("listing/auction_info/id_num");
        String[] seller = ds.fetchStringArray("listing/seller_info/seller_name");
        String[] buyer = ds.fetchStringArray("listing/auction_info/high_bidder/bidder_name");
        String[] itemInfo = ds.fetchStringArray("listing/item_info/description");

        AuctionTable<Integer, Auction> auctionTable = new AuctionTable<>();

        for (int i = 0; i < seller.length; i++) {
            int tempTime = 0;

            String idVar = id[i];

            String sellerVar = seller[i];

            String infoVar = itemInfo[i];

            if (time[i].contains("days") || time[i].contains("day"))
                tempTime += 24 * Integer.parseInt(time[i].charAt(time[i].indexOf("day") - 2) + "");
            if (time[i].contains("hours") || time[i].contains("hrs"))
                tempTime += Integer.parseInt(time[i].charAt(time[i].indexOf("h") - 2) + "");

                String bid;
            double bidVal;
            if (!current[i].equalsIgnoreCase("")) {
                bid = current[i].replace("$", "").replace(",", "");
                bidVal = Double.parseDouble(bid);
            } else {
                bidVal = 0;
            }

            String buyerVar;
            if (!buyer[i].equalsIgnoreCase("")) {
                buyerVar = buyer[i];
            } else {
                buyerVar = "N/A";
            }

            auctionTable.put(idVar, new Auction(tempTime, bidVal, idVar, sellerVar, buyerVar, infoVar));

        }
        return auctionTable;
    }

}
