package com.ph.thread.producerComsumer;

public interface Channel<P> {

    /***
     * 从通道中取出产品
     * @return
     * @throws InterruptedException
     */
    P take() throws InterruptedException;

    /***
     * 往通道中放入产品
     * @param product
     * @throws InterruptedException
     */
    void put(P product) throws InterruptedException;
}
