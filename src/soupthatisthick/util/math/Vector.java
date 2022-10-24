package soupthatisthick.util.math;

import soupthatisthick.util.logger.Logger;

public class Vector {
    public final int numDimensions;
    public final double[] cell;

    public Vector(final int numDimensions) {
        assert(numDimensions > 0);
        this.numDimensions = numDimensions;
        this.cell = new double[numDimensions];
    }
    public Vector(double... elem) {
        assert(elem != null);
        assert(elem.length > 0);
        this.numDimensions = elem.length;
        cell = elem;
    }

    public double dotProduct(Vector v2) {
        assert(numDimensions == v2.numDimensions);
        double product = 0D;
        for(int i=0; i<numDimensions; i++) {
            product += cell[i] * v2.cell[i];
        }
        return product;
    }

    public double length() {
        return dotProduct(this);
    }

    public Vector copy() {
        final Vector vNew = new Vector(numDimensions);
        for(int i=0; i<numDimensions; i++) {
            vNew.cell[i] = cell[i];
        }
        return vNew;
    }

    public Vector add(Vector... v) {
        final Vector vNew = copy();
        assert(v != null);
        for(int i=0; i<v.length; i++) {
            assert(numDimensions == v[i].numDimensions);
            for(int dimension=0; dimension<numDimensions; dimension++) {
                vNew.cell[dimension] += v[i].cell[dimension];
            }
        }
        return vNew;
    }

    public Vector scale(double value) {
        Vector v = new Vector(numDimensions);
        for(int i=0; i<numDimensions; i++) {
            v.cell[i] = cell[i] * value;
        }
        return v;
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder();
        for(int i=0; i<numDimensions; i++) {
            sb.append(String.format("[%5.2f]", cell[i]));
        }
        return sb.toString();
    }


    public boolean equals(Object o) {
        if (o==null) {
            return false;
        }
        if (!(o instanceof Vector)) {
            return super.equals(o);
        }

        Vector v2 = (Vector) o;
        if (numDimensions != v2.numDimensions) {
            return false;
        }
        for(int i=0; i<numDimensions; i++) {
            if (cell[i] != v2.cell[i]) {
                return false;
            }
        }
        return true;
    }

    Angle angleBetween(final Vector v) {
        assert(numDimensions == v.numDimensions);
        final double dotProduct = this.dotProduct(v);
        final double m1 = length();
        final double m2 = v.length();
        final double theta = Math.acos(dotProduct / (m1 * m2));
        return new Angle(true, theta);
    }

    Vector crossProduct(final Vector... others) {

        final int expectedOthersLength = this.numDimensions - 2;
        if (others.length != expectedOthersLength) {
            throw new RuntimeException("Expected " + expectedOthersLength + " other vectors to be parallel to");
        }

        for(Vector v : others) {
            assert(numDimensions == v.numDimensions);
        }

        final Matrix m = new Matrix(numDimensions, numDimensions);

        Logger.info(m.description("m: matrix formed from array of vectors (others)"));
        for(int i=0; i<numDimensions; i++) {
            m.cell[0][i] = 1D;
            m.cell[1][i] = this.cell[i];
        }
        for(int r=0; r<others.length; r++) {
            m.setRow(r+2, others[r]);
        }

        Logger.info(m.description("Determinant solving matrix"));

        final Vector result = new Vector(numDimensions);
        for(int i=0; i<numDimensions; i++) {
            final Matrix subMatrix = m.getDeterminantMatrix(0, i);
            final double det = subMatrix.determinant();
            final int sign = (i%2==0) ? 1 : -1;

            result.cell[i] = m.cell[0][i] * det * sign ;
        }
        return result;
    }
}
