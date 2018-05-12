package com.pvbarredo.portal.trello.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.pvbarredo.portal.trello.model.Card;
import com.pvbarredo.portal.trello.model.Label;
import com.pvbarredo.portal.trello.model.Ticket;
import com.pvbarredo.portal.trello.model.User;
import java.awt.Checkbox;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Panel;

public class MainWindow {

	private JFrame frmTicketPortalTrello;
	private static Dialog dialog;
	private JTextField timeTextField;
	private JTable configTable;
	private ScheduledExecutorService exec;
	private Runnable executeRunnable;
	private static TextArea consoleTextArea;
	private String key = "46308c25cef506f226fe7721d7b2d95f";
	private String token = "d2b62245631a432f9507505542dd0ab9992a96171d79b265a0f682e956b7b479";
	private static String url = "http://hkctrtrp/hkctrtrpapps/layouts/SR/SRViewBrowse.aspx?viewid=Outstanding_TT_for_IRIS-4_SHP_ALL";
	private String board_id = "ZWpit6sv";//ZWpit6sv,xZ5BG9Pr
	private JPanel memberPanel2;
	private JPanel labelPanel2;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmTicketPortalTrello.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					dialog = new Dialog(e.getMessage());
					dialog.frame.setVisible(true);
				}
			}
		});
	}


	public MainWindow() {
		initialize();
	}
	
	public static String getURLSource(String url) throws IOException
    {
        URL urlObject = new URL(url);
        URLConnection urlConnection = urlObject.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

        return toString(urlConnection.getInputStream());
    }
	
	private static String toString(InputStream inputStream) throws IOException
    {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")))
        {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }

            return stringBuilder.toString();
        }
    }


	private void initialize() {
		frmTicketPortalTrello = new JFrame();
		frmTicketPortalTrello.setTitle("Ticket Portal Trello Card Generator v1.0 - Peter Barredo");
		frmTicketPortalTrello.setBounds(100, 100, 823, 353);
		frmTicketPortalTrello.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTicketPortalTrello.getContentPane().setLayout(new BorderLayout(0, 0));
				
				JTabbedPane tabbedPanel = new JTabbedPane(JTabbedPane.TOP);
				frmTicketPortalTrello.getContentPane().add(tabbedPanel, BorderLayout.CENTER);
				
				JPanel mainPanel = new JPanel();
				tabbedPanel.addTab("Main", null, mainPanel, null);
				mainPanel.setLayout(new BorderLayout(0, 0));
				
				JPanel mainPanel1 = new JPanel();
				mainPanel.add(mainPanel1, BorderLayout.NORTH);
				mainPanel1.setLayout(new FormLayout(new ColumnSpec[] {
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,},
					new RowSpec[] {
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,}));
				
				timeTextField = new JTextField();
				mainPanel1.add(timeTextField, "10, 2, fill, default");
				timeTextField.setColumns(10);
				
				JLabel lblMinutes = new JLabel("Minutes");
				mainPanel1.add(lblMinutes, "12, 2");
				
				JButton btnStop = new JButton("Stop");
				btnStop.setEnabled(false);
				JButton btnRun = new JButton("Run");
				
				
				
				btnStop.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						exec.shutdown();
						consoleTextArea.setText(consoleTextArea.getText() + "Tool already SHUTDOWN" + "\n" );
						btnStop.setEnabled(false);
						btnRun.setEnabled(true);
					}
				});
				
				
				btnRun.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						 key = (String) configTable.getModel().getValueAt(0, 1);
						 token = (String) configTable.getModel().getValueAt(1, 1);
						 url = (String) configTable.getModel().getValueAt(2, 1);
						 board_id = (String) configTable.getModel().getValueAt(3, 1);
						
						consoleTextArea.setText(consoleTextArea.getText() + "Tool already run for N minutes" + "\n" );
						consoleTextArea.setText(consoleTextArea.getText() + "================" + "\n" );
						consoleTextArea.setText(consoleTextArea.getText() + "KEY : " + key + "\n" );
						consoleTextArea.setText(consoleTextArea.getText() + "TOKEN : " + token + "\n" );
						consoleTextArea.setText(consoleTextArea.getText() + "URL : " + url + "\n" );
						consoleTextArea.setText(consoleTextArea.getText() + "BOARD : " + board_id + "\n" );
						consoleTextArea.setText(consoleTextArea.getText() + "================" + "\n" );
						executeRunnable = new Runnable() {
						    public void run() {
						    	startRun();
						    }
						};
						
						exec = Executors.newScheduledThreadPool(1);
						exec.scheduleAtFixedRate(executeRunnable , 0, 5, TimeUnit.MINUTES);	
				
						
						btnStop.setEnabled(true);
						btnRun.setEnabled(false);
					}
				});
				mainPanel1.add(btnRun, "14, 2");
				
				
				mainPanel1.add(btnStop, "16, 2");
				
				JPanel mainPanel2 = new JPanel();
				mainPanel.add(mainPanel2, BorderLayout.CENTER);
				mainPanel2.setLayout(new BorderLayout(0, 0));
				
				JTabbedPane mainTab = new JTabbedPane(JTabbedPane.TOP);
				mainPanel2.add(mainTab, BorderLayout.CENTER);
				
				JPanel consolePanel = new JPanel();
				mainTab.addTab("Console", null, consolePanel, null);
				consolePanel.setLayout(new BorderLayout(0, 0));
				
				consoleTextArea = new TextArea();
				consoleTextArea.setBackground(Color.BLACK);
				consoleTextArea.setForeground(Color.GREEN);
				consoleTextArea.setEditable(false);
				consolePanel.add(consoleTextArea, BorderLayout.CENTER);
				
				JPanel configPanel = new JPanel();
				mainTab.addTab("Config", null, configPanel, null);
				configPanel.setLayout(new BorderLayout(0, 0));
				
				configTable = new JTable();
				configTable.setModel(new DefaultTableModel(
					new Object[][] {
						{"KEY", "46308c25cef506f226fe7721d7b2d95f"},
						{"TOKEN", "d2b62245631a432f9507505542dd0ab9992a96171d79b265a0f"},
						{"URL", "http://hkctrtrp/hkctrtrpapps/layouts/SR/SRViewBrowse.aspx?viewid=Outstanding_TT_for_IRIS-4_SHP_ALL"},
						{"BOARD_ID", "xZ5BG9Pr"},
					},
					new String[] {
						"KEY", "VALUE"
					}
				));
				configPanel.add(configTable, BorderLayout.NORTH);
				
				JPanel memberPanel = new JPanel();
				tabbedPanel.addTab("Members", null, memberPanel, null);
				memberPanel.setLayout(new BorderLayout(0, 0));
				
				JPanel memberPanel1 = new JPanel();
				memberPanel.add(memberPanel1, BorderLayout.NORTH);
				
				JButton btnGetAllMembers = new JButton("Get Members");
				btnGetAllMembers.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						try {
							String url = "https://api.trello.com/1/boards/"+board_id+"/members?key="+key+"&token="+token ;
							System.out.println(url);
							HttpClient client = HttpClientBuilder.create().build();
							HttpGet request = new HttpGet(url);
							HttpResponse response;
							response = client.execute(request);
							System.out.println("Response Code : " 
					                + response.getStatusLine().getStatusCode());

							BufferedReader rd = new BufferedReader(
								new InputStreamReader(response.getEntity().getContent()));
	
							StringBuffer result = new StringBuffer();
							String output = "";
							while ((output = rd.readLine()) != null) {
								result.append(output);
								
								JSONArray array = new JSONArray(output);
								Gson gson = new Gson();
								int y= 1;
								for(int i = 0 ; i < array.length() ; i++){
									
									JSONObject userObject = array.getJSONObject(i);
									User user = gson.fromJson(userObject.toString(), User.class);
									
									Checkbox checkbox = new Checkbox(user.getFullName() + "(" + user.getUsername() +")");
									memberPanel2.add(checkbox, "1, " + y);
									y += 2;
								}
								
								memberPanel2.revalidate();
								memberPanel2.repaint();
							}
							
							System.out.println(result.toString());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							dialog = new Dialog(e1.getMessage());
							dialog.frame.setVisible(true);
							e1.printStackTrace();
							
						}

						
					}
				});
				
				
				
				memberPanel1.setLayout(new BorderLayout(0, 0));
				memberPanel1.add(btnGetAllMembers, BorderLayout.EAST);
				
				memberPanel2 = new JPanel();
				memberPanel2.setAutoscrolls(true);
				
						memberPanel.add(memberPanel2, BorderLayout.CENTER);
						memberPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						
						JPanel labelPanel = new JPanel();
						tabbedPanel.addTab("Labels", null, labelPanel, null);
						labelPanel.setLayout(new BorderLayout(0, 0));
						
						JPanel labelPanel1 = new JPanel();
						labelPanel.add(labelPanel1, BorderLayout.NORTH);
						
						JButton btnGetLabels = new JButton("Get Labels");
						btnGetLabels.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
									String url = "https://api.trello.com/1/boards/"+board_id+"/labels?key="+key+"&token="+token ;
									System.out.println(url);
									HttpClient client = HttpClientBuilder.create().build();
									HttpGet request = new HttpGet(url);
									HttpResponse response;
									response = client.execute(request);
									System.out.println("Response Code : " 
							                + response.getStatusLine().getStatusCode());

									BufferedReader rd = new BufferedReader(
										new InputStreamReader(response.getEntity().getContent()));
	
									StringBuffer result = new StringBuffer();
									String output = "";
									while ((output = rd.readLine()) != null) {
										result.append(output);
										
										JSONArray array = new JSONArray(output);
										Gson gson = new Gson();
										int y= 1;
										for(int i = 0 ; i < array.length() ; i++){
											
											JSONObject labelObject = array.getJSONObject(i);
											Label label = gson.fromJson(labelObject.toString(), Label.class);
											
											Checkbox checkbox = new Checkbox(label.getName() + "(" + label.getColor() + ")");
											labelPanel2.add(checkbox, "1, " + y);
											y += 2;
										}
										
										labelPanel2.revalidate();
										labelPanel2.repaint();
									}
									
									System.out.println(result.toString());
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									dialog = new Dialog(e1.getMessage());
									dialog.frame.setVisible(true);
									e1.printStackTrace();
									
								}
							}
						});
						labelPanel1.setLayout(new BorderLayout(0, 0));
						labelPanel1.add(btnGetLabels, BorderLayout.EAST);
						
						labelPanel2 = new JPanel();
						labelPanel.add(labelPanel2, BorderLayout.CENTER);
						labelPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						
						JPanel customFieldPanel = new JPanel();
						tabbedPanel.addTab("Custom Field", null, customFieldPanel, null);
		
		

	}
	
	public void startRun() {
				
		consoleTextArea.setText(consoleTextArea.getText() + "Running now!" + "\n" );
		
		getFileString();
		consoleTextArea.setText(consoleTextArea.getText() + "DONE!" + "\n" );
			
	}
	
	public static void getFileString() 
    {
	
		List<Ticket> tickets = new ArrayList<>();
		try {
			String source = getURLSource(url);
			File file = new File("1.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			System.out.println("Contents of file:");
			System.out.println(stringBuffer.toString());
			
			Pattern p = Pattern.compile("\\<tr class=\"RowStyle\">(.*?)\\</tr>");
			Matcher m = p.matcher(source);//stringBuffer.toString() ; source
			while(m.find())
			{
//				System.out.println(m.group(1)); //1 record
				
				Pattern p1 = Pattern.compile("\\<td .*?>(.*?)\\</td>");
				Matcher m1 = p1.matcher(m.group(1));
				
				int index = 0;
				Ticket ticket = new Ticket();
				while(m1.find())
				{
					System.out.println(m1.group(1));
					switch (index) {
					case 0:
						String str = m.group(1);
						String link = str.substring(str.indexOf("<a href=\"") + 9, str.indexOf("\" target=\"_blank\">"));
						String number = str.substring(str.indexOf("blank\">") + 7, str.indexOf("</a>"));
						System.out.println(link);
						System.out.println(number);
						ticket.setLink(link);
						ticket.setNumber(number);
					case 1:
						ticket.setSeverity(m1.group(1));
						
					case 2:
						ticket.setCategory(m1.group(1));
						
					case 3:
						ticket.setSubject(m1.group(1));
						
					case 4:
						ticket.setStatus(m1.group(1));
						
					case 5:
						ticket.setCreateDate(m1.group(1));
						
					case 6:
						ticket.setResponseParty(m1.group(1));
						
					
					}
					
					index++;
				}
				tickets.add(ticket);

			}
			
			Pattern c = Pattern.compile("\\<tr class=\"AlternatingRowStyle\">(.*?)\\</tr>");
			Matcher a = c.matcher(source);
			while(a.find())
			{
				
				Pattern p2 = Pattern.compile("\\<td .*?>(.*?)\\</td>");
				Matcher m2 = p2.matcher(a.group(1));
				
				int index = 0;
				Ticket ticket = new Ticket();
				while(m2.find())
				{
					System.out.println(m2.group(1)); 
					
					switch (index) {
					case 0:
						String str = m2.group(1);
						String link = str.substring(str.indexOf("<a href=\"") + 9, str.indexOf("\" target=\"_blank\">"));
						String number = str.substring(str.indexOf("blank\">") + 7, str.indexOf("</a>"));
						System.out.println(link);
						System.out.println(number);
						ticket.setLink(link);
						ticket.setNumber(number);
						
					case 1:
						ticket.setSeverity(m2.group(1));
						
					case 2:
						ticket.setCategory(m2.group(1));
						
					case 3:
						ticket.setSubject(m2.group(1));
						
					case 4:
						ticket.setStatus(m2.group(1));
					
					case 5:
						ticket.setCreateDate(m2.group(1));
						
					case 6:
						ticket.setResponseParty(m2.group(1));
					
					
					}
					index++;
				}
				tickets.add(ticket);
			

			}
			
			//check duplicate ticket card
			List<Ticket> newTickets = new ArrayList<>();
			List<Card> cards = new ArrayList<>();
			String url = "https://api.trello.com/1/boards/xZ5BG9Pr/cards?key=46308c25cef506f226fe7721d7b2d95f&token=d2b62245631a432f9507505542dd0ab9992a96171d79b265a0f682e956b7b479";

			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			System.out.println("Response Code : " 
		                + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String output = "";
			while ((output = rd.readLine()) != null) {
				result.append(output);
				
				
				JSONArray array = new JSONArray(output);
				Gson gson = new Gson();
				
				for(int i = 0 ; i < array.length() ; i++){
					
					JSONObject cardObject = array.getJSONObject(i);
					Card card = gson.fromJson(cardObject.toString(), Card.class);
					cards.add(card);		
				}

			}
			
			
			//find new ticket
			int index=0;
			ticketLoop:
			for (Ticket ticket1 : tickets) {
				
				for (Card card : cards) {
					if(card.getName().indexOf(ticket1.getNumber()) != -1) {
						continue ticketLoop;
					}
					
				}
				index++;
				newTickets.add(ticket1);
			}
			
			for (Ticket ticket : newTickets) {
				HttpClient httpclient = HttpClients.createDefault();
				HttpPost httppost = new HttpPost("https://api.trello.com/1/cards");
				// Request parameters and other properties.
				List<NameValuePair> params = new ArrayList<NameValuePair>(2);
				params.add(new BasicNameValuePair("name", ticket.getNumber() +"-"+ ticket.getSubject()));
				params.add(new BasicNameValuePair("desc", "Level : "+ ticket.getSeverity() +"\n" + "Ticket Created Date: " + ticket.getCreateDate() + "\n"+ticket.getSubject()));
				params.add(new BasicNameValuePair("pos", "top"));
				params.add(new BasicNameValuePair("idList", "5aefe1f98062b37abbcb95e0"));
				params.add(new BasicNameValuePair("idLabels", "5aeff50705baed7f89c9d876"));
				params.add(new BasicNameValuePair("idMembers", "5ac1e6bc961d1b718cd2317d"));
				params.add(new BasicNameValuePair("idMembers", "5a0b91fecdacfd463f72d094"));
				params.add(new BasicNameValuePair("idMembers", "56b05db57b949ea7b59ec0ce"));
				
				params.add(new BasicNameValuePair("urlSource", ticket.getLink()));
				params.add(new BasicNameValuePair("keepFromSource", "all"));
				params.add(new BasicNameValuePair("key", "46308c25cef506f226fe7721d7b2d95f"));
				params.add(new BasicNameValuePair("token", "d2b62245631a432f9507505542dd0ab9992a96171d79b265a0f682e956b7b479"));
				
				httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

				//Execute and get the response.
				HttpResponse response1 = httpclient.execute(httppost);
				HttpEntity entity = response1.getEntity();

				if (entity != null) {
				    InputStream instream = entity.getContent();
				    try {
				        // do something useful
				    	consoleTextArea.setText(consoleTextArea.getText() + "Card Created : "+ ticket.getNumber() + "-" + ticket.getSubject() + "\n" );
				    	
				    } finally {
				        instream.close();
				    }
				}
				
			}
		} catch (Exception e) {
			dialog = new Dialog(e.getMessage());
			dialog.frame.setVisible(true);
		}
    }

}
