package day0408;

/**
 * 실행중인 JVM에서 객체를 하나로 유지하고, 사용할 수 있도록 만드는 패턴
 * @author user
 */

public class Singleton {
	private static Singleton single;	
	/**
	 * 생성자의 접근지정자를 private으로 설정하여 클래스 외부에서는 직접 객체화 할 수 없도록 만듬
	 */
	private Singleton(){
		
	}//Singleton
	
	public static Singleton getInstance() {
		
		if(single==null) {
		single=new Singleton();
			
		}//if
		
		return single;
		
	}//getInstance

}//class
