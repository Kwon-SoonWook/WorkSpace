package bookManagement;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.util.*;

public class BookManagement_nam extends Frame implements ActionListener {
	//DB����
	PreparedStatement ps, ps2, ps3;
	ResultSet rs, rs2, rs3;
	String sql;
	

	//����
	Menu menu;
	MenuBar menubar;
	MenuItem user_add, book_add, book_lend, search_info, delay_info, book_topten, close;
	Panel p_main;
	
	//�ʱ� ȭ��
	Label lb_main;
	
	//����� ���
	Label lb_user_add_title, lb_user_add_name, lb_user_add_birth, lb_user_add_addr, lb_user_add_tel, lb_user_add_msg;
	TextField tf_user_add_name, tf_user_add_addr, tf_user_add_tel, tf_user_add_birth;
	Button bt_user_add;
	
	//å �űԵ�� �� ����
	Label lb_book_add_title, lb_book_add_genre, lb_book_add_bname, lb_book_add_author, lb_book_add_publisher, lb_book_add_msg;
	TextField tf_book_add_bname, tf_book_add_genre, tf_book_add_author, tf_book_add_publisher;
	Button bt_book_add, bt_book_delete;
	
	//å �뿩 �ݳ�
	Label lb_book_lend_title, lb_book_lend_pid, lb_book_lend_bid, lb_book_lend_msg;
	TextField tf_book_lend_pid, tf_book_lend_bid;
	Button bt_book_lend, bt_book_return;
	
	//�˻��ϱ�
	Label lb_search_info_title;
	CheckboxGroup cg_options;
	Checkbox cb_book, cb_person;
	Button bt_search_info;
	TextField tf_search_info_key;
	Panel p_show_result;
	
	//��ü��������
	Label lb_delay_info_title, lb_delay_info_bname, lb_delay_info_msg;
	Panel p_main_c_n;
	
	//�α����
	Label lb_rank_info_title;
	Panel p_main_rank;
	
	
	
	
	public BookManagement_nam() throws Exception{
		p_main = new Panel(new BorderLayout());
		lb_main = new Label("�������� ���α׷� v3.0", Label.CENTER);
		p_main.add(lb_main);
		this.add(p_main);
		
		menubar = new MenuBar();
		this.setMenuBar(menubar);
		menu = new Menu("�޴�");
		menubar.add(menu);
		user_add = new MenuItem("ȸ������ ���");
		book_add = new MenuItem("å �űԵ�� �� ����");
		book_lend = new MenuItem("å �뿩 �� �ݳ�");
		search_info = new MenuItem("�˻��ϱ�");
		delay_info = new MenuItem("��ü ���� ����");
		book_topten = new MenuItem("�뿩 ž10 å ����");
		close = new MenuItem("�ݱ�");
		
		menu.add(user_add);
		menu.add(book_add);
		menu.add(book_lend);
		menu.add(search_info);
		menu.add(delay_info);
		menu.add(book_topten);
		menu.addSeparator();
		menu.add(close);
		
		user_add.addActionListener(this);
		book_lend.addActionListener(this);
		book_add.addActionListener(this);
		search_info.addActionListener(this);
		delay_info.addActionListener(this);
		book_topten.addActionListener(this);
		close.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == user_add) {
			this.remove(p_main);
			userAddView();
			this.add(p_main);
			this.validate();
		}else if(obj == book_add) {
			this.remove(p_main);
			bookAddView();
			this.add(p_main);
			this.validate();
		}else if(obj == book_lend) {
			this.remove(p_main);
			bookLendView();
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
			this.add(p_main);
			this.validate();
			try {
				delayInfo();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(obj == book_topten) {
			this.remove(p_main);
			topTenView();
			this.add(p_main);
			this.validate();
		}else if(obj == close) {
			System.exit(0);
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
		}
	}
	//����� ��� ȭ�� �޼���
	public void userAddView() {
		p_main = new Panel(new BorderLayout(10,10));
		lb_user_add_title = new Label("����� ���", Label.CENTER);
		p_main.add(lb_user_add_title, "North");
		
		Panel p_center_temp = new Panel(new GridLayout(4,2,5,5));
		lb_user_add_name = new Label("����� �̸� : ", Label.CENTER);
		lb_user_add_birth = new Label("������� : ", Label.CENTER);
		lb_user_add_addr = new Label("�ּ� : ", Label.CENTER);
		lb_user_add_tel = new Label("��ȭ��ȣ : ", Label.CENTER);
		tf_user_add_name = new TextField();
		tf_user_add_name.setColumns(50);
		tf_user_add_birth = new TextField();
		tf_user_add_birth.setColumns(50);
		tf_user_add_addr = new TextField();
		tf_user_add_addr.setColumns(50);
		tf_user_add_tel = new TextField();
		tf_user_add_tel.setColumns(50);
		p_center_temp.add(lb_user_add_name);
		p_center_temp.add(tf_user_add_name);
		p_center_temp.add(lb_user_add_birth);
		p_center_temp.add(tf_user_add_birth);
		p_center_temp.add(lb_user_add_addr);
		p_center_temp.add(tf_user_add_addr);
		p_center_temp.add(lb_user_add_tel);
		p_center_temp.add(tf_user_add_tel);
		p_main.add(p_center_temp, "Center");
		
		Panel p_south_temp = new Panel(new BorderLayout(5,5));
		Panel p_south_south = new Panel();
		lb_user_add_msg = new Label("�޼��� : ");
		bt_user_add = new Button("���");
		p_south_temp.add(lb_user_add_msg);
		p_south_south.add(bt_user_add);
		p_south_temp.add(p_south_south, "South");
		p_main.add(p_south_temp, "South");
		
		bt_user_add.addActionListener(this);
	}
	
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
		
		lb_user_add_msg.setText("����� �Ϸ�Ǿ����ϴ�.");
		
		ps.close();
		con.close();
	}
	
	//å �ű� ��� �� ���� ȭ�� �޼���
	public void bookAddView() {
		p_main = new Panel(new BorderLayout(10,10));
		lb_book_add_title = new Label("å �űԵ�� �� ����", Label.CENTER);
		p_main.add(lb_book_add_title, "North");
		
		Panel p_center_temp = new Panel(new GridLayout(4,2,5,5));
		lb_book_add_genre = new Label("�帣 �Է� : ", Label.CENTER);
		lb_book_add_bname = new Label("å �̸� �Է� : ", Label.CENTER);
		lb_book_add_author = new Label("���� �Է� : ", Label.CENTER);
		lb_book_add_publisher = new Label("���ǻ� �Է� : ", Label.CENTER);
		tf_book_add_genre = new TextField();
		tf_book_add_bname = new TextField();
		tf_book_add_author = new TextField();
		tf_book_add_publisher = new TextField();		
		p_center_temp.add(lb_book_add_genre);
		p_center_temp.add(tf_book_add_genre);
		p_center_temp.add(lb_book_add_bname);
		p_center_temp.add(tf_book_add_bname);
		p_center_temp.add(lb_book_add_author);
		p_center_temp.add(tf_book_add_author);
		p_center_temp.add(lb_book_add_publisher);
		p_center_temp.add(tf_book_add_publisher);
		p_main.add(p_center_temp, "Center");
		
		Panel p_south_temp = new Panel(new BorderLayout(5,5));
		Panel p_south_south = new Panel();
		lb_book_add_msg = new Label("�޼��� : ");
		bt_book_add = new Button("����ϱ�");
		bt_book_delete = new Button("�����ϱ�");
		p_south_temp.add(lb_book_add_msg);
		p_south_south.add(bt_book_add);
		p_south_south.add(bt_book_delete);
		p_south_temp.add(p_south_south, "South");
		p_main.add(p_south_temp, "South");
		
		bt_book_add.addActionListener(this);
		bt_book_delete.addActionListener(this);
	}
	
	//å ��� �޼���
	 	public void bookAdd() throws Exception{
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      String url ="jdbc:oracle:thin:@localhost:1521:xe";
	      String user ="scott";
	      String pwd ="1234";
	      Connection con = DriverManager.getConnection(url, user, pwd);	
	      
	      Map<String, String> genreId = new HashMap<String, String>();
	      genreId.put("ö��",	 "philosophy_sq.NEXTVAL");
	      genreId.put("����",	 "literature_sq.NEXTVAL");
	      genreId.put("����",	 "science_sq.NEXTVAL");
	      
	      sql = "insert into book (book_id, book_name, author, publisher) "
	      		+ "values (" + genreId.get(tf_book_add_genre.getText()) + " ,?,?,?)";
	      ps = con.prepareStatement(sql);
	      
	      ps.setString(1, tf_book_add_bname.getText());
	      ps.setString(2, tf_book_add_author.getText());
	      ps.setString(3, tf_book_add_publisher.getText());
	      ps.executeUpdate();
	      lb_book_add_msg.setText(tf_book_add_bname.getText()+" å�� �ű� ����� �Ϸ�Ǿ����ϴ�.");   
	      ps.close();
	      con.close();
	   }
	   
	//å ���� �޼���
	   public void bookDelete() throws Exception{
		      Class.forName("oracle.jdbc.driver.OracleDriver");
		      String url ="jdbc:oracle:thin:@localhost:1521:xe";
		      String user ="scott";
		      String pwd ="1234";
		      Connection con = DriverManager.getConnection(url, user, pwd);
		      
		      sql = "delete from book where book_id=? ";
		      ps = con.prepareStatement(sql);
		      ps.setString(1, tf_book_add_genre.getText());
		      int count = ps.executeUpdate();
		      if(count == 0) {
		         lb_book_add_msg.setText("�ش� å ��ȣ�� ���� ��ȣ�Դϴ�.");
		      }else {
		         lb_book_add_msg.setText(tf_book_add_genre.getText()+" ���� å ������ ���� �Ǿ����ϴ�.");
		      }
		      ps.close();
		      con.close();
		   }
	
	//å �뿩 �� �ݳ� ȭ�� �޼���
	public void bookLendView() {
		p_main = new Panel(new BorderLayout(10,10));
		lb_book_lend_title = new Label("å �뿩 �� �ݳ�", Label.CENTER);
		p_main.add(lb_book_lend_title, "North");
		
		Panel p_center_temp = new Panel(new GridLayout(2,2,5,5));		
		lb_book_lend_pid = new Label("����� ��ȣ �Է� : ", Label.CENTER);
		tf_book_lend_pid = new TextField();
		lb_book_lend_bid = new Label("å ��ȣ �Է� : ", Label.CENTER);
		tf_book_lend_bid = new TextField();
		p_center_temp.add(lb_book_lend_pid);
		p_center_temp.add(tf_book_lend_pid);
		p_center_temp.add(lb_book_lend_bid);
		p_center_temp.add(tf_book_lend_bid);
		p_main.add(p_center_temp, "Center");
		
		Panel p_south_temp = new Panel(new BorderLayout(5,5));
		Panel p_south_south = new Panel();
		lb_book_lend_msg = new Label("�޼��� : ");
		bt_book_lend = new Button("�뿩�ϱ�");
		bt_book_return = new Button("�ݳ��ϱ�");
		p_south_temp.add(lb_book_lend_msg);
		p_south_south.add(bt_book_lend);
		p_south_south.add(bt_book_return);
		p_south_temp.add(p_south_south, "South");
		p_main.add(p_south_temp, "South");
		
		bt_book_lend.addActionListener(this);
		bt_book_return.addActionListener(this);
	}
	
	//å �뿩 �޼���
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
			lb_book_lend_msg.setText(person_id+"�� ����ڴ��� �̹� 5���� å�� �����̽��ϴ�. �ݳ� �� �̿����ּ���.");			
		}
		else {
			sql = "select * from records where book_id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, book_id);
			rs = ps.executeQuery();
			if(rs.next()) {
				lb_book_lend_msg.setText(book_id+"�� å�� �뿩 �� �����Դϴ�.");
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
					lb_book_lend_msg.setText(person_id+"�� ����ڴ���"+book_id+"�� å �뿩�� �Ϸ�Ǿ����ϴ�.");
				}
				else {
					lb_book_lend_msg.setText(book_id+"�� å�� ��ϵ��� �ʾҽ��ϴ�. å ��ȣ�� Ȯ�����ּ���.");				
				}
			}
		}			
		rs.close();
		ps.close();
		con.close();
	}
	
	//å �ݳ� �޼���
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
				lb_book_lend_msg.setText(person_id+"�� ����ڷκ��� "+book_id+"�� å�� �ݳ��Ǿ����ϴ�.");
			}else {
				lb_book_lend_msg.setText(person_id+"����ڴ��� "+book_id+"�� å�� �뿩���� �ʾҽ��ϴ�. å ��ȣ�� Ȯ�����ּ���.");
			}
		}else {
			lb_book_lend_msg.setText(book_id+"�� å�� ��ϵ��� �ʾҽ��ϴ�. å ��ȣ�� Ȯ�����ּ���.");
		}
		rs.close();
		ps.close();
		con.close();
	}
	
	
	//�˻��ϱ� ȭ�� �޼���
	public void searchInfoView() {
		p_main = new Panel(new BorderLayout(10,10));
		lb_search_info_title = new Label("�˻��ϱ�", Label.CENTER);
		p_main.add(lb_search_info_title, "North");
		
		Panel p_center_temp = new Panel(new BorderLayout(5, 5));
		Panel p_top = new Panel(new FlowLayout(FlowLayout.CENTER));
		
		Panel p_tf_and_bt = new Panel(new GridLayout(1, 4, 5, 5));
		cg_options = new CheckboxGroup();
		cb_book = new Checkbox("�������� å ã��", cg_options, true);
		cb_person = new Checkbox("�̸����� ��� ã��", cg_options, false);
		tf_search_info_key = new TextField();
		bt_search_info = new Button("�˻�");
		p_tf_and_bt.add(cb_book);
		p_tf_and_bt.add(cb_person);
		p_tf_and_bt.add(tf_search_info_key);
		p_tf_and_bt.add(bt_search_info);
		p_top.add(p_tf_and_bt);
		p_center_temp.add(p_top, "North");
		
		Panel p_show_result_temp = new Panel(new BorderLayout(5, 5));
		
		p_show_result = new Panel(new GridLayout(0, 5, 5, 5));
		p_show_result_temp.add(p_show_result, "North");
		
		p_center_temp.add(p_show_result_temp, "Center");
		
		p_main.add(p_center_temp, "Center");
		
		bt_search_info.addActionListener(this);
	}
	
	//�˻��ϱ� �޼���
	public void searchInfo() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pwd = "1234";
		Connection con = DriverManager.getConnection(url,user,pwd);
		
		String key = tf_search_info_key.getText();
		
		if (cb_book.getState()) {
			ps = con.prepareStatement("SELECT * FROM book WHERE book_name LIKE ?");
			ps.setString(1, "%" + key + "%");
			rs = ps.executeQuery();
			
			p_show_result.removeAll();
			
			p_show_result.add(new Label("book_id"));
			p_show_result.add(new Label("book_name"));
			p_show_result.add(new Label("author"));
			p_show_result.add(new Label("publisher"));
			p_show_result.add(new Label("lend_count"));
			
			while (rs.next()) {
				p_show_result.add(new Label(Integer.toString(rs.getInt("book_id"))));
				p_show_result.add(new Label(rs.getString("book_name")));
				p_show_result.add(new Label(rs.getString("author")));
				p_show_result.add(new Label(rs.getString("publisher")));
				p_show_result.add(new Label(Integer.toString(rs.getInt("lend_count"))));
			}
		}
		else if (cb_person.getState()){
			ps = con.prepareStatement("SELECT * FROM person WHERE person_name LIKE ?");
			ps.setString(1, "%" + key + "%");
			rs = ps.executeQuery();
			
			p_show_result.removeAll();
			
			p_show_result.add(new Label("person_id"));
			p_show_result.add(new Label("person_name"));
			p_show_result.add(new Label("tel"));
			p_show_result.add(new Label("addr"));
			p_show_result.add(new Label("birth"));
			
			while (rs.next()) {
				p_show_result.add(new Label(rs.getString("person_id")));
				p_show_result.add(new Label(rs.getString("person_name")));
				p_show_result.add(new Label(rs.getString("tel")));
				p_show_result.add(new Label(rs.getString("addr")));
				p_show_result.add(new Label(rs.getString("birth")));
			}
		}
		
		p_show_result.revalidate();
		rs.close();
		ps.close();
		con.close();
	}
	
	//��ü���� ȭ�� �޼���
	public void delayInfoView() {
		p_main = new Panel(new BorderLayout(10,10));
		lb_delay_info_title = new Label("��ü ȸ�� ����",Label.CENTER);
		p_main.add(lb_delay_info_title,"North");
		Panel p_main_c = new Panel(new BorderLayout(5,5));
		
		p_main.add(p_main_c,"Center");
		p_main_c_n = new Panel(new GridLayout(0,4,5,5));
		p_main_c_n.add(new Label("record_id"));
		p_main_c_n.add(new Label("book_id"));
		p_main_c_n.add(new Label("person_id"));
		p_main_c_n.add(new Label("event_time"));
		
		p_main_c.add(p_main_c_n,"North");
	}
	
	//��ü���� �޼���
	public void delayInfo() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pwd = "1234";
		
		Connection con = DriverManager.getConnection(url,user,pwd);
		sql = "select records_id,book_id,person_id,to_char(TRUNC(event_time),'YYYY-MM-DD') as event_time\r\n"
				+ "from records"+" where event_time+14>systimestamp";//�뿩���� 2�� ���� ȸ�� Ȯ��
		ps = con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		while(rs.next()) {
			p_main_c_n.add(new Label("\t"+Integer.toString(rs.getInt("records_id"))));
			p_main_c_n.add(new Label("\t"+Integer.toString(rs.getInt("book_id"))));
			p_main_c_n.add(new Label("\t"+Integer.toString(rs.getInt("person_id"))));
			p_main_c_n.add(new Label(rs.getString("event_time")));
		}
		
		p_main_c_n.revalidate();
		p_main_c_n.repaint();  
		rs.close();
		ps.close();
		con.close();
	}
	//�α���� ȭ�� �޼���
	public void topTenView() {
		p_main = new Panel(new BorderLayout(10,10));
		lb_rank_info_title = new Label("������ �α����",Label.CENTER);
		p_main.add(lb_rank_info_title,"North");
		p_main_rank = new Panel(new GridLayout(2,1,10,10));
		p_main.add(p_main_rank,"Center");
		
		Panel p_rank_one = new Panel(new GridLayout(3,2,30,20));
		Panel p_rank_two = new Panel(new GridLayout(7,2,10,20));
		
		p_main_rank.add(p_rank_one);
		p_main_rank.add(p_rank_two);
		
		p_rank_one.add(new Label("1�� : "));
		p_rank_one.add(new Label("2�� : "));
		p_rank_one.add(new Label("3�� : "));
		p_rank_two.add(new Label("4�� : "));
		p_rank_two.add(new Label("5�� : "));
		p_rank_two.add(new Label("6�� : "));
		p_rank_two.add(new Label("7�� : "));
		p_rank_two.add(new Label("8�� : "));
		p_rank_two.add(new Label("9�� : "));
		p_rank_two.add(new Label("10�� : "));
	}

	
	public static void main(String[] args) throws Exception{
		BookManagement_nam bm = new BookManagement_nam();
		bm.setSize(800,800);
		bm.setVisible(true);
	}
}