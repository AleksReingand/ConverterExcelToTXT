package converter;

import javax.swing.*;

public class RunApp
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(ReadExcel::createGUI);
    }
}

