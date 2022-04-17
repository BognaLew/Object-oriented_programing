package po_projekt2.worldElements;

import po_projekt2.worldElements.Board;
import po_projekt2.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import po_projekt2.Organism;

/**
 *
 * @author Bogna
 */
public class WorldDrafter implements ActionListener, KeyListener{
    private BoardDrafter boardDrafter;
    private MenuDrafter menuDrafter;
    private LegendDrafter legendDrafter;
    private NarratorDrafter narratorDrafter;
    private World world;
    private JPanel main;
    private Toolkit toolkit;
    private Dimension dimension;
    private JFrame frame;
    private final int separation;
    
    public WorldDrafter(){
        toolkit = Toolkit.getDefaultToolkit();
        dimension = toolkit.getScreenSize();
        separation = dimension.width / 100;
        frame = new JFrame("Symulator świata - Bogna Lew 184757");
        frame.setBounds((dimension.width - 1000) / 2, (dimension.height - 800) / 2, 1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(null);
        
        
        main = new JPanel();
        main.setBackground(Color.CYAN);
        main.setBounds(frame.getWidth() / 7, 0, frame.getWidth() *6 / 7 - separation, frame.getHeight() - 2 * separation);
        main.setLayout(null);
        
        narratorDrafter = new NarratorDrafter();
        main.add(narratorDrafter);
        
        menuDrafter = new MenuDrafter(this);
        legendDrafter = new LegendDrafter(this);
        frame.add(menuDrafter);
        frame.add(legendDrafter);
        
        frame.addKeyListener(this);
        frame.add(main);
        frame.setVisible(true);
    }
    
    public void drawWorld(){
        narratorDrafter.drawComments();
        boardDrafter.drawBoard();
        
        
        SwingUtilities.updateComponentTreeUI(frame);
        frame.requestFocusInWindow();
    }
    
    public void addNewOrganismToWorld(int x, int y, Organism.typeOfOrganism type){
        Coordinates c = new Coordinates(x, y);
        Organism o = world.createNewOrganism(type, c);
        world.addOrganism(o);
        boardDrafter.drawBoard();
        SwingUtilities.updateComponentTreeUI(frame);
        frame.requestFocusInWindow();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (world != null && world.getIsEndOfTurn()) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {
                if(world.getIsHumanAlive()){
                    world.getHuman().setDirection(Organism.direction.NONE);
                }
            } else if (world.getIsHumanAlive()) {
                if (keyCode == KeyEvent.VK_UP) {
                    world.getHuman().setDirection(Organism.direction.UP);
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    world.getHuman().setDirection(Organism.direction.DOWN);
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    world.getHuman().setDirection(Organism.direction.LEFT);
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    world.getHuman().setDirection(Organism.direction.RIGHT);
                } else if (keyCode == KeyEvent.VK_SPACE) {
                    if(!world.getHuman().getSkillIsActive() && world.getHuman().getNumberOfTurnsSkillWillBeActive() == 0){
                        world.getHuman().setDirection(Organism.direction.NONE);
                        world.getHuman().activateSkill();
                        drawWorld();
                        return;
                    }else{
                        world.clearComment();
                        world.addComment("Skill cannot be activated. You can activate it after " + world.getHuman().getNumberOfTurnsSkillWillBeActive() + " turns.\n");
                        drawWorld();
                        return;
                    }
                } else {
                    return;
                }
            }else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_SPACE) {
                world.clearComment();
                world.addComment("Human is dead. You cannot move him.\n");
                drawWorld();
                return;
            }
            
            world.setIsEndOfTurn(false);
            world.makeTurn();
            drawWorld();
            world.setIsEndOfTurn(true);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuDrafter.newGame){
            int sizeX = Integer.parseInt(JOptionPane.showInputDialog(frame, "Give width of the world: ", ""));
            int sizeY = Integer.parseInt(JOptionPane.showInputDialog(frame, "Give height of the world: ", ""));
            double fill = Double.parseDouble(JOptionPane.showInputDialog(frame, "Give the fill of the world (0-1): ", ""));
            int numberOfOrganisms = (int)Math.floor(fill * sizeX * sizeY);
            world = new World(numberOfOrganisms, sizeX, sizeY, this);
            if(boardDrafter != null){
                main.remove(boardDrafter);
            }
            boardDrafter = new BoardDrafter();
            boardDrafter.drawBoard();
            narratorDrafter.drawComments();
            main.add(boardDrafter); 
            menuDrafter.enableButtonsToPlay();
            SwingUtilities.updateComponentTreeUI(frame);
            frame.requestFocusInWindow();
        }else if(e.getSource() == menuDrafter.save){
            String fileName = JOptionPane.showInputDialog(frame, "Give file name (add .txt): ", "");
            boolean savedSuccessfully = world.saveWorld(fileName);
            if(savedSuccessfully){
                JOptionPane.showMessageDialog(frame, "World saved successfully.");
            }else{
                JOptionPane.showMessageDialog(frame, "Save failed.");
            }
        }else if(e.getSource() == menuDrafter.load){
            world = new World(this);
            String fileName = JOptionPane.showInputDialog(frame, "Give file name (add .txt): ", "");
            boolean loadedSuccessfully = world.loadWorld(fileName);
            if(loadedSuccessfully){
                JOptionPane.showMessageDialog(frame, "World loaded successfully.");
            }else{
                JOptionPane.showMessageDialog(frame, "Load failed.");
            }
            if(boardDrafter != null){
                main.remove(boardDrafter);
            }
            boardDrafter = new BoardDrafter();
            boardDrafter.drawBoard();
            narratorDrafter.drawComments();
            main.add(boardDrafter); 
            menuDrafter.enableButtonsToPlay();
            SwingUtilities.updateComponentTreeUI(frame);
            frame.requestFocusInWindow();
        }else if(e.getSource() == menuDrafter.nextTurn){
            world.setIsEndOfTurn(false);
            world.makeTurn();
            drawWorld();
            world.setIsEndOfTurn(true);
        }else if(e.getSource() == menuDrafter.exit){
            world.setIsEndOfGame(true);
            frame.dispose();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e){}
    @Override
    public void keyTyped(KeyEvent e) {}
    
    private class MenuDrafter extends JPanel{
        private JButton newGame, load, save, exit, nextTurn;
        
        public MenuDrafter(ActionListener a){
            super();
            setBackground(Color.LIGHT_GRAY);
            setBounds(0, 0, frame.getWidth() / 7, frame.getHeight() / 5 + separation);
            setLayout(new FlowLayout(FlowLayout.CENTER));
            
            newGame = new JButton("New Game");
            newGame.setBackground(Color.WHITE);
            newGame.setEnabled(true);
            newGame.addActionListener(a);
            add(newGame);
            
            load = new JButton("Load Game");
            load.setBackground(Color.WHITE);
            load.setEnabled(true);
            load.addActionListener(a);
            add(load);
            
            save = new JButton("Save Game");
            save.setBackground(Color.WHITE);
            save.setEnabled(false);
            save.addActionListener(a);
            add(save);
            
            exit = new JButton("Exit Game");
            exit.setBackground(Color.WHITE);
            exit.setEnabled(true);
            exit.addActionListener(a);
            add(exit);
            
            nextTurn = new JButton("Next Turn");
            nextTurn.setBackground(Color.GRAY);
            nextTurn.setEnabled(false);
            nextTurn.addActionListener(a);
            add(nextTurn);
            
        }
        
        public void enableButtonsToPlay(){
            save.setEnabled(true);
            nextTurn.setEnabled(true);
        }
    }
    
    private class LegendDrafter extends JPanel implements ActionListener{
        private JButton human, wolf, sheep, fox, turtle, antelope, cyberSheep, grass, milt, guarana, belladonna, hogweed;
        private WorldDrafter worldD;
        private int x, y;
        
        public LegendDrafter(WorldDrafter worldD){
            super();
            this.worldD = worldD;
            setBackground(Color.LIGHT_GRAY);
            setBounds(0, frame.getHeight()/5 + separation, frame.getWidth() / 7, frame.getHeight() * 4 / 5);
            setLayout(new FlowLayout(FlowLayout.CENTER));
            
            human = new JButton("Human");
            human.setBackground(new Color(255,235,205));
            human.setEnabled(false);
            human.setAlignmentX(Component.CENTER_ALIGNMENT);
            human.addActionListener(this);
            add(human);
            
            wolf = new JButton("Wolf");
            wolf.setBackground(Color.DARK_GRAY);
            wolf.setForeground(Color.WHITE);
            wolf.setEnabled(false);
            wolf.setAlignmentX(Component.CENTER_ALIGNMENT);
            wolf.addActionListener(this);
            add(wolf);
            
            sheep = new JButton("Sheep");
            sheep.setBackground(Color.LIGHT_GRAY);
            sheep.setEnabled(false);
            sheep.setAlignmentX(Component.CENTER_ALIGNMENT);
            sheep.addActionListener(this);
            add(sheep);
            
            fox = new JButton("Fox");
            fox.setBackground(new Color(255,140,0));
            fox.setEnabled(false);
            fox.setAlignmentX(Component.CENTER_ALIGNMENT);
            fox.addActionListener(this);
            add(fox);
            
            turtle = new JButton("Turtle");
            turtle.setBackground(new Color(0,100,0));
            turtle.setForeground(Color.WHITE);
            turtle.setEnabled(false);
            turtle.setAlignmentX(Component.CENTER_ALIGNMENT);
            turtle.addActionListener(this);
            add(turtle);
            
            antelope = new JButton("Antelope");
            antelope.setBackground(new Color(150, 75, 0));
            antelope.setEnabled(false);
            antelope.setAlignmentX(Component.CENTER_ALIGNMENT);
            antelope.addActionListener(this);
            add(antelope);
            
            cyberSheep = new JButton("Cyber Sheep");
            cyberSheep.setBackground(new Color(112,128,144));
            cyberSheep.setEnabled(false);
            cyberSheep.addActionListener(this);
            add(cyberSheep);
            
            grass = new JButton("Grass");
            grass.setBackground(new Color(0,255,127));
            grass.setEnabled(false);
            grass.setAlignmentX(Component.CENTER_ALIGNMENT);
            grass.addActionListener(this);
            add(grass);
            
            milt = new JButton("Milt");
            milt.setBackground(Color.YELLOW);
            milt.setEnabled(false);
            milt.setAlignmentX(Component.CENTER_ALIGNMENT);
            milt.addActionListener(this);
            add(milt);
            
            guarana = new JButton("Guarana");
            guarana.setBackground(Color.RED);
            guarana.setEnabled(false);
            guarana.setAlignmentX(Component.CENTER_ALIGNMENT);
            guarana.addActionListener(this);
            add(guarana);
            
            belladonna = new JButton("Belladonna");
            belladonna.setBackground(new Color(25,25,112));
            belladonna.setForeground(Color.WHITE);
            belladonna.setEnabled(false);
            belladonna.setAlignmentX(Component.CENTER_ALIGNMENT);
            belladonna.addActionListener(this);
            add(belladonna);
            
            hogweed = new JButton("Hogweed");
            hogweed.setBackground(new Color(173,255,47));
            hogweed.setEnabled(false);
            hogweed.setAlignmentX(Component.CENTER_ALIGNMENT);
            hogweed.addActionListener(this);
            add(hogweed);
        }
        
         @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == wolf){
                worldD.addNewOrganismToWorld(x, y, Organism.typeOfOrganism.WOLF);
                disableButtons();
            }else if(e.getSource() == sheep){
                worldD.addNewOrganismToWorld(x, y, Organism.typeOfOrganism.SHEEP);
                disableButtons();
            }else if(e.getSource() == fox){
                worldD.addNewOrganismToWorld(x, y, Organism.typeOfOrganism.FOX);
                disableButtons();
            }else if(e.getSource() == turtle){
                worldD.addNewOrganismToWorld(x, y, Organism.typeOfOrganism.TURTLE);
                disableButtons();
            }else if(e.getSource() == antelope){
                worldD.addNewOrganismToWorld(x, y, Organism.typeOfOrganism.ANTELOPE);
                disableButtons();
            }else if(e.getSource() == cyberSheep){
                worldD.addNewOrganismToWorld(x, y, Organism.typeOfOrganism.CYBER_SHEEP);
                disableButtons();
            }else if(e.getSource() == grass){
                worldD.addNewOrganismToWorld(x, y, Organism.typeOfOrganism.GRASS);
                disableButtons();
            }else if(e.getSource() == milt){
                worldD.addNewOrganismToWorld(x, y, Organism.typeOfOrganism.MILT);
                disableButtons();
            }else if(e.getSource() == guarana){
                worldD.addNewOrganismToWorld(x, y, Organism.typeOfOrganism.GUARANA);
                disableButtons();
            }else if(e.getSource() == belladonna){
                worldD.addNewOrganismToWorld(x, y, Organism.typeOfOrganism.BELLADONNA);
                disableButtons();
            }else if(e.getSource() == hogweed){
                worldD.addNewOrganismToWorld(x, y, Organism.typeOfOrganism.HOGWEED);
                disableButtons();
            }
        }
        
        public void enableButtons(int x, int y){
            this.x = x;
            this.y = y;
            wolf.setEnabled(true);
            sheep.setEnabled(true);
            fox.setEnabled(true);
            turtle.setEnabled(true);
            antelope.setEnabled(true);
            cyberSheep.setEnabled(true);
            grass.setEnabled(true);
            milt.setEnabled(true);
            guarana.setEnabled(true);
            belladonna.setEnabled(true);
            hogweed.setEnabled(true);
            
            SwingUtilities.updateComponentTreeUI(frame);
            frame.requestFocusInWindow();
        }
        
        public void disableButtons(){
            wolf.setEnabled(false);
            sheep.setEnabled(false);
            fox.setEnabled(false);
            turtle.setEnabled(false);
            antelope.setEnabled(false);
            cyberSheep.setEnabled(false);
            grass.setEnabled(false);
            milt.setEnabled(false);
            guarana.setEnabled(false);
            belladonna.setEnabled(false);
            hogweed.setEnabled(false);
            
        }
    }
    
    private class NarratorDrafter extends JPanel{
        private String text = "";
        private String instructions = "Arrows - controling human\n space - activating special skill (\"Antelope's speed\")\nEnter - move to next turn\n\n";
        private JTextArea textArea;
        
        public NarratorDrafter() {
            super();
            setBounds(0, 0, main.getWidth(), main.getHeight() / 6);
            textArea = new JTextArea(text);
            textArea.setEditable(false);
            setLayout(new CardLayout());

            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setMargin(new Insets(5, 5, 5, 5));
            JScrollPane sp = new JScrollPane(textArea);
            add(sp);
        }
        
        public void drawComments() {
            String comments = world.getComments();
            if(comments != null){
                text = instructions + comments; 
            }else{
                text = instructions;
            }
            textArea.setText(text);
        }
    }
    
    private class BoardDrafter extends JPanel{
        private Board board;
        private BoardElement[][] boardElements;
        
        public BoardDrafter(){
            super();
            board = world.getBoard();
            boardElements = new BoardElement[board.getSizeY()][board.getSizeX()];
            setBounds(0, main.getHeight() / 6, main.getWidth(), main.getHeight() * 5 / 6 - separation);
            for(int i = 0; i < board.getSizeY(); i++){
                for(int j = 0; j < board.getSizeX(); j++){
                    Organism o = board.getOrganism(j, i);
                    if(o == null){
                        boardElements[i][j] = new BoardElement(j,i);
                    }else{
                        boardElements[i][j] = new BoardElement(j,i, o.getColor());
                    }
                    boardElements[i][j].addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e){
                            if(e.getSource() instanceof BoardElement){
                                BoardElement tmp = (BoardElement) e.getSource();
                                legendDrafter.enableButtons(tmp.x, tmp.y);
                            }
                        }
                    });
                    this.add(boardElements[i][j]);
                }
            }
            this.setLayout(new GridLayout(board.getSizeY(), board.getSizeX()));
        }
        
        public void drawBoard(){
            for(int i = 0; i < board.getSizeY(); i++){
                for(int j = 0; j < board.getSizeX(); j++){
                    Organism tmp = board.getOrganism(j, i);
                    if(tmp == null){
                        boardElements[i][j].setEnabled(true);
                        boardElements[i][j].setColor(Color.WHITE);
                    }else{
                        boardElements[i][j].setEnabled(false);
                        boardElements[i][j].setColor(tmp.getColor());
                    }
                }
            }
        }
        
        public class BoardElement extends JButton{
            private final int x, y;
            private Color color;
            public BoardElement(int x, int y){
                this.x = x;
                this.y = y;
                this.color = Color.WHITE;
                setBackground(Color.WHITE);
            }
            
            public BoardElement(int x, int y, Color color){
                this.x = x;
                this.y = y;
                this.color = color;
                setBackground(color);
            }
            
            public void setColor(Color color){
                this.color = color;
                setBackground(color);
            }
            
            public Color getColor(){
                return this.color;
            }
        }
    }
}
