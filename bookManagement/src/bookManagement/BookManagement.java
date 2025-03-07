package bookManagement;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.awt.event.*;
import java.util.*;
import java.awt.List;
import java.util.Date;

import javax.imageio.plugins.bmp.BMPImageWriteParam;

public class BookManagement extends Frame implements ActionListener {
	//DB연동
	PreparedStatement ps, ps2, ps3;
	ResultSet rs, rs2, rs3;
	String sql;

	//공통
	Menu menu;
	MenuBar menubar;
	MenuItem user_add, user_update, book_add, book_delete, book_lend, search_info, delay_info, book_topten, logout;
	Panel p_main;
	
	//로그인 화면
	Label lb_login_title, lb_login_id, lb_login_pw, lb_login_msg;
	TextField tf_login_id, tf_login_pw;
	Button bt_login, bt_login_add;

	//회원가입 화면
	Label lb_admin_add_title, lb_admin_add_id, lb_admin_add_pw, lb_admin_add_key, lb_admin_add_msg;
	TextField tf_admin_add_id, tf_admin_add_pw, tf_admin_add_key;
	Button bt_admin_add, bt_admin_add_home;
	
	//초기 화면
	Label lb_main_msg1, lb_main_msg2;
	
	//사용자 정보 등록
	Label lb_user_add_title, lb_user_add_name, lb_user_add_birth, lb_user_add_addr, lb_user_add_tel, lb_user_add_msg;
	TextField tf_user_add_name, tf_user_add_addr, tf_user_add_tel, tf_user_add_birth;
	TextArea ta_user_list;
	Button bt_user_add;
	
	//사용자 정보 수정
	Label lb_user_update_title, lb_user_update_id, lb_user_update_name, lb_user_update_addr, lb_user_update_tel, lb_user_update_birth, lb_user_update_msg;
	TextField tf_user_update_id, tf_user_update_name, tf_user_update_addr, tf_user_update_tel, tf_user_update_birth;
	TextArea ta_user_update_list;
	Button bt_user_update_uinfo, bt_user_update;
	
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
	Label lb_delay_info_title, lb_delay_info_bname, lb_delay_info_msg, day_delay, lb_time;
	Panel p_main_c_n, p_main_c, p_main_s;
	Button bt_delay;
	List l_delay, l_book_id, l_person_name, l_book_name, l_event_time;

	// 인기순위
	Label lb_rank_info_title;
	Panel p_main_rank, p_main_rank_center;
	
	boolean login_info = false;
	
	
	public BookManagement(boolean login) throws Exception{
		loginView();
		menubar = new MenuBar();
		this.setMenuBar(menubar);
		menu = new Menu("메뉴");
		menubar.add(menu);
		this.add(p_main);
		user_add = new MenuItem("회원정보 등록");
		user_update = new MenuItem("회원정보 수정");
		book_add = new MenuItem("책 정보 등록");
		book_delete = new MenuItem("책 정보 삭제");
		book_lend = new MenuItem("책 대여 및 반납");
		search_info = new MenuItem("검색하기");
		delay_info = new MenuItem("연체 정보 보기");
		book_topten = new MenuItem("인기도서 Top10");
		logout = new MenuItem("로그아웃");
		
		user_add.setEnabled(login_info);
		user_update.setEnabled(login_info);
		book_add.setEnabled(login_info);
		book_delete.setEnabled(login_info);
		book_lend.setEnabled(login_info);
		search_info.setEnabled(login_info);
		delay_info.setEnabled(login_info);
		book_topten.setEnabled(login_info);
		logout.setEnabled(login_info);
		
		menu.add(user_add);
		menu.add(user_update);
		menu.add(book_add);
		menu.add(book_delete);
		menu.add(book_lend);
		menu.add(search_info);
		menu.add(delay_info);
		menu.add(book_topten);
		menu.addSeparator();
		menu.add(logout);
		
		user_add.addActionListener(this);
		user_update.addActionListener(this);
		book_add.addActionListener(this);
		book_delete.addActionListener(this);
		book_lend.addActionListener(this);
		search_info.addActionListener(this);
		delay_info.addActionListener(this);
		book_topten.addActionListener(this);
		logout.addActionListener(this);
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
		Insets i = new Insets(60, 30, 40, 30);
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
			delayInfoView();
			try {
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
		}else if(obj == logout) {
			this.remove(p_main);
			logout();
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
			delayInfoView();
			try {
				delayInfo();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.add(p_main);
			this.validate();
		}else if (obj == bt_user_update) {
			try {
				userUpdate();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(obj == user_update) {
			this.remove(p_main);
			try {
				userUpdateView(userList());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.add(p_main);
			this.validate();
		}else if(obj == bt_user_update_uinfo) {
			try {
				userInfo();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else if(obj == bt_login) {
			try {
				login();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(login_info == true) {
				this.remove(p_main);
				mainScreen();
				this.add(p_main);
				this.validate();
			}
		}else if(obj == bt_login_add) {
			this.remove(p_main);
			adminAddView();
			this.add(p_main);
			this.validate();
		}else if(obj == bt_admin_add) {
			try {
				adminAdd();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(obj == bt_admin_add_home) {
			this.remove(p_main);
			loginView();
			this.add(p_main);
			this.validate();
		}
	}
	
	public void mainScreen() {
		p_main = new Panel(new BorderLayout());
		lb_main_msg1 = new Label("도서관리 프로그램 v3.0", Label.CENTER);
		lb_main_msg2 = new Label("상단 메뉴에서 사용할 기능을 선택해주세요.", Label.CENTER);
		p_main.add(lb_main_msg1);
		p_main.add(lb_main_msg2);

		this.add(p_main);
	}
	
	//메인 화면 메서드
	public void loginView() {
		p_main = new Panel(new BorderLayout());
		Panel p_main_north = new Panel(new GridLayout(4, 1, 10, 10));
		Panel p_main_east = new Panel(new GridLayout(1, 1, 10, 10));
		Panel p_main_west = new Panel(new GridLayout(1, 1, 10, 10));
		Panel p_main_south = new Panel(new GridLayout(4, 1, 10, 10));
		Panel p_main_center = new Panel(new GridLayout(2, 3, 10, 10));
		
		lb_login_title = new Label("도서관리 프로그램 v3.0", Label.CENTER);
		lb_login_msg = new Label("", Label.CENTER);
		lb_login_id = new Label("          ID   : ");
		lb_login_pw = new Label("          PW : ");
		tf_login_id = new TextField("");
		tf_login_pw = new TextField("");
		tf_login_pw.setEchoChar('*');
		bt_login = new Button("로그인");
		bt_login_add = new Button("회원가입");	
		
		p_main_north.add(new Label());
		p_main_north.add(new Label());
		p_main_north.add(lb_login_title);
		p_main_north.add(new Label());		
		p_main_east.add(new Label("                                            "));
		p_main_west.add(new Label("                                            "));
		p_main_south.add(new Label());
		p_main_south.add(lb_login_msg);
		p_main_south.add(new Label());
		p_main_south.add(new Label());								
	
		
		p_main_center.add(lb_login_id);
		p_main_center.add(tf_login_id);
		p_main_center.add(bt_login);
		p_main_center.add(lb_login_pw);
		p_main_center.add(tf_login_pw);
		p_main_center.add(bt_login_add);
		
		
		p_main.add(p_main_center, "Center");
		p_main.add(p_main_north, "North");
		p_main.add(p_main_east, "East");
		p_main.add(p_main_west, "West");
		p_main.add(p_main_south, "South");
		
		this.add(p_main);
		
		bt_login.addActionListener(this);
		bt_login_add.addActionListener(this);		
	}
	
	//관리자 로그인 메서드
	public void login() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
	    String url = "jdbc:oracle:thin:@localhost:1521:xe";
	    String user = "scott";
	    String pwd = "1234";
	    Connection con = DriverManager.getConnection(url, user, pwd);
	    sql = "select * from admin where admin_id = ? and admin_pw = ?";
	    ps = con.prepareStatement(sql);
	    ps.setString(1, tf_login_id.getText());
	    ps.setString(2, tf_login_pw.getText());
	    rs = ps.executeQuery();
	    if(rs.next()) {
			login_info = true;
			user_add.setEnabled(login_info);
			user_update.setEnabled(login_info);
			book_add.setEnabled(login_info);
			book_delete.setEnabled(login_info);
			book_lend.setEnabled(login_info);
			search_info.setEnabled(login_info);
			delay_info.setEnabled(login_info);
			book_topten.setEnabled(login_info);
			logout.setEnabled(login_info);
	    }else {
	    	if(tf_login_id.getText().equals("")||tf_login_pw.getText().equals("")) {
	    		lb_login_msg.setText("로그인 정보가 입력되지 않았습니다. 입력한 로그인 정보를 확인해주세요.");
	    	}else {
	    		lb_login_msg.setText("로그인 정보가 일치하지 않습니다.");	    		
	    	}
	    }
	    rs.close();
	    ps.close();
	    con.close();
	}
	
	//로그아웃 메서드
	public void logout() {
		login_info = false;
		user_add.setEnabled(login_info);
		user_update.setEnabled(login_info);
		book_add.setEnabled(login_info);
		book_delete.setEnabled(login_info);
		book_lend.setEnabled(login_info);
		search_info.setEnabled(login_info);
		delay_info.setEnabled(login_info);
		book_topten.setEnabled(login_info);
		logout.setEnabled(login_info);
		loginView();
	}
	
	//관리자 회원가입 화면 메서드
	public void adminAddView() {
		p_main = new Panel(new BorderLayout());
		Panel p_main_north = new Panel(new GridLayout(2, 1, 10, 10));
		Panel p_main_east = new Panel(new GridLayout(1, 1, 10, 10));
		Panel p_main_west = new Panel(new GridLayout(1, 1, 10, 10));
		Panel p_main_south = new Panel(new BorderLayout(10, 10));
		Panel p_main_south_center = new Panel(new GridLayout(3, 5, 10, 10));
		Panel p_main_center = new Panel(new GridLayout(4, 2, 10, 10));
		
		lb_admin_add_title = new Label("회원가입", Label.CENTER);
		lb_admin_add_id = new Label("ID   : ");
		lb_admin_add_pw = new Label("PW : ");
		lb_admin_add_key = new Label("Admin key : ");
		lb_admin_add_msg = new Label("", Label.CENTER);
		tf_admin_add_id = new TextField("");
		tf_admin_add_pw = new TextField("");
		tf_admin_add_key = new TextField("");
		tf_admin_add_pw.setEchoChar('*');
		tf_admin_add_key.setEchoChar('*');
		bt_admin_add = new Button("회원가입");
		bt_admin_add_home = new Button("처음으로");
		
		p_main_north.add(lb_admin_add_title);
		p_main_north.add(new Label());
		p_main_east.add(new Label("                                                           "));
		p_main_west.add(new Label("                                                           "));
		p_main_south_center.add(new Label());		
		p_main_south_center.add(new Label());
		p_main_south_center.add(new Label());
		p_main_south_center.add(new Label());
		p_main_south_center.add(new Label());
		p_main_south_center.add(new Label());
		p_main_south_center.add(new Label());
		p_main_south_center.add(bt_admin_add);
		p_main_south_center.add(new Label());
		p_main_south_center.add(new Label());
		p_main_south_center.add(new Label());
		p_main_south_center.add(new Label());
		p_main_south_center.add(bt_admin_add_home);
		
		p_main_south.add(lb_admin_add_msg, "North");
		p_main_south.add(p_main_south_center);
		
		p_main_center.add(lb_admin_add_id);
		p_main_center.add(tf_admin_add_id);
		p_main_center.add(lb_admin_add_pw);
		p_main_center.add(tf_admin_add_pw);
		p_main_center.add(lb_admin_add_key);
		p_main_center.add(tf_admin_add_key);
		
		p_main.add(p_main_center, "Center");
		p_main.add(p_main_north, "North");
		p_main.add(p_main_east, "East");
		p_main.add(p_main_west, "West");
		p_main.add(p_main_south, "South");
		
		this.add(p_main);
		
		bt_admin_add.addActionListener(this);
		bt_admin_add_home.addActionListener(this);
	}
	
	//회원가입 메서드
	public void adminAdd() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
	    String url = "jdbc:oracle:thin:@localhost:1521:xe";
	    String user = "scott";
	    String pwd = "1234";
	    Connection con = DriverManager.getConnection(url, user, pwd);
	    sql = "select * from admin where admin_id = ?";
	    ps = con.prepareStatement(sql);
	    ps.setString(1, tf_admin_add_id.getText());
	    rs = ps.executeQuery();
	    if(rs.next()) {
	    	if(tf_admin_add_id.getText().equals("") || tf_admin_add_pw.getText().equals("") || tf_admin_add_key.getText().equals("")) {
	    		lb_admin_add_msg.setText("입력 정보가 모두 입려되지 않았습니다. 입력 정보를 확인해주세요.");	    			    		
	    	}else {
	    		if(tf_admin_add_key.getText().equals("1234")) {
	    			lb_admin_add_msg.setText("중복된 ID입니다.");	    		
	    		}else {
	    			lb_admin_add_msg.setText("관리자 키를 잘못 입력하였습니다. 관리자 키를 확인해주세요.");	    			    		
	    		}	    		
	    	}
	    }else {
	    	if(tf_admin_add_id.getText().equals("") || tf_admin_add_pw.getText().equals("") || tf_admin_add_key.getText().equals("")) {
	    		lb_admin_add_msg.setText("입력 정보가 모두 입려되지 않았습니다. 입력 정보를 확인해주세요.");	    			    		
	    	}else {
	    		if(tf_admin_add_key.getText().equals("1234")) {
	    			sql = "insert into admin values (?, ?)";
	    			ps = con.prepareStatement(sql);
	    			ps.setString(1, tf_admin_add_id.getText());
	    			ps.setString(2, tf_admin_add_pw.getText());
	    			ps.execute();
	    			tf_admin_add_id.setText("");
	    			tf_admin_add_pw.setText("");
	    			tf_admin_add_key.setText(""); 			
	    			lb_admin_add_msg.setText("회원가입이 완료되었습니다.");	    			    			    		
	    		}else {
	    			lb_admin_add_msg.setText("관리자 키를 잘못 입력하였습니다. 관리자 키를 확인해주세요.");	    			    		
	    		}    			    		
	    	}
	    }
	    rs.close();
	    ps.close();
	    con.close();
	}
	
	//사용자 등록 현황 메서드
	public String userList() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
	    String url = "jdbc:oracle:thin:@localhost:1521:xe";
	    String user = "scott";
	    String pwd = "1234";
	    Connection con = DriverManager.getConnection(url, user, pwd);
	    sql = "select person_id, person_name, addr, tel, birth from person order by person_id asc";
	    ps = con.prepareStatement(sql);
	    rs = ps.executeQuery();
	    String str = "회원번호   이름\t      주소        연락처\t         생년월일\n";
	    while(rs.next()) {
	       str = str + rs.getInt("person_id")+"\t   "+String.format("%-"+(15-(rs.getString("person_name").length()*2))+"s", rs.getString("person_name"))
	       + String.format("%-"+(14-(rs.getString("addr").length()*2))+"s", rs.getString("addr"))
	       + String.format("%-"+(32-(rs.getString("tel").length()))+"s", rs.getString("tel"))
	       + rs.getString("birth") + "\n";
	    }
	    return str;
	}
	
	//사용자 등록 화면 메서드
	public void userAddView(String str) {
	    p_main = new Panel(new BorderLayout(10,10));
	    lb_user_add_title = new Label("회원 정보 추가", Label.CENTER);
	    p_main.add(lb_user_add_title, "North");
	      
	    Panel p_center_temp = new Panel(new BorderLayout(20,20));
	    Panel p_center_temp_west = new Panel(new GridLayout(6,2,5,16));
	    Panel p_center_temp_center = new Panel(new BorderLayout(5,5));
	      
	    lb_user_add_name = new Label("회원 이름 : ");
	    tf_user_add_name = new TextField("");
	    lb_user_add_addr = new Label("주소 : ");
	    tf_user_add_addr = new TextField("");
	    lb_user_add_tel = new Label("연락처 : ");
	    tf_user_add_tel = new TextField("");
	    lb_user_add_birth = new Label("생년월일 : ");
	    tf_user_add_birth = new TextField("");
	    ta_user_list = new TextArea(str, 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	    ta_user_list.setEditable(false);
	      
	    p_center_temp_west.add(new Label());
	    p_center_temp_west.add(new Label());
	    p_center_temp_west.add(lb_user_add_name);
	    p_center_temp_west.add(tf_user_add_name);
	    p_center_temp_west.add(lb_user_add_addr);
	    p_center_temp_west.add(tf_user_add_addr);
	    p_center_temp_west.add(lb_user_add_tel);
	    p_center_temp_west.add(tf_user_add_tel);
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
	    bt_user_update = new Button("수정하기");
	    p_south_temp.add(lb_user_add_msg);
	    p_south_south.add(bt_user_add);
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
	    
	    if(tf_user_add_name.getText().equals("") || tf_user_add_tel.getText().equals("") || tf_user_add_addr.getText().equals("") || tf_user_add_birth.getText().equals("")) {
	    	lb_user_add_msg.setText("필수 입력 정보를 확인해주세요.");  	
	    	
	    }else {
	    	sql = "insert into person (person_name,tel,addr,birth) values(?,?,?,?)";
	    	ps = con.prepareStatement(sql);
	    	ps.setString(1, tf_user_add_name.getText());
	    	ps.setString(2, tf_user_add_tel.getText());
	    	ps.setString(3, tf_user_add_addr.getText());
	    	ps.setString(4, tf_user_add_birth.getText());
	    	ps.executeUpdate();
	    	
	    	lb_user_add_msg.setText(tf_user_add_name.getText()+"님의 등록이 완료되었습니다.");  	
	    	
	    	ta_user_list.setText(userList());	
	    }
	    ps.close();
	    con.close();
	}


	// 회원 정보 수정 화면 메서드
	public void userUpdateView(String str) {

		p_main = new Panel(new BorderLayout(10, 10));
		lb_user_update_title = new Label("회원 정보 수정", Label.CENTER);
		p_main.add(lb_user_update_title, "North");

		Panel pCenter = new Panel(new BorderLayout(20, 20));
		Panel pCenterWest = new Panel(new GridLayout(6, 2, 5, 15));
		Panel pCenterCenter = new Panel(new BorderLayout(5, 5));
		Panel p_user_info = new Panel(new GridLayout(1, 2, 5, 5));

		
		lb_user_update_id = new Label("회원 번호 : ");
		tf_user_update_id = new TextField("");
		bt_user_update_uinfo = new Button("검색");
		lb_user_update_name = new Label("회원 이름 : ");
		tf_user_update_name = new TextField("");
		lb_user_update_addr = new Label("주소 : ");
		tf_user_update_addr = new TextField("");
		lb_user_update_tel = new Label("연락처 : ");
		tf_user_update_tel = new TextField("");
		lb_user_update_birth = new Label("생년월일 : ");
		tf_user_update_birth = new TextField("");
		bt_user_update = new Button("수정하기");
		ta_user_update_list = new TextArea(str, 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		ta_user_update_list.setEditable(false);

		p_user_info.add(tf_user_update_id);
		p_user_info.add(bt_user_update_uinfo);
		
		pCenterWest.add(new Label());
		pCenterWest.add(new Label());
		pCenterWest.add(lb_user_update_id);
		pCenterWest.add(p_user_info);
		pCenterWest.add(lb_user_update_name);
		pCenterWest.add(tf_user_update_name);
		pCenterWest.add(lb_user_update_addr);
		pCenterWest.add(tf_user_update_addr);
		pCenterWest.add(lb_user_update_tel);
		pCenterWest.add(tf_user_update_tel);
		pCenterWest.add(lb_user_update_birth);
		pCenterWest.add(tf_user_update_birth);

		pCenterCenter.add(new Label("회원 정보 현황", Label.CENTER), "North");
		pCenterCenter.add(ta_user_update_list, "Center");
		pCenter.add(pCenterWest, "West");
		pCenter.add(pCenterCenter, "Center");
		p_main.add(pCenter, "Center");

		Panel pSouth = new Panel(new BorderLayout(5, 5));
		Panel pSouthSouth = new Panel();
		lb_user_update_msg = new Label("메세지 : ");
		bt_user_update = new Button("수정하기");
		pSouth.add(lb_user_update_msg);
		pSouthSouth.add(bt_user_update, "South");
		pSouth.add(pSouthSouth, "South");
		p_main.add(pSouth, "South");

		bt_user_update_uinfo.addActionListener(this);
		bt_user_update.addActionListener(this);

	}

	public void userInfo() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pwd = "1234";
		Connection con = DriverManager.getConnection(url, user, pwd);
		sql = "select * from person where person_id = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, tf_user_update_id.getText());
		rs = ps.executeQuery();
		if(rs.next()) {
			tf_user_update_name.setText(rs.getString("person_name"));
			tf_user_update_addr.setText(rs.getString("addr"));
			tf_user_update_tel.setText(rs.getString("tel"));
			tf_user_update_birth.setText(rs.getString("birth"));
		}else {
			if(tf_user_update_id.getText().equals("")) {
				lb_user_update_msg.setText("회원번호가 입력되지 않았습니다.");								
			}else {
				lb_user_update_msg.setText("등록되지 않은 회원번호입니다. 회원번호를 확인해 주세요.");				
			}
		}
		rs.close();
		ps.close();
		con.close();
	}
	
	// 회원 정보 수정 메서드
	public void userUpdate() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pwd = "1234";
		Connection con = DriverManager.getConnection(url, user, pwd);
		
		if(tf_user_update_name.getText().equals("") || tf_user_update_addr.getText().equals("") || tf_user_update_tel.getText().equals("") || tf_user_update_birth.getText().equals("")) {
			lb_user_update_msg.setText("입력되지 않은 정보가 있습니다. 입력 정보를 확인해주세요.");
		}else {
			sql = "update person set person_name = ? , addr = ?, tel = ?, birth = ? where person_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, tf_user_update_name.getText());
			ps.setString(2, tf_user_update_addr.getText());
			ps.setString(3, tf_user_update_tel.getText());
			ps.setString(4, tf_user_update_birth.getText());
			ps.setString(5, tf_user_update_id.getText());
			int count = ps.executeUpdate();
			if (count > 0) {
				lb_user_update_msg.setText(tf_user_update_id.getText() + "번 회원 정보가 수정 되었습니다.");
				tf_user_update_id.setText("");
				tf_user_update_name.setText("");
				tf_user_update_addr.setText("");
				tf_user_update_tel.setText("");
				tf_user_update_birth.setText("");
			} else {
				lb_user_update_msg.setText("회원 정보 변경에 실패 하였습니다.");
			}
			ta_user_update_list.setText(userList());		
		}
		
		ps.close();
		con.close();
	}

	// 책 정보 현황 메서드
	public String bookList() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pwd = "1234";

		Connection con = DriverManager.getConnection(url, user, pwd);
		sql = "select * from book order by book_id asc";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		String str = "책번호\t"
					+String.format("%-"+(45-("도서명".length()*2))+"s", "도서명") + "\t\t"
					+ String.format("%-"+(20-("출판사".length()*2))+"s", "출판사") + "\t"
					+ "저자\n";
		while (rs.next()) {
			str += rs.getString("book_id") + "\t"
					+ String.format("%-"+(55-(rs.getString("book_name").length()*2))+"s", rs.getString("book_name"))+"\t" 
					+ String.format("%-"+(20-(rs.getString("publisher").length()*2))+"s", rs.getString("publisher"))+"\t"
					+ rs.getString("author") + "\n";
		}
		rs.close();
		ps.close();
		con.close();
		return str;
	}

	// 책 정보 등록 화면 메서드
	public void bookAddView(String str) {
		p_main = new Panel(new BorderLayout(10, 10));
		lb_book_add_title = new Label("책 정보 등록", Label.CENTER);
		p_main.add(lb_book_add_title, "North");

		Panel p_center_temp = new Panel(new BorderLayout(20, 20));
		Panel p_center_temp_west = new Panel(new GridLayout(8, 1, 5, 5));
		Panel p_center_temp_center = new Panel(new BorderLayout(5, 5));

		lb_book_add_bname = new Label("도서명 :                        ");
		lb_book_add_genre = new Label("장르 :                        ");
		lb_book_add_author = new Label("저자 :                        ");
		lb_book_add_publisher = new Label("출판사 :                        ");
		tf_book_add_bname = new TextField("");
		tf_book_add_genre = new TextField("");
		tf_book_add_author = new TextField("");
		tf_book_add_publisher = new TextField("");
		ta_book_add_list = new TextArea(str);
		ta_book_add_list.setEditable(false);
		
		p_center_temp_west.add(lb_book_add_bname);
		p_center_temp_west.add(tf_book_add_bname);
		p_center_temp_west.add(lb_book_add_genre);
		p_center_temp_west.add(tf_book_add_genre);
		p_center_temp_west.add(lb_book_add_author);
		p_center_temp_west.add(tf_book_add_author);
		p_center_temp_west.add(lb_book_add_publisher);
		p_center_temp_west.add(tf_book_add_publisher);

		p_center_temp_center.add(new Label("책 정보 현황", Label.CENTER), "North");
		p_center_temp_center.add(ta_book_add_list, "Center");
		p_center_temp.add(p_center_temp_west, "West");
		p_center_temp.add(p_center_temp_center, "Center");
		p_main.add(p_center_temp, "Center");

		Panel p_south_temp = new Panel(new BorderLayout(5, 5));
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
	    if(tf_book_add_bname.getText().equals("") || tf_book_add_author.getText().equals("") || tf_book_add_publisher.getText().equals("")) {
	    	lb_book_add_msg.setText("입력되지 않은 정보가 있습니다. 입력 정보를 확인해주세요.");
	    }else {
	    	sql = "insert into book (book_id, book_name, author, publisher) "
	    			+ "values (" + genreId.get(tf_book_add_genre.getText()) + " ,?,?,?)";
	    	ps = con.prepareStatement(sql);
	    	
	    	ps.setString(1, tf_book_add_bname.getText());
	    	ps.setString(2, tf_book_add_author.getText());
	    	ps.setString(3, tf_book_add_publisher.getText());
	    	ps.executeUpdate();
	    	lb_book_add_msg.setText(tf_book_add_bname.getText()+" 책의 신규 등록이 완료되었습니다.");
	    	
	    	ta_book_add_list.setText(bookList());    	
	    }
	    ps.close();
	    con.close();
	}
	
	public void bookDeleteView(String str) {
		p_main = new Panel(new BorderLayout(10,10));
		lb_book_delete_title = new Label("책 정보 삭제", Label.CENTER);
		p_main.add(lb_book_delete_title, "North");
		
		Panel p_center_temp = new Panel(new BorderLayout(20,20));
		Panel p_center_temp_west = new Panel(new GridLayout(6,2,5,13));
		Panel p_center_temp_center = new Panel(new BorderLayout(5,5));
		
		lb_book_delete_bid = new Label("책 번호 : ");
		tf_book_delete_bid = new TextField("");
		ta_book_delete_list = new TextArea(str);
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
		if(tf_book_delete_bid.getText().equals("")) {
			lb_book_delete_msg.setText("책 번호가 입력되지 않았습니다.");			
		}else {
			sql = "delete from records where book_id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, tf_book_delete_bid.getText());
			ps.executeUpdate();	
			sql = "delete from book where book_id=? ";
			ps = con.prepareStatement(sql);
			ps.setString(1, tf_book_delete_bid.getText());
			int count = ps.executeUpdate();
			if(count == 0) {
				lb_book_delete_msg.setText("해당 책에 대한 정보가 등록되어 있지 않습니다. 입력한 책 번호를 확인해주세요.");
			}else {
				lb_book_delete_msg.setText(tf_book_delete_bid.getText()+" 번 책의 정보가 삭제 되었습니다.");
			}
			ta_book_delete_list.setText(bookList());
			
		}
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
		String str = "책번호\t\t회원번호\t대여일\n";
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
		
		lb_book_lend_pid = new Label("회원 번호 : ");
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
		cb_book = new Checkbox("도서명으로 검색", cg_options, true);
		cb_person = new Checkbox("사람 이름으로 검색", cg_options, false);
		tf_search_info_key = new TextField();
		bt_search_info = new Button("검색");
		p_tf_and_bt.add(cb_book);
		p_tf_and_bt.add(cb_person);
		p_tf_and_bt.add(tf_search_info_key);
		p_tf_and_bt.add(bt_search_info);
		p_top.add(p_tf_and_bt);
		p_center_temp.add(p_top, "North");
		
		ta_show_result = new TextArea("");
		ta_show_result.setEditable(false);
		
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
					+ "        WHEN book_id IN (SELECT book_id FROM records) THEN '대여 불가' "
					+ "        ELSE '대여 가능' "
					+ "    END AS status "
					+ "FROM book, genres "
					+ "WHERE book_id BETWEEN min AND max "
					+ "    AND book_name LIKE ? "
					+ "ORDER BY book_id ASC");
			ps.setString(1, "%" + key + "%");
			rs = ps.executeQuery();
			
			ta_show_result.setText("");

			ta_show_result.append("책번호\t도서명\t\t\t\t\t저자\t\t\t출판사\t\t장르\t대여상태\n");
			
			while (rs.next()) {
				String row = rs.getInt("book_id") + "\t" + String.format("%-"+(55-(rs.getString("book_name").length()*2))+"s", rs.getString("book_name")) + "\t" 
						+ String.format("%-"+(30-(rs.getString("author").length()*2))+"s", rs.getString("author")) + "\t" + String.format("%-"+(20-(rs.getString("publisher").length()*2))+"s", rs.getString("publisher")) + "\t"
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
					+ "AND person_name LIKE ? order by person.person_id asc");
			ps.setString(1, "%" + key + "%");
			rs = ps.executeQuery();
			
			ta_show_result.setText("");

			ta_show_result.append("사용자ID\t 이름\t\t연락처\t\t   주소\t\t    생년월일\t대여가능도서(최대 5권)\n");
			
			while (rs.next()) {
				String row = rs.getInt("person_id") + "\t " + String.format("%-"+(23-(rs.getString("person_name").length()*2))+"s", rs.getString("person_name"))
						+ rs.getString("tel") + "\t   " + String.format("%-"+(25-(rs.getString("addr").length()*2))+"s", rs.getString("addr"))
						+ rs.getString("birth") + "\t" + rs.getInt("lend_limit") + "권 ("+(5-rs.getInt("lend_limit"))+"권 대여중)\n";
				ta_show_result.append(row);
			}
		}
		
		rs.close();
		ps.close();
		con.close();
	}
	
	// 연체정보 화면 메서드
	public void delayInfoView() {

		p_main = new Panel(new BorderLayout(10, 10));
		lb_delay_info_title = new Label("연체 회원 정보", Label.CENTER);
		;

		p_main_c = new Panel(new BorderLayout(5, 5));
		p_main_s = new Panel(new GridLayout(1, 3));
		Panel p_main_n = new Panel(new GridLayout(1, 3));

		// 새로고침 버튼
		bt_delay = new Button("새로고침");
		bt_delay.setSize(100, 100);

		// 현재 시간 넣기

		p_main.add(p_main_c, "Center");
		p_main.add(p_main_s, "South");
		p_main.add(p_main_n, "North");

		p_main_n.add(new Label(" "));
		p_main_n.add(lb_delay_info_title);
		p_main_n.add(bt_delay);

		Panel p_main_c_n = new Panel(new GridLayout(1, 4, 10, 10));
		p_main_c.add(p_main_c_n, "North");

		Label l_book_id_sub = new Label("대여번호");
		Label l_person_name_sub = new Label("회원이름");
		Label l_book_name_sub = new Label("대여날짜");
		Label l_event_time_sub = new Label("책제목");

		p_main_c_n.add(l_book_id_sub);
		p_main_c_n.add(l_person_name_sub);
		p_main_c_n.add(l_book_name_sub);
		p_main_c_n.add(l_event_time_sub);

		l_delay = new List(0, false);

		day_delay = new Label("연체일 : ");

		bt_delay.addActionListener(this);
		p_main_c.revalidate();
		p_main_c.repaint();

	}

	public void delayInfo() throws Exception {
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

		p_main_c.add(l_delay, "Center");
		String blank = " ";
		int a = 40;
		int b = 40;

		lb_time = new Label(" ");

		p_main_s.add(lb_time);

		startTimer();

		p_main_s.add(new Label(" "));

		List time_cal = new List(0, false);

		while (rs.next()) {

			a -= String.valueOf(rs.getInt("records_id")).length();
			b -= 3 * (rs.getString("person_name").length());
			l_delay.add(rs.getInt("records_id") + blank.repeat(a) + rs.getString("person_name") + blank.repeat(b)
					+ rs.getString("event_time") + blank.repeat(22) + rs.getString("book_name"));
			a = 40;
			b = 40;
			time_cal.add(rs.getString("event_time"));
		}

		l_delay.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					p_main_s.remove(day_delay);

					time_cal.select(l_delay.getSelectedIndex());
					String selectedString = time_cal.getSelectedItem();

					try {
						SimpleDateFormat time_format = new SimpleDateFormat("yyyy-MM-dd");
						Date selectedDate = time_format.parse(selectedString);
						Date now = new Date();

						long delay_time = (now.getTime() - selectedDate.getTime()) / (24 * 60 * 60 * 1000);

						day_delay = new Label("연체일 : " + delay_time);
						p_main_s.add(day_delay);

						p_main_c.revalidate();
						p_main_c.repaint();
					} catch (Exception ex) {
						ex.printStackTrace();
						p_main_c.revalidate();
						p_main_c.repaint();
					}

				}

			}
		});

		rs.close();
		ps.close();
		con.close();
	}

	public void startTimer() {
		new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						updateTimeLabel();
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					return;
				}
			}
		}).start();
	}

	public void updateTimeLabel() {
		Date now = new Date();
		SimpleDateFormat time_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedNow = time_format.format(now);
		EventQueue.invokeLater(() -> lb_time.setText("현재시간 : " + formattedNow));
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
		BookManagement bm = new BookManagement(false);
		bm.setSize(600,400);
		bm.setVisible(true);
		bm.setResizable(false);
	}
}