package soupthatisthick.util.math;

import org.junit.jupiter.api.Test;
import soupthatisthick.util.logger.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {

    @Test
    public void multiply() {
        Matrix4D m = Matrix4D.identity();
        Vector4D v = new Vector4D(1000, 0, 0, 1D);
        Matrix colM = Matrix4D.columnMatrix(v);

        Matrix r = m.multiply(colM);

        Logger.info(m.description("m"));
        Logger.info(colM.description("colM"));
        Logger.info(r.description("r"));
    }

    @Test
    public void projectionBasic() {
        Angle aov = new Angle(false, 50);
        double near = 0D;
        double far = 100D;


        Matrix4D m = Matrix4D.projectionBasic(aov, near, far);
        Logger.info(m.description("Projection"));

    }

    @Test
    public void echalonForm_and_reducedRowEchalonForm() {
        Matrix4D identity = Matrix4D.identity();

        assertTrue(identity.isEchalonForm());
        assertTrue(identity.isReducedRowEchalonForm());

        Logger.info(identity.description("identity"));

        Matrix4D m = Matrix4D.identity();
        m.cell[0][1] = 1D;

        assertTrue(m.isEchalonForm());
        assertFalse(m.isReducedRowEchalonForm());

        Matrix4D m2 = Matrix4D.identity();
        m2.cell[1][0] = 1D;

        Logger.info(m2.description("m2"));

        assertFalse(m2.isEchalonForm());
        assertFalse(m2.isReducedRowEchalonForm());

        Matrix4D m3 = Matrix4D.identity();
        m3.cell[0][0] = 0D;

        Logger.info(m3.description("m3"));

        assertFalse(m3.isEchalonForm());
        assertFalse(m3.isReducedRowEchalonForm());

        Matrix4D m4 = Matrix4D.identity();
        m4.cell[3][3] = 0D;

        Logger.info(m4.description("m4"));

        assertTrue(m4.isEchalonForm());
        assertTrue(m4.isReducedRowEchalonForm());


        Matrix4D m5 = Matrix4D.identity();
        m5.cell[2][2] = 2D;

        Logger.info(m5.description("m5"));

        assertTrue(m5.isEchalonForm());
        assertFalse(m5.isReducedRowEchalonForm());

    }

    @Test
    public void toRREF() {
        Matrix m = new Matrix(
            new double[][] {
                    {1D, 2D, 3D, -1D},
                    {4D, 5D, 6D, 3D},
                    {7D, 8D, 9D, 5D}
            }
        );

        Logger.info(m.description("Before"));

        m.toRREF();

        Logger.info(m.description("After"));
    }

    @Test
    public void equals() {
        Matrix4D i1 = Matrix4D.identity();
        Matrix4D i2 = Matrix4D.identity();
        Matrix4D m = Matrix4D.identity();
        m.cell[0][1] = 2D;

        i1.validateEqual(i1);
        i2.validateEqual(i2);
        m.validateEqual(m);

        i1.validateEqual(i2);
        i2.validateEqual(i1);

        assertTrue(i1.equals(i1));
        assertTrue(i2.equals(i2));
        assertTrue(m.equals(m));

        assertTrue(i1.equals(i2));
        assertTrue(i2.equals(i1));

        assertFalse(i1.equals(m));
        assertFalse(i2.equals(m));

        assertFalse(i1.equals(null));
        assertFalse(i1.equals("STUFF"));
    }

    @Test
    public void identity() {
        final Matrix4D identity = Matrix4D.identity();
        assertEquals(1D, identity.cell[0][0]);
        assertEquals(1D, identity.cell[1][1]);
        assertEquals(1D, identity.cell[2][2]);
        assertEquals(1D, identity.cell[3][3]);

        assertEquals(0D, identity.cell[0][1]);
        assertEquals(0D, identity.cell[0][2]);
        assertEquals(0D, identity.cell[0][3]);
        assertEquals(0D, identity.cell[1][0]);
        assertEquals(0D, identity.cell[1][2]);
        assertEquals(0D, identity.cell[1][3]);
        assertEquals(0D, identity.cell[2][0]);
        assertEquals(0D, identity.cell[2][1]);
        assertEquals(0D, identity.cell[2][3]);
        assertEquals(0D, identity.cell[3][0]);
        assertEquals(0D, identity.cell[3][1]);
        assertEquals(0D, identity.cell[3][2]);

        final Matrix identity2 = Matrix.identity(4);
        assertEquals(1D, identity2.cell[0][0]);
        assertEquals(1D, identity2.cell[1][1]);
        assertEquals(1D, identity2.cell[2][2]);
        assertEquals(1D, identity2.cell[3][3]);

        assertEquals(0D, identity2.cell[0][1]);
        assertEquals(0D, identity2.cell[0][2]);
        assertEquals(0D, identity2.cell[0][3]);
        assertEquals(0D, identity2.cell[1][0]);
        assertEquals(0D, identity2.cell[1][2]);
        assertEquals(0D, identity2.cell[1][3]);
        assertEquals(0D, identity2.cell[2][0]);
        assertEquals(0D, identity2.cell[2][1]);
        assertEquals(0D, identity2.cell[2][3]);
        assertEquals(0D, identity2.cell[3][0]);
        assertEquals(0D, identity2.cell[3][1]);
        assertEquals(0D, identity2.cell[3][2]);


    }

    @Test
    public void setColAndRow() {
        final Matrix4D m = Matrix4D.identity();
        m.setCol(0, new Vector(2,3,4,5));
        assertEquals(2D, m.cell[0][0]);
        assertEquals(3D, m.cell[1][0]);
        assertEquals(4D, m.cell[2][0]);
        assertEquals(5D, m.cell[3][0]);

        m.setRow(3, new Vector(11, 12, 13, 14));
        assertEquals(11D, m.cell[3][0]);
        assertEquals(12D, m.cell[3][1]);
        assertEquals(13D, m.cell[3][2]);
        assertEquals(14D, m.cell[3][3]);
    }

    @Test
    public void copy() {
        final Matrix m = Matrix4D.identity();
        m.setCol(0, new Vector(2,3,4,5));
        assertEquals(2D, m.cell[0][0]);
        assertEquals(3D, m.cell[1][0]);
        assertEquals(4D, m.cell[2][0]);
        assertEquals(5D, m.cell[3][0]);

        final Matrix mCopy = m.copy();
        for(int row=0; row<m.nRows; row++) {
            for(int col=0; col<m.nCols; col++) {
                assertEquals(m.cell[row][col], mCopy.cell[row][col]);
            }
        }

    }

    @Test
    public void add() {
        final Matrix m1 = Matrix4D.identity();
        final Matrix m2 = Matrix4D.identity();

        m1.cell[0][0] = 2D;
        m1.cell[1][1] = 3D;
        m1.cell[2][2] = 4D;
        m1.cell[3][3] = 5D;

        final Matrix m3 = m1.add(m2);
        final Matrix m4 = m2.add(m1);

        assertEquals(3D, m3.cell[0][0]);
        assertEquals(0D, m3.cell[0][1]);
        assertEquals(0D, m3.cell[0][2]);
        assertEquals(0D, m3.cell[0][3]);
        assertEquals(0D, m3.cell[1][0]);
        assertEquals(4D, m3.cell[1][1]);
        assertEquals(0D, m3.cell[1][2]);
        assertEquals(0D, m3.cell[1][3]);
        assertEquals(0D, m3.cell[2][0]);
        assertEquals(0D, m3.cell[2][1]);
        assertEquals(5D, m3.cell[2][2]);
        assertEquals(0D, m3.cell[2][3]);
        assertEquals(0D, m3.cell[3][0]);
        assertEquals(0D, m3.cell[3][1]);
        assertEquals(0D, m3.cell[3][2]);
        assertEquals(6D, m3.cell[3][3]);

        for(int row=0; row<m3.nRows; row++) {
            for(int col=0; col<m3.nCols; col++) {
                assertEquals(m3.cell[row][col], m4.cell[row][col]);
            }
        }

    }

    @Test
    public void inverse() {

        Matrix4D i = Matrix4D.identity();
        Logger.info(i.description("i"));


        Matrix4D m = Matrix4D.identity();
        m.cell[0][1] = 1D;
        m.cell[0][2] = 2D;
        m.cell[0][3] = 3D;
        m.cell[1][0] = 4D;
        m.cell[2][1] = 5D;

        Logger.info(m.description("m"));

        Matrix4D mi = m.inverse();
        Logger.info(mi.description("mi"));

        Matrix r = m.multiply(mi);
        Logger.info(r.description("r"));

        assertEquals(r.description(""), i.description(""));


    }

    @Test
    public void getDeterminantMatrix() {
        final Matrix m = new Matrix(5, 5);
        for (int r = 0; r < m.nRows; r++) {
            for (int c = 0; c < m.nCols; c++) {
                m.cell[r][c] = 10 * r + c;
            }
        }

        Logger.info(m.description("m"));

        for (int r = 0; r < m.nRows; r++) {
            for (int c = 0; c < m.nCols; c++) {
                final Matrix dm = m.getDeterminantMatrix(r, c);
                final String matrixName = "dm[" + r + ", " + c + "]";

                Logger.info(dm.description(matrixName));

                final double det = dm.determinant();
                Logger.info("Determinant of " + matrixName + " = " + det);
            }
        }
    }

    @Test
    public void determinant1D() {
        final Matrix m1 = new Matrix(1,1);
        m1.cell[0][0] = 42;

        assertEquals(42, m1.determinant());
    }

    @Test
    public void determinant2D() {
        final Matrix m1 = new Matrix(2,2);
        m1.cell[0][0] = 3;
        m1.cell[0][1] = 8;
        m1.cell[1][0] = 4;
        m1.cell[1][1] = 6;

        assertEquals(-14D, m1.determinant());
    }

    @Test
    public void determinant3D() {
        final Matrix m1 = new Matrix(3,3);
        m1.cell[0][0] = 6;
        m1.cell[0][1] = 1;
        m1.cell[0][2] = 1;
        m1.cell[1][0] = 4;
        m1.cell[1][1] = -2;
        m1.cell[1][2] = 5;
        m1.cell[2][0] = 2;
        m1.cell[2][1] = 8;
        m1.cell[2][2] = 7;

        assertEquals(-306D, m1.determinant());
    }

    @Test
    public void determinant4D() {
        final Matrix m1 = new Matrix(4,4);
        m1.cell[0][0] = 1D;
        m1.cell[0][1] = 0D;
        m1.cell[0][2] = 4D;
        m1.cell[0][3] = -6D;
        m1.cell[1][0] = 2D;
        m1.cell[1][1] = 5D;
        m1.cell[1][2] = 0D;
        m1.cell[1][3] = 3D;
        m1.cell[2][0] = -1D;
        m1.cell[2][1] = 2D;
        m1.cell[2][2] = 3D;
        m1.cell[2][3] = 5D;
        m1.cell[3][0] = 2D;
        m1.cell[3][1] = 1D;
        m1.cell[3][2] = -2D;
        m1.cell[3][3] = 3D;

        assertEquals(318D, m1.determinant());

    }

    @Test
    public void crossProduct() {
        Vector a = new Vector(3, 5, 7);
        Vector b = new Vector(2, -6, 4);

        Vector c = a.crossProduct(b);

        Logger.info("a: " + a.toString());
        Logger.info("b: " + b.toString());
        Logger.info("-------------------------");
        Logger.info("c: " + c.toString());

        assertEquals(62D, c.cell[0]);
        assertEquals(2D, c.cell[1]);
        assertEquals(-28D, c.cell[2]);
    }
}
