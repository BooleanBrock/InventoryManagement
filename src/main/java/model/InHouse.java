package model;

/**
 * Model for an in-house part that inherits from the Part class
 *
 * @author Wyatt Brock
 *
 */

public class InHouse extends Part{

    /**
     * Machine ID for this part
     */
    private int machineId;

    /**Constructor for new instance of In-house part
     *
     * @param id is the id for this part
     * @param name is the name for this part
     * @param price is the price for this part
     * @param stock is the stock or inventory for this part
     * @param min is the min value for this part
     * @param max is the max value for this part
     * @param machineId is the machine ID for this part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }


    /**
     * The setter for the machine ID of this part
     *
     * @param machineId is the machine ID of the part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**The getter for the machine ID of this part
     *
     * @return machineId the machine ID for this part
     */
    public int getMachineId(){
        return machineId;
    }
}

