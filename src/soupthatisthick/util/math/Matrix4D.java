package soupthatisthick.util.math;

public class Matrix4D extends Matrix {
    public Matrix4D() {
        super(4, 4);
    }

    public static Matrix rowMatrix(Vector vector) {
        final Matrix m = new Matrix(1, vector.numDimensions);
        for(int i=0; i< vector.numDimensions; i++) {
            m.cell[0][i] = vector.cell[i];
        }
        return m;
    }

    public static Matrix columnMatrix(Vector vector) {
        final Matrix m = new Matrix(vector.numDimensions, 1);
        for(int i=0; i< vector.numDimensions; i++) {
            m.cell[i][0] = vector.cell[i];
        }
        return m;
    }

    public Point3D transform(Point3D point) {

        return new Point3D(transform(new Vector4D(point)));

    }

    public Vector4D transform(Vector4D v) {
        final Matrix colMatrix = columnMatrix(v);
        final Matrix r = multiply(colMatrix);
        return new Vector4D(r.cell[0][0], r.cell[1][0], r.cell[2][0], r.cell[3][0]);
    }

    public static Matrix4D identity() {
        final Matrix4D m = new Matrix4D();
        for(int row=0; row<m.nRows; row++) {
            for(int col=0; col<m.nCols; col++) {
                m.cell[row][col] = (row==col) ? 1D : 0D;
            }
        }
        return m;
    }

    public static Matrix4D translation(double tx, double ty, double tz) {
        final Matrix4D m = identity();
        m.cell[0][3] = tx;
        m.cell[1][3] = ty;
        m.cell[2][3] = tz;
        return m;
    }

    public static Matrix4D translation(Vector4D v) {
        return translation(v.getX(), v.getY(), v.getZ());
    }

    public static Matrix4D scale(double scaleX, double scaleY, double scaleZ) {
        final Matrix4D m = identity();
        m.cell[0][0] = scaleX;
        m.cell[1][1] = scaleY;
        m.cell[2][2] = scaleZ;
        return m;
    }

    public static Matrix4D scale(double scalar) {
        return scale(scalar, scalar, scalar);
    }

    public static Matrix4D rotateX(final Angle angle) {
        final Matrix4D m = identity();
        final double angleRadians = angle.radians();
        m.cell[1][1] = Math.cos(angleRadians);
        m.cell[1][2] = -Math.sin(angleRadians);
        m.cell[2][1] = Math.sin(angleRadians);
        m.cell[2][2] = Math.cos(angleRadians);
        return m;
    }

    public static Matrix4D rotateY(final Angle angle) {
        final Matrix4D m = identity();
        final double angleRadians = angle.radians();
        m.cell[0][0] = Math.cos(angleRadians);
        m.cell[0][2] = -Math.sin(angleRadians);
        m.cell[2][0] = Math.sin(angleRadians);
        m.cell[2][2] = Math.cos(angleRadians);
        return m;
    }

    public static Matrix4D rotateZ(final Angle angle) {
        final Matrix4D m = identity();
        final double angleRadians = angle.radians();
        m.cell[0][0] = Math.cos(angleRadians);
        m.cell[0][1] = -Math.sin(angleRadians);
        m.cell[1][0] = Math.sin(angleRadians);
        m.cell[1][1] = Math.cos(angleRadians);
        return m;
    }

    public static Matrix4D projectionBasic(final Angle angle, double near, double far) {
        assert(near >= 0D);
        assert(far > near);

        final double angleOfViewRadians = angle.radians();
        final double scale = 1D/Math.tan(angleOfViewRadians*0.5D);
        Matrix4D m = new Matrix4D();

        m.cell[0][0] = scale;
        m.cell[0][1] = 0D;
        m.cell[0][2] = 0D;
        m.cell[0][3] = 0D;

        m.cell[1][0] = 0D;
        m.cell[1][1] = scale;
        m.cell[1][2] = 0D;
        m.cell[1][3] = 0D;

        m.cell[2][0] = 0D;
        m.cell[2][1] = 0D;
        m.cell[2][2] = -far / (far - near);
        m.cell[2][3] = -1D;

        m.cell[3][0] = 0D;
        m.cell[3][1] = 0D;
        m.cell[3][2] = -((far * near) / (far - near));
        m.cell[3][3] = 0D;

        return m;
    }

    public static Matrix4D projectionOpenGL(final Angle angle, double left, double right, double top, double bottom, double near, double far) {
        assert(near >= 0D);
        assert(far > near);
        assert(right > left);
        assert(top > bottom);

        final double angleOfViewRadians = angle.radians();

        final double scale = 1D/Math.tan(angleOfViewRadians*0.5D);
        Matrix4D m = identity();

        m.cell[0][0] = 2D*near / (right - left);
        m.cell[0][2] = (right + left) / (right-left);
        m.cell[1][1] = 2D*near / (top - bottom);
        m.cell[1][2] = (top + bottom) / (top - bottom);
        m.cell[2][2] = -((far + near) / (far - near));
        m.cell[3][2] = -(2D*far*near / (far - near));
        m.cell[2][3] = -1D;
        m.cell[3][3] = 0D;

        return m;
    }

    public static Matrix4D toCameraFPS(Point3D eyeLocation, final Angle pitch, final Angle yaw) {
        final double pitchRadians = pitch.radians();
        final double yawRadians = yaw.radians();

        double cosPitch = Math.cos(pitchRadians);
        double sinPitch = Math.sin(pitchRadians);
        double cosYaw = Math.cos(yawRadians);
        double sinYaw = Math.sin(yawRadians);

        Point3D xaxis = new Point3D( cosYaw, 0, -sinYaw );
        Point3D yaxis = new Point3D( sinYaw * sinPitch, cosPitch, cosYaw * sinPitch );
        Point3D zaxis = new Point3D( sinYaw * cosPitch, -sinPitch, cosPitch * cosYaw );

        // Create a 4x4 view matrix from the right, up, forward and eye position vectors
        Matrix4D m = new Matrix4D();
        m.cell[0][0] = xaxis.getX();
        m.cell[1][0] = xaxis.getY();
        m.cell[2][0] = xaxis.getZ();
        m.cell[3][0] = -xaxis.dotProduct(eyeLocation);

        m.cell[0][1] = yaxis.getX();
        m.cell[1][1] = yaxis.getY();
        m.cell[2][1] = yaxis.getZ();
        m.cell[3][1] = -yaxis.dotProduct(eyeLocation);

        m.cell[0][2] = zaxis.getX();
        m.cell[1][2] = zaxis.getY();
        m.cell[2][2] = zaxis.getZ();
        m.cell[3][2] = -zaxis.dotProduct(eyeLocation);

        m.cell[0][3] = 0D;
        m.cell[1][3] = 0D;
        m.cell[2][3] = 0D;
        m.cell[3][3] = 1D;

        return m;

    }

    public Matrix4D inverse() {
        Matrix m = new Matrix(nRows, nCols*2);
        m.setSubMatrix(0, 0, this);
        m.setSubMatrix(0, nCols, Matrix4D.identity());
        m.toRREF();
        Matrix i = m.getSubMatrix(0, nCols, nRows, nCols);
        Matrix4D r = new Matrix4D();
        r.setSubMatrix(0, 0, i);
        return r;
    }
}
