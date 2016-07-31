import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Parking {
    int capacity;
    PriorityQueue<Slot> unOccupied;
    List<Slot> occupied = new ArrayList<Slot>();

    void createParkingLot(int size) {
        unOccupied = new PriorityQueue<Slot>(size);
        for (int i = 0; i < size; i++) {
            unOccupied.offer(new Slot(i + 1));
        }
        System.out.println("Created a parking lot with " + size + " slots");
    }

    void park(String reg, String color) {
        Slot sl = unOccupied.poll();
        if (sl == null) {
            System.out.println("Sorry, parking lot is full");
            return;
        }
        sl.setDetails(reg, color);
        occupied.add(sl);
        System.out.println("Allocated Slot Number" + sl.slot);
    }

    void leave(int slotNo) {
        if (occupied.size() <= 0) {
            System.out.println("this slot was initiallly empty");
            return;
        }
        boolean bFound = false;
        for (int i = 0; i < occupied.size(); i++) {
            if (occupied.get(i).slot == slotNo) {
                Slot sl = occupied.get(i);
                occupied.remove(i);
                sl.setDetails("", "");
                unOccupied.offer(sl);
                System.out.println("Slot number" + sl.getSlot() + " is free");
                bFound = true;
                break;
            }
        }
        if (!bFound)
            System.out.println("No Car is alloted this slot previoulsy");
    }

    void status() {
        System.out.println("Slot No \t Registration No \t Color");
        Collections.sort(occupied);
        for (Slot sl : occupied) {
            System.out.println(sl);
        }
    }

    void registration_numbers_for_cars_with_colour(String color) {
        boolean bFirst = true;
        for (Slot sl : occupied) {
            if (sl.getColor().equalsIgnoreCase(color)) {
                if (bFirst) {
                    bFirst = false;
                    System.out.print(sl.getRegNo());
                } else
                    System.out.print(", " + sl.getRegNo());
            }
        }
    }

    void slot_numbers_for_cars_with_colour(String color) {
        boolean bFirst = true;
        for (Slot sl : occupied) {
            if (sl.getColor().equalsIgnoreCase(color)) {
                if (bFirst) {
                    bFirst = false;
                    System.out.print(sl.getSlot());
                }
                else
                    System.out.print(", " + sl.getSlot());
            }
        }
    }

    void slot_number_for_registration_number(String regNo) {
        boolean bFound = false;
        for (Slot sl : occupied) {
            if (sl.getRegNo().equalsIgnoreCase(regNo)) {
                bFound = true;
                System.out.println(sl.getSlot());
                break;
            }
        }
        if (!bFound)
            System.out.println("Not found");
    }

    static void processLine(Parking p, String line) {
        String first = line.split(" ")[0];
        if (first.contains("create_parking_lot")) {
            p.createParkingLot(Integer.parseInt(line.split(" ")[1]));
        } else if (first.equals("park")) {
            p.park(line.split(" ")[1], line.split(" ")[2]);
        } else if (first.equals("leave")) {
            p.leave(Integer.parseInt(line.split(" ")[1]));
        } else if (first.equals("status")) {
            p.status();
        } else if (first.equals("registration_numbers_for_cars_with_colour")) {
            p.registration_numbers_for_cars_with_colour(line.split(" ")[1]);
        } else if (first.equals("slot_numbers_for_cars_with_colour")) {
            p.slot_numbers_for_cars_with_colour(line.split(" ")[1]);
        } else if (first.contains("slot_number_for_registration_number")) {
            p.slot_number_for_registration_number(line.split(" ")[1]);
        } else {
            System.out.println("unknown input");
        }
    }

    public static void main(String[] args) {
        Parking p = new Parking();
        if (args.length > 0) {
            String line;
            InputStream fis;
            try {
                fis = new FileInputStream("src/" + args[0]);
                InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                BufferedReader br = new BufferedReader(isr);
                while ((line = br.readLine()) != null) {
                    processLine(p, line);
                }
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()) {
                String line = sc.nextLine();
                processLine(p, line);
            }
        }
    }

}
