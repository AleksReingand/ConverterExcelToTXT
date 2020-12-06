package converter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReadExcel extends JPanel implements ActionListener
{
  static private final String newline = "\n";
  JButton chooseButton, saveButton;
  JTextArea log;
  JFileChooser fc;
  ValidatorImpl validatorImpl;

  public ReadExcel()
  {
    super(new BorderLayout());
    validatorImpl = new ValidatorImpl();

    //Create top menu bar
    JMenuBar menuBar = new JMenuBar();
    JLabel sign = new JLabel("by @Aleks_R Air-Doctor  ");

    menuBar.setUI(new BasicMenuBarUI()
    {
      @Override
      public void paint(Graphics g, JComponent c)
      {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
      }
    });

    sign.setFont(new Font("Verdana", Font.PLAIN, 7));
    sign.setForeground(Color.darkGray);

    menuBar.add(Box.createHorizontalGlue());
    menuBar.add(sign);
    sign.setAlignmentX(Component.LEFT_ALIGNMENT);

    //Create the log first, because the action listeners
    //need to refer to it.
    log = new JTextArea(5, 20);
    log.setMargin(new Insets(5, 5, 5, 5));
    log.setEditable(false);
    JScrollPane logScrollPane = new JScrollPane(log);

    //Create a file chooser
    fc = new JFileChooser();

    //Create the open button.  We use the image from the JLF
    //Graphics Repository (but we extracted it from the jar).
    chooseButton = new JButton("Choose .xlsx file...");
    chooseButton.addActionListener(this);

    //Create the save button.  We use the image from the JLF
    //Graphics Repository (but we extracted it from the jar).
    saveButton = new JButton("Save as .txt file...");
    saveButton.setVisible(false);
    saveButton.addActionListener(this);

    //For layout purposes, put the buttons in a separate panel
    JPanel buttonPanel = new JPanel(); //use FlowLayout
    buttonPanel.add(chooseButton);
    buttonPanel.add(saveButton);

    //Add the buttons and the log to this panel.
    add(menuBar, BorderLayout.NORTH);
    add(logScrollPane, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.PAGE_END);
  }

  public void actionPerformed(ActionEvent e)
  {
    //Handle choose button action.
    if(e.getSource() == chooseButton)
    {
      int returnVal = fc.showOpenDialog(ReadExcel.this);

      if(returnVal == JFileChooser.APPROVE_OPTION)
      {
        File file = fc.getSelectedFile();
        //This is where a real application would open the file.
        log.append("Choosing: " + file.getName() + newline);

        if(validatorImpl.validateFile(file))
          saveButton.setVisible(true);
        else
          validatorImpl.getErrors().forEach(error -> log.append(error + newline));
      }
      else
      {
        log.append("Choosing command cancelled by user." + newline);
      }
      log.setCaretPosition(log.getDocument().getLength());

      //Handle save button action.
    }
    else if(e.getSource() == saveButton)
    {
      File excelFile = fc.getSelectedFile();
      File txtFile = new File("TXT_" + DateTimeFormatter.ofPattern("yyyyMMddHHmm").format(LocalDateTime.now()) + ".txt");
      fc.setSelectedFile(txtFile);
      int returnVal = fc.showSaveDialog(ReadExcel.this);
      String pathToSave = fc.getCurrentDirectory().getAbsolutePath();
      if(returnVal == JFileChooser.APPROVE_OPTION)
      {
        Converter.convertExcelToTxt(excelFile, txtFile, pathToSave + "\\");
        //This is where a real application would save the file.
        log.append("File :" + txtFile.getName() + " saved to: " + pathToSave + newline);
      }
      else
      {
        log.append("Save command cancelled by user." + newline);
      }
      log.setCaretPosition(log.getDocument().getLength());
    }
  }

  /**
   * Create the GUI and show it.
   */
  public static void createGUI()
  {
    //Create and set up the window.
    JFrame frame = new JFrame("Converter Excel to TXT");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 250);

    //Add content to the window.
    frame.add(new ReadExcel());

    //Display the window.
    frame.setVisible(true);
  }
}