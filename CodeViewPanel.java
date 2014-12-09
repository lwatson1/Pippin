package pippin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class CodeViewPanel implements Observer {
	private Code code;
	private Processor cpu;
	private JScrollPane scroller;
	private JTextField[] codeText = new JTextField[Code.CODE_MAX];
	private JTextField[] codeHex = new JTextField[Code.CODE_MAX];
	
	public CodeViewPanel(Machine machine) {
		setCode(machine.getCode());
		setCpu(machine.getCpu());
		machine.addObserver(this);
	}
	
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CodeViewPanel codeViewPanel = new CodeViewPanel(new Machine());
                JFrame frame = new JFrame("Code View Panel");
                frame.add(codeViewPanel.createCodeDisplay());
                frame.setSize(300,600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
	
	public JPanel createCodeDisplay() {
		JPanel returnPanel = new JPanel();
		JPanel panel = new JPanel();
		JPanel numPanel = new JPanel();
		JPanel sourcePanel = new JPanel();
		JPanel hexPanel = new JPanel();
		
		returnPanel.setPreferredSize(new Dimension(300,150));;
        returnPanel.setLayout(new BorderLayout());
        panel.setLayout(new BorderLayout());
        numPanel.setLayout(new GridLayout(0,1));
        sourcePanel.setLayout(new GridLayout(0,1));
        hexPanel.setLayout(new GridLayout(0,1));
        
        for(int i = 0; i < Code.CODE_MAX; i++) {
        	numPanel.add(new JLabel(i+": ", JLabel.RIGHT));
        	codeText[i] = new JTextField(10);
        	codeHex[i] = new JTextField(10);
        	sourcePanel.add(codeText[i]);
        	hexPanel.add(codeHex[i]);
        }
        
        Border border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Code Memory View",
                TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
        returnPanel.setBorder(border);
		
        panel.add(numPanel, BorderLayout.LINE_START);
        panel.add(sourcePanel, BorderLayout.CENTER);
        panel.add(hexPanel, BorderLayout.LINE_END);
        scroller = new JScrollPane(panel);
        returnPanel.add(scroller);
        
        return returnPanel;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(code != null) {
            for(int i = 0; i < Code.CODE_MAX; i++) {
                codeText[i].setText(code.getCodeText(i));
                codeHex[i].setText(code.getCodeHex(i));
            }           
        }
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public Processor getCpu() {
		return cpu;
	}

	public void setCpu(Processor cpu) {
		this.cpu = cpu;
	}
}
