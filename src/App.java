import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;
import java.util.Stack;

public class App extends JFrame {

    private JPanel towerPanel;
    private JPanel panelDown;
    private JPanel PanelUp;
    private JPanel torreInicial;
    private JPanel torreDestino;
    private JPanel torrePivote;
    private JButton bInicial;
    private JButton cInicial;
    private JButton aPivote;
    private JButton cPivote;
    private JButton aDestino;
    private JButton bDestino;
    private JComboBox comboDiscos;
    private int paso = 0;
    private JButton iniciarButton;
    private boolean c = false;
    private JButton reiniciarButton;
    private JButton resolverButton;
    private JLabel minMovimientos;
    private JLabel nMovimientos;
    private boolean continueFlag = true;
    private JPanel fieldPanel;
    private JPanel fieldPanel2;
    private JPanel fieldPanel3;
    TowerHanoi t = new TowerHanoi();


    public App() {
        setContentPane(towerPanel);

        //Torre inicial
        torreInicial.setLayout(new BorderLayout());
        fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.PAGE_AXIS));
        torreInicial.add(fieldPanel, BorderLayout.SOUTH);

        //Torre pivote
        torrePivote.setLayout(new BorderLayout());
        fieldPanel2 = new JPanel();
        fieldPanel2.setLayout(new BoxLayout(fieldPanel2, BoxLayout.PAGE_AXIS));
        torrePivote.add(fieldPanel2, BorderLayout.SOUTH);

        //Torre destino
        torreDestino.setLayout(new BorderLayout());
        fieldPanel3 = new JPanel();
        fieldPanel3.setLayout(new BoxLayout(fieldPanel3, BoxLayout.PAGE_AXIS));
        torreDestino.add(fieldPanel3, BorderLayout.SOUTH);

        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciar();
            }
        });
        bInicial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inicialApivote();
            }
        });
        cInicial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inicialAdestino();
            }
        });
        aPivote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pivoteAinicial();
            }
        });
        cPivote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pivoteAdestino();
            }
        });
        aDestino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destinoAinicial();
            }
        });
        bDestino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destinoApivote();
            }
        });
        resolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resolver();
            }
        });
        reiniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciar();
            }
        });
    }

    private void iniciar(){
        paso = 0;
        t.getMovimientos().clear();
        t.setIntentos(0);
        nMovimientos.setText(String.valueOf(t.getIntentos()));

        //Se remueven los elementos de los paneles
        fieldPanel.removeAll();
        fieldPanel2.removeAll();
        fieldPanel3.removeAll();

        //Se eliminan los elementos de las pilas en caso de haber
        t.getTorreI().clear();
        t.getTorreP().clear();
        t.getTorreD().clear();

        //Se guarda en una v el numero de discos
        int discosSeleccionados = Integer.parseInt(comboDiscos.getSelectedItem().toString());

        //Actualizacion del numero de discos y el minimo de intentos
        t.setnDiscos(discosSeleccionados);
        t.setMinIntentos();

        //For que crea los discos en la torre inicial.
        for (int i = 0; i < discosSeleccionados; i++) {
            String hashtags = "";
            JTextField field = new JTextField(10);
            for (int j = 0; j < discosSeleccionados-i; j++) {
                hashtags += "#";
            }
            t.getTorreI().add(hashtags);
            field.setText(hashtags);
            field.setHorizontalAlignment(SwingConstants.CENTER);
            field.setMaximumSize(field.getPreferredSize());
            fieldPanel.add(field,0);
        }
        fieldPanel.revalidate();
        fieldPanel.repaint();

        minMovimientos.setText(String.valueOf(t.getMinIntentos()));
    }
    private void reiniciar(){
        t.getMovimientos().clear();
        t.setIntentos(0);
        nMovimientos.setText(String.valueOf(t.getIntentos()));

        fieldPanel.removeAll();
        fieldPanel2.removeAll();
        fieldPanel3.removeAll();

        t.getTorreI().clear();
        t.getTorreP().clear();
        t.getTorreD().clear();

        fieldPanel.revalidate();
        fieldPanel.repaint();
    }
    private void inicialApivote(){
        if (fieldPanel.getComponentCount() > 0) {
            try {
                // Si la torre pivote está vacía, no haremos la comparación.
                if (!t.getTorreP().empty() && t.getTorreP().peek().compareTo(t.getTorreI().peek()) <= 0) {
                    return; // Si la comparación es inválida, termina el método aquí.
                }
            } catch (EmptyStackException e) {
                // No haremos nada aquí, porque si la torre pivote está vacía, queremos continuar con el movimiento.
            }
            Component component = fieldPanel.getComponent(0);
            if (component instanceof JTextField) {

                t.inicialApivote();

                JTextField field = (JTextField) component;

                JPanel pivotFieldPanel = (JPanel) torrePivote.getComponent(0);

                // Mueve el JTextField de fieldPanel a pivotFieldPanel
                fieldPanel.remove(field);
                pivotFieldPanel.add(field, 0);

                fieldPanel.revalidate();
                fieldPanel.repaint();

                pivotFieldPanel.revalidate();
                pivotFieldPanel.repaint();

                nMovimientos.setText(String.valueOf(t.getIntentos()));

                pasos("IP");

                if (juegoCompletado()) {
                    JOptionPane.showMessageDialog(null, "¡Felicidades! Has completado el juego.");
                }
            }
        }
    }
    private void inicialAdestino(){
        if (fieldPanel.getComponentCount() > 0) {
            try {
                // Si la torre pivote está vacía, no haremos la comparación.
                if (!t.getTorreD().empty() && t.getTorreD().peek().compareTo(t.getTorreI().peek()) <= 0) {
                    return; // Si la comparación es inválida, termina el método aquí.
                }
            } catch (EmptyStackException e) {
                // No haremos nada aquí, porque si la torre pivote está vacía, queremos continuar con el movimiento.
            }
            Component component = fieldPanel.getComponent(0);
            if (component instanceof JTextField) {
                t.inicialAdestino();

                JTextField field = (JTextField) component;

                JPanel pivotFieldPanel = (JPanel) torreDestino.getComponent(0);

                // Mueve el JTextField de fieldPanel a pivotFieldPanel
                fieldPanel.remove(field);
                pivotFieldPanel.add(field, 0);

                fieldPanel.revalidate();
                fieldPanel.repaint();

                pivotFieldPanel.revalidate();
                pivotFieldPanel.repaint();

                nMovimientos.setText(String.valueOf(t.getIntentos()));

                pasos("ID");

                if (juegoCompletado()) {
                    JOptionPane.showMessageDialog(null, "¡Felicidades! Has completado el juego.");
                }
            }
        }
    }
    private void pivoteAinicial(){
        if (fieldPanel2.getComponentCount() > 0) {
            try {
                // Si la torre pivote está vacía, no haremos la comparación.
                if (!t.getTorreI().empty() && t.getTorreI().peek().compareTo(t.getTorreP().peek()) <= 0) {
                    return; // Si la comparación es inválida, termina el método aquí.
                }
            } catch (EmptyStackException e) {
                // No haremos nada aquí, porque si la torre pivote está vacía, queremos continuar con el movimiento.
            }
            Component component = fieldPanel2.getComponent(0);
            if (component instanceof JTextField) {
                t.pivoteAinicial();

                JTextField field = (JTextField) component;

                JPanel initialFieldPanel = (JPanel) torreInicial.getComponent(0);

                // Mueve el JTextField de fieldPanel2 a initialFieldPanel
                fieldPanel2.remove(field);
                initialFieldPanel.add(field, 0);

                fieldPanel2.revalidate();
                fieldPanel2.repaint();

                initialFieldPanel.revalidate();
                initialFieldPanel.repaint();


                nMovimientos.setText(String.valueOf(t.getIntentos()));

                pasos("PI");

                if (juegoCompletado()) {
                    JOptionPane.showMessageDialog(null, "¡Felicidades! Has completado el juego.");
                }
            }
        }
    }
    private void pivoteAdestino(){
        if (fieldPanel2.getComponentCount() > 0) {
            try {
                // Si la torre pivote está vacía, no haremos la comparación.
                if (!t.getTorreD().empty() && t.getTorreD().peek().compareTo(t.getTorreP().peek()) <= 0) {
                    return; // Si la comparación es inválida, termina el método aquí.
                }
            } catch (EmptyStackException e) {
                // No haremos nada aquí, porque si la torre pivote está vacía, queremos continuar con el movimiento.
            }
            Component component = fieldPanel2.getComponent(0);
            if (component instanceof JTextField) {
                t.pivoteAdestino();

                JTextField field = (JTextField) component;

                JPanel initialFieldPanel = (JPanel) torreDestino.getComponent(0);

                // Mueve el JTextField de fieldPanel2 a initialFieldPanel
                fieldPanel2.remove(field);
                initialFieldPanel.add(field, 0);

                fieldPanel2.revalidate();
                fieldPanel2.repaint();

                initialFieldPanel.revalidate();
                initialFieldPanel.repaint();

                nMovimientos.setText(String.valueOf(t.getIntentos()));

                pasos("PD");

                if (juegoCompletado()) {
                    JOptionPane.showMessageDialog(null, "¡Felicidades! Has completado el juego.");
                }
            }
        }
    }
    private void destinoAinicial(){
        if (fieldPanel3.getComponentCount() > 0) {
            try {
                // Si la torre pivote está vacía, no haremos la comparación.
                if (!t.getTorreI().empty() && t.getTorreI().peek().compareTo(t.getTorreD().peek()) <= 0) {
                    return; // Si la comparación es inválida, termina el método aquí.
                }
            } catch (EmptyStackException e) {
                // No haremos nada aquí, porque si la torre pivote está vacía, queremos continuar con el movimiento.
            }
            Component component = fieldPanel3.getComponent(0);
            if (component instanceof JTextField) {
                t.destinoAinicial();

                JTextField field = (JTextField) component;

                JPanel initialFieldPanel = (JPanel) torreInicial.getComponent(0);

                // Mueve el JTextField de fieldPanel2 a initialFieldPanel
                fieldPanel3.remove(field);
                initialFieldPanel.add(field, 0);

                fieldPanel3.revalidate();
                fieldPanel3.repaint();

                initialFieldPanel.revalidate();
                initialFieldPanel.repaint();

                nMovimientos.setText(String.valueOf(t.getIntentos()));

                pasos("DI");

                if (juegoCompletado()) {
                    JOptionPane.showMessageDialog(null, "¡Felicidades! Has completado el juego.");
                }
            }
        }
    }
    private void destinoApivote(){
        if (fieldPanel3.getComponentCount() > 0) {
            try {
                // Si la torre pivote está vacía, no haremos la comparación.
                if (!t.getTorreP().empty() && t.getTorreP().peek().compareTo(t.getTorreD().peek()) <= 0) {
                    return; // Si la comparación es inválida, termina el método aquí.
                }
            } catch (EmptyStackException e) {
                // No haremos nada aquí, porque si la torre pivote está vacía, queremos continuar con el movimiento.
            }
            Component component = fieldPanel3.getComponent(0);
            if (component instanceof JTextField) {
                t.destinoApivote();

                JTextField field = (JTextField) component;

                JPanel initialFieldPanel = (JPanel) torrePivote.getComponent(0);

                // Mueve el JTextField de fieldPanel2 a initialFieldPanel
                fieldPanel3.remove(field);
                initialFieldPanel.add(field, 0);

                fieldPanel3.revalidate();
                fieldPanel3.repaint();

                initialFieldPanel.revalidate();
                initialFieldPanel.repaint();

                nMovimientos.setText(String.valueOf(t.getIntentos()));

                pasos("DP");


                if (juegoCompletado()) {
                    JOptionPane.showMessageDialog(null, "¡Felicidades! Has completado el juego.");
                }
            }
        }
    }
    private void pasos(String p){
        StringBuilder invertido = new StringBuilder(p);
        if(t.getMovimientos().isEmpty() || (!t.getMovimientos().peek().equals(p) && !t.getMovimientos().peek().equals(invertido.reverse().toString()))){
            // Agrega el movimiento a la pila
            t.getMovimientos().push(p);
            c = true;
        }else{
            c = false;
            t.getMovimientos().pop();
        }
    }
    private void resolver(){
        paso = 0;

        if(!juegoCompletado()){
            if(t.getTorreI().size() != t.getnDiscos()){
                undoMoves();
            }

            if(t.getTorreI().size() == t.getnDiscos()){
                solveHanoi(t.getnDiscos(), "o", "a", "d");
            }
        }else{
            JOptionPane.showMessageDialog(null, "¡Felicidades! Has completado el juego.");
        }
    }
    public void moveDisk(String  origin, String  destination) {
        if(origin.equals("o") && destination.equals("a")){
            inicialApivote();
        }else if(origin.equals("o") && destination.equals("d")){
            inicialAdestino();
        }else if(origin.equals("a") && destination.equals("o")){
            pivoteAinicial();
        }else if(origin.equals("a") && destination.equals("d")){
            pivoteAdestino();
        }else if(origin.equals("d") && destination.equals("o")){
            destinoAinicial();
        }else{
            destinoApivote();
        }
    }
    public boolean askForContinue() {
        if(!juegoCompletado()){
            paso+=1;
            int opcion = JOptionPane.showConfirmDialog(null, "PASO #" + paso + "\n¿Desea continuar?", "Pregunta", JOptionPane.YES_NO_OPTION);
            return opcion == JOptionPane.YES_OPTION;
        }
        return false;
    }
    private void undoMoves() {
        boolean paso = true;

        if (t.getMovimientos().isEmpty()) {
            return; // No hay movimientos para deshacer, salir del método
        }

        while (!t.getMovimientos().isEmpty() && paso) {
            if(juegoCompletado() || (t.getTorreI().size() == t.getnDiscos())){
                t.getMovimientos().clear();
                break;
            }
            String move = t.getMovimientos().pop();
            if ("IP".equals(move)) {
                pivoteAinicial();
            } else if ("PD".equals(move)) {
                destinoApivote();
            } else if ("ID".equals(move)) {
                destinoAinicial();
            }else if ("PI".equals(move)) {
                inicialApivote();
            }else if ("DI".equals(move)) {
                inicialAdestino();
            }else{
                pivoteAdestino();
            }
            if(c){
                t.getMovimientos().pop();
            }
            paso = askForContinue();
        }
    }
    public void solveHanoi(int n, String origen, String auxiliar, String destino) {
        if(!juegoCompletado()){
            if (n == 1) {
                moveDisk(origen, destino);
                continueFlag = askForContinue();
            } else {
                solveHanoi(n - 1, origen, destino, auxiliar);
                if (continueFlag) {
                    moveDisk(origen, destino);
                    continueFlag = askForContinue();
                }
                if (continueFlag) {
                    solveHanoi(n - 1, auxiliar, origen, destino);
                }
            }
        }
    }
    private boolean juegoCompletado() {
        return t.juegoCompletado();
    }

}
