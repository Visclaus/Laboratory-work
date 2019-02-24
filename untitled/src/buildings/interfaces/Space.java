package buildings.interfaces;

public interface Space extends Cloneable, Comparable<Space> {

     double getSq();

     void setSq(double square);

     int getRn();

     void setRn(int rn);

     Object clone();
}