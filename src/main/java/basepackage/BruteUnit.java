package basepackage;

import java.math.BigInteger;

class BruteUnit {

    private final BigInteger lowerBoundInclusive;
    private final BigInteger upperBoundExclusive;
    private final BigInteger guessedNumber;

    BruteUnit(
        BigInteger lowerBoundInclusive,
        BigInteger upperBoundExclusive,
        BigInteger guessedNumber
    ) {
        this.lowerBoundInclusive = lowerBoundInclusive;
        this.upperBoundExclusive = upperBoundExclusive;
        this.guessedNumber = guessedNumber;
    }

    boolean bruteForce() {
        for (
            BigInteger currentNumber = lowerBoundInclusive;
            currentNumber.compareTo(upperBoundExclusive) < 0;
            currentNumber = currentNumber.add(BigInteger.ONE)
        ) {
            if (currentNumber.equals(guessedNumber)) {
                return true;
            }
        }
        return false;
    }
}
