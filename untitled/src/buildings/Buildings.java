package buildings;

import buildings.dwelling.DwellingFactory;
import buildings.interfaces.Building;
import buildings.interfaces.BuildingFactory;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.*;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Scanner;

public class Buildings {

    public static BuildingFactory bf = new DwellingFactory();

    public static void setBf(BuildingFactory bf) {
        Buildings.bf = bf;
    }

    public Floor synchronizedFloor (Floor floor){
        return new SynchronizedFloor(floor);
    }

    public static Building createBuilding(Floor[] floors){
        return bf.createBuilding(floors);
    }

    public static Building createBuilding(int[] spacesCounts){
        return bf.createBuilding(spacesCounts);
    }

    public static void outputBuilding(Building building, OutputStream out) {
        int bCnt;
        int fCnt;
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            bCnt = building.getCnt();
            dataOutputStream.writeInt(bCnt);
            for (int i = 1; i < bCnt + 1; i++) {
                fCnt = building.getFloor(i).getCnt();
                dataOutputStream.writeInt(fCnt);
                for (int j = 1; j < fCnt + 1; j++) {
                    dataOutputStream.writeInt(building.getFloor(i).getSpace(j).getRn());
                    dataOutputStream.writeDouble(building.getFloor(i).getSpace(j).getSq());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Building inputBuilding(InputStream in) {
        Building building;
        Floor[] floors;
        try {
            DataInputStream dataInputStream = new DataInputStream(in);
            floors = new Floor[dataInputStream.readInt()];
            for (int i = 0; i < floors.length; i++) {
                Space[] tmp = new Space[dataInputStream.readInt()];
                for (int j = 0; j < tmp.length; j++) {
                    tmp[j] = bf.createSpace(dataInputStream.readInt(),dataInputStream.readDouble());
                }
                floors[i] = bf.createFloor(tmp);
            }
            building = bf.createBuilding(floors);
            return building;
        } catch (IOException e) {
            e.getMessage();
        }
        return null;
    }

    public static void writeBuilding(Building building, Writer out) {
        PrintWriter printWriter = new PrintWriter(out);
        int tmp = building.getCnt();
        printWriter.print(tmp + " ");
        for (int i = 1; i < tmp + 1; i++) {
            int tmp_1 = building.getFloor(i).getCnt();
            printWriter.print(tmp_1 + " ");
            for (int j = 1; j < tmp_1 + 1; j++) {
                printWriter.print(building.getFloor(i).getSpace(j).getRn() + " " + building.getFloor(i).getSpace(j).getSq() + " ");
            }
        }
    }

    public static Building readBuilding(Reader in) {
        Building building;
        Floor[] floors;
        StreamTokenizer st = new StreamTokenizer(in);
        try {
            st.nextToken();
            floors = new Floor[(int) st.nval];
            for (int i = 0; i < floors.length; i++) {
                st.nextToken();
                Space[] tmp = new Space[(int) st.nval];
                for (int j = 0; j < tmp.length; j++) {
                    st.nextToken();
                    tmp[j] = bf.createSpace((int)st.nval);
                    st.nextToken();
                    tmp[j].setSq(st.nval);
                }
                floors[i] = bf.createFloor(tmp);
            }
            building = bf.createBuilding(floors);
            return building;
        } catch (IOException e) {
            e.getMessage();
        }
        return null;
    }

    public static void serializeBuilding(Building building, OutputStream out) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(building);
        } catch (IOException e) {
            System.out.println("Some error occurred!");
        }
    }

    public static Building deserializeBuilding(InputStream in) {
        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            return (Building) ois.readObject();
        } catch (IOException e) {
            System.out.println("Some error occurred!");
        } catch (ClassNotFoundException e) {
            System.out.println("Wrong object type");
        }
        return null;
    }

    public static void writeBuildingFormat(Building building, Writer out) {
        Formatter formatter = new Formatter(out);
        int tmp = building.getCnt();
        formatter.format("%d ", tmp);
        for (int i = 1; i < tmp + 1; i++) {
            int tmp_1 = building.getFloor(i).getCnt();
            formatter.format("%d ", tmp_1);
            for (int j = 1; j < tmp_1 + 1; j++) {
                formatter.format("%d %4.1f ", building.getFloor(i).getSpace(j).getRn(), building.getFloor(i).getSpace(j).getSq());
            }
        }
    }

    public static Building readBuilding(Scanner scanner) {
        Building building;
        Floor[] floors;
        floors = new Floor[scanner.nextInt()];
        for (int i = 0; i < floors.length; i++) {
            Space[] tmp = new Space[scanner.nextInt()];
            for (int j = 0; j < tmp.length; j++) {
                tmp[j] = bf.createSpace(scanner.nextInt(),scanner.nextDouble());
            }
            floors[i] = bf.createFloor(tmp);
        }
        building = bf.createBuilding(floors);
        return building;
    }

    public static <T extends Comparable<T>> void sort_1 (T[] objects){
        for (int i = 0; i < objects.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < objects.length; j++) {
                if (objects[j].compareTo(objects[minIndex]) < 0)

                    minIndex = j;
            }
            T swapBuf = objects[i];
            objects[i] = objects[minIndex];
            objects[minIndex] = swapBuf;
        }
    }

    static <T> void sort_2 (T[] objects, Comparator<T> comparator) {
        for (int i = 0; i < objects.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < objects.length; j++) {
                if (comparator.compare(objects[j], objects[minIndex]) < 0)

                    minIndex = j;
            }
            T swapBuf = objects[i];
            objects[i] = objects[minIndex];
            objects[minIndex] = swapBuf;
        }
    }
}
