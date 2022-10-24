package soupthatisthick.util.math;

public class Vector4D extends Vector {


    public Vector4D() {
        super(4);
    }

    public Vector4D(Point3D point) {
        super(point.getX(), point.getY(), point.getY(), 1D);
    }

    public Vector4D(double x, double y, double z) {
        this(x, y, z, 1D);
    }
    public Vector4D(double x, double y, double z, double w) {
        super(x, y, z, w);
    }

    public double getX()    {   return cell[0];     }
    public double getY()    {   return cell[1];     }
    public double getZ()    {   return cell[2];     }
    public double getW()    {   return cell[3];     }

    public void setX(double x)  {   cell[0] = x;    }
    public void setY(double y)  {   cell[1] = y;    }
    public void setZ(double z)  {   cell[2] = z;    }
    public void setW(double w)  {   cell[3] = w;    }

    public Vector4D forward(Vector4D up) {
        return crossProduct(up).normalized();
    }


    /**
     * This will return a vector of the same magnitude in the opposite direction
     * @return
     */
    public Vector4D reverse() {
        return new Vector4D(-getX(), -getY(), -getZ(), getW());
    }

    /**
     * This will find a vector in 3D space that is orthagonal (90 deg to the plane formed by this vector and vector v
     * using the right hand rule.
     * @param v
     * @return
     */
    public Vector4D crossProduct(Vector4D v) {
        Vector4D r = new Vector4D(
            getY()*v.getZ() - getZ()*v.getY(),
            getZ()*v.getX() - getX()*v.getZ(),
            getX()*v.getY() - getY()*v.getX(),
            1D
        );
        return r;
    }

    public Vector4D normalized() {
        return new Vector4D(getX() / getW(), getY()/getW(), getZ()/getW());
    }

}
