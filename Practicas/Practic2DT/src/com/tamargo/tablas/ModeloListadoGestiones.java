package com.tamargo.tablas;

import com.tamargo.GestionEntity;
import org.hibernate.ObjectNotFoundException;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ModeloListadoGestiones extends AbstractTableModel {

    private final String[] columnas = {"Proveedor", "Pieza", "Proyecto", "Cantidad"};
    private final ArrayList<GestionEntity> gestiones;

    public ModeloListadoGestiones(ArrayList<GestionEntity> gestiones) {
        this.gestiones = gestiones;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        GestionEntity gestion = gestiones.get(rowIndex);

        try {
            return switch (columnIndex) {
                case 0 -> gestion.getProveedoresByCodProveedor().getCodigo();
                case 1 -> gestion.getPiezasByCodPieza().getCodigo();
                case 2 -> gestion.getProyectosByCodProyecto().getCodigo();
                case 3 -> String.valueOf(gestion.getCantidad());
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
        return gestiones.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

}
