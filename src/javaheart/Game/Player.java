package javaheart.Game;

import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javaheart.Game.Global.POSITION;
import javaheart.Game.Global.CARD_TYPES;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.application.ResourceMap;

public class Player {

	private ArrayList<Card> cards = new ArrayList<Card>();
	private String name;
	private POSITION position;
	private String pickedName = "";
	private Card pickedCard = null;

	private static ResourceMap resourceMap;
	private static char cardTypeOnboard;
	private static JPanel boardPanel;
	private static boolean hasHelp = Boolean.FALSE;

	public String getPickedName() {
		return pickedName;
	}

	public void setCardTypeOnboard(char cardTypeOnboard) {
		Player.cardTypeOnboard = cardTypeOnboard;
	}

	public static void setResourceMap(ResourceMap resourceMap) {
		Player.resourceMap = resourceMap;
	}

	public Player(String name, POSITION position, JPanel boardPanel) {
		this.name = name;
		this.position = position;
		Player.boardPanel = boardPanel;
	}

	public ArrayList<JLabel> getCardLabels() {
		ArrayList<JLabel> jLabels = new ArrayList<JLabel>();
		for (int i = 0; i < cards.size(); i++) {
			jLabels.add(cards.get(i).getJLabel());
		}
		return jLabels;
	}

	// Generate the cards if new game and load old cards if loading.
	public void Prepare(List shuffledCards) {
		
		/* pos is the first position of cards.
		   offset is the gap between cards. */
		Point pos = null, offset = null;

		switch(this.position) {
			case TOP   : 	pos = new Point(150,20); offset = new Point(20, 0);  break;
			case RIGHT : 	pos = new Point(150 + 13*20 + 100, 20); offset = new Point(0,20);break;
			case BOTTOM: 	pos = new Point(150, 20 + 13*20); offset = new Point(20,0);break;
			case LEFT  : 	pos = new Point(20 ,20);
					offset = new Point(0,20); break;
		}
		
		/* '1' and 1 in ascii is 48 difference so we minus 48 to get the int */
		int slot = (int)name.charAt(name.length()-1)-48;
		for (int i = (slot-1)*13 ; i < slot*13; i++) {
			CARD_TYPES type = (CARD_TYPES) shuffledCards.get(i);
			int cardOrder = i - (slot-1) * 13;
			Point location = new Point(pos.x + offset.x*cardOrder, pos.y+offset.y*cardOrder);

			switch(type) {
				/* Spades */
				case SACE: addCard("SACE", location); break;
				case STWO: addCard("STWO", location); break;
				case STHREE: addCard("STHREE", location); break;
				case SFOUR: addCard("SFOUR", location); break;
				case SFIVE: addCard("SFIVE", location); break;
				case SSIX: addCard("SSIX", location); break;
				case SSEVEN: addCard("SSEVEN", location); break;
				case SEIGHT: addCard("SEIGHT", location); break;
				case SNINE: addCard("SNINE", location); break;
				case STEN: addCard("STEN", location); break;
				case SJACK: addCard("SJACK", location); break;
				case SQUEEN: addCard("SQUEEN", location); break;
				case SKING: addCard("SKING", location);  break;
				/* Clubs */
				case CACE: addCard("CACE", location); break;
				case CTWO: addCard("CTWO", location); break;
				case CTHREE: addCard("CTHREE", location); break;
				case CFOUR: addCard("CFOUR", location); break;
				case CFIVE: addCard("CFIVE", location); break;
				case CSIX: addCard("CSIX", location); break;
				case CSEVEN: addCard("CSEVEN", location); break;
				case CEIGHT: addCard("CEIGHT", location); break;
				case CNINE: addCard("CNINE", location); break;
				case CTEN: addCard("CTEN", location); break;
				case CJACK: addCard("CJACK", location); break;
				case CQUEEN: addCard("CQUEEN", location); break;
				case CKING: addCard("CKING", location);  break;
				/* Diamonds */
				case DACE: addCard("DACE", location); break;
				case DTWO: addCard("DTWO", location); break;
				case DTHREE: addCard("DTHREE", location); break;
				case DFOUR: addCard("DFOUR", location); break;
				case DFIVE: addCard("DFIVE", location); break;
				case DSIX: addCard("DSIX", location); break;
				case DSEVEN: addCard("DSEVEN", location); break;
				case DEIGHT: addCard("DEIGHT", location); break;
				case DNINE: addCard("DNINE", location); break;
				case DTEN: addCard("DTEN", location); break;
				case DJACK: addCard("DJACK", location); break;
				case DQUEEN: addCard("DQUEEN", location); break;
				case DKING: addCard("DKING", location);  break;
				/* Diamonds */
				case HACE: addCard("HACE", location); break;
				case HTWO: addCard("HTWO", location); break;
				case HTHREE: addCard("HTHREE", location); break;
				case HFOUR: addCard("HFOUR", location); break;
				case HFIVE: addCard("HFIVE", location); break;
				case HSIX: addCard("HSIX", location); break;
				case HSEVEN: addCard("HSEVEN", location); break;
				case HEIGHT: addCard("HEIGHT", location); break;
				case HNINE: addCard("HNINE", location); break;
				case HTEN: addCard("HTEN", location); break;
				case HJACK: addCard("HJACK", location); break;
				case HQUEEN: addCard("HQUEEN", location); break;
				case HKING : addCard("HKING", location); break;
			}
		}
	}

	public void EnterBoard() {
		// generate random cards here
		for (int i = 0; i < cards.size(); i++) {
			boardPanel.add(cards.get(i).getJLabel());
		}
	}

	public void Pick(String name) {
		boolean sameType = cardTypeOnboard == name.charAt(0);
		if (cardTypeOnboard ==  '\u0000')
			sameType = true;

		if (!sameType) {
			addHelp();	
		} else if (sameType && (pickedName.equals("") || pickedName.equals(name))) {
			for (int i = 0; i < cards.size(); i++) {
				Card card = cards.get(i);
				if (card.getName().equals(name)) {
					card.Pick(position);
					pickedName = pickedName.equals("") ? name : "";
					break;
				}
			}
		}
	}
	// Helpers
	private void addCard(String name, Point location) {
		ImageIcon icon = resourceMap.getImageIcon("Game." + name);
		icon.setDescription(name);
		Card card = new Card(icon,location);
		cards.add(card);
	}

	/* add help hints in the board */
	private void addHelp() {
		if (!hasHelp) {
			final JLabel helpLabel = new JLabel("Bạn phải chọn quân bài cùng loại với đối thủ");
			helpLabel.setBounds(200, 200, 250, 30);
			helpLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
			boardPanel.add(helpLabel);
			boardPanel.revalidate();
			boardPanel.repaint();
			hasHelp = Boolean.TRUE;

			/* wait 3 seconds and then remove the hint help */
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					boardPanel.remove(helpLabel);
					boardPanel.revalidate();
					boardPanel.repaint();
					hasHelp = Boolean.FALSE;
				}
			}, 3000);
		}
	}

	/* Player moves his picked card to the board */
	public void MoveCard() {
		if (!pickedName.equals("")) {
			Point destination = null; // this is the position the picked card will be moved to
			switch(this.position) {
				case TOP   : destination = new Point (200,200); break;
				case RIGHT : destination = new Point (200,200); break;
				case BOTTOM: destination = new Point (200,200); break;
				case LEFT  : destination = new Point (200,200); break;
			}
			for (int i = 0; i < cards.size(); i++) {
				final Card card = cards.get(i);
				if (card.getName().equals(pickedName)) {
					card.Move(destination);
					cards.remove(card);
					
					/* wait 3 seconds then the card disappeared */
					new java.util.Timer().schedule(new java.util.TimerTask() {
						@Override
						public void run() { card.getJLabel().setVisible(false); }
					}, 3000); 
					break;
				}
			}
		}
	}

}
