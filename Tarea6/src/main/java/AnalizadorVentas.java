import javax.swing.*;
import java.io.*;
import java.util.*;

public class AnalizadorVentas {
    public static void main(String[] args) {
        // Abrir cuadro de diálogo para seleccionar archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo CSV de ventas");

        int resultado = fileChooser.showOpenDialog(null);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            System.out.println("No se seleccionó ningún archivo.");
            return;
        }

        File archivo = fileChooser.getSelectedFile();
        double totalVendido = 0;
        Map<String, Double> ventasPorVendedor = new HashMap<>();
        Map<String, Integer> ventasPorProducto = new HashMap<>();
        int totalRegistros = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine(); // saltar encabezado

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String vendedor = datos[1];
                String producto = datos[2];
                int cantidad = Integer.parseInt(datos[3]);
                double total = Double.parseDouble(datos[4]);

                totalVendido += total;
                totalRegistros++;

                // acumular ventas por vendedor
                ventasPorVendedor.put(vendedor,
                        ventasPorVendedor.getOrDefault(vendedor, 0.0) + total);

                // acumular cantidad por producto
                ventasPorProducto.put(producto,
                        ventasPorProducto.getOrDefault(producto, 0) + cantidad);
            }

            // vendedor con mayores ventas
            String mejorVendedor = Collections.max(ventasPorVendedor.entrySet(),
                    Map.Entry.comparingByValue()).getKey();

            // producto más vendido
            String productoMasVendido = Collections.max(ventasPorProducto.entrySet(),
                    Map.Entry.comparingByValue()).getKey();

            // resultados
            System.out.println("Total vendido: $" + totalVendido);
            System.out.println("Vendedor con mayores ventas: " + mejorVendedor);
            System.out.println("Venta promedio: $" + (totalVendido / totalRegistros));
            System.out.println("Producto más vendido: " + productoMasVendido);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
