package bookManagement;
import java.awt.*;
import java.awt.event.*;
public class BookManagement extends Frame implements ActionListener {
	//공통
	Menu menu;
	MenuBar menubar;
	MenuItem user_add, book_add, book_lend, search_info, delay_info, book_topten, close;
	Panel p_main;
	
	//초기 화면
	Label lb_main;
	
	//사용자 등록
	Label lb_user_add_title, lb_user_add_name, lb_user_add_birth, lb_user_add_addr, lb_user_add_tel, lb_user_add_msg;
	TextField tf_user_add_name, tf_user_add_addr, tf_user_add_tel, tf_user_add_birth;
	Button bt_user_add;
	
	//책 신규등록 및 삭제
	Label lb_book_add_title, lb_book_add_bname, lb_book_add_author, lb_book_add_publisher, lb_book_add_msg;
	TextField tf_book_add_bname, tf_book_add_author, tf_book_add_publisher;
	Button bt_book_add, bt_book_delete;
	
	//책 대여 반납
	Label lb_book_lend_title, lb_book_lend_bid, lb_book_lend_msg;
	TextField tf_book_lend_bid;
	Button bt_book_lend, bt_book_return;
	
	//검색하기 
	Label lb_search_info_title;
	Button bt_search_info_uname, bt_search_info_bname;
	
	//연체정보보기
	Label lb_delay_info_title, lb_delay_info_bname, lb_delay_info_msg;
	TextField tf_delay_info_bname;
	
	
	public BookManagement() {
		p_main = new Panel(new BorderLayout());
		lb_main = new Label("도서관리 프로그램 v3.0", Label.CENTER);
		p_main.add(lb_main);
		this.add(p_main);
		
		menubar = new MenuBar();
		this.setMenuBar(menubar);
		menu = new Menu("메뉴");
		menubar.add(menu);
		user_add = new MenuItem("회원정보 등록");
		book_add = new MenuItem("책 신규등록 및 삭제");
		book_lend = new MenuItem("책 대여 및 반납");
		search_info = new MenuItem("검색하기");
		delay_info = new MenuItem("연체 정보 보기");
		book_topten = new MenuItem("대여 탑10 책 정보");
		close = new MenuItem("닫기");
		
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
			userAdd();
			this.add(p_main);
			this.validate();
		}else if(obj == book_add) {
			this.remove(p_main);
			bookAdd();
			this.add(p_main);
			this.validate();
		}else if(obj == book_lend) {
			this.remove(p_main);
			bookLend();
			this.add(p_main);
			this.validate();
		}else if(obj == search_info) {
			this.remove(p_main);
			searchInfo();
			this.add(p_main);
			this.validate();
		}else if(obj == delay_info) {
			
		}else if(obj == book_topten) {
			
		}else if(obj == close) {
			System.exit(0);
		}
		
	}
	//사용자 등록 화면 메서드
	public void userAdd() {
		p_main = new Panel(new BorderLayout(10,10));
		lb_user_add_title = new Label("사용자 등록", Label.CENTER);
		p_main.add(lb_user_add_title, "North");
		
		Panel p_center_temp = new Panel(new GridLayout(4,2,5,5));
		lb_user_add_name = new Label("사용자 이름 : ", Label.CENTER);
		lb_user_add_birth = new Label("생년월일 : ", Label.CENTER);
		lb_user_add_addr = new Label("주소 : ", Label.CENTER);
		lb_user_add_tel = new Label("전화번호 : ", Label.CENTER);
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
		lb_user_add_msg = new Label("메세지 : ");
		bt_user_add = new Button("등록");
		p_south_temp.add(lb_user_add_msg);
		p_south_south.add(bt_user_add);
		p_south_temp.add(p_south_south, "South");
		p_main.add(p_south_temp, "South");
	}
	
	//책 신규 등록 및 삭제 화면 메서드
	public void bookAdd() {
		p_main = new Panel(new BorderLayout(10,10));
		lb_book_add_title = new Label("책 신규등록 및 삭제", Label.CENTER);
		p_main.add(lb_book_add_title, "North");
		
		Panel p_center_temp = new Panel(new GridLayout(3,2,5,5));
		lb_book_add_bname = new Label("책 이름 입력 : ", Label.CENTER);
		lb_book_add_author = new Label("저자 입력 : ", Label.CENTER);
		lb_book_add_publisher = new Label("출판사 입력 : ", Label.CENTER);
		tf_book_add_bname = new TextField();
		tf_book_add_author = new TextField();
		tf_book_add_publisher = new TextField();
		p_center_temp.add(lb_book_add_bname);
		p_center_temp.add(tf_book_add_bname);
		p_center_temp.add(lb_book_add_author);
		p_center_temp.add(tf_book_add_author);
		p_center_temp.add(lb_book_add_publisher);
		p_center_temp.add(tf_book_add_publisher);
		p_main.add(p_center_temp, "Center");
		
		Panel p_south_temp = new Panel(new BorderLayout(5,5));
		Panel p_south_south = new Panel();
		lb_book_add_msg = new Label("메세지 : ");
		bt_book_add = new Button("등록하기");
		bt_book_delete = new Button("삭제하기");
		p_south_temp.add(lb_book_add_msg);
		p_south_south.add(bt_book_add);
		p_south_south.add(bt_book_delete);
		p_south_temp.add(p_south_south, "South");
		p_main.add(p_south_temp, "South");
	}
	
	//책 대여 및 반납 화면 메서드
	public void bookLend() {
		p_main = new Panel(new BorderLayout(10,10));
		lb_book_lend_title = new Label("책 대여 및 반납", Label.CENTER);
		p_main.add(lb_book_lend_title, "North");
		
		Panel p_center_temp = new Panel(new GridLayout(1,2,5,5));
		lb_book_lend_bid = new Label("책 번호 입력 : ", Label.CENTER);
		tf_book_lend_bid = new TextField();
		p_center_temp.add(lb_book_lend_bid);
		p_center_temp.add(tf_book_lend_bid);
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
	}
	
	//검색하기 화면 메서드
	public void searchInfo() {
		p_main = new Panel(new BorderLayout(10,10));
		lb_search_info_title = new Label("검색하기", Label.CENTER);
		p_main.add(lb_search_info_title, "North");
		
		Panel p_center_temp = new Panel();
		bt_search_info_uname = new Button("사람 이름으로 검색");
		bt_search_info_bname = new Button("책 이름으로 검색");
		p_center_temp.add(bt_search_info_uname);
		p_center_temp.add(bt_search_info_bname);
		p_main.add(p_center_temp, "Center");
	}
	
	//연체정보 화면 메서드
	public void delayInfo() {
		
	}
	
	public void topTen() {
		
	}
	
	public static void main(String[] args) {
		BookManagement bm = new BookManagement();
		bm.setSize(800,800);
		bm.setVisible(true);
	}

}