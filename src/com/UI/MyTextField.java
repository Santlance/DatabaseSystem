package com.UI;

import javax.swing.*;
import java.awt.*;

public class MyTextField extends JTextField {
    public MyTextField(int columns, Font font) {
        this.setColumns(columns);
        this.setFont(font);
    }
}
