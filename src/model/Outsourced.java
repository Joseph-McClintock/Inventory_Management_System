package model;

public class Outsourced extends Part{

    /**
     * A parts companyName
     * */
    private String companyName;

    /**Constructor for a new InHouse part
     *
     * @param id of part
     * @param name of part
     * @param price of part
     * @param stock of part
     * @param min of part
     * @param max of part
     * @param companyName of part
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**Setter for machineId
     * @param companyName of part
     * */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**Getter for machineId
     * @return companyName of part
     * */
    public String getCompanyName(){
        return companyName;
    }
}
