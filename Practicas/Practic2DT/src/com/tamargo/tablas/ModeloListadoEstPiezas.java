package com.tamargo.tablas;

import com.tamargo.PiezasEntity;
import com.tamargo.util.CargarDatos;
import org.hibernate.ObjectNotFoundException;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModeloListadoEstPiezas extends AbstractTableModel {

    private final String[] columnas = {"Cod", "Nombre", "Precio",
            "Nº Proveedores", "Cantidad total", "Nº Proyectos"};
    private final ArrayList<PiezasEntity> piezas;

    public ModeloListadoEstPiezas(ArrayList<PiezasEntity> piezas) {
        this.piezas = piezas;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PiezasEntity pieza = piezas.get(rowIndex);
        float[] datosPieza = CargarDatos.piezaDatosListadoEstadisticas(pieza.getCodigo());
        try {
            return switch (columnIndex) {
                case 0 -> pieza.getCodigo();
                case 1 -> pieza.getNombre();
                case 2 -> String.format("%.2f", pieza.getPrecio());
                case 3 -> String.format("%.0f", datosPieza[0]);
                case 4 -> String.format("%.2f", datosPieza[1]);
                case 5 -> String.format("%.0f", datosPieza[2]);
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
        return piezas.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

}