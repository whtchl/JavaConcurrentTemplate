package com.tchl.concurrency.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 使用Semaphore模拟数据库链接池的使用
 * 
 */
public class ConnectPool {
	private final List<Conn> pool = new ArrayList<Conn>(3);
	private final Semaphore semaphore = new Semaphore(3);

	/**
	 * 初始化分配3个链接
	 * 
	 * @author happen
	 *
	 */
	public ConnectPool() {
		pool.add(new Conn());
		pool.add(new Conn());
		pool.add(new Conn());

	}

	/**
	 * 请求分配连接
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public Conn getConn() throws InterruptedException  
	{
		semaphore.acquire();
		Conn c = null;
		synchronized(pool)  //假如semaphore中有2个许可，恰好两个线程同时进入了， 加入sync就可以防止将同一个conn 同时分配给这两个线程了
		{
			c = pool.remove(0);
		}
	    System.out.println(Thread.currentThread().getName()+" get a conn " + c);  
        return c ; 
	}
	
    /** 
     * 释放连接 
     * @param c 
     */  
    public void release(Conn c)  
    {  
        pool.add(c);  
        System.out.println(Thread.currentThread().getName()+" release a conn " + c);  
        semaphore.release();  
    }
	
	public static void main(String[] args) {
		final ConnectPool pool = new ConnectPool();
		
		 /** 
         * 第一个线程占用1个连接3秒 
         */
		new Thread(){
			public void run(){
				try{
					Conn c = pool.getConn();
					Thread.sleep(3000);
					pool.release(c);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}.start();
		
		/**
		 * 开启3个线程请求分配链接
		 */
		for(int i=0; i<3; i++){
			new Thread(){
				public void run(){
					try{
						Conn c = pool.getConn();
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
	
	private class Conn {
	}
}
