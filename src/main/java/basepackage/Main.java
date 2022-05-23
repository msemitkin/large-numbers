package basepackage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Main {

    private final List<Integer> bitLengths;
    private final List<BigInteger> randomGeneratedValuesForEachBitLength;

    Main() {
        bitLengths = List.of(8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096);
        randomGeneratedValuesForEachBitLength = new ArrayList<>();
    }

    void run() {
        System.out.println("Printing number of combinations for each bit length");
        for (int bitLength : bitLengths) {
            System.out.println(bitLength + ": " + getNumberOfCombinations(bitLength));
        }

        System.out.println("Generating random values for each bit length");
        for (int bitLength : bitLengths) {
            BigInteger randomBigInteger = new BigInteger(bitLength, new Random());
            randomGeneratedValuesForEachBitLength.add(randomBigInteger);
        }

        System.out.println("Guessing");
        for (BigInteger randomlyGenerated : randomGeneratedValuesForEachBitLength) {
            bruteForce(randomlyGenerated);
        }

    }

    private BigInteger getNumberOfCombinations(int bitSequenceLength) {
        return BigInteger.TWO.pow(bitSequenceLength);
    }

    private void bruteForce(BigInteger actualNumber) {
        int bitLength = actualNumber.bitLength();
        BigInteger upperBound = getMaxForBitLength(bitLength);
        long start = System.currentTimeMillis();
        for (
            BigInteger currentNumber = BigInteger.ZERO;
            currentNumber.compareTo(upperBound) <= 0;
            currentNumber = currentNumber.add(BigInteger.ONE)
        ) {
            if (currentNumber.equals(actualNumber)) {
                long finish = System.currentTimeMillis();
                long duration = finish - start;
                System.out.println("Guessed number of bit length: " + bitLength + " in " + duration + " ms");
            }
        }
    }

    private BigInteger getMaxForBitLength(int bitLength) {
        return BigInteger.ZERO.setBit(bitLength).subtract(BigInteger.ONE);
    }

}
