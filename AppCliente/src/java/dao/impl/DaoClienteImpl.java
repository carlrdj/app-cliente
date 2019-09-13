/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dao.DaoCliente;
import db.ConnectDB;
import dto.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author carlr
 */
public class DaoClienteImpl implements DaoCliente{
    private final ConnectDB db;
    private final StringBuilder sql;

    public DaoClienteImpl() {
        db = new ConnectDB();
        sql = new StringBuilder();
    }

    @Override
    public List<Cliente> obtenerClientes() {
        sql.delete(0, sql.length());
        sql.append("SELECT vc_nombre, vc_apellido, vc_dni, vc_direccion, ch_habilitar FROM t_cliente");
        
        try (
                // Almacenar conexión en el objeto "cn"
                Connection cn = db.getConnection();
                // Preparar el scrip a ejecutar "ps"
                PreparedStatement ps = cn.prepareCall(sql.toString());
                // Ejecutar el script y almacenar resultado en "rs"
                ResultSet rs = ps.executeQuery();
                ){
            List<Cliente> listarCliente = new LinkedList<>();
            
            while (rs.next()) {                
                // Instancia la clase cliente
                Cliente cliente = new Cliente();
                // Setear datos
                cliente.setNombre(rs.getString("vc_nombre"));
                cliente.setApellido(rs.getString("vc_apellido"));
                cliente.setDni(rs.getString("vc_dni"));
                cliente.setDireccion(rs.getString("vc_direccion"));
                cliente.setHabilitar(rs.getString("ch_habilitar"));
                
                listarCliente.add(cliente);                
            }
            
            return listarCliente;            
            
        } catch (Exception e) {
            return null;
        }
        
    }

    @Override
    public Cliente obtenerCliente(String dni) {
        sql.delete(0, sql.length());
        sql.append("SELECT vc_nombre, vc_apellido, vc_dni, vc_direccion, ch_habilitar"
                + " FROM t_cliente"
                + " WHERE vc_dni = ?");
        
        
        try (
                Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareCall(sql.toString());
                ){
            
            
            ps.setString(1, dni);
            
            try (ResultSet rs = ps.executeQuery()){
                
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setNombre(rs.getString("vc_nombre"));
                    cliente.setApellido(rs.getString("vc_apellido"));
                    cliente.setDni(rs.getString("vc_dni"));
                    cliente.setDireccion(rs.getString("vc_direccion"));
                    cliente.setHabilitar(rs.getString("ch_habilitar"));
                    
                    return cliente;
                } else {
                    return null;
                }
                
                
            } catch (Exception e) {
                return null;
            }
            
            
            
            
        } catch (Exception e) {
            return null;
        }
        
    }

    @Override
    public boolean insertarCliente(Cliente cliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarCliente(Cliente cliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean habilitarCliente(String dni, String estado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
