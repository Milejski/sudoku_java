import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame implements ActionListener{
	protected String[] menu;
	Menu(){
		String[] s={"SUDOKU","New Game","Load Game","Database","Options","Exit"};
		menu=s;
		this.setBounds(0,0,200,300);
	}
	protected void buildMenu(){
		this.setTitle("SUDOKU by Mateusz Milejski");
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextField header=new JTextField(this.menu[0],this.menu[0].length());
		header.setEditable(false);
		header.setAlignmentX(Component.CENTER_ALIGNMENT);
		header.setHorizontalAlignment(JTextField.CENTER);

		this.add(Box.createRigidArea(new Dimension(0,10)));
		this.add(header);
		this.add(Box.createRigidArea(new Dimension(0,10)));
		int width=header.getPreferredSize().width;
		int height=header.getPreferredSize().height;
		
		JButton[] b=new JButton[this.menu.length-1];
		for(int i=0;i<this.menu.length-1;i++){
			b[i]=new JButton(menu[i+1]);
			b[i].addActionListener(this);
			b[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			this.add(b[i]);
			this.add(Box.createRigidArea(new Dimension(0,5)));
			if(b[i].getPreferredSize().width>width)
			width=b[i].getPreferredSize().width;
			if(b[i].getPreferredSize().height>height)
			height=b[i].getPreferredSize().height;
		}
		
		header.setMaximumSize(new Dimension(width,height));
		for(int i=0;i<this.menu.length-1;i++){
			b[i].setMaximumSize(new Dimension(width,height));
		}
		
		this.setVisible(true);
	}
	protected void buildMenu(Rectangle r){
		this.setBounds(r);
		this.buildMenu();
	}
	@Override public void actionPerformed(ActionEvent e){
		JButton s=(JButton)e.getSource();
		if(s.getText().contains(menu[1])){
			NewGameMenu ng=new NewGameMenu();
			ng.buildMenu(this.getBounds());
			this.setVisible(false);
		}else if(s.getText().contains(menu[2])){
			System.out.println("Load Game: work in development...");
		}else if(s.getText().contains(menu[3])){
			DatabaseMenu d=new DatabaseMenu();
			d.buildMenu(this.getBounds());
			this.setVisible(false);
		}else if(s.getText().contains(menu[4])){
			System.out.println("Options: work in development...");
		}else if(s.getText().contains(menu[5])){
			this.dispose();
		}
	}
	
	class NewGameMenu extends Menu{
		NewGameMenu(){
			String[] s={"NEW GAME","Test","Random chart","Chose chart","Return"};
			menu=s;
		}
		@Override public void actionPerformed(ActionEvent e){
			JButton b=(JButton)e.getSource();
			if(b.getText().contains(menu[1])){
				this.setVisible(false);
				SudokuTemplate test=new SudokuTemplate();
				test.buildSudoku(this.getBounds());
			}else if(b.getText().contains(menu[2])){
				System.out.println("Random chart: work in development...");
			}else if(b.getText().contains(menu[3])){
				System.out.println("Chose chart: work in development...");
			}else if(b.getText().contains(menu[4])){
				Menu.this.setBounds((Rectangle)this.getBounds());
				this.dispose();
				Menu.this.setVisible(true);
			}
		}
	}
	
	class DatabaseMenu extends Menu{
		DatabaseMenu(){
			String[] s={"DATABASE","Add new chart","Browse charts","Return"};
			menu=s;
		}
		@Override public void actionPerformed(ActionEvent e){
			JButton b=(JButton)e.getSource();
			if(b.getText().contains(menu[1])){
				AddSudoku as=new AddSudoku();
				as.buildSudoku(this.getBounds());
				this.setVisible(false);
			}else if(b.getText().contains(menu[2])){
				System.out.println("Browse charts: work in development...");
			}else if(b.getText().contains(menu[3])){
				Menu.this.setBounds((Rectangle)this.getBounds());
				Menu.this.setVisible(true);
				this.dispose();
			}
		}
		class AddSudoku extends SudokuTemplate{
			AddSudoku(){
				header="ADD SUDOKU";
				for(int i=0;i<9;i++){
					for(int j=0;j<9;j++){
						this.value[i][j]=0;
						this.solved[i][j]=false;
					}
				}
			}
			@Override public void actionPerformed(ActionEvent e){
				AddSudokuMenu asm=new AddSudokuMenu();
				asm.buildMenu(this.getBounds());
				this.setVisible(false);
			}
			@Override public void keyReleased(KeyEvent e){
				int r=Integer.parseInt(""+e.getKeyChar());
				JTextField s=(JTextField)e.getSource();
				int i1=0,j1=0;
				if(r>=1&&r<=9){
					for(int i=0;i<9;i++){
						for(int j=0;j<9;j++){
							if(s==t[i][j]){
								i1=i;
								j1=j;
							}
						}
					}
					if(value[i1][j1]==r){
						solved[i1][j1]=true;
						t[i1][j1].setEditable(false);
					}
				}
				int tosolve=0;
				for(int i=0;i<9;i++){
					for(int j=0;j<9;j++){
						if(!solved[i][j]){
						tosolve++;
						}
					}
					if(tosolve==0){
						System.out.println("Chart has been solved!");
					}
				}
			}
			
			class AddSudokuMenu extends Menu{
				AddSudokuMenu(){
					String[] s={"ADD SUDOKU - OPTIONS","Show solved","Add to Database","Return to chart","Return to main menu"};
					menu=s;
				}
				@Override public void actionPerformed(ActionEvent e){
					JButton s=(JButton)e.getSource();
					if(s.getText().contains(menu[1])){
						AddSudoku.this.solve();
					}else if(s.getText().contains(menu[2])){
						System.out.println("Add to database - in development...");
					}else if(s.getText().contains(menu[3])){
						AddSudoku.this.setBounds((Rectangle)this.getBounds());
						this.dispose();
						AddSudoku.this.setVisible(true);
					}else if(s.getText().contains(menu[4])){
						Menu.this.setBounds((Rectangle)this.getBounds());
						Menu.this.setVisible(true);
						this.dispose();
						AddSudoku.this.dispose();	
						DatabaseMenu.this.dispose();
					}
				}
			}
		}
	}
	
	class SudokuTemplate extends JFrame implements ActionListener, KeyListener{
		protected int[][] value=new int[9][9];
		protected boolean[][] solved=new boolean[9][9];
		protected JTextField[][] t=new JTextField[9][9];
		protected String header;
		SudokuTemplate(){
			header="SUDOKU TEMPLATE";
			int[][] value={{9,6,5,4,2,1,7,3,8},{4,3,8,5,7,9,1,2,6},{1,2,7,6,3,8,9,4,5},{8,9,6,7,4,3,5,1,2},{7,5,4,2,1,6,3,8,9},{2,1,3,9,8,5,6,7,4},{6,8,1,3,5,4,2,9,7},{3,7,9,8,6,2,4,5,1},{5,4,2,1,9,7,8,6,3}};
			boolean[][] solved={{true,false,true,false,false,false,false,false,true},{true,false,false,true,true,false,true,false,true},{false,true,true,true,false,false,false,true,false},{false,true,true,false,false,true,true,true,true},{true,false,true,false,true,false,true,false,false},{true,true,false,true,true,false,false,false,true},{false,true,true,false,false,true,false,true,false},{true,false,false,true,false,false,false,true,true},{false,false,true,false,false,true,false,true,false}};
			this.value=value;
			this.solved=solved;
		}
		protected void buildSudoku(Rectangle r){
			this.setTitle("SUDOKU by Mateusz Milejski");
			this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JTextField header=new JTextField(this.header,this.header.length());
			header.setEditable(false);
			header.setAlignmentX(Component.CENTER_ALIGNMENT);
			header.setHorizontalAlignment(JTextField.CENTER);
			header.setMaximumSize(header.getPreferredSize());
			this.add(header);
			
			this.add(Box.createRigidArea(new Dimension(0,10)));
			
			JPanel p=new JPanel(new GridLayout(9,9));
			p.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.add(p);
			
			this.add(Box.createRigidArea(new Dimension(0,10)));
			
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
					if(solved[i][j]){
						t[i][j]=new JTextField(""+value[i][j],1);
						t[i][j].setEditable(false);
					}else{
						t[i][j]=new JTextField("",1);
					}
					t[i][j].addKeyListener(this);
					t[i][j].setHorizontalAlignment(JTextField.CENTER);
					p.add(t[i][j]);
				}
			}
			JButton b=new JButton("Options");
			b.addActionListener(this);
			b.setAlignmentX(Component.CENTER_ALIGNMENT);
			b.setHorizontalAlignment(JTextField.CENTER);
			this.add(b);		
			this.setBounds(r);			
			this.setVisible(true);
		}
		protected void solve(){
			
		}
		@Override public void actionPerformed(ActionEvent e){
		}
		@Override public void keyTyped(KeyEvent e){
		}
		@Override public void keyPressed(KeyEvent e){
		}
		@Override public void keyReleased(KeyEvent e){
		}
	}
}