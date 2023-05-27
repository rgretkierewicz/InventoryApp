package model;

/**
 * This class is used for creating, modifying, and accessing data for InHouse parts.
 */
public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return the machine id
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

}
