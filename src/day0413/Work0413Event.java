package day0413;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class Work0413Event extends WindowAdapter implements ActionListener, MouseListener {

	private RunCrudDAO rcDAO;
	private boolean selectFlag;
	
	public Work0413Event(RunCrudDAO rcDAO) {
		this.rcDAO=rcDAO;
	}//Work0413Event
	
	@Override
	public void windowClosing(WindowEvent we) {
		rcDAO.dispose();
	}//windowClosing

	@Override
	public void mouseClicked(MouseEvent me) {
		//마우스 버튼이 클릭 되었을 때 동작하는 method
		switch(me.getButton()) {
		case MouseEvent.BUTTON1:
			//선택된 아이템을 받아와서  JTextField에 추가한다
			String csvData=rcDAO.getJl().getSelectedValue();
			String[] arrData=csvData.split(",");
			
			rcDAO.getJtfNum().setText(arrData[0]);
			rcDAO.getJtfName().setText(arrData[1]);
			rcDAO.getJtfAge().setText(arrData[2]);
			rcDAO.getJtfAddr().setText(arrData[3]);
			//JList의 item이 선택이 되었는지
			selectFlag=true;
		}//if
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		//추가
		if(ae.getSource()==rcDAO.getJbtnInsert()) {
			addWork();
		}//if
		
		//닫기
		if(ae.getSource()==rcDAO.getJbtnClose()) {
			rcDAO.dispose();
		}//if
		
		//변경
		if(ae.getSource()==rcDAO.getJbtnUpdate()) {
			if(!selectFlag) {
				JOptionPane.showMessageDialog(rcDAO,"변경하시려는 아이템을 먼저 선택해주세요");
				return;
			}//if
			modifyWork();
		}//if
		
		//삭제
		if(ae.getSource()==rcDAO.getJbtnDelete()) {	if(!selectFlag) {
			JOptionPane.showMessageDialog(rcDAO,"삭제하시려는 아이템을 먼저 선택해주세요");
			return;
		}//if
		removeWork();
		}//if
		
		//버튼을 눌러서 작업을 수행하고 난 이후에는 JList의 item선택상태를 해제한다
		selectFlag=false;
		
	}//actionPerformed

	public void addWork() {
		//JTextField의 값을 받아와서 DB에 추가
		String name=rcDAO.getJtfName().getText().trim();
		String age=rcDAO.getJtfAge().getText().trim();
		String addr=rcDAO.getJtfAddr().getText().trim();
		
		if("".equals(name)) {
			JOptionPane.showMessageDialog(rcDAO,"이름은 필수 입력");
			rcDAO.getJtfName().requestFocus();
			return;
		}//if
		
		if("".equals(age)) {
			JOptionPane.showMessageDialog(rcDAO,"나이는 필수 입력");
			rcDAO.getJtfAge().requestFocus();
			return;
		}//if
		
		int intAge=0;
		try {
		intAge=Integer.parseInt(age);
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(rcDAO,"나이는 숫자형식으로 입력");
			rcDAO.getJtfAge().setText("");
			rcDAO.getJtfAge().requestFocus();
			return;
		}//catch
		
		if("".equals(addr)) {
			JOptionPane.showMessageDialog(rcDAO,"주소는 필수 입력");
			rcDAO.getJtfAddr().requestFocus();
			return;
		}//if
		
		WorkAddVO waVO=new WorkAddVO(name,intAge,addr);
		Work0413DAO wDAO=Work0413DAO.getInstance();
		try {
			wDAO.insertWork(waVO);//데이터를 DBMS Table에 추가하고,
			setJlistWork();//입력된 상태의 데이터를 갱신하여 JList에 출력하고
			JOptionPane.showMessageDialog(rcDAO,"입력하신 정보가 추가되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(rcDAO,"예외발생");
		}//catch
		
	}//addWork
	
	
	/**
	 * WORK테이블의 모든 레코드를 조회하여 JList에 설정하는 일
	 */
	public void setJlistWork() {
		Work0413DAO wDAO=Work0413DAO.getInstance();
		try {
			List<WorkAllVO> list=wDAO.selectAllWork();
			
			//디자인 클래스에서 뷰(JList)의 데이터를 관리하는 객체(DefaultListModel)를 얻는다
			DefaultListModel<String> dlm=rcDAO.getDflm();
			//기존의 데이터를 초기화
			dlm.clear();
			
			//조회된 결과를 Model객체에 설정한다
			StringBuilder viewData=new StringBuilder();
			for(WorkAllVO waVO:list) {
				viewData.append(waVO.getNum()).append(",")
				.append(waVO.getName()).append(",")
				.append(waVO.getAge()).append(",")
				.append(waVO.getAddr());
				
				dlm.addElement(viewData.toString());
	
				viewData.delete(0,viewData.length());
			}//for
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(rcDAO,"서비스가 원할하지 못합니다");
			e.printStackTrace();
		}//catch
		
	}//setJlist
	
	
	private void removeWork() {
		//JTextField의 값을 받아와서 DB에 삭제
		String num=rcDAO.getJtfNum().getText().trim();
		
		switch (JOptionPane.showConfirmDialog(rcDAO,num+"번 데이터를 삭제하시겠습니까?")) {
		case JOptionPane.OK_OPTION:
			Work0413DAO wDAO=Work0413DAO.getInstance();
			
			try {
				int cnt=wDAO.deleteWork(Integer.parseInt(num));
				String outputMsg=num+"번 데이터가 삭제되지 않았습니다.번호를 확인하세요";
				if(cnt==1) {
					outputMsg=num+"번 데이터가 삭제되었습니다";
					setJlistWork();
				}//if
				
				JOptionPane.showMessageDialog(rcDAO,outputMsg);
			} catch (SQLException se) {
				se.printStackTrace();
				JOptionPane.showMessageDialog(rcDAO,"ㅈㅅㅈㅅ");
			}//catch
		
		}//switch
		
	}//removeWork
	
	
	private void modifyWork() {
		//JTextField의 값을 받아와서 DB에 변경
		String num=rcDAO.getJtfNum().getText().trim();
		String name=rcDAO.getJtfName().getText().trim();
		String age=rcDAO.getJtfAge().getText().trim();
		String addr=rcDAO.getJtfAddr().getText().trim();

		
		if("".equals(name)) {
			JOptionPane.showMessageDialog(rcDAO,"이름은 필수 입력");
			rcDAO.getJtfName().requestFocus();
			return;
		}//if
		
		if("".equals(age)) {
			JOptionPane.showMessageDialog(rcDAO,"나이는 필수 입력");
			rcDAO.getJtfAge().requestFocus();
			return;
		}//if
		
		int intAge=0;
		try {
		intAge=Integer.parseInt(age);
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(rcDAO,"나이는 숫자형식으로 입력");
			rcDAO.getJtfAge().setText("");
			rcDAO.getJtfAge().requestFocus();
			return;
		}//catch
		
		if("".equals(addr)) {
			JOptionPane.showMessageDialog(rcDAO,"주소는 필수 입력");
			rcDAO.getJtfAddr().requestFocus();
			return;
		}//if
		
		switch (JOptionPane.showConfirmDialog(rcDAO,name+"의 정보를 변경하시겠습니까?")) {
		case JOptionPane.OK_OPTION:
			
			//유효성 검증이 종료되었다면 분할되어 있는 값을 VO에 넣는다
			WorkAllVO waVO=new WorkAllVO(Integer.parseInt(num),name,intAge,addr);
			
			//DB작업을 위한 DAO클래스의 객체 얻기
			Work0413DAO wDAO=Work0413DAO.getInstance();

			try {
				int cnt=wDAO.updateWork(waVO);
				String outputMsg=waVO.getName()+"님의 정보가 변경되지 않았습니다";
				if(cnt==1) {
					outputMsg=waVO.getName()+"님의 정보가 변경되었습니다";
					setJlistWork();//변경내용을 사용자에게 제공한다
				}//if
				
				JOptionPane.showMessageDialog(rcDAO,outputMsg);
			} catch (SQLException se) {
				se.printStackTrace();
				JOptionPane.showMessageDialog(rcDAO,"변경작업 중 알 수 없는 문제발생");
			}//catch
			
		}//switch
		
	}//modifyWork

}//class
