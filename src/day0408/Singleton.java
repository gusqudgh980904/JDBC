package day0408;

/**
 * �������� JVM���� ��ü�� �ϳ��� �����ϰ�, ����� �� �ֵ��� ����� ����
 * @author user
 */

public class Singleton {
	private static Singleton single;	
	/**
	 * �������� ���������ڸ� private���� �����Ͽ� Ŭ���� �ܺο����� ���� ��üȭ �� �� ������ ����
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
