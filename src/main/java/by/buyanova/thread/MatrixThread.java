package by.buyanova.thread;

import by.buyanova.entity.Matrix;
import by.buyanova.exception.MatrixIndexOutOfBoundsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class MatrixThread extends Thread {

    private Matrix matrix = Matrix.INSTANCE;

    private int diagonalIndex;
    private int number;
    private int rowAndColumnElementsSum;

    private static Logger logger = LogManager.getLogger();

    public MatrixThread(int diagonalIndex, int number) {
        super(String.valueOf(number));
        this.diagonalIndex = diagonalIndex;
        this.number = number;
    }

    @Override
    public void run() {
        try {
            matrix.setNumberAt(diagonalIndex, diagonalIndex, number);

            Random random = new Random();
            if (random.nextBoolean()) {
                matrix.setNumberAt(diagonalIndex, generateRandomNumber(), number);
            } else {
                matrix.setNumberAt(generateRandomNumber(), diagonalIndex, number);
            }

            calculateRowAndColumnElementsSum();

        } catch (MatrixIndexOutOfBoundsException e) {
            logger.warn(e);
        }
    }

    public int getRowAndColumnElementsSum() {
        return rowAndColumnElementsSum;
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(matrix.getSize());
    }

    private void calculateRowAndColumnElementsSum() {
        int matrixSize = matrix.getSize();
        for (int i = 0; i < matrixSize; i++) {
            rowAndColumnElementsSum += matrix.getNumberAt(i, diagonalIndex);
            rowAndColumnElementsSum += matrix.getNumberAt(diagonalIndex, i);
        }
        rowAndColumnElementsSum -= matrix.getNumberAt(diagonalIndex, diagonalIndex);
    }
}
