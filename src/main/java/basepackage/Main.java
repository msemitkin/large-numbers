package basepackage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

class Main {

    private final List<Integer> bitLengths;
    private final List<BigInteger> randomGeneratedValuesForEachBitLength;

    Main() {
        bitLengths = List.of(8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096);
        randomGeneratedValuesForEachBitLength = new ArrayList<>();
    }

    void run() throws ExecutionException, InterruptedException {
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
            new Bruter(randomlyGenerated).bruteForce();
        }

    }

    static BigInteger getNumberOfCombinations(int bitSequenceLength) {
        return BigInteger.TWO.pow(bitSequenceLength);
    }

}
