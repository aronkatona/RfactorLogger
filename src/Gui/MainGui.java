package Gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainGui extends JFrame{
	
	/**
	 * De h minek?
	 */
	private static final long serialVersionUID = 1L;
	public static Date actualTime;
	public static String pathOfFile;
	//public static String pathOfDirectory;
	public static boolean haveToSend = false;
	public JLabel nameLabel ;
	public JTextField nameField;
	public JFileChooser pathChooser;
	public boolean firstRun;
	public BufferedReader fileReader;
	public String userName;
	
	public MainGui(){
		setSize(400,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("A-liga");
		setResizable(false);
		setLayout(new BorderLayout());
		
		addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                int result = rlyClose();
                if (result == JOptionPane.YES_OPTION)       {
                	try {
						Runtime.getRuntime().exec("taskkill /F /IM rF_TotalControl.exe");
						Runtime.getRuntime().exec("taskkill /F /IM explorer.exe");
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Runtime.getRuntime().exec("cmd.exe /c start /min c:\\Windows\\explorer.exe");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }                  
                else if (result == JOptionPane.NO_OPTION)  
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
		
		 
		 try {
			 String command = "cmd.exe /c start /min hider.exe "; 
			 //String command = "cmd.exe /c start /min chrome.exe";
			 Process p=Runtime.getRuntime().exec(command);
		 } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			fileReader = new BufferedReader(new FileReader("properties773.txt"));
			try {
				userName = fileReader.readLine();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Irj a katonaron@gmail-com címre ezzel a számmal: " + "1");
			}
		} catch (FileNotFoundException e) {
			System.out.println("not");
			userName =  JOptionPane.showInputDialog(
	        		this,
	                "Add meg a neved",
	                "Névválasztás",
	                JOptionPane.PLAIN_MESSAGE);;
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("properties773.txt"), "utf-8"));
			    writer.write(userName);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Irj a katonaron@gmail.com címre ezzel a  számmal: " + "2");
			} finally {
			   try {writer.close();} catch (Exception ex) {}
			}
		}
	
		nameLabel = new JLabel();
		nameLabel.setFont(new Font("Serif", Font.BOLD, 50));
		nameLabel.setText("Üdv " + userName);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setPreferredSize(new Dimension(400, 300));
		add(nameLabel,BorderLayout.PAGE_START);
		
		/*nameField = new JTextField();
		nameField.setEditable(true);
		nameField.setPreferredSize(new Dimension(400, 300));
		add(nameField,BorderLayout.CENTER);*/
		
		/*pathChooser = new JFileChooser(); 
		pathChooser.setCurrentDirectory(new java.io.File("."));
		pathChooser.setDialogTitle("Válaszd ki a mappát");
		pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		pathChooser.setAcceptAllFileFilterUsed(false);
		if (pathChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
		      pathOfDirectory = pathChooser.getSelectedFile().toString();
		      System.out.println(pathOfDirectory);
		}*/

		     
		
	    actualTime = new Date();
		
		JButton btn = new JButton();
		btn.setText("Küldés");
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//chooseBiggestFile();
				System.out.println(haveToSend);
				if(haveToSend) 	{
					//sendEmail(pathOfFile,userName);
					JOptionPane.showMessageDialog(null, "Elküldve");
				} 
				else{
					JOptionPane.showMessageDialog(null, "Nincs mit küldeni, de ha úgy gondolod lenne, akkor " +
							"írj a katonaron@gmail.com címre ezzel a  számmal: " + "4");
				}
				
				try {
					Runtime.getRuntime().exec("taskkill /F /IM rF_TotalControl.exe");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}
		});
		btn.setPreferredSize(new Dimension(400, 100));
		add(btn,BorderLayout.PAGE_END);
		
		setLocationRelativeTo(null);
	}
	
	public int rlyClose(){
		int result = JOptionPane.showOptionDialog(null, 
		        "Végeztél?", 
		        "Kilépés", 
		        JOptionPane.OK_CANCEL_OPTION, 
		        JOptionPane.INFORMATION_MESSAGE, 
		        null, 
		        new String[]{"Igen", "Nem"}, // this is the array
		        "default");
		return result;
	}
	
	public static void chooseBiggestFile(){
		 
		  String path = "UserData/LOG/rf_TotalControl"; 
		 
		  File folder = new File(path);
		  File[] listOfFiles = folder.listFiles(); 
		  Long maxSize = 0l;
		  int index = 0;
		 
		  for (int i = 0; i < listOfFiles.length; i++) 
		  {
			   //csak a fájlokon megyunk végig
			   if (listOfFiles[i].isFile()) 
			   {			   
			   Date fileModifiedDate = new Date(listOfFiles[i].lastModified());
			   	   //ha a fájl  a futás után kreálodott, azokbol kiválasztjuk a legnagyobbat
				   if(fileModifiedDate.after(actualTime) && maxSize < listOfFiles[i].length()) {
					   haveToSend = true;
					   maxSize = listOfFiles[i].length();
					   index = i;
				   } 
			   }
			   
		  }
		  pathOfFile = listOfFiles[index].getAbsolutePath();
	}
	
	public static void sendEmail(String attachMent, String playerName){
		final String username = "aligatestlogger@gmail.com";
		final String password = "aligaTest";
 
		Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");

	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(username, password);
	                }
	            });

	    try {

	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("aligatestlogger@gmail.com"));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse("aligatestlogger@gmail.com"));
	        message.setSubject(playerName);
	        message.setText("Szia!");

	        MimeBodyPart messageBodyPart = new MimeBodyPart();

	        Multipart multipart = new MimeMultipart();

	        messageBodyPart = new MimeBodyPart();
	        String fileName = "LogFile";
	        DataSource source = new FileDataSource(attachMent);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(fileName);
	        multipart.addBodyPart(messageBodyPart);

	        message.setContent(multipart);

	        Transport.send(message);
	        


	    } catch (MessagingException e) {
	    	JOptionPane.showMessageDialog(null, "Irj a katonaron@gmail-com címre ezzela  számmal: " + "3");
	    }
	}
	

	public static void main(String[] args) {
		
		
		JFrame gui = new MainGui();
		gui.setVisible(true);

	}

}
