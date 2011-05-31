/*
 * JavaHeartView.java
 */
package javaheart;

import java.awt.Color;
import java.awt.event.MouseEvent;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javaheart.Game.Global;
import javaheart.Game.Global.POSITION;
import javaheart.Game.Player;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * The application's main frame.
 */
public class JavaHeartView extends FrameView implements MouseListener {

	private ArrayList<Player> players ;
	private static int currentPlayerId;
	private static int cardOnboard;

	// Catch mouse pressed
	public JavaHeartView(SingleFrameApplication app) {
		super(app);

		initComponents();


		/* Our program begin here */
		Player.setResourceMap(getResourceMap());
		/* Get the shuffled cards here */
		List shuffledCards = Global.getShuffledCards();

		/* Deal */
		players = new ArrayList<Player>();
		players.add(new Player("Player1", POSITION.BOTTOM, mainPanel));
		players.add(new Player("Player2", POSITION.LEFT, mainPanel));
		players.add(new Player("Player3", POSITION.TOP, mainPanel));
		players.add(new Player("Player4", POSITION.RIGHT, mainPanel));

		/* Add events to click a card */
		for (Player player : players) {
			player.Prepare(shuffledCards);
			player.EnterBoard();
			ArrayList<JLabel> labels = player.getCardLabels();
			for (int i = 0; i < labels.size(); i++) {
				labels.get(i).addMouseListener(this);
			}
		}

		currentPlayerId = 0;
		/* first there is no card on board */
		cardOnboard = 0; 

		/* play button */
		JButton button = new JButton("play");
		button.setBounds(800, 100, 100, 50);
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
					if (players.get(currentPlayerId).getPickedCard() == null) return;
					players.get(currentPlayerId).MoveCard();
					if (cardOnboard == 3) {
						Player.setCardTypeOnboard('\u0000');
						cardOnboard = 0;
						/* remove all cards on board */
						new java.util.Timer().schedule(new java.util.TimerTask() {
							@Override
							public void run() {
								for (Player player : players) {
									player.getPickedCard().getJLabel().setVisible(false);
									player.RemovePickedcard();
								}
							}
						}, 1000); 
					} else cardOnboard++;
					/* if this is the first card moved on board so it sets the type of card onboard */
					if (cardOnboard == 1) {
						String firstPicked = players.get(currentPlayerId).getPickedCard().getName();
						Player.setCardTypeOnboard(firstPicked.charAt(0));
					}
					/* cycles around players */
					if (currentPlayerId == 3) currentPlayerId = 0;
					else currentPlayerId++;
			}
		};
		button.addActionListener(actionListener);
		mainPanel.add(button);
		/* end play button */

		/* Our program end here */

		// status bar initialization - message timeout, idle icon and busy animation, etc
		ResourceMap resourceMap = getResourceMap();
		int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
		messageTimer = new Timer(messageTimeout, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			}
		});
		messageTimer.setRepeats(false);
		int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
		for (int i = 0; i < busyIcons.length; i++) {
			busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
		}
		busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
			}
		});
		idleIcon = resourceMap.getIcon("StatusBar.idleIcon");

		// connecting action tasks to status bar via TaskMonitor
		TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
		taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				String propertyName = evt.getPropertyName();
				if ("started".equals(propertyName)) {
					if (!busyIconTimer.isRunning()) {
						busyIconIndex = 0;
						busyIconTimer.start();
					}
				} else if ("done".equals(propertyName)) {
					busyIconTimer.stop();
				} else if ("message".equals(propertyName)) {
					String text = (String) (evt.getNewValue());
					messageTimer.restart();
				} else if ("progress".equals(propertyName)) {
					int value = (Integer) (evt.getNewValue());
				}
			}
		});
	}

	@Action
	public void showAboutBox() {
		if (aboutBox == null) {
			JFrame mainFrame = JavaHeartApp.getApplication().getMainFrame();
			aboutBox = new JavaHeartAboutBox(mainFrame);
			aboutBox.setLocationRelativeTo(mainFrame);
		}
		JavaHeartApp.getApplication().show(aboutBox);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                mainPanel = new javax.swing.JPanel();
                menuBar = new javax.swing.JMenuBar();
                javax.swing.JMenu fileMenu = new javax.swing.JMenu();
                javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
                javax.swing.JMenu helpMenu = new javax.swing.JMenu();
                javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

                mainPanel.setName("mainPanel"); // NOI18N
                mainPanel.setLayout(null);

                menuBar.setName("menuBar"); // NOI18N

                org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javaheart.JavaHeartApp.class).getContext().getResourceMap(JavaHeartView.class);
                fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
                fileMenu.setName("fileMenu"); // NOI18N

                javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(javaheart.JavaHeartApp.class).getContext().getActionMap(JavaHeartView.class, this);
                exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
                exitMenuItem.setName("exitMenuItem"); // NOI18N
                fileMenu.add(exitMenuItem);

                menuBar.add(fileMenu);

                helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
                helpMenu.setName("helpMenu"); // NOI18N

                aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
                aboutMenuItem.setName("aboutMenuItem"); // NOI18N
                helpMenu.add(aboutMenuItem);

                menuBar.add(helpMenu);

                setComponent(mainPanel);
                setMenuBar(menuBar);
        }// </editor-fold>//GEN-END:initComponents
        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JPanel mainPanel;
        private javax.swing.JMenuBar menuBar;
        // End of variables declaration//GEN-END:variables
	private final Timer messageTimer;
	private final Timer busyIconTimer;
	private final Icon idleIcon;
	private final Icon[] busyIcons = new Icon[15];
	private int busyIconIndex = 0;
	private JDialog aboutBox;

	public void mouseClicked(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		players.get(currentPlayerId).Pick(label.getName());
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		label.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.yellow));
	}

	public void mouseExited(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		label.setBorder(BorderFactory.createLineBorder(Color.yellow, 0));
	}
}
