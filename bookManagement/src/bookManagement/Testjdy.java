package bookManagement;

import java.awt.*;
import java.awt.event.*;

public class Testjdy {

	public static void main(String[] args) {
		Frame frame = new Frame("AWT List with Row Selection");

		List list1 = new List(5, false);
		List list2 = new List(5, false);
		List list3 = new List(5, false);

		list1.add("1");
		list1.add("2");
		list1.add("3");

		list2.add("A");
		list2.add("B");
		list2.add("C");

		list3.add("a");
		list3.add("b");
		list3.add("c");

		list1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				int index = list1.getSelectedIndex();
				if (index != -1) {
					list2.select(index);
					list3.select(index);
				}

			}
		});

		list2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				int index = list2.getSelectedIndex();
				if (index != -1) {
					list1.select(index);
					list3.select(index);
				}

			}
		});

		list3.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				int index = list3.getSelectedIndex();
				if (index != -1) {
					list1.select(index);
					list2.select(index);
				}

			}
		});
		
		frame.setLayout(new GridLayout(1,3));
		frame.add(list1);
		frame.add(list2);
		frame.add(list3);
		
		frame.setSize(400,200);
		frame.setVisible(true);
		

	}
	
	

}
