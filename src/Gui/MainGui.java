package Gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
	
	public MainGui(){
		setSize(400,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("A-liga");
		setResizable(false);
		setLayout(new BorderLayout());
		
		nameLabel = new JLabel();
		nameLabel.setFont(new Font("Serif", Font.BOLD, 50));
		nameLabel.setText("Név");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setPreferredSize(new Dimension(400, 200));
		add(nameLabel,BorderLayout.PAGE_START);
		
		nameField = new JTextField();
		nameField.setEditable(true);
		nameField.setPreferredSize(new Dimension(400, 100));
		add(nameField,BorderLayout.CENTER);
		
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
				
				chooseBiggestFile();
				System.out.println(haveToSend);
				if(haveToSend) 	sendEmail(pathOfFile,nameField.getText());

				
			}
		});
		btn.setPreferredSize(new Dimension(400, 100));
		add(btn,BorderLayout.PAGE_END);
		
		setLocationRelativeTo(null);
	}
	
	public static void chooseBiggestFile(){
		 
		  String path = "D:/rfactor/aliga2014/UserData/LOG/rf_TotalControl"; 
		 
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
	        e.printStackTrace();
	    }
	}

	public static void main(String[] args) {
		
		
		JFrame gui = new MainGui();
		gui.setVisible(true);

	}

}
