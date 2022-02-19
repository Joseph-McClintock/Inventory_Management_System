package model;

public class InHouse extends Part{

    /**
     * A parts machine id
     * */
    private int machineId;

    /**Constructor for a new InHouse part
     *
     * @param id of part
     * @param name of part
     * @param price of part
     * @param stock of part
     * @param min of part
     * @param max of part
     * @param machineId of part
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**Setter for machineId
     * @param machineId of part
     * */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**Getter for machineId
     * @return machineId of part
     * */
    public int getMachineId(){
        return machineId;
    }
}
