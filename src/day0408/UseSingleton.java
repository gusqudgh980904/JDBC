package day0408;

/**
 * Singleton Pattern���� ������� Ŭ������ ���
 * @author user
 */
public class UseSingleton {

	public static void main(String[] args) {
//		Singleton s=new Singleton();//���� ��üȭ �� �� ����
		
		Singleton s=Singleton.getInstance();
		Singleton s1=Singleton.getInstance();
		System.out.println(s+"/"+s1);
	}//main

}//class
