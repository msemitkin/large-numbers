package basepackage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static basepackage.Main.getNumberOfCombinations;

public class Bruter {

    private final BigInteger guessedNumber;
    private BigInteger availableProcessors;
    private ExecutorService executorService;

    public Bruter(BigInteger guessedNumber) {
        this.guessedNumber = guessedNumber;
    }

    public void bruteForce() throws ExecutionException, InterruptedException {
        int bitLength = guessedNumber.bitLength();
        availableProcessors = BigInteger.valueOf(Runtime.getRuntime().availableProcessors());
        executorService = Executors.newFixedThreadPool(availableProcessors.intValue());

        List<Callable<Boolean>> tasks = getTasks(bitLength);

        long start = System.currentTimeMillis();
        boolean succeededToGuessNumber = isSucceededToGuessNumber(tasks);
        long finish = System.currentTimeMillis();

        if (succeededToGuessNumber) {
            System.out.println("Found guessed number of bit length: " + bitLength + " in " + (finish - start) + " ms");
        } else {
            //should not get there
            System.out.println("Failed to find guessed number of bit length: " + bitLength);
        }
        executorService.shutdown();
    }

    private List<Callable<Boolean>> getTasks(int bitLength) {
        List<Callable<Boolean>> tasks = new ArrayList<>();

        BigInteger numberOfCombinations = getNumberOfCombinations(bitLength);
        BigInteger segmentSize = numberOfCombinations.divide(availableProcessors);
        BigInteger segmentLowerBound = BigInteger.ZERO;
        BigInteger segmentUpperBound = segmentSize.add(numberOfCombinations.mod(availableProcessors));
        for (int i = 0; i < availableProcessors.intValue(); i++) {
            BruteUnit bruteUnit = new BruteUnit(segmentLowerBound, segmentUpperBound, guessedNumber);
            tasks.add(bruteUnit::bruteForce);

            segmentLowerBound = segmentUpperBound;
            segmentUpperBound = segmentLowerBound.add(segmentSize);
        }
        return tasks;
    }

    private boolean isSucceededToGuessNumber(
        List<Callable<Boolean>> tasks
    ) throws InterruptedException, ExecutionException {
        List<Future<Boolean>> futures = executorService.invokeAll(tasks);

        while (!futures.isEmpty()) {
            for (Iterator<Future<Boolean>> iterator = futures.iterator(); iterator.hasNext(); ) {
                Future<Boolean> future = iterator.next();
                if (future.isDone()) {
                    Boolean foundAnswer = future.get();
                    if (foundAnswer) {
                        return true;
                    } else {
                        iterator.remove();
                    }
                }
            }
        }
        return false;
    }


}
