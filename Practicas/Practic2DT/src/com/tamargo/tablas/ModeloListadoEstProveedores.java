package com.tamargo.tablas;

import com.tamargo.ProveedoresEntity;
import com.tamargo.util.CargarDatos;
import org.hibernate.ObjectNotFoundException;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModeloListadoEstProveedores extends AbstractTableModel {

    private final String[] columnas = {"Cod", "Nombre", "Apellidos",
            "Nº Piezas", "Cantidad total", "Nº Proyectos"};
    private final ArrayList<ProveedoresEntity> proveedores;

    public ModeloListadoEstProveedores(ArrayList<ProveedoresEntity> proveedores) {
        this.proveedores = proveedores;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProveedoresEntity proveedor = proveedores.get(rowIndex);
        float[] datosProveedor = CargarDatos.proveedorDatosListadoEstadisticas(proveedor.getCodigo());
        try {
            return switch (columnIndex) {
                case 0 -> proveedor.getCodigo();
                case 1 -> proveedor.getNombre();
                case 2 -> proveedor.getApellidos();
                case 3 -> String.format("%.0f", datosProveedor[0]);
                case 4 -> String.format("%.2f", datosProveedor[1]);
                case 5 -> String.format("%.0f", datosProveedor[2]);
                default -> "";
            };
        } catch (NullPointerException | IllegalArgumentException | ObjectNotFoundException ignored) {
            return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public int getRowCount() {
        return proveedores.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

}