package com.ph.thread.masterSlave;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class Test {
    public static void main(String[] args) throws Exception {
        PrimeGeneratorService service = new PrimeGeneratorService();
        Set<BigInteger> result = service.generatePrime(Integer.valueOf(args[0]));
        System.out.println("Generated" + result.size() + " prime:");
        System.out.println(result);

    }

    static class Range{
        public final int lowerBound;
        public final int upperBound;
        public Range(int lowerBound, int upperBound) {
            if(upperBound < lowerBound){
                throw new IllegalArgumentException("upperBound should not be less than lowerBound");
            }
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        @Override
        public String toString() {
            return "Range{" +
                    "lowerBound=" + lowerBound +
                    ", upperBound=" + upperBound +
                    '}';
        }
    }

    /***
     * 子任务分解算法实现类
     */
    static class PrimeGeneratorService extends AbstractMaster<Range, Set<BigInteger>, Set<BigInteger>>{
        public PrimeGeneratorService() {
            this.init();
        }

        @Override
        protected TaskDivideStrategy<Range> newTaskDivideStrategy(final Object... params) {
            final int numOfSlaves = slaves.size();
            final int originalTaskScale = (Integer) params[0];
            final int subTaskScale = originalTaskScale/numOfSlaves;
            final int subTaskCount = (0 == (originalTaskScale % numOfSlaves)) ? numOfSlaves : numOfSlaves + 1;

            TaskDivideStrategy<Range> tds = new TaskDivideStrategy<Range>() {
                private int i = 1;
                @Override
                public Range nextChunk() {
                    int upperBound;
                    if(i < subTaskCount){
                        upperBound = i * subTaskScale;
                    }else if(i == subTaskCount){
                        upperBound = originalTaskScale;
                    }else {
                        return null;
                    }
                    int lowerBound = (i - 1) * subTaskScale + 1;
                    i++;
                    return new Range(lowerBound, upperBound);
                }
            };
            return tds;
        }

        /***
         * 创建slave线程
         * @return
         */
        @Override
        protected Set<? extends SlaveSpec<Range, Set<BigInteger>>> createSlaves() {
            Set<PrimeGenerator> testSet = new HashSet<PrimeGenerator>();
            for(int i = 0; i < Runtime.getRuntime().availableProcessors(); i++){
                testSet.add(new PrimeGenerator(new ArrayBlockingQueue<Runnable>(2)));
            }
            return testSet;
        }

        /***
         * 合并子任务处理结果
         * @param subResult 子任务处理结果
         * @return
         */
        @Override
        protected Set<BigInteger> combineResult(Iterator<Future<Set<BigInteger>>> subResult) {
            Set<BigInteger> result = new HashSet<BigInteger>();
            while (subResult.hasNext()){
                try {
                    result.addAll(subResult.next().get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if(SubTaskFailureException.class.isInstance(cause)){
                        RetryInfo retryInfo = ((SubTaskFailureException)cause).retryInfo;
                        Object subTask = retryInfo.subTask;
                        log.info("failed subTask : " + subTask);
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

        public Set<BigInteger> generatePrime(int upperBound) throws Exception{
            return this.service(upperBound);
        }
    }


    private static class PrimeGenerator extends WorkerThreadSlave<Range, Set<BigInteger>>{

        public PrimeGenerator(BlockingQueue<Runnable> taskQueue) {
            super(taskQueue);
        }

        @Override
        protected Set<BigInteger> doProcess(Range task) throws Exception {
            Set<BigInteger> result = new TreeSet<BigInteger>();
            BigInteger start = BigInteger.valueOf(task.lowerBound);
            BigInteger end = BigInteger.valueOf(task.upperBound);
            while(-1 == (start = start.nextProbablePrime()).compareTo(end)){
                result.add(start);
            }
            return result;
        }
    }
}
