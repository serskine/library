package soupthatisthick.util.math;

public class Point3D extends Vector {
    public Point3D(double x, double y, double z) {
        super(x, y, z);
    }

    public Point3D(Vector4D v) {
        this(v.cell[0], v.cell[1], v.cell[2]);
    }

    public double getX() {  return cell[0];    }
    public double getY() {  return cell[1];    }
    public double getZ() {  return cell[2];    }

    public void setX(double x)  {   cell[0] = x;    }
    public void setY(double y)  {   cell[1] = y;    }
    public void setZ(double z)  {   cell[2] = z;    }
}
