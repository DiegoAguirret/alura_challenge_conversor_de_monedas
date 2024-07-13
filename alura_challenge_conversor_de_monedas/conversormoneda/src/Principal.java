import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Principal extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Principal frame = new Principal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Principal() {
        setResizable(false);
        setTitle("Conversor de monedas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(240, 240, 240));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(20, 70, 20, 70));
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        contentPane.add(panel, BorderLayout.CENTER);

        JComboBox<String> cbConversores = new JComboBox<>();
        cbConversores.setMaximumRowCount(3);
        cbConversores.setModel(new DefaultComboBoxModel<>(new String[]{"Conversor de monedas"}));
        cbConversores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(cbConversores);

        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String opcion = (String) cbConversores.getSelectedItem();
                if (opcion.equals("Conversor de monedas")) {
                    List<List<String>> lstOpciones = new ArrayList<>();
                    String[] valores = new String[10];
                    int indiceValores = 0;
                    lstOpciones.add(Arrays.asList("SOL", "USD", "Nuevo sol", "Dólar estadounidense", "3.73"));
                    lstOpciones.add(Arrays.asList("SOL", "EU", "Nuevo sol", "Euro", "4.09"));
                    lstOpciones.add(Arrays.asList("SOL", "GBP", "Nuevo sol", "Libra esterlina", "4.76"));
                    lstOpciones.add(Arrays.asList("SOL", "JPY", "Nuevo sol", "Yen japonés", "0.024"));
                    lstOpciones.add(Arrays.asList("SOL", "KRW", "Nuevo sol", "Won surcoreano", "0.002715"));
                    for (List<String> lista : lstOpciones) {
                        valores[indiceValores++] = "De " + lista.get(2) + " a " + lista.get(3);
                        valores[indiceValores++] = "De " + lista.get(3) + " a " + lista.get(2);
                    }
                    Object opcionMoneda = JOptionPane.showInputDialog(
                            null,
                            "Seleccione una opción de conversión:",
                            "Conversor de monedas",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            valores,
                            valores[0]);
                    if (opcionMoneda != null) {
                        Object input = JOptionPane.showInputDialog(
                                null,
                                "Ingrese la cantidad de dinero que deseas convertir: ",
                                "Conversor de monedas",
                                JOptionPane.QUESTION_MESSAGE);
                        if (input != null) {
                            try {
                                String opcionConversion = (String) opcionMoneda, opInicial, opFinal;
                                Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");

                                if (!pattern.matcher(input.toString()).matches()) {
                                    throw new NumberFormatException();
                                }
                                double valor = Double.parseDouble(input.toString()), conversion = 0, cambio = 0;
                                opcionConversion = opcionConversion.replace("De ", "");
                                opcionConversion = opcionConversion.replace(" a ", ";");
                                String[] subCadenas = opcionConversion.split(";");
                                opInicial = subCadenas[0];
                                opFinal = subCadenas[1];
                                for (List<String> lista : lstOpciones) {
                                    if ((opInicial.equals(lista.get(2)) && opFinal.equals(lista.get(3))) ||
                                            (opInicial.equals(lista.get(3)) && opFinal.equals(lista.get(2)))) {
                                        cambio = Double.parseDouble(lista.get(4));
                                        break;
                                    }
                                }
                                if (opInicial.equals("Nuevo sol")) {
                                    conversion = valor / cambio;
                                    DecimalFormat df = new DecimalFormat("#.##");

                                    JOptionPane.showMessageDialog(null, "Tienes " + df.format(conversion) + " " + opFinal, "Resultado", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    conversion = valor * cambio;
                                    DecimalFormat df = new DecimalFormat("#.##");

                                    JOptionPane.showMessageDialog(null, "Tienes S/. " + df.format(conversion) + " " + opFinal, "Resultado", JOptionPane.INFORMATION_MESSAGE);
                                }


                                int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar usando el programa?", "Confirmación", JOptionPane.YES_NO_OPTION);

                                if (respuesta == JOptionPane.NO_OPTION) {
                                    JOptionPane.showMessageDialog(null, "Programa finalizado", "Información", JOptionPane.INFORMATION_MESSAGE);
                                    System.exit(0);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "El valor ingresado no es un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });
        panel.add(btnContinuar);

        JLabel lblTitulo = new JLabel("<html>Bienvenido al conversor de monedas</html>");
        lblTitulo.setVerticalAlignment(SwingConstants.TOP);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Monospaced", Font.PLAIN, 14));
        lblTitulo.setBorder(new EmptyBorder(50, 0, 0, 0));
        contentPane.add(lblTitulo, BorderLayout.NORTH);
    }
}
