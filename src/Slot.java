public class Slot implements Comparable<Slot> {
    private String regNo;
    private String color;
    int slot;

    public String getRegNo() {
        return regNo;
    }

    public void setDetails(String regNo, String color) {
        this.regNo = regNo;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public Slot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    public int compareTo(Slot o) {
        if (this.getSlot() == o.getSlot())
            return 0;
        if (this.getSlot() > o.getSlot())
            return 1;
        else
            return -1;
    }

    @Override
    public String toString() {
        return this.getSlot() + "\t" + this.getRegNo() + "\t" + this.getColor();
    }

}
