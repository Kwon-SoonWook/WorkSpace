package bookManagement;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.util.*;
import java.awt.List;

public class BookManagement extends Frame implements ActionListener {
	//DB연동
	PreparedStatement ps, ps2, ps3;
	ResultSet rs, rs2, rs3;
	String sql;

	//공통
	Menu menu;
	MenuBar menubar;
	MenuItem user_add, book_add, book_delete, book_lend, search_info, delay_info, book_topten, home;
	Panel p_main;
	
	//초기 화면
	Label lb_main;
	
	//사용자 등록
	Label lb_user_add_title, lb_user_add_name, lb_user_add_birth, lb_user_add_addr, lb_user_add_tel, lb_user_add_msg;
	TextField tf_user_add_name, tf_user_add_addr, tf_user_add_tel, tf_user_add_birth;
	TextArea ta_user_list;
	Button bt_user_add;
	
	//책 정보 등록
	Label lb_book_add_title, lb_book_add_bname, lb_book_add_genre, lb_book_add_author, lb_book_add_publisher, lb_book_add_msg;
	TextField tf_book_add_bname, tf_book_add_genre, tf_book_add_author, tf_book_add_publisher;
	TextArea ta_book_add_list;
	Button bt_book_add;
	
	//책 정보 삭제
	Label lb_book_delete_title, lb_book_delete_bid, lb_book_delete_msg;
	TextField tf_book_delete_bid;
	TextArea ta_book_delete_list;
	Button bt_book_delete;
	
	//책 대여 반납
	Label lb_book_lend_title, lb_book_lend_pid, lb_book_lend_bid, lb_book_lend_msg;
	TextField tf_book_lend_pid, tf_book_lend_bid;
	TextArea ta_book_lend_list;
	Button bt_book_lend, bt_book_return;
	
	//검색하기
	Label lb_search_info_title;
	CheckboxGroup cg_options;
	Checkbox cb_book, cb_person;
	Button bt_search_info;
	TextField tf_search_info_key;
	TextArea ta_show_result;

	// 연체정보보기
	Label lb_delay_info_title, lb_delay_info_bname, lb_delay_info_msg;
	Panel p_main_c_n,p_main_c;
	Button bt_delay;
	List l_delay,l_book_id,l_person_name,l_book_name,l_event_time;

	// 인기순위
	Label lb_rank_info_title;
	Panel p_main_rank, p_main_rank_center;
	
	
	
	public BookManagement() throws Exception{
		mainScreen();
		this.add(p_main);
		menubar = new MenuBar();
		this.setMenuBar(menubar);
		menu = new Menu("메뉴");
		menubar.add(menu);
		user_add = new MenuItem("회원정보 등록");
		book_add = new MenuItem("책 정보 등록");
		book_delete = new MenuItem("책 정보 삭제");
		book_lend = new MenuItem("책 대여 및 반납");
		search_info = new MenuItem("검색하기");
		delay_info = new MenuItem("연체 정보 보기");
		book_topten = new MenuItem("대여 탑10 책 정보");
		home = new MenuItem("처음으로");
		
		menu.add(user_add);
		menu.add(book_add);
		menu.add(book_delete);
		menu.add(book_lend);
		menu.add(search_info);
		menu.add(delay_info);
		menu.add(book_topten);
		menu.addSeparator();
		menu.add(home);
		
		user_add.addActionListener(this);
		book_add.addActionListener(this);
		book_delete.addActionListener(this);
		book_lend.addActionListener(this);
		search_info.addActionListener(this);
		delay_info.addActionListener(this);
		book_topten.addActionListener(this);
		home.addActionListener(this);
		this.addWindowListener(
			new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			}
		);
	}
	
	@Override
	public Insets insets() {
		Insets i = new Insets(60, 30, 20, 50);
		return i;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == user_add) {
			this.remove(p_main);
			try {
				userAddView(userList());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.add(p_main);
			this.validate();
		}else if(obj == book_add) {
			this.remove(p_main);
			try {
				bookAddView(bookList());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.add(p_main);
			this.validate();
		}else if(obj == book_delete) {
			this.remove(p_main);
			try {
				String str = bookList();
				bookDeleteView(str);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.add(p_main);
			this.validate();
		}else if(obj == book_lend) {
			this.remove(p_main);
			try {
				bookLendView(bookLendList());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.add(p_main);
			this.validate();
		}else if(obj == search_info) {
			this.remove(p_main);
			searchInfoView();
			this.add(p_main);
			this.validate();
		}else if(obj == delay_info) {
			this.remove(p_main);
			try {
				delayInfoView();
				delayInfo();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.add(p_main);
			this.validate();	
		}else if(obj == book_topten) {
			this.remove(p_main);
			topTenView();
			this.add(p_main);
			this.validate();
			try {
				topTen();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(obj == home) {
			this.remove(p_main);
			mainScreen();
			this.add(p_main);
			this.validate();
		}else if(obj == bt_user_add) {
			try {
				userAdd();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(obj == bt_book_lend) {
			try {
				bookLend();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(obj == bt_book_add) {
			try {
				bookAdd();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}else if(obj == bt_book_return) {
			try {
				bookReturn();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(obj == bt_book_delete) {
			try {
				bookDelete();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if (obj == bt_search_info) {
			try {
				searchInfo();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if (obj == bt_delay) {
			this.remove(p_main);
			try {
				delayInfoView();
				delayInfo();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.add(p_main);
			this.validate();
		}
	}
	
	//메인 화면 메서드
	public void mainScreen() {
		p_main = new Panel(new BorderLayout());
		lb_main = new Label("도서관리 프로그램 v3.0", Label.CENTER);
		p_main.add(lb_main);
	}
	
	//사용자 등록 현황 메서드g
	public String userList() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
	    String url = "jdbc:oracle:thin:@localhost:1521:xe";
	    String user = "scott";
	    String pwd = "1234";
	    Connection con = DriverManager.getConnection(url, user, pwd);
	    sql = "select person_id, person_name, birth from person order by person_id asc";
	    ps = con.prepareStatement(sql);
	    rs = ps.executeQuery();
	    String str = "회원 번호\t이름\t      생년월일\n";
	    while(rs.next()) {
	       str = str+rs.getInt("person_id")+"\t            "+rs.getString("person_name")+"\t      "+rs.getString("birth")+"\n";
	    }
	    return str;
	}
	
	//사용자 등록 화면 메서드
	public void userAddView(String str) {
	    p_main = new Panel(new BorderLayout(10,10));
	    lb_user_add_title = new Label("회원 정보 추가",Label.CENTER);
	    p_main.add(lb_user_add_title, "North");
	      
	    Panel p_center_temp = new Panel(new BorderLayout(20,20));
	    Panel p_center_temp_west = new Panel(new GridLayout(6,2,5,16));
	    Panel p_center_temp_center = new Panel(new BorderLayout(5,5));
	      
	    lb_user_add_name = new Label("회원 이름 : ");
	    tf_user_add_name = new TextField();
	    lb_user_add_tel = new Label("전화 번호 : ");
	    tf_user_add_tel = new TextField();
	    lb_user_add_addr = new Label("주소 : ");
	    tf_user_add_addr = new TextField();
	    lb_user_add_birth = new Label("생년월일 : ");
	    tf_user_add_birth = new TextField();
	    ta_user_list = new TextArea(str,0,0,ta_book_lend_list.SCROLLBARS_VERTICAL_ONLY);
	    ta_user_list.setEditable(false);
	      
	    p_center_temp_west.add(new Label());
	    p_center_temp_west.add(new Label());
	    p_center_temp_west.add(lb_user_add_name);
	    p_center_temp_west.add(tf_user_add_name);
	    p_center_temp_west.add(lb_user_add_tel);
	    p_center_temp_west.add(tf_user_add_tel);
	    p_center_temp_west.add(lb_user_add_addr);
	    p_center_temp_west.add(tf_user_add_addr);
	    p_center_temp_west.add(lb_user_add_birth);
	    p_center_temp_west.add(tf_user_add_birth);
	      
	    p_center_temp_center.add(new Label("회원 정보 현황",Label.CENTER), "North");
	    p_center_temp_center.add(ta_user_list, "Center");
	    p_center_temp.add(p_center_temp_west,"West");
	    p_center_temp.add(p_center_temp_center,"Center");
	    p_main.add(p_center_temp,"Center");
	      
	    Panel p_south_temp = new Panel(new BorderLayout(5,5));
	    Panel p_south_south = new Panel();
	    lb_user_add_msg = new Label("메세지 : ");
	    bt_user_add = new Button("등록하기");
	    p_south_temp.add(lb_user_add_msg);
	    p_south_south.add(bt_user_add,"South");
	    p_south_temp.add(p_south_south,"South");
	    p_main.add(p_south_temp, "South");
	      
	    bt_user_add.addActionListener(this);
	}
	   
	//회원 등록
	public void userAdd() throws Exception{
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    String url = "jdbc:oracle:thin:@localhost:1521:xe";
	    String user = "scott";
	    String pwd = "1234";
	    Connection con = DriverManager.getConnection(url,user,pwd);
	    sql = "insert into person (person_name,tel,addr,birth) values(?,?,?,?)";
	    ps = con.prepareStatement(sql);
	    ps.setString(1, tf_user_add_name.getText());
	    ps.setString(2, tf_user_add_tel.getText());
	    ps.setString(3, tf_user_add_addr.getText());
	    ps.setString(4, tf_user_add_birth.getText());
	    ps.executeUpdate();
	      
	    lb_user_add_msg.setText(tf_user_add_name.getText()+"님의 등록이 완료되었습니다.");
	     
	    ta_user_list.setText(userList());
	      
	    rs.close();
	    ps.close();
	    con.close();
	}
	
	//책 정보 현황 메서드
	public String bookList() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pwd = "1234";
		
		Connection con = DriverManager.getConnection(url,user,pwd);
		sql = "select book_id,book_name from book order by book_id asc";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		String str = "책 번호\t책 이름\n";
		while(rs.next()) {
			str += rs.getInt("book_id")+"\t"+rs.getString("book_name")+"\n";
		}	
		rs.close();
		ps.close();
		con.close();
		return str;	
	}
	
	//책 정보 등록 화면 메서드
	public void bookAddView(String str) {	
		p_main = new Panel(new BorderLayout(10,10));
		lb_book_add_title = new Label("책 정보 등록", Label.CENTER);
		p_main.add(lb_book_add_title, "North");
		
		Panel p_center_temp = new Panel(new GridLayout(1,2,20,20));
		Panel p_center_temp_west = new Panel(new GridLayout(8,1,5,5));
		Panel p_center_temp_center = new Panel(new BorderLayout(5,5));
		
		lb_book_add_bname = new Label("책 이름 : ");
		lb_book_add_genre = new Label("장르 : ");
		lb_book_add_author = new Label("저자 : ");
		lb_book_add_publisher = new Label("출판사 : ");
		tf_book_add_bname = new TextField();
		tf_book_add_genre = new TextField();
		tf_book_add_author = new TextField();
		tf_book_add_publisher = new TextField();
		ta_book_add_list = new TextArea(str,0,0,ta_book_add_list.SCROLLBARS_VERTICAL_ONLY);
		ta_book_add_list.setEditable(false);				
		
		p_center_temp_west.add(lb_book_add_bname);
		p_center_temp_west.add(tf_book_add_bname);
		p_center_temp_west.add(lb_book_add_genre);
		p_center_temp_west.add(tf_book_add_genre);
		p_center_temp_west.add(lb_book_add_author);
		p_center_temp_west.add(tf_book_add_author);
		p_center_temp_west.add(lb_book_add_publisher);
		p_center_temp_west.add(tf_book_add_publisher);
		
		p_center_temp_center.add(new Label("책 정보 현황",Label.CENTER), "North");
		p_center_temp_center.add(ta_book_add_list, "Center");
		p_center_temp.add(p_center_temp_west, "West");
		p_center_temp.add(p_center_temp_center, "Center");
		p_main.add(p_center_temp, "Center");
		
		Panel p_south_temp = new Panel(new BorderLayout(5,5));
		Panel p_south_south = new Panel();
		lb_book_add_msg = new Label("메세지 : ");
		bt_book_add = new Button("등록하기");
		p_south_temp.add(lb_book_add_msg);
		p_south_south.add(bt_book_add, "South");
		p_south_temp.add(p_south_south, "South");
		p_main.add(p_south_temp, "South");
		
		bt_book_add.addActionListener(this);
	}
	
	//책 등록 메서드
	public void bookAdd() throws Exception{
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    String url ="jdbc:oracle:thin:@localhost:1521:xe";
	    String user ="scott";
	    String pwd ="1234";
	    Connection con = DriverManager.getConnection(url, user, pwd);	
	      
	    Map<String, String> genreId = new HashMap<String, String>();
	    genreId.put("철학",	 "philosophy_sq.NEXTVAL");
	    genreId.put("문학",	 "literature_sq.NEXTVAL");
	    genreId.put("과학",	 "science_sq.NEXTVAL");
	      
	    sql = "insert into book (book_id, book_name, author, publisher) "
	     	+ "values (" + genreId.get(tf_book_add_genre.getText()) + " ,?,?,?)";
	    ps = con.prepareStatement(sql);
	      
	    ps.setString(1, tf_book_add_bname.getText());
	    ps.setString(2, tf_book_add_author.getText());
	    ps.setString(3, tf_book_add_publisher.getText());
	    ps.executeUpdate();
	    lb_book_add_msg.setText(tf_book_add_bname.getText()+" 책의 신규 등록이 완료되었습니다.");   
	    ta_book_add_list.setText(bookList());
	    ps.close();
	    con.close();
	}
	
	public void bookDeleteView(String str) {
		p_main = new Panel(new BorderLayout(10,10));
		lb_book_delete_title = new Label("책 정보 삭제", Label.CENTER);
		p_main.add(lb_book_delete_title, "North");
		
		Panel p_center_temp = new Panel(new BorderLayout(20,20));
		Panel p_center_temp_west = new Panel(new GridLayout(6,2,5,16));
		Panel p_center_temp_center = new Panel(new BorderLayout(5,5));
		
		lb_book_delete_bid = new Label("삭제할 책 번호 : ");
		tf_book_delete_bid = new TextField();
		ta_book_delete_list = new TextArea(str,0,0,ta_book_lend_list.SCROLLBARS_VERTICAL_ONLY);
		ta_book_delete_list.setEditable(false);		
		
		p_center_temp_west.add(new Label());
		p_center_temp_west.add(new Label());
		p_center_temp_west.add(lb_book_delete_bid);
		p_center_temp_west.add(tf_book_delete_bid);
		p_center_temp_west.add(new Label());
		p_center_temp_west.add(new Label());
		p_center_temp_west.add(new Label());
		p_center_temp_west.add(new Label());
		
		p_center_temp_center.add(new Label("책 정보 현황",Label.CENTER), "North");
		p_center_temp_center.add(ta_book_delete_list, "Center");
		p_center_temp.add(p_center_temp_west, "West");
		p_center_temp.add(p_center_temp_center, "Center");
		p_main.add(p_center_temp, "Center");
		
		Panel p_south_temp = new Panel(new BorderLayout(5,5));
		Panel p_south_south = new Panel();
		lb_book_delete_msg = new Label("메세지 : ");
		bt_book_delete = new Button("삭제하기");
		p_south_temp.add(lb_book_delete_msg);
		p_south_south.add(bt_book_delete, "South");
		p_south_temp.add(p_south_south, "South");
		p_main.add(p_south_temp, "South");
		
		bt_book_delete.addActionListener(this);
	}
	   
	//책 삭제 메서드
	public void bookDelete() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url ="jdbc:oracle:thin:@localhost:1521:xe";
		String user ="scott";
		String pwd ="1234";
		Connection con = DriverManager.getConnection(url, user, pwd);
		sql = "delete from records where book_id=?";
		ps = con.prepareStatement(sql);
		ps.setString(1, tf_book_delete_bid.getText());
		ps.executeUpdate();	
		sql = "delete from book where book_id=? ";
		ps = con.prepareStatement(sql);
		ps.setString(1, tf_book_delete_bid.getText());
		int count = ps.executeUpdate();
		if(count == 0) {
			lb_book_delete_msg.setText("해당 책은 등록되지 않았습니다.");
		}else {
				lb_book_delete_msg.setText(tf_book_delete_bid.getText()+" 번 책의 정보가 삭제 되었습니다.");
		}
		ta_book_delete_list.setText(bookList());
		ps.close();
		con.close();
	}
	
	//책 대여 현황 리스트 메서드
	public String bookLendList() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pwd = "1234";
		
		Connection con = DriverManager.getConnection(url,user,pwd);
		sql = "select records_id,book_id,person_id,to_char(TRUNC(event_time),'YYYY-MM-DD') as event_time from records order by records_id asc";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		String str = "책 번호\t\t사용자ID\t\t대여일\n";
		while(rs.next()) {
			str += rs.getInt("book_id")+"\t\t"+rs.getInt("person_id")+"\t\t"+rs.getString("event_time")+"\n";
		}
		rs.close();
		ps.close();
		con.close();
		return str;
	}
	
	
	//책 대여 및 반납 화면 메서드
	public void bookLendView(String str) {	
		p_main = new Panel(new BorderLayout(10,10));
		lb_book_lend_title = new Label("책 대여 및 반납", Label.CENTER);
		p_main.add(lb_book_lend_title, "North");
		
		Panel p_center_temp = new Panel(new BorderLayout(20,20));
		Panel p_center_temp_west = new Panel(new GridLayout(6,2,5,16));
		Panel p_center_temp_center = new Panel(new BorderLayout(5,5));
		
		lb_book_lend_pid = new Label("사용자 ID : ");
		tf_book_lend_pid = new TextField();
		lb_book_lend_bid = new Label("책 번호     : ");
		tf_book_lend_bid = new TextField();
		ta_book_lend_list = new TextArea(str,0,0,ta_book_lend_list.SCROLLBARS_VERTICAL_ONLY);
		ta_book_lend_list.setEditable(false);		
		
		p_center_temp_west.add(new Label());
		p_center_temp_west.add(new Label());
		p_center_temp_west.add(lb_book_lend_pid);
		p_center_temp_west.add(tf_book_lend_pid);
		p_center_temp_west.add(lb_book_lend_bid);
		p_center_temp_west.add(tf_book_lend_bid);
		p_center_temp_west.add(new Panel());
		p_center_temp_center.add(new Label("책 대여 현황",Label.CENTER), "North");
		p_center_temp_center.add(ta_book_lend_list, "Center");
		p_center_temp.add(p_center_temp_west, "West");
		p_center_temp.add(p_center_temp_center, "Center");
		p_main.add(p_center_temp, "Center");
		
		Panel p_south_temp = new Panel(new BorderLayout(5,5));
		Panel p_south_south = new Panel();
		lb_book_lend_msg = new Label("메세지 : ");
		bt_book_lend = new Button("대여하기");
		bt_book_return = new Button("반납하기");
		p_south_temp.add(lb_book_lend_msg);
		p_south_south.add(bt_book_lend);
		p_south_south.add(bt_book_return);
		p_south_temp.add(p_south_south, "South");
		p_main.add(p_south_temp, "South");
		
		bt_book_lend.addActionListener(this);
		bt_book_return.addActionListener(this);
	}
	
	//책 대여 메서드
	public void bookLend() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pwd = "1234";
		Connection con = DriverManager.getConnection(url,user,pwd);
		int person_id = Integer.parseInt(tf_book_lend_pid.getText());
		int book_id = Integer.parseInt(tf_book_lend_bid.getText());
		int count = 0;
		sql = "select * from records where person_id=?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, person_id);
		rs = ps.executeQuery();
		while(rs.next()) {
			count++;
		}
		if(count >= 5) {
			lb_book_lend_msg.setText(person_id+"번 사용자님은 이미 5권의 책을 빌리셨습니다. 반납 후 이용해주세요.");			
		}
		else {
			sql = "select * from records where book_id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, book_id);
			rs = ps.executeQuery();
			if(rs.next()) {
				lb_book_lend_msg.setText(book_id+"번 책은 대여 된 상태입니다.");
			}
			else {
				sql = "select * from book where book_id=?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, book_id);
				rs = ps.executeQuery();
				if(rs.next()) {
					sql = "update book set lend_count=lend_count+1 where book_id=?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, book_id);
					ps.executeUpdate();
					sql = "insert into records (book_id,person_id) values (?,?)";
					ps = con.prepareStatement(sql);
					ps.setInt(1, book_id);
					ps.setInt(2, person_id);
					ps.executeUpdate();
					lb_book_lend_msg.setText(person_id+"번 사용자님의"+book_id+"번 책 대여가 완료되었습니다.");
				}
				else {
					lb_book_lend_msg.setText(book_id+"번 책은 등록되지 않았습니다. 책 번호를 확인해주세요.");				
				}
			}
		}
		
		ta_book_lend_list.setText(bookLendList());
		rs.close();
		ps.close();
		con.close();
	}
	
	//책 반납 메서드
	public void bookReturn() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pwd = "1234";
		Connection con = DriverManager.getConnection(url,user,pwd);
		int person_id = Integer.parseInt(tf_book_lend_pid.getText());
		int book_id = Integer.parseInt(tf_book_lend_bid.getText());
		sql = "select * from book where book_id=?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, book_id);
		rs = ps.executeQuery();
		if(rs.next()) {
			sql = "select * from records where book_id=? and person_id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, book_id);
			ps.setInt(2, person_id);
			ps.executeUpdate();
			rs = ps.executeQuery();
			if(rs.next()) {
				sql = "delete from records where book_id=?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, book_id);
				ps.executeUpdate();
				lb_book_lend_msg.setText(person_id+"번 사용자로부터 "+book_id+"번 책이 반납되었습니다.");
			}else {
				lb_book_lend_msg.setText(person_id+"사용자님은 "+book_id+"번 책을 대여하지 않았습니다. 책 번호를 확인해주세요.");
			}
		}else {
			lb_book_lend_msg.setText(book_id+"번 책은 등록되지 않았습니다. 책 번호를 확인해주세요.");
		}
		
		ta_book_lend_list.setText(bookLendList());
		rs.close();
		ps.close();
		con.close();
	}
	
	
	//검색하기 화면 메서드
	public void searchInfoView() {
		p_main = new Panel(new BorderLayout(10,10));
		lb_search_info_title = new Label("검색하기", Label.CENTER);
		p_main.add(lb_search_info_title, "North");
		
		Panel p_center_temp = new Panel(new BorderLayout(5, 5));
		Panel p_top = new Panel(new FlowLayout(FlowLayout.CENTER));
		
		Panel p_tf_and_bt = new Panel(new GridLayout(1, 4, 5, 5));
		cg_options = new CheckboxGroup();
		cb_book = new Checkbox("제목으로 책 찾기", cg_options, true);
		cb_person = new Checkbox("이름으로 사람 찾기", cg_options, false);
		tf_search_info_key = new TextField();
		bt_search_info = new Button("검색");
		p_tf_and_bt.add(cb_book);
		p_tf_and_bt.add(cb_person);
		p_tf_and_bt.add(tf_search_info_key);
		p_tf_and_bt.add(bt_search_info);
		p_top.add(p_tf_and_bt);
		p_center_temp.add(p_top, "North");
		
		ta_show_result = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		
		p_center_temp.add(ta_show_result);
		
		p_main.add(p_center_temp, "Center");
		
		bt_search_info.addActionListener(this);
	}
	
	//검색하기 메서드
	public void searchInfo() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pwd = "1234";
		Connection con = DriverManager.getConnection(url,user,pwd);
		
		String key = tf_search_info_key.getText();
		
		if (cb_book.getState()) {
			ps = con.prepareStatement("SELECT book_id, book_name, author, publisher, genre, "
					+ "    CASE "
					+ "        WHEN book_id IN (SELECT book_id FROM records) THEN '이미 대여됨' "
					+ "        ELSE '대여 가능' "
					+ "    END AS status "
					+ "FROM book, genres "
					+ "WHERE book_id BETWEEN min AND max "
					+ "    AND book_name LIKE ? "
					+ "ORDER BY book_id ASC");
			ps.setString(1, "%" + key + "%");
			rs = ps.executeQuery();
			
			ta_show_result.setText("");
			
			ta_show_result.append("ID\t제목\t작가\t출판사\t분야\t상태\n");
			
			while (rs.next()) {
				String row = rs.getInt("book_id") + "\t" + rs.getString("book_name") + "\t"
						+ rs.getString("author") + "\t" + rs.getString("publisher") + "\t"
						+ rs.getString("genre") + "\t" + rs.getString("status") +"\n";
				ta_show_result.append(row);
			}
		}
		else if (cb_person.getState()){
			ps = con.prepareStatement("SELECT person.person_id, person_name, tel, addr, birth, NVL(5 - lend_per_person, 5) AS lend_limit "
					+ "FROM person, (SELECT person_id, COUNT(person_id) AS lend_per_person "
					+ "              FROM records "
					+ "              GROUP BY person_id) lend_per_person "
					+ "WHERE person.person_id = lend_per_person.person_id(+) "
					+ "AND person_name LIKE ?");
			ps.setString(1, "%" + key + "%");
			rs = ps.executeQuery();
			
			ta_show_result.setText("");
			
			ta_show_result.append("ID\t이름\t전화번호\t주소\t출생연도\t가능대여권수\n");
			
			while (rs.next()) {
				String row = rs.getInt("person_id") + "\t" + rs.getString("person_name") + "\t"
						+ rs.getString("tel") + "\t\t" + rs.getString("addr") + "\t"
						+ rs.getString("birth") + "\t\t" + rs.getInt("lend_limit") + "\n";
				ta_show_result.append(row);
			}
		}
		
		rs.close();
		ps.close();
		con.close();
	}
	
	// 연체정보 화면 메서드
	public void delayInfoView() throws Exception {
		
		p_main = new Panel(new BorderLayout(10, 10));
		lb_delay_info_title = new Label("연체 회원 정보", Label.CENTER);; 
		p_main.add(lb_delay_info_title, "North");
		p_main_c = new Panel(new BorderLayout(5, 5));
		Panel p_main_s = new Panel(new FlowLayout());
		 
		p_main.add(p_main_c, "Center");
		p_main.add(p_main_s, "South"); 
		
		Panel p_main_c_n=new Panel(new GridLayout(1,4,10,10));
		p_main_c.add(p_main_c_n,"North");
		
		Label l_book_id_sub=new Label("대여번호");
		Label l_person_name_sub=new Label("회원이름");
		Label l_book_name_sub=new Label("대여날짜");
		Label l_event_time_sub=new Label("책제목");
		p_main_c_n.add(l_book_id_sub);
		p_main_c_n.add(l_person_name_sub);
		p_main_c_n.add(l_book_name_sub);
		p_main_c_n.add(l_event_time_sub);
		
		l_delay=new List(0,false);
		
		bt_delay = new Button("새로고침");
		bt_delay.setSize(100, 100);
		p_main_s.add(bt_delay, "South");

		bt_delay.addActionListener(this);
		p_main_c.revalidate();
		p_main_c.repaint();
		
		
	}
	
	public void delayInfo() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pwd = "1234";

		Connection con = DriverManager.getConnection(url, user, pwd);
		sql = "select records_id,person_name,book_name,to_char(TRUNC(r.event_time),'YYYY-MM-DD')as event_time\r\n"
				+ "from records r,book b,person p\r\n" + "where r.book_id=b.book_id\r\n"
				+ "and r.person_id=p.person_id\r\n" + "and event_time+14>systimestamp";// 대여한지 2주 지난 회원 확인
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		
		p_main_c.add(l_delay,"Center");
		String blank=" ";
		int a=40;
		int b=40;

		while (rs.next()) {
			
			a-=String.valueOf(rs.getInt("records_id")).length();
			b-=3*(rs.getString("person_name").length());
			l_delay.add(rs.getInt("records_id")+blank.repeat(a)+rs.getString("person_name")+blank.repeat(b)+rs.getString("event_time")+blank.repeat(22)+rs.getString("book_name"));
			a=40;
			b=40;
		}
		rs.close();
		ps.close();
		con.close();
	}
	public void topTenView() {
		p_main = new Panel(new BorderLayout(20, 20));
		lb_rank_info_title = new Label("인기 도서 Top10", Label.CENTER);
		p_main.add(lb_rank_info_title, "North");
		p_main_rank = new Panel(new BorderLayout(5, 5));
		p_main_rank_center = new Panel(new GridLayout(10,1,5,5));
	
		p_main_rank.add(p_main_rank_center, "Center");
		p_main.add(p_main_rank, "Center");
		
	}

	public void topTen() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pwd = "1234";

		Connection con = DriverManager.getConnection(url, user, pwd);

		sql = "SELECT * FROM (\r\n" + "    SELECT ROW_NUMBER() OVER (ORDER BY SUM(lend_count) DESC) AS rank,\r\n"
				+ "    book_name\r\n" + "    FROM book\r\n" + "    GROUP BY book_name\r\n" + ")\r\n"
				+ "WHERE ROWNUM <= 10";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			int rank = rs.getInt("rank");
			String title = rs.getString("book_name");

			p_main_rank_center.add(new Label(rank+"     "+title, Label.CENTER));

		}
		
		p_main_rank.revalidate();
		rs.close();
		ps.close();
		con.close();
	}
	
	public static void main(String[] args) throws Exception{
		BookManagement bm = new BookManagement();
		bm.setSize(600,400);
		bm.setVisible(true);
		bm.setResizable(false);
	}
}