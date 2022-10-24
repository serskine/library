package soupthatisthick.util.math;

import soupthatisthick.util.logger.Logger;

import static soupthatisthick.util.Util.assertError;
import static soupthatisthick.util.Util.assertExpected;

public class Matrix {
    public final int nCols, nRows;
    public final double[][] cell;

    public void validateEqual(Matrix m) {
        assertExpected("Must not be null", true, (m != null));
        assertExpected("Must have same number of rows.", nRows, m.nRows);
        assertExpected("Must have the same number of columns.", nCols, m.nCols);

        Logger.info(this.description("this"));
        Logger.info(m.description("validateEqual(m)"));
        for(int row=0; row<nRows; row++) {
            for(int col=0; col<nCols; col++) {
                double expected = cell[row][col];
                double observed = m.cell[row][col];
                boolean isSame = (expected == observed);
                assertExpected("cell[" + row + "][" + col + "] should be the same for both matricies.", expected, observed);
                assertExpected("cell[" + row + "][" + col + "] should be the same for both matricies.", true, isSame);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o==null) {
            return false;
        } else if (o instanceof Matrix) {
            Matrix m = (Matrix) o;
            try {
                validateEqual(m);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return super.equals(o);
        }
    }

    public static Matrix identity(int numDimensions) {
        final Matrix m = new Matrix(numDimensions, numDimensions);
        for(int row=0; row<numDimensions; row++) {
            for(int col=0; col<numDimensions; col++) {
                m.cell[row][col] = (row==col) ? 1D : 0D;
            }
        }
        return m;
    }

    public Matrix(final int numRows, final int numCols) {
        assertExpected("nCols should be > 0", true, numCols>0);
        assertExpected("nRows should be > 0", true, numRows>0);

        this.nCols = numCols;
        this.nRows = numRows;
        cell = new double[nRows][nCols];
    }

    public Matrix(final double[][] data) {
        assertExpected("Data is not null!", true, (data != null));
        this.nRows = data.length;
        this.nCols = data[0].length;
        cell = data;

    }

    public Vector getRow(int rowIndex) {
        final Vector v = new Vector(nCols);
        for(int i=0; i<nCols; i++) {
            v.cell[i] = cell[rowIndex][i];
        }
        return v;
    }

    public Vector getCol(int colIndex) {
        final Vector v = new Vector(nRows);
        for(int i=0; i<nRows; i++) {
            v.cell[i] = cell[i][colIndex];
        }
        return v;
    }

    public void setCol(int col, Vector v) {
        assertExpected("Expected number of dimensions to equal the number of rows in the matrix.", nRows, v.numDimensions);
        for(int row=0; row<nRows; row++) {
            cell[row][col] = v.cell[row];
        }
    }

    public void setRow(int row, Vector v) {
        assertExpected("Expected number of dimensions to equals the number of columns in the matrix.", nCols, v.numDimensions);
        for(int col=0; col<nCols; col++) {
            cell[row][col] = v.cell[col];
        }
    }


    public Matrix copy() {
        final Matrix m = new Matrix(nRows, nCols);
        for(int row=0; row<nRows; row++) {
            for(int col=0; col<nCols; col++) {
                m.cell[row][col] = cell[row][col];
            }
        }
        return m;
    }

    public Matrix multiply(Matrix m) {
        assertExpected("Matrix should not be null.", true, m != null);
        assertExpected("Matrix should be square (m.nRows == nCols)", true, nRows == nCols);

        final Matrix r = new Matrix(nRows, m.nCols);
        for(int row = 0; row<r.nRows; row++) {
            for(int col=0; col<m.nCols; col++) {
                final Vector v1 = getRow(row);
                final Vector v2 = m.getCol(col);
                r.cell[row][col] = v1.dotProduct(v2);
            }
        }
        return r;
    }

    public Matrix add(Matrix m) {
        assertExpected("Matrix should not be null", true, m!=null);
        assertExpected("Number of rows should match", nRows, m.nRows);
        assertExpected("Number of columns should match", nCols, m.nCols);

        final Matrix r = new Matrix(nRows, nCols);

        for(int row=0; row<nRows; row++) {
            for(int col=0; col<nCols; col++) {
                r.cell[row][col] = cell[row][col] + m.cell[row][col];
            }
        }

        return r;
    }

    public String description(final String name) {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n***\n*** Matrix %s (%d rows x %d cols )\n***", name, nRows, nCols));
        for(int row=0; row<nRows; row++) {
            sb.append("\n");
            for(int col=0; col<nCols; col++) {
                sb.append(String.format("[%30f]", cell[row][col]));
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    void validateReducedRowEchalonForm() {
        validateEchalonForm();
        for(int row=0; row<nRows; row++) {
            int col = findFirstNonZeroColumn(row);
            if (col>=0) {
                assertExpected("Leading non-zero column should be 1", 1D, cell[row][col]);

                for (int i = 0; i < row; i++) {
                    assertExpected("Column value should be 0", 0D, cell[i][col]);
                }
            }
        }
    }

    public boolean isEchalonForm() {
        try {
            validateEchalonForm();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isReducedRowEchalonForm() {
        try {
            validateReducedRowEchalonForm();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void validateEchalonForm() {
        try {
            validateAllZeroRowsAreAtTheBottom();
            validateEachLeadingNonZeroColumnToRight();
        } catch (Exception e) {
            assertError("Matrix is not in Echalon Form", e);
        }
    }

    void validateEachLeadingNonZeroColumnToRight() {
        int prevNonZero = findFirstNonZeroColumn(0);
        for(int row=1; row<nRows; row++) {
            int col = findFirstNonZeroColumn(row);
            boolean isLeadingEntryValid = (col<0 || prevNonZero < col);
            assertExpected("Leading non-zero column of the row above is expecte to be < Leading non-zero column of this row.", true, isLeadingEntryValid);
        }
    }

    void validateAllZeroRowsAreAtTheBottom() {
        boolean foundNonZeroRow = false;
        for(int row=nRows-1; row>=0; row--) {
            boolean isRowAllZeros = isRowAllZeros(row);
            boolean isValidRow = !foundNonZeroRow || !isRowAllZeros;
            foundNonZeroRow = foundNonZeroRow || !isRowAllZeros;
            assertExpected("All non-zero rows should be above all zero rows", true, isValidRow);
        }
    }

    int findFirstNonZeroColumn(int row) {
        for(int col=0; col<nCols; col++) {
            if (cell[row][col] != 0D) {
                return col;
            }
        }
        return -1;
    }

    public boolean isRowAllZeros(int row) {
        return findFirstNonZeroColumn(row) < 0;
    }


    public void swapRows(int row1, int row2) {
        Vector v1 = getRow(row1);
        Vector v2 = getRow(row2);

        setRow(row1, v2);
        setRow(row2, v1);
    }

    public void swapCols(int col1, int col2) {
        Vector v1 = getCol(col1);
        Vector v2 = getCol(col2);

        setCol(col1, v2);
        setCol(col2, v1);
    }

    public Matrix getSubMatrix(int row, int col, int height, int width) {
        assertExpected("Row must be valid", true, (row>=0 && row<nRows));
        assertExpected("Column must be valid", true, (col>=0 && col<nCols));
        assertExpected("Height + row should be <= nRows", true, (row + height) <= nRows);
        assertExpected("Width + column should be <= nCols", true, (col + width) <= nCols);

        Matrix m = new Matrix(height, width);
        for(int r=0; r<height; r++) {
            for(int c=0; c<width; c++) {
                m.cell[r][c] = cell[row + r][col + c];
            }
        }
        return m;
    }

    public void setSubMatrix(int row, int col, Matrix m) {
        assertExpected("Row must be valid", true, (row>=0 && row<nRows));
        assertExpected("Column must be valid", true, (col>=0 && col<nCols));
        assertExpected("Matrix.nRows + row should be <= nRows", true, (row + m.nRows) <= nRows);
        assertExpected("Matrix.nCols + column should be <= nCols", true, (col + m.nCols) <= nCols);

        for(int r=0; r<m.nRows; r++) {
            for(int c=0; c<m.nCols; c++) {
                cell[row + r][col + c] = m.cell[r][c];
            }
        }
    }

    public void toRREF() {
        int lead = 0;
        int i = 0;
        int j = 0;
        int r = 0;

        for(r = 0; r < nRows; r++) {
            if(nCols <= lead) {
                break;
            }
            i = r;

            while(cell[i][lead] == 0) {
                i++;

                if(nRows == i) {
                    i = r;
                    lead++;

                    if(nCols == lead) {
                        return;
                    }
                }
            }
            swapRows(i, r);
            double val = cell[r][lead];

            for(j = 0; j < nCols; j++) {
                cell[r][j] /= val;
            }

            for(i = 0; i < nRows; i++) {
                if(i == r) {
                    continue;
                }
                val = cell[i][lead];

                for(j = 0; j < nCols; j++) {
                    cell[i][j] -= val * cell[r][j];
                }
            }
            lead++;
        }
    }

    public final boolean isSquare() {
        return nCols == nRows;
    }

    public double determinant() {
        assert isSquare();

        if (nCols==1) {
            return cell[0][0];
        }

        double det = 0D;
        for(int i=0; i<nCols; i++) {
            final Matrix m = getDeterminantMatrix(0, i);
            final double mDet = m.determinant();

            final double dCol = cell[0][i] * mDet * ((i%2==0) ? 1D : -1D);
            det += dCol;
        }
        return det;
    }

    public Matrix getDeterminantMatrix(int rowExcluded, int colExcluded) {
        if (rowExcluded < 0 || rowExcluded >= nRows) {
            throw new RuntimeException("We can't exclude row " + rowExcluded + ". There are only " + nRows + " rows.");
        }
        if (colExcluded < 0 || colExcluded >= nRows) {
            throw new RuntimeException("We can't exclude row " + rowExcluded + ". There are only " + nRows + " rows.");
        }
        final Matrix m = new Matrix(nRows-1, nCols-1);

        for(int r=0; r<nRows; r++) {
            if (r != rowExcluded) {
                for (int c = 0; c < nCols; c++) {
                    if (c != colExcluded) {
                        int r2 = (r>rowExcluded) ? r-1 : r;
                        int c2 = (c>colExcluded) ? c-1 : c;
                        m.cell[r2][c2] = this.cell[r][c];
                    }
                }
            }
        }

        return m;
    }
}
