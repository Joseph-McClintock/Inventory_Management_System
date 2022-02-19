package model;

public abstract class Part {

    /**
     * A parts id
     * */
    private int partId;

    /**
     * A parts name
     * */
    private String partName;

    /**
     * A parts price
     * */
    private double partPrice;

    /**
     * A parts current stock
     * */
    private int partStock;

    /**
     * A parts minimum stock
     * */
    private int partMin;

    /**
     * A parts maximum stock
     * */
    private int partMax;

    /** Constructor of a new Part
     * @param partId a parts id
     * @param partName a parts name
     * @param partPrice a parts price
     * @param partStock a parts current stock
     * @param partMin a parts minimum stock
     * @param partMax a parts maximum stock
     * */
    public Part(int partId, String partName, double partPrice, int partStock, int partMin, int partMax){
        this.partId = partId;
        this.partName = partName;
        this.partPrice = partPrice;
        this.partStock = partStock;
        this.partMin = partMin;
        this.partMax = partMax;
    }

    //setters

    /**Setter for partId
     * @param partId of part
     * */
    public void setPartId(int partId){
        this.partId = partId;
    }

    /**Setter for partName
     * @param partName of part
     * */
    public void setPartName(String partName){
        this.partName = partName;
    }

    /**Setter for partPrice
     * @param partPrice of part
     * */
    public void setPartPrice(double partPrice){
        this.partPrice = partPrice;
    }

    /**Setter for partStock
     * @param partStock of part
     * */
    public void setPartStock(int partStock){
        this.partStock = partStock;
    }

    /**Setter for partMin
     * @param partMin of part
     * */
    public void setPartMin(int partMin){
        this.partMin = partMin;
    }

    /**Setter for partMax
     * @param partMax of part
     * */
    public void setPartMax(int partMax){
        this.partMax = partMax;
    }

    //getters

    /**Getter for partId
     * @return partId of part
     * */
    public int getPartId(){
        return partId;
    }

    /**Getter for partName
     * @return partName of part
     * */
    public String getPartName(){
        return partName;
    }

    /**Getter for partPrice
     * @return partPrice of part
     * */
    public double getPartPrice(){
        return partPrice;
    }

    /**Getter for partStock
     * @return partStock of part
     * */
    public int getPartStock(){
        return partStock;
    }

    /**Getter for partMin
     * @return partMin of part
     * */
    public int getPartMin(){
        return partMin;
    }

    /**Getter for partMax
     * @return partMax of part
     * */
    public int getPartMax(){
        return partMax;
    }

}
