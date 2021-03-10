/**
 * @author joshua.s.george@stonybrook.edu
 * ID: 112839378
 * Rec: 02
 */
public class Auction {
    private int timeRemaining;
    private double currentBid;
    private String auctionID, sellerName, buyerName, itemInfo;

    /**
     *
     * @param timeRemaining Time left in auction
     * @param currentBid Current bid price
     * @param auctionID ID of auction
     * @param sellerName Name of seller account
     * @param buyerName Name of buyer account
     * @param itemInfo Information of item being sold
     */
    public Auction(int timeRemaining, double currentBid, String auctionID, String sellerName, String buyerName, String itemInfo) {
        this.timeRemaining = timeRemaining;
        this.currentBid = currentBid;
        this.auctionID = auctionID;
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        if(itemInfo.length() < 38)
            this.itemInfo = itemInfo;
        else
            this.itemInfo = itemInfo.substring(0,38);
    }

    /**
     *
     * @return Time remaining in this auction
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     *
     * @return Value of current bid
     */
    public double getCurrentBid() {
        return currentBid;
    }

    /**
     *
     * @return ID of auction
     */
    public String getAuctionID() {
        return auctionID;
    }

    /**
     *
     * @return Name of the seller
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     *
     * @return Name of the buyer
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     *
     * @return Name of the buyer
     */
    public String getItemInfo() {
        return itemInfo;
    }

    /**
     *
     * @param time Time to decrease timeRemaining by
     * @return Auction object with timeRemaining value decreased by given time
     */
    public Auction decrementTimeRemaining(int time) {
        if(timeRemaining >= time)
            return new Auction(timeRemaining - time, currentBid, auctionID, sellerName, buyerName, itemInfo);
        else
            return new Auction(0, currentBid, auctionID, sellerName, buyerName, itemInfo);
    }

    /**
     *
     * @return String representation of an auction in tabular form
     */
    public String toString() {
        return String.format("%-10s | $  %8.2f | %-24s | %-24s | %3s hours | %-25s", auctionID, currentBid, sellerName, buyerName, timeRemaining, itemInfo);
    }
}
