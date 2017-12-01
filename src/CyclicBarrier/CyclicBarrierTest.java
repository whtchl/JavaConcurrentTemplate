package CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/** 
 * 安全的门禁系统 
 */
public class CyclicBarrierTest {
    /** 
     * 学生总数 
     */ 
	private final int STUDENT_COUNT = 10;
	
    /** 
     * 当人到齐，自动开门程序 
     */
    final CyclicBarrier barrier = new CyclicBarrier(STUDENT_COUNT, new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			System.out.println("人到齐了，开门....");  
		}
		
	});
    
    public void goHome() throws InterruptedException, BrokenBarrierException{
    	System.out.println(Thread.currentThread().getName()+"已刷卡，等待开门回家~");
    	barrier.await();
    	 System.out.println(Thread.currentThread().getName() + "放学回家~");  
    }
    
    public static void main(String[] args) throws InterruptedException,  
    BrokenBarrierException  {
		final CyclicBarrierTest instance = new CyclicBarrierTest();
	      /** 
         * 每个线程代表一个学生 
         */ 
		for(int i=0; i<instance.STUDENT_COUNT;i++){
			   new Thread("学生" + i +"  " )  
	            {  
	                public void run()  
	                {  
	  
	                    try  
	                    {  
	                        instance.goHome();  
	                    } catch (InterruptedException e)  
	                    {  
	                        e.printStackTrace();  
	                    } catch (BrokenBarrierException e)  
	                    {  
	                        e.printStackTrace();  
	                    }  
	  
	                };  
	  
	            }.start();  
		}
	}
}

