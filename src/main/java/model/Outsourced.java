package model;

/**
 * Model for Outsourced variont of Part
 *
 * Outsourced inherits from Part class
 * @author Wyatt Brock
 */

public class Outsourced extends Part{

    /**
     * Company name of Part object
     */
    private String companyName;

    /**Constructor for new instance of Outsourced part
     *
     * @param id is the id for this part
     * @param name is the name for this part
     * @param price is the price/cost for this part
     * @param stock is the stock for this part
     * @param min is the minimum for this part
     * @param max is the maximum for this part
     * @param companyName is the company name for this part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * The setter for the company name of this part
     *
     * @param companyName is the company name of this part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**The getter for the company name of this part
     *
     * @return companyName the company name for this part
     */
    public String getCompanyName() {
        return companyName;
    }
}
