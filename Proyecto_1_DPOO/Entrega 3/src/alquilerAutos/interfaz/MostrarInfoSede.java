package alquilerAutos.interfaz;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import alquilerAutos.logica.AlquilerVehiculos;
import alquilerAutos.manejoDatos.Sede;

import java.awt.*;
import java.util.HashMap;

@SuppressWarnings("serial")
public class MostrarInfoSede extends JPanel{
	
	private VentanaPrincipal ventanaPrincipal;
	private JLabel titulo;
	private JTable tablaVehiculos;
	private JTable tablaDias;
	private JPanel panelAbajo = new JPanel();
	private JPanel panelBotones = new JPanel();
	private JButton botonVolver = new JButton("VOLVER");
	private JButton botonMostrarGrafico = new JButton("Mostrar info disponibilidad");
	private Color color;
	private Sede sede;
	private AlquilerVehiculos alquiler;
	
	public MostrarInfoSede(Color color, VentanaPrincipal ventanaPrincipal, Sede sede, AlquilerVehiculos alquiler) {
		this.ventanaPrincipal = ventanaPrincipal;
		this.sede = sede;
		this.alquiler = alquiler;
		configurarPanel();
	}
	
	private void configurarTitulo()
	{
		titulo = new JLabel("Informacion" + sede.getNombre());
		titulo.setFont(new Font("Roboto Mono", Font.PLAIN, 60));
		titulo.setForeground(color);
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(titulo, BorderLayout.NORTH);
	}
	
	private void configurarTablaDias() {
		DefaultTableModel modelo = new DefaultTableModel(7, 2);
		
		String[] dias = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
		String horario = "07:00 18:00";
		
		for (int i = 0; i<7;i++) {
			modelo.setValueAt(dias[i], i, 0);
			modelo.setValueAt(horario,i,1);
		}
		
		tablaDias = new JTable(modelo);
		tablaDias.setBackground(color);
		tablaDias.setShowGrid(false);
        tablaDias.setIntercellSpacing(new Dimension(2, 2));
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                // Establecer color del texto a blanco
                comp.setForeground(Color.WHITE);

                // Utilizar fuente Roboto
                return comp;
            }
            };
        renderer.setHorizontalAlignment(SwingConstants.CENTER); // Centrar contenido en las celdas
        tablaDias.setDefaultRenderer(Object.class, renderer);
        setSize(400, 300);
        
        panelAbajo.add(tablaDias, BorderLayout.WEST);
        
	}
	
	private void configurarPanelBotones() {
		
		botonVolver.setBackground(color);
		botonVolver.setForeground(Color.WHITE);
		botonVolver.setOpaque(true);
		botonVolver.addActionListener(event ->
		{
			String etiqueta = ((JButton) event.getSource()).getText();
			ventanaPrincipal.cambiarPanel(etiqueta);
			
		});
		
		botonMostrarGrafico.setBackground(color);
		botonMostrarGrafico.setForeground(Color.WHITE);
		botonMostrarGrafico.setOpaque(true);
		botonMostrarGrafico.addActionListener(event ->
		{
			GraficarDisponibilidad graficoDisponibilidad = new GraficarDisponibilidad(alquiler.calcularValoresDisponibilidad());
		}		
		);
		
		panelAbajo.add(panelBotones, BorderLayout.SOUTH);
	}
	
	private void configurarTablaVehiculos()
	{
		DefaultTableModel modelo = new DefaultTableModel(4, 2);
		HashMap<String, Integer> datos = alquiler.vehiculosSegunCategoria();
		
		int i = 0;
		
		for (String key: datos.keySet()) {
			modelo.setValueAt(key, i, 0);
			modelo.setValueAt("" + datos.get(key), i, 1);
		}
		
		tablaVehiculos = new JTable(modelo);
		tablaVehiculos.setBackground(color);
		tablaVehiculos.setShowGrid(false);
		tablaVehiculos.setIntercellSpacing(new Dimension(2, 2));
		
		 DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(){
	            @Override
	            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	                comp.setForeground(Color.WHITE);
	                return comp;
	            }
	            };
        
	     renderer.setHorizontalAlignment(SwingConstants.CENTER); // Centrar contenido en las celdas
	     tablaDias.setDefaultRenderer(Object.class, renderer);
	     setSize(400, 300);
	            
	     panelAbajo.add(tablaDias, BorderLayout.EAST);
		
		
	}
	private void configurarPanel()
	{
		setLayout(new BorderLayout());
		add(panelAbajo, BorderLayout.SOUTH);
		configurarTitulo();
		configurarTablaDias();
		configurarTablaVehiculos();
	}

}
